<%@ page language="java" import="java.util.*" pageEncoding="utf-8"
	contentType="application/excel"
%>
<%
	//设置下载的文件名:user.xls
	response.setHeader("Content-Disposition","attachment;filename="+"xxx.xls");
%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="this is my page">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  </head>
  <body>

<table>
	<tr>
		<td>账号</td>
		<td>订单号</td>
		<td>运输单号</td>
		<td>供运商 </td>
		<td>备注</td>
	</tr>
	<s:iterator value="orders" var="item">
	<tr>
		<td>
	     	<s:property value="getZhangHaoId(#item.zhanghaoId)"/>
		</td>
		<td>${item.orderId}</td>
		<td>${item.danhao}</td>
		<td>${item.gongyunshang}</td>
		<td>${item.remark}</td>
	</tr>
	</s:iterator>
</table></body></html>