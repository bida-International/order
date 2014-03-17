<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript" src="./inc/js/bianhao.js"></script>
<m:frame>
<script>
	 function quan()
	 {
		 var h=document.getElementsByName("checkbox");
		 var da=document.getElementsByName("chkItems");
		 if(h[0].checked==true)
		 {
		  for(i=0;i<da.length;i++)
		  {
		   da[i].checked=true;
		  }
		 }
		 else
		 {
		   	 for(i=0;i<da.length;i++)
			  {
			    da[i].checked=false;
			  }
	 	}
	  }
  function mytest(){
	 	var items=document.getElementsByTagName("input"); 
	 	var userid = document.getElementsByName("selyewu");
	    var lujing = "admin!yewu_fenpei.do?pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
	    fenpei(items,userid,lujing);
	 }
   function tiaozhuan(){
	var pageNumber = document.getElementById("pageNumber").value;
	location.href = "admin!getZhangHaoAll.do?pager.offset="+(pageNumber*10-10)+"&pageNumber="+pageNumber;
  }
</script>
<s:form action="admin!getZhangHaoAll.do" method="post" >
  <table border="1" width="500"  class="datagrid2" id="list" cellspacing="0" style="float:left">
    <tr>
      <td colspan="3" align="center"><strong>查看全部账号</strong></td>
    </tr>
     <tr>
    <td colspan="3">
    	请输入账号：<input type="text" name="name" value="${param.name}"/><input type="submit" value="查询"/>
    	<input type="button" value="分配账号给业务 " onclick="mytest()"/>
    </td>
    </tr>
    <tr align="center">
    <td width="34" height="25">
		<input name="checkbox" onclick="quan()" type="checkbox" style="cursor:pointer"/></td>
       <td width="300"><span class="STYLE2">账号</span></td>
      <td width="64"><span class="STYLE2">业务</span></td>
    </tr>
    <s:iterator value="pageBean.list" var="sd" >
	     <tr align="center">
	      <td><input name="chkItems" value="${sd.id}" onclick="chkItems_click(this)" type="checkbox"  id="chk_${sd.id}" readonly="readonly"  style="cursor:pointer"/></td>
	          <td>${sd.name}</td>      
	           <td>
	           <select name="selyewu" id="selyewu"> 
		     	<option value="0">-请选择-</option>
			      	<s:iterator value="getYeWu1All()" id = "i">
			      		<option value="${i.userid}">${i.name}</option>
			      	</s:iterator>
		      	</select>
				</td>
	     </tr>   
     </s:iterator> 
 <tr>     
         <td colspan="3" align="center">
	        总记录数：${pageBean.allRow} 共几页：${pageBean.allPage} 当前第 ${pageBean.currentPage} 页
        <pg:pager url="admin!getZhangHaoAll.do" items="${pageBean.allRow}" export="currentPageNumber=pageNumber" maxPageItems="10"> 
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
  <s:iterator value="#strsd" var="i">
  ${i}
  </s:iterator>
  </s:form>
  <br>
</m:frame>