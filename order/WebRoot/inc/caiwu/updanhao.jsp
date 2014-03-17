<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<m:frame>
<form action="caiwu!updatedanhao.do?ordertable.id=${updateIds.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}" method="post">
 
  <table border="1" cellspacing="0" style="float:left">
    <tr>
      <td colspan="4" align="center"><strong>修改订单</strong></td>
    </tr>
    <tr>
     <td width="54" align="right"><span class="STYLE2">订单号</span></td>
      <td><input name="ordertable.orderId" type="text" id="orderId" size="12" title="订单号" value="${updateIds.orderId}" readonly="readonly"></td>
	</tr>
	 <tr>
     <td width="54" align="right"><span class="STYLE2">运输单号</span></td>
      <td><input name="ordertable.danhao" type="text" id="danhao" size="12" title="运输单号" value="${updateIds.danhao}"></td>
	</tr>
	<tr>
	 <td colspan="2" align="center">
         <input type="submit" id="t" value="保存">
          </td>
	</tr>
	</table>
</form>
</m:frame>