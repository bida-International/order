﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
  <script type="text/javascript" src="js/jquery-1.8.2.js"></script>
  <script type="text/javascript" src="./inc/js/bianhao.js"></script>
  <script type="text/javascript">
<!--

//-->
	function page(pageNumber) 
	 {
		document.getElementById("pageNumber").value = pageNumber;
		document.getElementById("pager.offset").value = (pageNumber*10-10);
		document.forms.submit();
	 }
	 //全选
	 function quan()
	 {
		 var h=document.getElementsByName("checkbox");
		 var da=document.getElementsByName("chkItems");
		 quan1(h,da);
	 }
	 function mytest(){
	 	var items=document.getElementsByTagName("input"); 
	 	var userid = document.getElementsByName("category");
	    var lujing = "yewu!update_category.do?pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
	    fenpei(items,userid,lujing);
	 }
	  function checkedSel(){
		var ss = document.getElementsByTagName("select");
		var chk = document.getElementsByName("chkItems");
		for(var i=0;i<ss.length;i++){
			if(ss[i].value != 0){
				chk[i].checked=true;
			}else{
				chk[i].checked=false;
			}
		}
	 }
</script>
<m:frame>
 <form action="yewu!getModifyCategory.do" method="post" name="forms" id="forms">
  <table><tr><td><input type="button" value="修改类目" style="cursor: pointer;" onclick="mytest()"></td></tr></table>
   <table border="1"  width="700px" cellspacing="0" style="float:left" >
   <tr><td colspan="6" align="center">修改类目</td></tr>
   <tr><td colspan="6"> 
   		<s:hidden name="pageNumber" value="1"></s:hidden>
		<s:hidden name="pager.offset" id="pager.offset" value="0"></s:hidden>
		<input type="hidden" value="${tit}" name="tit"> 
	</td></tr>
    <tr align="center">
     <td width="34" height="25">
	<input name="checkbox" onclick="quan()" type="checkbox" style="cursor:pointer"/></td>
     <td width="54"><span class="STYLE2">账号</span></td>
       <td width="90"><span class="STYLE2">订单号</span></td>
        <td width="90"><span class="STYLE2">编码</span></td>
     <td width="163"><span class="STYLE2">类目</span></td>
       <td width="100"><span class="STYLE2">上传时间</span></td>
    </tr>
    <s:iterator value="pageBean.list" var="sd">
	     <tr align="center" style="font-size:30px">
		      <td><input name="chkItems" value="${sd.id}" onclick="chkItems_click(this)" type="checkbox"  id="chk_${sd.id}" readonly="readonly"  style="cursor:pointer"/></td>
		     <td>	
		     	<s:property value="getZhangHaoId(#sd.zhanghaoId)"/> 	  
		     </td>
		     <td>${sd.orderId}</td>
		      <td>${sd.bianma}</td>
	          <td>
	         	<select name="category" id="category" title="类目">
		    		<option value="0">-请选择 -</option>
		    		<s:iterator value="getAllLeiMu()" id="i">
		    			<option value="${i.id}">${i.leimu}</option>
		    		</s:iterator>
	    		</select>
	         </td>
			  <td>${sd.time}</td>	    
	     </tr>   
     </s:iterator> 
      <tr> 
	    <td colspan="19" align="center">
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
  <s:iterator value="strsd" var="i">${i}</s:iterator>
</m:frame>