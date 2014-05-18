<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script>
 function page(pageNumber) 
 {
	document.getElementById("pageNumber").value = pageNumber;
	document.getElementById("pager.offset").value = (pageNumber*10-10);
	document.forms.submit();
 }
</script>
<m:frame>
<form action="yewu1!getYeAllOrder.do" method="post" onsubmit="return as()" id="forms" name="forms">
  <table border="1" width="900px" cellspacing="0" style="float:left">
     <tr>
      <td colspan="8" align="center"><strong>全部订单</strong></td>

    </tr>
    <tr>
      <td colspan="7" align="right">订单号：<input type="text" name="orderId" id="orderId" value="${param.orderId}"/></td>
      <td><input type="submit" value="查询"/>
      <s:hidden name="pageNumber" value="1"></s:hidden>
		<s:hidden name="pager.offset" id="pager.offset" value="0"></s:hidden>
		<input type="hidden" value="${tit}" name="tit">  
      </td>
      
    </tr>
    <tr align="center">
		<td width="130"><span class="STYLE2">账号</span></td>
      <td width="100"><span class="STYLE2">订单号</span></td>
      <td width="100"><span class="STYLE2">运输单号</span></td>
        <td width="130"><span class="STYLE2">采购时间</span></td>
         <td width="100"><span class="STYLE2">备注</span></td>
         <td width="100"><span class="STYLE2">采购员</span></td>
          <td width="120"><span class="STYLE2">发货时间</span></td>
       <td width="100"><span class="STYLE2">操作</span></td>
    </tr>
    <s:iterator value="pageBean.list" var="sd">
	     <tr align="center" style="cursor:pointer">
	         <td><s:property value="getZhangHaoId(#sd.zhanghaoId)"/></td>
	         <td>${sd.orderId}</td>
	   		 <td><textarea rows="5" cols="10">${sd.danhao}</textarea></td>	
	   		 <td>${sd.caigoutime}</td>
	   		 <td><textarea rows="5" cols="10">${sd.remark}</textarea></td>
	   		 <td><s:property value="getUserId(#sd.caigouyuan)"/></td>
	   		  <td>${sd.dcsj}</td>
		      <td>
		      	<a href="yewu1!upOrder.do?ordertable.id=${sd.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}">修改订单</a>
		      <s:if test="#sd.jingji==1">
	   	 		<font color="red">紧急订单(已经被点击)</font> 
		   	 </s:if>
		   	 <s:if test="#sd.jingji !=1">
		   	 	/<a href="yewu1!upjinji.do?ordertable.id=${sd.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}" onclick="return confirm('确定?')">紧急订单</a>
		   	 </s:if>
		      </td>
	     </tr>   
     </s:iterator> 
     <tr>     
         <td colspan="8" align="center">
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
    <s:iterator value="#strsd" var="d">
  	${d}<br>
  </s:iterator>
   <s:iterator value="#wenti" var="dd">
  	${dd}<br>
  </s:iterator>
  </form>
  <br>
</m:frame>