<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript"  src="My97DatePicker/WdatePicker.js"></script>
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
	
	  function chulis(){
	 	var items=document.getElementsByName("chkItems"); 
	 	var lujing = "yewu1!BusinessProcess.do?pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
	 	 bianhao(items,lujing);
	 }
</script>
<m:frame>
<form  action="yewu1!getOrders.do" method="post" id="forms" name="forms">	
<table>
	<tr>
		<td>
			<input type="button" value="处理完毕" onclick="chulis()">
		</td>
	</tr>
</table>
  <table border="1"  width="700px" cellspacing="0" style="float:left" >
    <tr>
      <td colspan="5" align="center"><strong>得到订单</strong>
    </tr>
    <tr>
    	<td colspan="5" align="center">
	    
	  	订单号: <input type="text" name="orderId" id="orderId" value="${param.orderId}"/>
        <s:hidden name="pageNumber" value="1"></s:hidden>
		<s:hidden name="pager.offset" id="pager.offset" value="0"></s:hidden>
		<input type="hidden" value="${tit}" name="tit">  
	  	<input type="submit" value="查询"/>
	  	</td>
    </tr>
    <tr align="center">
    <td width="34" height="25">
		<input name="checkbox" onclick="quan()" type="checkbox" style="cursor:pointer"/></td>
     <td width="54"><span class="STYLE2">账号</span></td>
       <td width="90"><span class="STYLE2">订单号</span></td>
       <td width="90"><span class="STYLE2">上传时间</span></td>
        <td width="90"><span class="STYLE2">编码</span></td>   
    </tr>
    <s:iterator value="pageBean.list" var="sd">
	     <tr align="center" style="font-size:30px">
	     <td><input name="chkItems" value="${sd.id}" onclick="chkItems_click(this)" type="checkbox"  id="chk_${sd.id}" readonly="readonly"  style="cursor:pointer"/></td>
	     <td><s:property value="getZhangHaoId(#sd.zhanghaoId)"/></td>
	     <td><font color="blue">${sd.orderId}</font></td>
	        <td>${sd.time}</td>
	          <td>${sd.bianma}</td> 
	     </tr>   
     </s:iterator> 
    <tr>     
         <td colspan="5" align="center">
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
<s:iterator value="#strsd" var="i">
${i}
</s:iterator>
  <br>
</m:frame>