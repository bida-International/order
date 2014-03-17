<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<m:frame>
<s:form  action="test!getKanXin.do" cssStyle="cellspacing:0;cellpadding:0">
  <table border="1" width="700px" cellspacing="0" style="float:left">
    <tr> 
      <td colspan="3" align="center"><strong>看信</strong>   未读信息<a href="test!getWeiDuXin.do"><FONT color="red"><s:property value="getXieXin()"/></FONT></a>
    </tr>
    <tr align="center">
        <td width="250"><span class="STYLE2">标题</span></td>
       <td width="90"><span class="STYLE2">时间</span></td>
        <td width="90"><span class="STYLE2">操作</span></td>
    </tr>
    <s:iterator value="pageBean.list" var="sd" >
	     <tr align="center" style="cursor:pointer">
	         <td>
	         <c:if test="${sd.chakan == 0 || sd.chakan == null}">
	        	<a href="test!biaoti.do?id=${sd.id}&chakan=${sd.chakan}"><font color="red">${sd.title}</font></a>
	         </c:if>
	         <c:if test="${sd.chakan == 1}">
	        	<a href="test!biaoti.do?id=${sd.id}&chakan=${sd.chakan}"><font color="blue">${sd.title}</font></a>
	         </c:if>
	         </td>
	     	 <td>${sd.time}</td>
	     	 <td><a href="test!delxx.do?ids=${sd.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}" onclick="return confirm('确定删除吗？')" >删除</a></td>
	     </tr>
     </s:iterator> 
	     <tr>     
         <td colspan="3" align="center">
        	
	        总记录数：${pageBean.allRow} 共几页：${pageBean.allPage} 当前第 ${pageBean.currentPage} 页
        <pg:pager url="test!getKanXin.do" items="${pageBean.allRow}" export="currentPageNumber=pageNumber" maxPageItems="10"> 
 		 <pg:first> 
    		<a href="${pageUrl}&pageNumber=1">首页</a> 
  		 </pg:first> 
  	   <pg:prev> 
   		 <a href="${pageUrl}&pageNumber=${pageBean.currentPage-1}">上一页</a> 
  	   </pg:prev> 
  	 <pg:pages> 
    	<c:choose> 
      	  <c:when test="${currentPageNumber eq pageNumber}"> 
       		 <font color="red">${pageNumber}</font> 
      	  </c:when> 
       <c:otherwise> 
        <a href="${pageUrl}&pageNumber=${pageNumber}">${pageNumber}</a> 
      </c:otherwise> 
    </c:choose> 
  </pg:pages> 
  <pg:next> 
    <a href="${pageUrl}&pageNumber=${pageBean.currentPage+1}">下一页</a> 
  </pg:next> 
  <pg:last> 
    <a href="${pageUrl}&pageNumber=${pageBean.allPage}">尾页</a> 
  </pg:last>
  <pg:page> 
  <input type="text" name="pageNumber" id="pageNumber" size="5"/>
  <input type="button" onclick="tiaozhuan()" value="跳转"/>
  </pg:page>
	</pg:pager> 
	    </td>   		
    </tr>
  </table>
  </s:form>
  <br>
</m:frame>