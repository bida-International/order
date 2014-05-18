<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<link href="tablecloth/tablecloth.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="tablecloth/tablecloth.js"></script> 
<m:frame>

<style>

body{
	margin:0;
	padding:0;
}
a{
	text-decoration:none;
	color:#057fac;
}
a:hover{
	text-decoration:none;
	color:#999;
}
h1{
	font-size:140%;
	margin:0 20px;
	line-height:80px;	
}
h2{
	font-size:120%;
}
.container{
	margin:0 auto;
	width:680px;
	background:#fff;
	padding-bottom:20px;
}
.content{margin:0 20px;}
p.sig{	
	margin:0 auto;
	width:680px;
	padding:1em 0;
}
form{
	margin:1em 0;
	padding:.2em 20px;
	background:#eee;
}
</style>
	<div id="demo"  class="container" style = "overflow: auto;font: 12px Verdana;color:#000;background-color:#FFF;border: 1px dotted #999;
scrollbar-face-color: #CBDDF1;
scrollbar-highlight-color: #547CBB;
scrollbar-shadow-color: #547CBB;
scrollbar-3dlight-color: #ffffff;
scrollbar-arrow-color:  #547CBB;
scrollbar-track-color: #FFFFFF;
scrollbar-darkshadow-color: #FFFFFF;">
			<div id="imgwrapper"  class="content">	
  <table border="1" align="left"  cellspacing="0" cellpadding="0" width="800px">
    <tr align="center">
       <th>是否处理</th>
       <th>速卖通地址</th>
       <th>退款</th>
      <th>完成情况</th>
      <th>汇率</th>
       <th>纠纷时间</th>
       <th>上传到采购时间差(小时)</th>
       <th>采购到入账时间差(小时)</th>
    </tr>
    <s:iterator value="getReAllOrders()" var="i">
      <tr align="center">
	    <td>
	     	<s:if test="#i.jiufen==1 && (#i.chuli == 0 || #i.chuli == null)">
	     		否
	     	</s:if>
	     	<s:if test="#i.jiufen==1 && #i.chuli == 1">
	     		是
	     	</s:if>	
		</td>	 
	    <td>${i.guojia}</td>
	    <td>
	    	${i.tuikuan}
	    </td>       
	    <td>${(i.wancheng==0 || i.wancheng == null)?('否'):('是')}</td>
	    <td>${i.huilv}</td>
	    <td>${i.jiufentime}</td>
	    <td>
	    <s:if test="#i.time==null || #i.caigoutime == null">
	    
	    </s:if>
	    <s:if test="#i.time!=null && #i.caigoutime != null">
	    	<s:property value="getTime(#i.time,#i.caigoutime)"/>
	    </s:if>
	    </td>
	    <td>
	      <s:if test="#i.ruzhangtime==null || #i.caigoutime == null">
	    	
	    </s:if>
	    <s:if test="#i.ruzhangtime!=null && #i.caigoutime != null">
	    	<s:property value="getRuzhangTime(#i.caigoutime,#i.ruzhangtime)"/>
	    </s:if>  
	    </td>
     </tr>    
   </s:iterator>
   <tr><td colspan="8" align="center"><a href="admin!reorder.do?pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}">返回</a></td></tr>
  </table>
	</div>	
		</div>
  <br> 
</m:frame>