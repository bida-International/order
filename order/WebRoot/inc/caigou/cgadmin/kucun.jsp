<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript"  src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="./inc/js/bianhao.js"></script>
<script type="text/javascript">
	 //全选 
	 function quan()
	 {
		 var h=document.getElementsByName("checkbox");
		 var da=document.getElementsByName("chkItems");
		 quan1(h,da);
	  }
	  //跳转
   function tiaozhuan(){
	var pageNumber = document.getElementById("pageNumber").value;
	location.href = "caigouadmin!getKuCun.do?pager.offset="+(pageNumber*10-10)+"&pageNumber="+pageNumber;
  }
</script>
 
<m:frame>
${msg}
<s:form  action="caigouadmin!getKuCun.do" cssStyle="cellspacing:0;cellpadding:0" onsubmit="return as()">
  <table border="1" width="800px" cellspacing="0" style="float:left" >
    <tr>
      <td colspan="4" align="center"><strong>库存订单</strong></td>
    </tr>
    <tr>
    	<td colspan="4" align="center">
	  	订单号 <input type="text" name="orderId" id="orderId" value="${param.orderId}"/>
	  	<input type="submit" value="查询"/>
	  	</td>
    </tr>
    <tr align="center">
    <td width="34" height="25">
		<input name="checkbox" onclick="quan()" type="checkbox" style="cursor:pointer"/></td>
       <td width="90"><span class="STYLE2">订单号</span></td>
     <td width="100"><span class="STYLE2">数量</span></td>
     <td width="100"><span class="STYLE2">编号</span></td>
    </tr>
    <s:iterator value="pageBean.list" var="sd">
	     <tr align="center" style="font-size:30px">
	      <td><input name="chkItems" value="${sd.id}" onclick="chkItems_click(this)" type="checkbox"  id="chk_${sd.id}" readonly="readonly"  style="cursor:pointer"/></td>
	     
	          <td>${sd.orderId}</td>
	          <td>${sd.num}</td>
	           <td>${sd.biaoji}</td>
	     </tr>   
     </s:iterator> 
  <tr>     
         <td colspan="4" align="center">
	        总记录数：${pageBean.allRow} 共几页：${pageBean.allPage} 当前第 ${pageBean.currentPage} 页
        <pg:pager url="caigouadmin!getKuCun.do" items="${pageBean.allRow}" export="currentPageNumber=pageNumber" maxPageItems="10"> 
 		 <pg:first> 
    		<a href="${pageUrl}&pageNumber=1&orderId=${param.orderId}">首页</a> 
  		 </pg:first> 
  	   <pg:prev> 
   		 <a href="${pageUrl}&pageNumber=${pageBean.currentPage-1}&orderId=${param.orderId}">上一页</a> 
  	   </pg:prev> 
  	 <pg:pages> 
    	<c:choose> 
      	  <c:when test="${currentPageNumber eq pageNumber}"> 
       		 <font color="red">${pageNumber}</font> 
      	  </c:when> 
       <c:otherwise> 
        <a href="${pageUrl}&pageNumber=${pageNumber}&orderId=${param.orderId}">${pageNumber}</a> 
      </c:otherwise> 
    </c:choose> 
  </pg:pages> 
  <pg:next> 
    <a href="${pageUrl}&pageNumber=${pageBean.currentPage+1}&orderId=${param.orderId}">下一页</a> 
  </pg:next> 
  <pg:last> 
    <a href="${pageUrl}&pageNumber=${pageBean.allPage}&orderId=${param.orderId}">尾页</a> 
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
</m:frame>