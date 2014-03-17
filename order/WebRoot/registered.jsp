<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>
<base href="<%=basePath%>">
<title>用户注册</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<link href="./CSS/style.css" rel="stylesheet">
 <script type="text/javascript">     
    function Login()
    {
     var username=document.getElementById("username").value;
     var pwd=document.getElementById("pwd").value;   
      var pwds=document.getElementById("pwds").value;
     var phone=document.getElementById("phone").value;  
      var QQ=document.getElementById("QQ").value;
     var name=document.getElementById("name").value;  
     if(username == ''){
     	alert("用户名必须填写 ");
     	return false;
     }
     if(pwd == ''){
     	alert("密码必须填写");
     	return false;
     }
     if(pwd.length < 6){
     	alert("密码长度不能小于6位");
     	return false;
     }
     if(pwd.length != pwds.length ){
     	alert("密码长度不一致");
     	return false;
     }
     if(phone == ''){
     	alert("手机号码必须填写 ");
     	return false;
     }   
     if(QQ == ''){
     	alert("QQ号码必须填写 ");
     	return false;
     }
     if(name == ''){
     	alert("姓名必须填写");
     	return false;
     }
    }
    </script>
</head>
<body>
${msg}
<s:form action="system!zuche.do" method="post" onsubmit="return Login()">
 <table border="1" height="100" width="300" cellpadding="0" cellspacing="0" class="tableBorder">
 <tr><td colspan="2" align="center">用户注册</td></tr>
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
		<td  align="right">联系电话:</td>
		<td><s:textfield name="phone" id="phone"/></td>
	</tr>
	<tr>
		<td  align="right">QQ:</td>
		<td><s:textfield name="QQ" id="QQ"/></td>
	</tr>
	<tr>
		<td  align="right">姓名:</td>
		<td><s:textfield name="name" id="name"/></td>
	</tr>	
	<tr>
		<td colspan="2" align="center"><s:submit value="创建账号" style="cursor:hand"/></td>
	</tr>
</table>	
</s:form>
</body>
</html>
