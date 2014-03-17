<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
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
	</script>
<m:frame>
  <form action="caigouadmin!addorder.do" method="post" id="myform" onsubmit="return as()">
  <table border="1"  class="datagrid2" id="list" cellspacing="0" style="float:left">
    <tr>
      <td colspan="5" align="center"><strong>上传订单</strong></td>
    </tr>
    <tr align="center">
      <td width="163"><span class="STYLE2">订单号</span></td>
      <td width="54"><span class="STYLE2">金额</span></td>
      <td width="163"><span class="STYLE2">类目</span></td>
      <td width="163"><span class="STYLE2">备注</span></td>
      <td width="64"><span class="STYLE2">选择帐号</span></td>
    </tr>
    <s:iterator begin="1" end="5" status="t">
      <tr align="center">
        <td><input name="dingdan" type="text" id="dingdan" title="订单号" style="ime-mode:disabled" onkeydown="if(event.keyCode==13)event.keyCode=9" onkeyup="value=value.replace(/[^\d]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"></td>
	    <td><input name="jine" type="text" id="jine" size="7" title="金额" style="ime-mode:disabled" onkeyup="onlyNumber(this)" onblur="onlyNumber(this)" onKeyPress="if((event.keyCode<48 || event.keyCode>57) && event.keyCode!=46 || /\.\d\d$/.test(value))event.returnValue=false"></td>
	    <td><input name="yunshu" type="text" id="yunshu" title="类目"/></td>
	    <td><textarea name="remark" title="备注"></textarea> </td>	 
	      <td><select name="zhanghaoId"> 
	      	<option value="0">-请选择-</option>
	      	<s:iterator value="getZhangHao()" id = "i">
	      	<option value="${i.id}">${i.name}</option>
	      </s:iterator>
	      </select> </td>       
     </tr>    
   </s:iterator>
    <tr>    
        <td colspan="5" align="center">
         <input type="submit" id="t" value=" 添 加 ">
         <input type="reset" name="Submit2" value="重置">
         <a href="admin!addzh.do">添加账号</a>      
          </td>
    </tr>
  </table>
  <s:iterator value="#insert" var="d">
  	${d }<br>
  </s:iterator>
  <br>
  </form>
</m:frame>