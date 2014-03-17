<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
	 function quan()
	 {
		 var h=document.getElementsByName("checkbox");
		 var da=document.getElementsByName("chkItems");
		 quan1(h,da);
	  }
	 function upCategory(){
	 	var items=document.getElementsByTagName("input"); 
	 	var userid = document.getElementsByName("yunshu");
	    var lujing = "yewu!upCategory.do?pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
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
<form action="yewu!getModifyCategory.do" method="post"  name="forms" id="forms" onsubmit="return as()">
<table><tr><td><input type="button" value="修改类目" onclick="upCategory()"> </td></tr></table>
  <table border="1" width="600px" cellspacing="0" style="float:left">
    <tr>
      <td colspan="4" align="center" style="cursor: pointer;"><strong>修改类目</strong>
       	<s:hidden name="pageNumber" value="1"></s:hidden>
	<s:hidden name="pager.offset" id="pager.offset" value="0"></s:hidden>
	<input type="hidden" value="${tit}" name="tit">  
	</td>
    </tr>
    <tr align="center">
     <td width="34" height="25">
		<input name="checkbox" onclick="quan()" type="checkbox" style="cursor:pointer"/></td>
     <td width="120"><span class="STYLE2">订单号</span></td>
      <td width="100"><span class="STYLE2">编码</span></td>
      <td width="100"><span class="STYLE2">类目</span></td>
    </tr>
    <s:iterator value="pageBean.list" var="i">
      <tr align="center">
        <td><input name="chkItems" value="${i.id}" onclick="chkItems_click(this)" type="checkbox"  id="chk_${i.id}" readonly="readonly"  style="cursor:pointer"/></td>
        <td>${i.orderId}</td>
        <td>${i.itemcode}</td>
         <td>	     
			   	<select name="yunshu" id="yunshu" title="类目"  onchange="checkedSel()">
			    		<option value="0">-请选择 -</option>
			    		<s:iterator value="getAllLeiMu()" id="i">
			    			<option value="${i.id}">${i.leimu}</option>
			    		</s:iterator>
			    	</select>			
	    </td>    
     </tr>    
   </s:iterator>
     <tr>     
         <td colspan="4" align="center">
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
  <br>
  </form>
  <s:iterator value="strsd" var="i">${i}</s:iterator>
</m:frame>