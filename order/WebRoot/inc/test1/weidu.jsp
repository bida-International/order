<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<m:frame>
<s:form  action="test!getWeiDuXin.do" cssStyle="cellspacing:0;cellpadding:0">
  <table border="1" cellspacing="0" style="float:left">
    <tr> 
      <td colspan="2" align="center"><strong>未读信</strong>
    </tr>
    <tr align="center">
        <td width="250"><span class="STYLE2">标题</span></td>
       <td width="90"><span class="STYLE2">时间</span></td>
    </tr>
    <s:iterator value="pageBean.list" var="sd" >
	     <tr align="center" style="cursor:pointer">
	         <td><a href="test!biaoti.do?id=${sd.id}&chakan=${sd.chakan}"><font color="red">${sd.title}</font></a></td>
	     	 <td>${sd.time}</td>
	     </tr>
     </s:iterator> 
	     <tr>     
         <td colspan="2" align="center">
        	
	        总记录数：${pageBean.allRow} 共几页：${pageBean.allPage} 当前第 ${pageBean.currentPage} 页
        <pg:pager url="test!getWeiDuXin.do" items="${pageBean.allRow}" export="currentPageNumber=pageNumber" maxPageItems="10"> 
 		 <pg:first> 
    		<a href="${pageUrl}&pageNumber=1&orderId=${param.orderId}&time=${param.time}&time1=${param.time1}&zhanghaoId=${param.zhanghaoId}">首页</a> 
  		 </pg:first> 
  	   <pg:prev> 
   		 <a href="${pageUrl}&pageNumber=${pageBean.currentPage-1}&orderId=${param.orderId}&time=${param.time}&time1=${param.time1}&zhanghaoId=${param.zhanghaoId}">上一页</a> 
  	   </pg:prev> 
  	 <pg:pages> 
    	<c:choose> 
      	  <c:when test="${currentPageNumber eq pageNumber}"> 
       		 <font color="red">${pageNumber}</font> 
      	  </c:when> 
       <c:otherwise> 
        <a href="${pageUrl}&pageNumber=${pageNumber}&orderId=${param.orderId}&time=${param.time}&time1=${param.time1}&zhanghaoId=${param.zhanghaoId}">${pageNumber}</a> 
      </c:otherwise> 
    </c:choose> 
  </pg:pages> 
  <pg:next> 
    <a href="${pageUrl}&pageNumber=${pageBean.currentPage+1}&orderId=${param.orderId}&time=${param.time}&time1=${param.time1}&zhanghaoId=${param.zhanghaoId}">下一页</a> 
  </pg:next> 
  <pg:last> 
    <a href="${pageUrl}&pageNumber=${pageBean.allPage}&orderId=${param.orderId}&time=${param.time}&time1=${param.time1}&zhanghaoId=${param.zhanghaoId}">尾页</a> 
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