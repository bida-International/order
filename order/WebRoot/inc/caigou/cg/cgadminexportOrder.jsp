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

<table >
	   <tr align="center">
     <td width="54"><span class="STYLE2">纠纷日期</span></td>
       <td width="90"><span class="STYLE2">订单号</span></td>
     <td width="163"><span class="STYLE2">金额</span></td>
      <td width="130"><span class="STYLE2">运输单号</span></td>
      <td width="130"><span class="STYLE2">编码</span></td>
       <td width="100"><span class="STYLE2">供运商</span></td>
        <td width="163"><span class="STYLE2">退款</span></td>
        <td width="100"><span class="STYLE2">备注</span></td>
        <td width="163"><span class="STYLE2">是否纠纷</span></td>
       <td width="100"><span class="STYLE2">账号</span></td>
    </tr>
	<s:iterator value="orders" var="item">
	 <tr align="center" style="font-size:15px">   
	          <td>${item.jiufentime}</td>
	          <td>${item.orderId}</td>
	   		 <td>${item.money}</td>	  
	   		<td>${item.danhao}</td>     
	   		<td>${item.bianma}</td>    
	   		 <td>${item.gongyunshang}</td> 
	   		 <td>${item.tuikuan}</td>
		     <td>${item.remark}</td>
		     <td>${item.jiufen}</td>	    
	   		  <td><s:property value="getZhangHaoId(#item.zhanghaoId)"/></td>
	     </tr> 
	</s:iterator>
</table></body></html>
