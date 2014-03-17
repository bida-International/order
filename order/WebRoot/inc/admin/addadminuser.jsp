<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<script language="javascript" type="text/javascript">
	function as()
	{
		var username = document.getElementById("username").value;
		var pwd = document.getElementById("pwd").value;
		var pwds = document.getElementById("pwds").value;	
		if(username == "")
		{
			alert("请输入账号");
			document.getElementById("username").focus();
			return false;
		}
		if(pwd == "")
		{
			alert("请输入密码");
			document.getElementById("pwd").value="";
			document.getElementById("pwd").focus();
			return false;
		}
		if(pwds != pwd)
		{
			alert("密码不一致");
			document.getElementById("pwds").value="";
			return false;
		}
	}
	</script>
<m:frame>
${msg}
<s:form name="form" action="admin!add_user.do" method="post" onsubmit="return as()" theme="simple">
<table border="1"  class="datagrid2" id="list" height="150" width="500" cellpadding="0" cellspacing="0">
	<tr>
		<td class="tableBorder_B"  align="right">用户类型:</td>
		<td class="tableBorder_B" ><s:radio list="#{0:'业务助理',1:'采购',2:'采购管理员',3:'仓库员',4:'客户',5:'财务',6:'业务',7:'美工',8:'技术员'}" id="usertype" name="usertype" onclick="stu(this)" value="0" /></td>
	</tr>
	<tr>
		<td align="right">帐号:</td>
		<td><s:textfield name="user.username"id="username"/></td>
	</tr>
	<tr>
		<td  align="right">密码:</td>
		<td><s:password name="user.pwd" id="pwd"/></td>
	</tr>
	<tr>
		<td  align="right">确认密码:</td>
		<td><s:password name="pwds" id="pwds"/></td>
	</tr>
	<tr>
		<td  align="right">姓名:</td>
		<td><s:textfield name="name" id="name"/></td>
	</tr>	
	<tr>
		<td colspan="2"><s:submit align="center" style="cursor:hand" value="创建账号"></s:submit></td>
	</tr>
</table>	
</s:form>
</m:frame>
