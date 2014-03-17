<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<script type="text/javascript"  src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
<!--

//-->
 function myshow()
	{
		var value = document.getElementById("div1").style.display;
		if(value=="none")
		{
			document.getElementById("div1").style.display="block";
				
		}
		else{
			document.getElementById("div1").style.display="none";
		}
	}
</script>
<m:frame>
<s:form action="caiwu.do" method="post" theme="simple">
<table border="1" width="200px">
	<tr>
	<td>姓名：</td>
	</tr>
	<tr>
		<td>
			<select name="guke">
			<s:iterator value="getGuKeAll()" var="i">
				<option value="${i.userid}">${i.name}</option>
			</s:iterator>
			</select>
		</td>
	</tr>
	<tr>
		<td>完成时间：</td>
		</tr>
		<tr>
		<td>
		<input type="radio" name="shijian" value="0" checked="checked" onclick="myshow()"/>一天
		<input type="radio" name="shijian" value="1" onclick="myshow()"/>一个时间段
		</td>
		</tr>
		<tr>
		<td>
			<input type="text" name="time" id="time" onfocus="WdatePicker()" value="${param.time}"/>
			<div style="display:none" id="div1">至
			<input type="text" name="time1" id="time1" onfocus="WdatePicker()" value="${param.time1}"/>
			</div>
		</td>
	</tr>
	<tr>
		<td><input type="submit" value="提交 " align="middle" style = "cursor:hand"/></td>
	</tr>
</table>
</s:form>
</m:frame>