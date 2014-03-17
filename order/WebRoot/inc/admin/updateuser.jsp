<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<script type="text/javascript">
<!--

//-->
function as()
{
	var pwd = document.getElementById("pwd").value;
	var pwds = document.getElementById("pwds").value;
	if(pwd != pwds)
	{
		alert("输入密码不一致");
		document.getElementById("pwd").value = "";
		document.getElementById("pwds").value = "";
		document.getElementById("pwd").focus();
		return false;
	}
}
</script>
<m:frame>
${msg}
<s:form name="form" action="admin!update_pwd.do" method="post" onsubmit="return as()" theme="simple">
<table border="1" height="150" width="400" cellpadding="0" cellspacing="0" class="datagrid2" id="list">
	<tr><td class="tableBorder_B"  align="right">修改密码:</td></tr>
	<tr>
		<td align="right">用户名:</td>
		<td><input type="text" name="username" value="${logininfo.account}" readonly="readonly"></td>
	</tr>
	<tr>
		<td align="right">请输入原密码:</td>
		<td><s:password name="oldpwd"id="oldpwd"/></td>
	</tr>
	<tr>
		<td  align="right">请输入新密码:</td>
		<td><s:password name="user.pwd" id="pwd"/></td>
	</tr>
	<tr>
		<td  align="right">输入确认密码:</td>
		<td><s:password name="repwd" id="pwds"/></td>
	</tr>	
	<tr>
		<td colspan="2"><s:submit value="保存" cssStyle="cursor:hand"></s:submit> </td>
	</tr>
</table>	
</s:form>
</m:frame>
