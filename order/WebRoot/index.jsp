<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>
<base href="<%=basePath%>">
<title>后台登录</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<link href="./CSS/style.css" rel="stylesheet">
<script language="javascript">     
    function Login()
    {
     var uname=document.getElementById("username").value;
     var pwd=document.getElementById("pwd").value;    
      if(trim(uname) == ''){
        alert("请输入用户名");
        return false;
      }
      if(trim(pwd) == ''){
      	alert("请输入密码");
      	return false;
      }
    }

</script>
</head>
<body>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td bgcolor="#EEEEEE"><table width="464" height="294" border="0" align="center" cellpadding="0" cellspacing="0" background="./images/m_login.jpg">
      <tr>
        <td width="157" height="153">&nbsp;</td>
        <td width="307" align="left" valign="bottom">&nbsp; ${denglu} ${msg}</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td valign="top">
			<s:form action="system!login.do" method="post"  name="managerForm" onsubmit="return login()" theme="simple">
                 <table width="100%"  border="0" cellpadding="0" cellspacing="0" bordercolorlight="#FFFFFF" bordercolordark="#D2E3E6">
	                    <tr>
	                     <td width="20%" height="30">帐号：</td>
	                 	 <td width="80%"><s:textfield name="username" id="username" size="25"/></td>
                   </tr>
	                   <tr>
	                     <td height="30">密码：</td>
	                	 <td><s:password name="pwd" id="pwd" size="25"/></td>
	                   </tr>
	                   <tr>
	                     <td height="33" colspan="2" align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                     <s:submit styleClass="btn_grey" value="确定"/>
	                  		
	                   	 <s:reset value="重置" styleClass="btn_grey"/>
	                   	 <a href="system!registered.do">注册</a>
					   </td>
	                  </tr>
	                   <tr>
	                	 <td height=30 colspan="2" align=center class=STYLE2><span id="tip"></span>&nbsp;</td>
	                   </tr>
         		</table>
			</s:form>		
		</td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
