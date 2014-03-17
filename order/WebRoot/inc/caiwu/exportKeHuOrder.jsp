<%@ page language="java" import="java.util.*" pageEncoding="utf-8"
	contentType="application/excel"
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
		<td>重量</td>
		<td>地址</td>
		<td>运输单号</td>
		<td>运费</td>
		<td>完成时间</td>
	</tr>
	<s:iterator value="orders" var="item">
	<tr>
		<td><s:property value="getZhangHaoId(#item.zhanghaoId)"/></td>
		<td>${item.zhongliang}</td>
		<td>${item.guowaidizhi}</td>
		<td>${item.danhao}</td>
		<td><fmt:formatNumber value="${item.yunfei}" pattern="0.00"></fmt:formatNumber></td>
		<td>${item.wanchengtime}</td>
	</tr>
	</s:iterator>
</table></body></html>