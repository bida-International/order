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
     	location.href = "admin!getYcDetail.do?pager.offset="+(pageNumber*10-10)+"&pageNumber="+pageNumber;
     }
</script>
<s:form action="admin!getYcDetail.do" method="post">
  <table border="1" width="900px"  class="datagrid2" id="list" cellspacing="0" style="float:left">
    <tr>
      <td colspan="6" align="center"><strong>预存资金详情</strong></td>
    </tr>
     <tr>
    <td colspan="6">
    	预存时间：<input type="text" name="time" value="${param.time}" onfocus="WdatePicker()"/>至<input type="text" name="time1" onfocus="WdatePicker()" value="${param.time1}"/>
    	<input type="submit" value="查询"/>
  
    </td>
    </tr>
    <tr align="center">
       <td width="120"><span class="STYLE2">预存金额</span></td>
       <td width="120"><span class="STYLE2">预存时间</span></td>
       <td width="120"><span class="STYLE2">顾客姓名</span></td>
       <td width="120"><span class="STYLE2">使用时间</span></td>
       <td width="120"><span class="STYLE2">所用运费</span></td>
      <td width="64"><span class="STYLE2">操作</span></td>
    </tr>
    <s:iterator value="pageBean.list" var="sd">
	     <tr align="center">
	          <td>${sd.money}</td>      
	          <td>${sd.time}</td>
	          <td><s:property value="getSelName(#sd.gukeId)"/></td>
	          <td>${sd.sytime}</td>
	          <td>${sd.symoney}</td>
		     <td>
		     	<a href="admin!upmoney.do?yunCun.id=${sd.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}" onclick="return confirm('确定修改?')">修改</a>
		     </td>	
	     </tr>   
     </s:iterator> 
		     <tr> 
	    <td colspan="6" align="center">
	        总记录数：${pageBean.allRow} 共几页：${pageBean.allPage} 当前第 ${pageBean.currentPage} 页
        <pg:pager url="admin!getYcDetail.do" items="${pageBean.allRow}" export="currentPageNumber=pageNumber" maxPageItems="10"> 
 		 <pg:first> 
    		<a href="${pageUrl}&pageNumber=1&name=${param.name}">首页</a> 
  		 </pg:first> 
  	   <pg:prev> 
   		 <a href="${pageUrl}&pageNumber=${pageBean.currentPage-1}&name=${param.name}">上一页</a> 
  	   </pg:prev> 
  	 <pg:pages> 
    	<c:choose> 
      	  <c:when test="${currentPageNumber eq pageNumber}"> 
       		 <font color="red">${pageNumber}</font> 
      	  </c:when> 
       <c:otherwise> 
        <a href="${pageUrl}&pageNumber=${pageNumber}&name=${param.name}">${pageNumber}</a> 
      </c:otherwise> 
    </c:choose> 
  </pg:pages> 
  <pg:next> 
    <a href="${pageUrl}&pageNumber=${pageBean.currentPage+1}&name=${param.name}">下一页</a> 
  </pg:next> 
  <pg:last> 
    <a href="${pageUrl}&pageNumber=${pageBean.allPage}&name=${param.name}">尾页</a> 
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