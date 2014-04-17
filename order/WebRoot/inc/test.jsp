<%@ page language="java" contentType="text/html;charset=gb2312" session="true" import="java.sql.*" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>DIV+css登录页面</title>
<script language="javascript" type="text/javascript" src="jquery-1.3.1.js"></script>
<script type="text/javascript">
$(document).ready(
   function(){
     $("#btnLogin").click(
   function(){
    alert("登录");
   }
  
     )
  $("#btnExit").click(
   function(){
   setTimeout('window.close();',50);
   }
  )
   }

)
</script>


</head>

<body>
<div align="center">
 <div id="1" style="background:url(./images/m_login.jpg); width:100%; height:100% ; position:relative; background-color:#000000">

  <div id="2" style="width:100%; height:100%; position:relative; left:-60px; top:335px;background-color:#FFFF00 ">
      <input type="button" id="btnLogin" value=" 登 录 " width="50px" /><input type="button" id="btnExit" value=" 退 出 " />     
  </div>
 </div>
 
    <label id="lable1" style="left:105px; position:relative;top:-265px" > 口 令 </label>
 <label id="lable2" style="left:55px; position:relative;top:-232px" name="lable2" > 密 码 </label>
 <label id="lable2" style="left:7px; position:relative;top:-202px" name="lable2" > 类 型 </label>
 <input type="text" id="loginName" style="position:relative ; left:30px ; top:-265px; width:120px;" />
    <input type="password" id="loginPwd" style="left: -100px; position: relative; top: -230px; width:120px;"   />
 <select name="select" id="select" style="left: -230px; position: relative; top: -200px; width:120px;" >
       <option value="学生">学生</option>
       <option value="老师">老师</option>
       <option value="管理员">管理员</option>
 </select>
</div>
</body>
</html>