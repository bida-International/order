<%@ tag language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@tag import="java.beans.Encoder"%>
<%@tag import="javax.sound.sampled.AudioFormat.Encoding"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>订单管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="./CSS/style.css" rel="stylesheet"/>
	<link href="./CSS/jquery-ui-1.8.18.custom.css" rel="stylesheet"/>
	<script src="jquery-1.8.2.js" type="text/javascript"></script>
	<script src="./CSS/jquery-ui-1.8.2.custom.min.js" type="text/javascript"></script>
	<script src="./CSS/common.js?v=20140306" type="text/javascript"></script>
	<style>
ul{list-style-type:none;margin:0px;}
.div-menu { margin:0px ;float:left;clear:both;background-color: #EEF2FB; line-height:30px;margin-left: 0px;}
body {
font:12px Arial, Helvetica, sans-serif;
color: #000;
margin: 0px;
	
}
#container {
	width: 182px;
}
H1 {
	font-size: 12px;
	margin: 0px;
	width: 182px;
	cursor: pointer;
	height: 30px;
	line-height: 20px;	
}
H1 a {
	display: block;
	width: 100px;
	color: #000;
	height: 10px;
	text-decoration: none;
	moz-outline-style: none;
	background-image: url(images/menu_bgS.gif);
	background-repeat: no-repeat;
	line-height: 10px;

	margin: 0px;
	padding: 0px;
}
.content{
	width: 182px;
	height: 70px;
	
}
.MM ul {
	list-style-type: none;
	margin: 0px;
	padding: 0px;
	display: block;
}
.MM li {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	line-height: 26px;
	color: #333333;
	list-style-type: none;
	display: block;
	text-decoration: none;
	height: 26px;
	width: 90px;
	padding-left: 0px;
}
.MM {
	width: 100px;
	margin: 0px;
	padding: 0px;
	left: 0px;
	top: 0px;
	right: 0px;
	bottom: 0px;
	clip: rect(0px,0px,0px,0px);
}
.MM a:link {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	line-height: 26px;
	color: #333333;
	background-image: url(images/menu_bg1.gif);
	background-repeat: no-repeat;
	height: 26px;
	width: 100px;
	display: block;
	margin: 0px;
	padding: 0px;
	overflow: hidden;
	text-decoration: none;
}
.MM a:visited {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	line-height: 26px;
	color: #333333;
	background-image: url(images/menu_bg1.gif);
	background-repeat: no-repeat;
	display: block;
	margin: 0px;
	padding: 0px;
	height: 26px;
	width: 100px;
	text-decoration: none;
}
.MM a:active {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	line-height: 26px;
	color: #333333;
	background-image: url(images/menu_bg1.gif);
	background-repeat: no-repeat;
	height: 26px;
	width: 100px;
	display: block;
	margin: 0px;
	padding: 0px;
	overflow: hidden;
	text-decoration: none;
}
.MM a:hover {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	line-height: 26px;
	font-weight: bold;
	color: #006600;
	background-image: url(images/menu_bg2.gif);
	background-repeat: no-repeat;
	display: block;
	margin: 0px;
	padding: 0px;
	height: 26px;
	width: 100px;
	text-decoration: none;
}
div{
text-decoration:none}
#Layer1 {
	position:absolute;
	width:56px;
	height:17px;
	z-index:1;
	left: 215px;
	top: 195px;
}
a{
text-decoration:none}
.div-height{border:1px solid #F00; width:300px; height:1000px} 
	</style>
	<script>
		String.prototype.getParameter = function (key) {  
			var re = new RegExp(key + '=([^&]*)(?:&)?');
	        return this.match(re) && this.match(re)[1];  
		};
		var s = location.href;  
		
   		 // 获取编号  
		var activeIndex = s.getParameter("activeIndex") || 0; 
		$(function() {
			$( "#accordion" ).accordion({
				collapsible: true,
				active:parseInt(activeIndex)
				
			});
		});
	</script>
  </head>
  <body style=" font-size:12px">
		<div id="body">
		<div style="height:90px;">
	    	<table width="1297" height="89" border="0" cellpadding="0" cellspacing="0">
			  <tr>
			    <td background="./images/m_top.jpg">&nbsp;
			    </td>
			  </tr>
			</table>    		
    	</div>
    	<div style="float: left">
	    	<table width="1297" height="30"  cellspacing="0" cellpadding="0">
		    	<tr>
					 <td width="150" bgcolor="#5898C8">
					 	<span class="word_white">&nbsp;网站后台管理</span>
					 </td>
					 <td bgcolor="#EEEEEE" class="tableBorder_thin">
			        	<table width="100%"  cellspacing="0" cellpadding="0">
				            <tr>
				              <td  width="50%" class="word_grey">&nbsp;
				             
				                                当前位置：<span class="word_darkGrey"  id="tit">
				                                
				               <s:property value="getTitle()"/>&gt;&gt;&gt;</span>
				               
				               </td>
				              <td align="right">
				              
				              <img src="./images/m_ico1.gif" width="5" height="9">
				              
				              <a href="system!sessions.do">退出</a>
				              
				            <c:if test="${logininfo.role == 6}">
				           	<font size="2">账号余额:</font><a><font size=5"><s:property value="getYunFeiYuCun()"/></font>元</a>
				         </c:if>
				             <font size="2"> 收信箱有未读信息：</font><a href="test!getKanXin.do">
				             <FONT color="red"><s:property value="getWeiDu()"/></FONT></a>		             
				              &nbsp;欢迎${logininfo.name}登录系统!&nbsp;
				              </td>
				            </tr>
			            </table>
					</td>
				</tr>
			</table>
    	</div>
  		<div  style="width:100%; background-color:#FFF;display:block">
  		<div id="accordion" style="width: 150px;float: left;border-right: 1px solid #ccc;">  
			<s:iterator value="getMenu(#session.logininfo.role)" id="m1" status="i">
		    <h3><a href="javascript:void(0)" ><img src="./images/m_ico.gif" width="11" height="11" />${m1.name}</a></h3>
		    <ul>
			    <s:iterator value="getMenu(#m1.id)" id="m2">  
		        	<li class="MM">
			        	<s:url value="%{#m2.url}" id="myurl"></s:url>
						<a href="${myurl}?tit=${m2.id}&activeIndex=${i.index}" title="${m2.name}">${m2.name}</a> 
					</li>  	             
			    </s:iterator>
		    </ul>
		    </s:iterator> 
        </div>
	  			<div id="maindiv" style="margin-left: 155px;">
	  				<jsp:doBody></jsp:doBody>
	  			</div>
  		</div>
  	</div>
  </body>
</html>
