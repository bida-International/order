<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<script type="text/javascript"  src="My97DatePicker/WdatePicker.js"></script>
<m:frame>
<s:form action="admin!importYunFei.do" method="post" theme="simple">
<table border="1px" width="500px">
	<tr>
	<td>姓名：</td>
		<td>
			<select name="guke">
			<s:iterator value="getGuKeAll()" var="i">
				<option value="${i.userid}">${i.name}</option>
			</s:iterator>
			</select>
		</td>
		<td>完成时间：</td>
		<td>
			<input type="text" name="time" id="time" onfocus="WdatePicker()" value="${param.time}"/>
		</td>
	</tr>
	<tr>
		<td><s:submit value="提交"></s:submit></td>
	</tr>
</table>
</s:form>
</m:frame>