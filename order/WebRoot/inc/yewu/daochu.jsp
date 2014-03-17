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
	   <tr align="center">
     <td width="100"><span class="STYLE2">日期</span></td>
      <td width="100"><span class="STYLE2">订单号</span></td>
      <td width="100"><span class="STYLE2">运费</span></td>
      <td width="100"><span class="STYLE2">国际运输单号</span></td>
       <td width="100"><span class="STYLE2">账号</span></td>
      <td width="100"><span class="STYLE2">采购员</span></td>
    </tr>
	<s:iterator value="orders" var="item">
	<tr>
		<td>${item.time}</td>
        <td>${item.orderId}</td>
	    <td>${item.yunfei}</td>
	    <td>${item.danhao}</td>
	    <td>
	     	<s:property value="getZhangHaoId(#item.zhanghaoId)"/>
	    </td>
	   	<td><s:property value="#item.caigouyuan==0?('未分配'):(getUserId(#item.caigouyuan))"/></td>
	</tr>
	</s:iterator>
</table></body></html>