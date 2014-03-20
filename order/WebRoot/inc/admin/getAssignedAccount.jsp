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
	 	var userid = document.getElementsByName("AssignedAccount");
	    var lujing = "admin!art_fenpei.do?pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
	    fenpei(items,userid,lujing);
	 }
   	 function page(pageNumber) 
	 {
		document.getElementById("pageNumber").value = pageNumber;
		document.getElementById("pager.offset").value = (pageNumber*10-10);
		document.forms.submit();
	 }
</script>
<form action="admin!getAssignedAccounts.do" method="post" name="forms" id="forms">
  <table border="1" width="500"  class="datagrid2" id="list" cellspacing="0" style="float:left">
    <tr>
      <td colspan="3" align="center"><strong>分配账号给美工</strong></td>
    </tr>
     <tr>
    <td colspan="3">
    	请输入账号：<input type="text" name="name" value="${param.name}"/><input type="submit" value="查询"/>
    	<input type="button" value="分配账号给美工 " onclick="mytest()"/>
    	<s:hidden name="pageNumber" value="1"></s:hidden>
		<s:hidden name="pager.offset" id="pager.offset" value="0"></s:hidden>
		<input type="hidden" value="${tit}" name="tit"> 
    </td>
    </tr>
    <tr align="center">
    <td width="34" height="25">
		<input name="checkbox" onclick="quan()" type="checkbox" style="cursor:pointer"/></td>
       <td width="300"><span class="STYLE2">账号</span></td>
      <td width="64"><span class="STYLE2">美工</span></td>
    </tr>
    <s:iterator value="pageBean.list" var="sd" >
	     <tr align="center">
	      <td><input name="chkItems" value="${sd.id}" onclick="chkItems_click(this)" type="checkbox"  id="chk_${sd.id}" readonly="readonly"  style="cursor:pointer"/></td>
	          <td>${sd.name}</td>     
	        
	           <td>
	           <select name="AssignedAccount" id="AssignedAccount"> 
		     	<option value="0">-请选择-</option>
			      	<s:iterator value="getAssignedAccount()" id = "i">
			      		<option value="${i.userid}">${i.name}</option>
			      	</s:iterator>
		      	</select>
				</td>
	     </tr>   
     </s:iterator> 
 <tr>     
         <td colspan="3" align="center">
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
  ${msg}
  <s:iterator value="#strsd" var="i">
  ${i}
  </s:iterator>
  </form>
  <br>
</m:frame>