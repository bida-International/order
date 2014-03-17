<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<script type="text/javascript"  src="My97DatePicker/WdatePicker.js"></script>
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
	function date(){
		var mydateInput = document.getElementById("mydate");
		var date = new Date();
		var dateString = date.getFullYear()+"/"+(date.getMonth()+1)+"/"+date.getDate();
		mydateInput.value=dateString;
	}
	</script>
<m:frame>
<body>
${msg}
  <form action="caigouadmin!cgadminupdaifahuo.do?ordertable.id=${cgUpOrderId.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}" method="post" id="myform" onsubmit="return as()">
  <input type="hidden" name="ordertable.caigouyuan" value="${cgUpOrderId.caigouyuan}"/>
  <table  class="datagrid2" id="list" border="1" width="600px" cellspacing="0" style="float:left">
    <tr>
      <td colspan="5" align="center"><strong>修改订单</strong></td>
    </tr>
    <tr>
     <td width="60" align="right"><span class="STYLE2">订单号</span></td>
      <td><input name="ordertable.orderId" type="text" id="orderId" size="12" title="订单号" value="${cgUpOrderId.orderId}" readonly="readonly"></td>
	</tr>
	<tr>
     	<td width="90" align="right"><span class="STYLE2">快递方式</span></td>
      	<td>
      	<s:select list="loaditems('KuaiDiFangShi','name','id')"  name="ordertable.kuaidifangshiId" value="%{cgUpOrderId.kuaidifangshiId}"></s:select>
	    </td>      
	 </tr>
	 <tr>
     <td width="80" align="right"><span class="STYLE2">货款</span></td>
      <td><input name="ordertable.huokuan" type="text" id="huokuan" size="12" title="货款" value="${cgUpOrderId.huokuan}"></td>
	</tr>
	 <tr>
      <td width="54" align="right"><span class="STYLE2">供应商</span></td>
      <td><textarea name="ordertable.gongyunshang" id="gongyunshang" title="供应商">${cgUpOrderId.gongyunshang}</textarea></td>
	</tr>
	<tr>
     <td width="80" align="right"><span class="STYLE2">哪里采购</span></td>
  	  
      <td> 
      <s:select list="loaditems('GuoNeiWangZhan','name','id')"  name="ordertable.guoneiwangzhanId" value="%{cgUpOrderId.guoneiwangzhanId}"></s:select>
	    </td>      
	</tr>
	 <tr>
     <td width="80" align="right"><span class="STYLE2">国外地址</span></td>
      <td><textarea name="ordertable.guowaidizhi" cols="40" rows="9" id="guowaidizhi" title="国外地址">${cgUpOrderId.guowaidizhi}</textarea></td>
	</tr>
	 <tr>
     <td width="40" align="right"><span class="STYLE2">物品</span></td>
      <td><textarea name="ordertable.wuping" id="wuping" title="物品">${cgUpOrderId.wuping}</textarea></td>
	</tr>
	<tr>
     <td width="80" align="right"><span class="STYLE2">采购时间</span></td>
      <td><input type="text" name="ordertable.caigoutime" id="mydate" onfocus="WdatePicker()" title="采购时间" value="${cgUpOrderId.caigoutime}"/></td>
	</tr>
	<tr>
     <td width="90" align="right"><span class="STYLE2">速卖通地址</span></td>
      <td><input name="ordertable.guojia" type="text" id="guojia" size="12" title="速卖通地址" value="${updateIds.guojia}"></td>
	</tr>
	 <tr>
     <td width="90" align="right"><span class="STYLE2">运输单号</span></td>
      <td><input name="ordertable.danhao" type="text" id="danhao" size="12" title="运输单号" value="${cgUpOrderId.danhao}"></td>
	</tr>
	<tr>
      <td width="54" align="right"><span class="STYLE2">备注</span></td>
      <td><textarea name="ordertable.remark" id="remark" title="备注">${cgUpOrderId.remark}</textarea></td>
	</tr>
    <tr>    
        <td colspan="5" align="center">
         <input type="submit" value="保存 ">   
          </td>
    </tr>
  </table>
  <br>
  </form>
  </body>
</m:frame>