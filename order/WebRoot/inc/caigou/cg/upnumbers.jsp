<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<m:frame>
<form action="caigou!updatekucuns.do?ordertable.id=${updateIds.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}" method="post">
  <input type="hidden" name="ordertable.caigouyuan" value="${updateIds.caigouyuan}"/>
  <table border="1"  class="datagrid2" id="list" width="300px" cellspacing="0" style="float:left">
    <tr>
      <td colspan="4" align="center"><strong>修改订单</strong></td>
    </tr>
    <tr>
     <td width="54" align="right"><span class="STYLE2">订单号</span></td>
      <td><input name="ordertable.orderId" type="text" id="orderId" size="12" title="订单号" value="${updateIds.orderId}" readonly="readonly"></td>
	</tr>
	 <tr>
     <td width="54" align="right"><span class="STYLE2">数量</span></td>
      <td><input name="ordertable.num" type="text" id="num" size="12" title="数量" value="${updateIds.num}"></td>
	</tr>
	<tr>
     <td width="54" align="right"><span class="STYLE2">备注</span></td>
      <td><textarea rows="5" cols="10" name="ordertable.remark" id="remark" title="备注">${updateIds.remark}</textarea></td>
	</tr>
	<tr>
	 <td colspan="2" align="center">
         <input type="submit" id="t" value="保存">
          </td>
	</tr>
	</table>
</form>
</m:frame>