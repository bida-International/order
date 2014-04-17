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
<div style="width: 100%; height: 100%; position:absolute;">    
<img width="100%" height="100%" src="images/m_login.jpg">
</div>
    <div style="width: 100%; height: 100%; position:absolute; margin-top:0px;">
    <div > <table width="100%"  height="100%" border="0" align="left" cellpadding="0" cellspacing="0">
      <tr>
        <td width="40%" height="270px">&nbsp;</td>
        <td  align="left" height="270px" valign="bottom">&nbsp; ${denglu} ${msg}</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td valign="top">
			<s:form action="system!login.do" method="post"  onsubmit="return login()" theme="simple">
                 <table width="100%"  border="0" cellpadding="0" cellspacing="0">
	                    <tr>
	                 	 <td width="8%" align="right" height="30">用户名:</td><td><input type="text" name="username" id="username" size="35" style="height: 25px;"></td>
                   </tr>
	                   <tr>
	                	  <td  width="8%" align="right" height="30">密码:</td><td><input type="password" name="pwd" id="pwd" size="35" style="height: 25px;"></td>
	                   </tr>
	                  <tr>
		                   <td height="5%" colspan="2" align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		                     <s:submit value="确定"/>
		                   	 <s:reset value="重置" />
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
    </table>
    </div>
    </div>
</body>
</html>
