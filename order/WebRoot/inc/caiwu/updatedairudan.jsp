 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
  <script type="text/javascript" src="js/jquery-1.8.2.js"></script>
  
	<script type="text/javascript">
	function onlyNumber(obj){
   
      //得到第一个字符是否为负号
      var t = obj.value.charAt(0);  
      //先把非数字的都替换掉，除了数字和. 
      obj.value = obj.value.replace(/[^\d\.]/g,'');   
       //必须保证第一个为数字而不是.   
       obj.value = obj.value.replace(/^\./g,'');   
       //保证只有出现一个.而没有多个.   
       obj.value = obj.value.replace(/\.{2,}/g,'.');   
       //保证.只出现一次，而不能出现两次以上   
       obj.value = obj.value.replace('.','$#$').replace(/\./g,'').replace('$#$','.');      
	}	
	function as(){
		var zhongliang = document.getElementById("zhongliang").value;
		var danhao = document.getElementById("danhao").value;
		var usertype = document.getElementById("updanhao");
		if(usertype.checked == true){
			if(danhao == ''){
				alert("运输单号未填写");
				return false;
			}
			if(zhongliang == ''){
				alert("重量不能为空 ");
				return false;
			}
		}
	}
	function fanhui(){
		window.location = "yewu!getDaiRuDan.do?pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
	}
function addRowToTable()
{
var tbl = document.getElementById('mytable');
var lastRow = tbl.rows.length;
var iteration = lastRow;
var row = tbl.insertRow(lastRow);
var usertype = document.getElementById("updanhao");
if(usertype.checked == false){
	alert("修改运输单号被选中、请先操作完成在进行其他操作");
	return false;
}
var cellRight = row.insertCell(0);
var el = document.createElement('input');
el.type = 'text';
el.name = 'orderIds';
el.id = 'orderIds';
el.size = 15;
el.value = '${updateIds.orderId}'
el.onkeypress = keyPressTest;
cellRight.appendChild(el);
var cellRight = row.insertCell(1);
var el = document.createElement('input');
el.type = 'text';
el.name = 'danhao';
el.id = 'danhao';
el.size = 15;
el.onkeypress = keyPressTest;
cellRight.appendChild(el);
var cellRight = row.insertCell(2);
var el = document.createElement('input');
el.type = 'text';
el.name = 'guojiaId';
el.id = 'guojiaId';
el.size = 15;
el.onkeypress = keyPressTest;
cellRight.appendChild(el);
// select cell
var cellRightSel = row.insertCell(3);
var sel = document.createElement('select');
sel.name = 'type';
sel.options[0] = new Option('小包(kg)', '0');
sel.options[1] = new Option('其他(kg)', '1');
sel.options[2] = new Option('E邮宝(g)', '2');
cellRightSel.appendChild(sel);
var cellRight = row.insertCell(4);
var el = document.createElement('input');
el.type = 'text';
el.name = 'zhongliang';
el.id = 'zhongliang';
el.size = 15;
el.onkeypress = keyPressTest;
cellRight.appendChild(el);
var cellRight = row.insertCell(5);
var el = document.createElement('input');
el.type = 'text';
el.name = 'yunfei';
el.id = 'yunfei';
el.size = 15;
el.onkeypress = keyPressTest;
cellRight.appendChild(el);
}
function keyPressTest(e, obj)
{
var validateChkb = document.getElementById('chkValidateOnKeyPress');
if (validateChkb.checked) {
var displayObj = document.getElementById('spanOutput');
var key;
if(window.event) {
key = window.event.keyCode;
}
else if(e.which) {
key = e.which;
}
var objId;
if (obj != null) {
objId = obj.id;
} else {
objId = this.id;
}
displayObj.innerHTML = objId + ' : ' + String.fromCharCode(key);
}
}
	function removeRowFromTable()
	{
		var tbl = document.getElementById('mytable');
		var lastRow = tbl.rows.length;
		if (lastRow > 2) tbl.deleteRow(lastRow - 1);
	}
	</script>
<m:frame>
${msg}
  <form action="caiwu!updatedairudan.do?ordertable.id=${updateIds.id}&dengluId=${updateIds.dengluId}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}" name="myform" method="post" id="myform" onsubmit="return as()">

  <p>
  		<input type=button value="增加" onclick="addRowToTable();" style="cursor: pointer;">
          <input type=button value="删除" onclick="removeRowFromTable();" style="cursor: pointer;"><br/>
         <input type="submit" id="t" value="保存" style="cursor: pointer;"><br/> <a href="caiwu!getDaiRuDan.do?pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}&danhao=${param.danhao}">返回</a>   
  </p>
   <table border="1" cellspacing="0" style="float:left"  id="mytable">
    <tr>
      <td colspan="6" align="center"><strong>修改订单<font size="2" color="blue">(小包被选中运费不需要填写，否则需要填写)</font></strong></td>
    </tr>
    <tr>
    	<td colspan="2" align="right"><font size="2">修改运输单号:</font></td>
    	<td colspan="4">
    		<input type="radio" name="usertype" id="updanhao" value="0" checked="checked">否 
         	<input type="radio" name="usertype" id="updanhao" value="1">是
         </td>
    </tr>
    <tr align="center">
      <td width="120"><span class="STYLE2">订单号</span></td>
      <td width="100"><span class="STYLE2">国际运输单号</span></td>
      <td width="120"><span class="STYLE2">国家</span></td>
       <td width="120"><span class="STYLE2">快递</span></td>
      <td width="100"><span class="STYLE2">重量</span></td>
       <td width="100"><span class="STYLE2">运费 </span></td>
    </tr>
      <tr>
        <td><input name="orderIds" type="text" id="orderIds" title="订单号" value="${updateIds.orderId}" size="15"></td>
	    <td> <input type="text" name="danhao" id="danhao" title="国际运输单号" value="${updateIds.danhao}" size="15"/></td>
	    <td>
			<input type="text" name="guojiaId" title="国家" value="${updateIds.country}" id="guojiaId" size="15"/>
	    </td>
	    <td>
	    	<select name="type" id="type">
	    		<option value="0">小包(kg)</option>
	    		<option value="1">其他(kg)</option>
	    		<option value="2">E邮宝(g)</option>
	    	</select>
	    </td>
	    <td><input type="text" title="重量" name="zhongliang" value="${updateIds.zhongliang}" id="zhongliang" size="15"/> </td>	
	     <td><input name="yunfei" type="text" id="yunfei" title="运费" value="${updateIds.yunfei}" size="15"/></td>     
     </tr>    
  </table>
  <s:iterator value="#insert" var="d">
  	${d}<br>
  </s:iterator>
  <br>
  </form>
</m:frame>