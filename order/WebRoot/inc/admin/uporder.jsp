<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<<script type="text/javascript">
<!--

//-->
function tiaozhuan(){
	var pageNumber = document.getElementById("pageNumber").value;
	location.href = "admin!getAllOrder.do?pager.offset="+(pageNumber*10-10)+"&pageNumber="+pageNumber;
}
</script>
<m:frame>
<s:form  action="admin!getOrderAll.do" cssStyle="cellspacing:0;cellpadding:0">
${msg}
  <table border="1"  class="datagrid2" id="list" cellspacing="0" style="float:left">
    <tr>
      <td colspan="4" align="center"><strong>修改订单</strong></td>
    </tr>
	  <tr>
	  	<td colspan="4">
	  	 订单号：<input type="text" name="orderId" value="${param.orderId}"/>
	  	 运输单号查询：<input type="text" name="danhao" value="${param.danhao}">
	  	 虚假单号查询：<input type="text" name="xujia" value="${param.xujia}">
	  	 <input type="submit" value="查询"/>
	  	</td>
	  </tr>
    <tr align="center">
      <td width="163"><span class="STYLE2">订单号</span></td>
      <td width="150"><span class="STYLE2">运输单号</span></td>
      <td width="150"><span class="STYLE2">上传时间</span></td>
      <td width="54"><span class="STYLE2">操作</span></td>
    </tr>
    <s:iterator value="pageBean.list" var="i">
      <tr align="center">
        <td><s:property value="#i.orderId"/></td>
        <td><s:property value="#i.danhao"/></td>
	    <td><s:property value="#i.time"/></td>	  
	    <td></td>   
     </tr>    
   </s:iterator>
    <tr> 
	    <td colspan="17" align="center">
	        总记录数：${pageBean.allRow} 共几页：${pageBean.allPage} 当前第 ${pageBean.currentPage} 页
        <pg:pager url="admin!getOrderAll.do" items="${pageBean.allRow}" export="currentPageNumber=pageNumber" maxPageItems="10"> 
 		 <pg:first> 
    		<a href="${pageUrl}&pageNumber=1&orderId=${param.orderId}&danhao=${param.danhao}&xujia=${param.xujia}">首页</a> 
  		 </pg:first> 
  	   <pg:prev> 
   		 <a href="${pageUrl}&pageNumber=${pageBean.currentPage-1}&orderId=${param.orderId}&danhao=${param.danhao}&xujia=${param.xujia}">上一页</a> 
  	   </pg:prev> 
  	 <pg:pages> 
    	<c:choose> 
      	  <c:when test="${currentPageNumber eq pageNumber}"> 
       		 <font color="red">${pageNumber}</font> 
      	  </c:when> 
       <c:otherwise> 
        <a href="${pageUrl}&pageNumber=${pageNumber}&orderId=${param.orderId}&danhao=${param.danhao}&xujia=${param.xujia}">${pageNumber}</a> 
      </c:otherwise> 
    </c:choose> 
  </pg:pages> 
  <pg:next> 
    <a href="${pageUrl}&pageNumber=${pageBean.currentPage+1}&orderId=${param.orderId}&danhao=${param.danhao}&xujia=${param.xujia}">下一页</a> 
  </pg:next> 
  <pg:last> 
    <a href="${pageUrl}&pageNumber=${pageBean.allPage}&orderId=${param.orderId}&danhao=${param.danhao}&xujia=${param.xujia}">尾页</a> 
  </pg:last>
  <pg:page> 
  <input type="text" name="pageNumber" id="pageNumber" size="5"/>
  <input type="button" onclick="tiaozhuan()" value="跳转"/>
  </pg:page>
</pg:pager> 
	    </td>   		
    </tr>
  </table>
  <br>
  	</s:form>
</m:frame>