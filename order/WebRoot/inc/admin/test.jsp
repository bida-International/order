<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>无标题页</title>
    
</head>
<body>
<form method="post" action="http://www.bidaship.com/api/upload.trno.php">
<textarea name="data"><s:iterator begin="1" end="2" value="allOrders" var="i">${i.orderId},${i.danhao},<s:if test="#i.kuaidifangshiId==1">0</s:if><s:if test="#i.kuaidifangshiId==8">100001</s:if><s:if test="#i.kuaidifangshiId==9">100003</s:if><s:if test="#i.kuaidifangshiId==null">NULL</s:if>,0;</s:iterator></textarea>
<input type="submit" value="提交"/>
</form>
</body>
</html>