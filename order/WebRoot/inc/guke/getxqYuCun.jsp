<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<script type="text/javascript"  src="My97DatePicker/WdatePicker.js"></script>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<m:frame>
<script type="text/javascript">
	function tiaozhuan(){
     	var pageNumber = document.getElementById("pageNumber").value;
     	location.href = "guke!getXqYunFei.do?pager.offset="+(pageNumber*10-10)+"&pageNumber="+pageNumber;
     }
</script>
<s:form action="guke!getXqYunFei.do" method="post">
  <table border="1" width="900px" cellspacing="0" style="float:left">
    <tr>
      <td colspan="6" align="center"><strong>预存运费 详情</strong></td>
    </tr>
    <tr align="center">
       <td width="120"><span class="STYLE2">预存金额</span></td>
       <td width="120"><span class="STYLE2">预存时间</span></td>
       <td width="120"><span class="STYLE2">顾客姓名</span></td>
       <td width="120"><span class="STYLE2">使用时间</span></td>
       <td width="120"><span class="STYLE2">所用运费</span></td>
    </tr>
    <s:iterator value="pageBean.list" var="sd">
	     <tr align="center">
	          <td>${sd.money}</td>      
	          <td>${sd.time}</td>
	          <td><s:property value="getSelName(#sd.gukeId)"/></td>
	          <td>${sd.sytime}</td>
	          <td>${sd.symoney}</td>
	     </tr>   
     </s:iterator> 
		     <tr> 
	    <td colspan="6" align="center">
	        总记录数：${pageBean.allRow} 共几页：${pageBean.allPage} 当前第 ${pageBean.currentPage} 页
        <pg:pager url="guke!getXqYunFei.do" items="${pageBean.allRow}" export="currentPageNumber=pageNumber" maxPageItems="10"> 
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
  ${msg}
  	</s:form>
  <br>
</m:frame>