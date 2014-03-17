<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<m:frame>
<form action="caigou!myupzhanghao.do?ordertable.id=${cgUpOrderId.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}" method="post">
  <input type="hidden" name="ordertable.caigouyuan" value="${cgUpOrderId.caigouyuan}"/>
  <table border="1"  class="datagrid2" id="list" width="200px" cellspacing="0" style="float:left">
    <tr>
      <td colspan="4" align="center"><strong>修改账号 </strong></td>
    </tr>
    <tr>
     <td width="54" align="right"><span class="STYLE2">订单号</span></td>
      <td><input name="ordertable.orderId" type="text" id="orderId" size="12" title="订单号" value="${cgUpOrderId.orderId}" readonly="readonly"></td>
	</tr>
	 <tr>
     <td width="54" align="right"><span class="STYLE2">账号</span></td>
      <td><s:select list="loaditems('ZhangHao','name','id')"  name="ordertable.zhanghaoId" value="%{cgUpOrderId.zhanghaoId}"></s:select></td>
	</tr>
	<tr>
	 <td colspan="2" align="center">
         <input type="submit" id="t" value="保存">
          </td>
	</tr>
	</table>
</form>
</m:frame>