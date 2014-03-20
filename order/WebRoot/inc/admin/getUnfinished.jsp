<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
 <%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<script type="text/javascript"  src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="./inc/js/bianhao.js"></script>
<script type="text/javascript" src="jquery-1.8.2.js"></script>
<m:frame>
	<script type="text/javascript">
		 function page(pageNumber) 
		 {
			document.getElementById("pageNumber").value = pageNumber;
			document.getElementById("pager.offset").value = (pageNumber*10-10);
			document.forms.submit();
		 }
	</script>
	
	<div style="WIDTH: 1150px; HEIGHT: 430px;overflow: auto;font: 12px Verdana;color:#000;background-color:#FFF;border: 1px dotted #999;
scrollbar-face-color: #CBDDF1;
scrollbar-highlight-color: #547CBB;
scrollbar-shadow-color: #547CBB;
scrollbar-3dlight-color: #ffffff;
scrollbar-arrow-color:  #547CBB;
scrollbar-track-color: #FFFFFF;
scrollbar-darkshadow-color: #FFFFFF;"> 
	<s:form action="admin!WrittenDocument.do" method="post" onsubmit="return as()">
	  <table border="1"  class="datagrid2" id="list" cellspacing="0" style="float:left">
    <tr>
      <td colspan="6" align="center"><strong>文档</strong></td>
    </tr>
    <tr align="center">
      <td width="120"><span class="STYLE2">内容</span></td>
    </tr>
    <s:iterator begin="1" end="5" status="t">
      <tr align="center">
        <td><input type="text" size="50" name="title" id="title" title="内容 "></td>
     </tr>    
   </s:iterator>
    <tr>    
        <td colspan="6" align="center">
         <input type="submit" id="t" value=" 添 加 " style="cursor: pointer;">    
          </td>
    </tr>
  </table>
  <s:iterator value="#insert" var="i">
  ${i}
  </s:iterator>
  </s:form>
	 <form action="admin!getUnfinished.do"  name="forms" id="forms" method="post" onsubmit="return as()">
  <table border="1"  cellspacing="0" cellpadding="0" align="center" width="1000px" cellspacing="0">
    <tr>
      <td colspan="2" align="center"><strong>未完成</strong>
    
         <s:hidden name="pageNumber" value="1"></s:hidden>
		<s:hidden name="pager.offset" id="pager.offset" value="0"></s:hidden>
		<input type="hidden" value="${tit}" name="tit"> 
      </td>
    </tr>
      <tr>
	    <td colspan="2">
	   		 时间：<input type="text" name="time" id="time" onfocus="WdatePicker()" value="${param.time}" size="9"/>至<input type="text" name="time1" id="time1"  onfocus="WdatePicker()" value="${param.time1}" size="9"/>
	   		 <input type="submit" value="查询"/>
	    </td>
    </tr>
    <tr align="center">
     <td width="163"><span class="STYLE2">内容</span></td>
     <td width="100"><span class="STYLE2">时间  </span>(<font color="red">  需要刷新页面才能显示正确的时间格式</font>)</td>
    </tr>
    <s:iterator value="pageBean.list" var="i">
      <tr align="center">
        <td>${i.title}</td>
        <td>${i.time}</td>  
     </tr>    
   </s:iterator>
    <tr> 
	    <td colspan="2" align="center">
	        总记录数：${pageBean.allRow} 共几页：${pageBean.allPage} 当前第 ${pageBean.currentPage} 页
       <pg:pager url="" items="${pageBean.allRow}" export="currentPageNumber=pageNumber" maxPageItems="10"> 
 	 		 <a href="javascript:page(1)">首页</a>
            <s:if test="%{pageBean.currentPage>1}">
             <a href ="javascript:page(${pageBean.currentPage-1})">上一页</a> 
             </s:if>
	       <pg:pages> 
			   	<c:choose> 
			     	  <c:when test="${currentPageNumber eq pageNumber}"> 
			      		 <font color="red">${pageNumber}</font> 
			     	  </c:when> 
			      <c:otherwise> 
			       <a href="javascript:page(${pageNumber})" id="page">${pageNumber}</a> 
			     </c:otherwise> 
			   </c:choose> 
	 		</pg:pages> 
            <s:if test="%{pageBean.allPage>pageBean.currentPage}"> 
            <a href="javascript:page(${pageBean.currentPage+1})">下一页</a>		 			 
            </s:if>
          <a href="javascript:page(${pageBean.allPage})">尾页</a>
	</pg:pager> 
	    </td>   		
    </tr>
  </table>
  </form>
	</div>
  <s:iterator value="#str" var="ss">
  ${ss}
  </s:iterator>
  <br> 
</m:frame>