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
${msg}
  <form action="guke!updatedaifahuo.do?orderTable.id=${updateId.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}" method="post" id="myform" onsubmit="return as()">
  <table border="1" cellspacing="0" width="900px" style="float:left">
    <tr>
      <td colspan="4" align="center"><strong>修改订单</strong></td>
    </tr>
    <tr>
     <td width="54" align="right"><span class="STYLE2">订单号</span></td>
      <td><input name="orderTable.orderId" type="text" id="orderId" size="12" title="订单号" value="${updateId.orderId}"></td>
	</tr>
	 <tr>
     <td width="100" align="right"><span class="STYLE2">国内运输方式</span></td>
      <td><s:select list="loaditems('GuoNeiKuaiDi','name','id')"  name="orderTable.guoneikuaidiId" value="%{updateId.guoneikuaidiId}"></s:select></td>
	</tr>
	 <tr>
     <td width="100" align="right"><span class="STYLE2">国内运输单号</span></td>
      <td><input name="orderTable.guoneidanhao" type="text" id="guoneidanhao" size="12" title="国内运输单号" value="${updateId.guoneidanhao}"></td>
	</tr>
	<tr>
		<td align="right">国家</td>
		<td>
			<select name="orderTable.guojiaId">
		    		<option value="">-请选择-</option>
		    		<s:iterator value="getAllGuoJia()" var="i">
		    		<option value="${i.id}">${i.guojia}</option>
		    		</s:iterator>
		    </select>
	    </td>
	</tr>
	 <tr>
     <td width="54" align="right"><span class="STYLE2">物品</span></td>
      <td><textarea name="orderTable.wuping" id="wuping" title="物品">${updateId.wuping}</textarea></td>
	</tr>
	 <tr>
     <td width="54" align="right"><span class="STYLE2">地址</span></td>
      <td><textarea name="orderTable.guowaidizhi" title="国内地址">${updateId.guowaidizhi}</textarea></td>
	</tr>
	 <tr>
     <td width="54" align="right"><span class="STYLE2">备注</span></td>
      <td><textarea name="orderTable.remark" title="备注">${updateId.remark}</textarea></td>
	</tr>
    <tr>    
        <td colspan="8" align="center">
         <input type="submit" value="保存 ">   
          </td>
    </tr>
  </table>
  <br>
  </form>
</m:frame>