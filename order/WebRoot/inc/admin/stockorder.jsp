<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript"  src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="./inc/js/bianhao.js"></script>
<script type="text/javascript">
	 function quan()
	 {
		 var h=document.getElementsByName("checkbox");
		 var da=document.getElementsByName("chkItems");
		 quan1(h,da);
	 }
     function page(pageNumber) 
	 {
		document.getElementById("pageNumber").value = pageNumber;
		document.getElementById("pager.offset").value = (pageNumber*10-10);
		document.forms.submit();
	 }

</script>
<m:frame>

<form  action="admin!getStockOrderAll.do" method="post" onsubmit="return as()" id="forms" name="forms">
	
  <table border="1"  width="700px" cellspacing="0" style="float:left" >
    <tr>
      <td colspan="11" align="center"><strong>SKU订单</strong></td>
    </tr>
    <tr>
    	<td colspan="11" align="center">
	  	时间查询：<input type="text" name="time" id="time" onfocus="WdatePicker()" value="${param.time}"/> 至<input type="text" name="time1" id="time1"  onfocus="WdatePicker()" value="${param.time1}"/>  
        <s:hidden name="pageNumber" value="1"></s:hidden>
		<s:hidden name="pager.offset" id="pager.offset" value="0"></s:hidden>
		<input type="hidden" value="${tit}" name="tit">  
	  	<input type="submit" value="查询"/>
	  	</td>
    </tr>
    <tr align="center">
    <td width="34" height="25">
		<input name="checkbox" onclick="quan()" type="checkbox" style="cursor:pointer"/></td>
	 <td width="80"><span class="STYLE2">图片</span></td>
     <td width="54"><span class="STYLE2">物品</span></td>
      <td width="90"><span class="STYLE2">编码</span></td>
      <td width="90"><span class="STYLE2">总价</span></td>
      <td width="90"><span class="STYLE2">单价</span></td>
      <td width="90"><span class="STYLE2">数量</span></td>
        <td width="90"><span class="STYLE2">供运商</span></td>
       <td width="100"><span class="STYLE2">上传时间</span></td>
       <td width="100"><span class="STYLE2">采购员</span></td>
       <td width="100"><span class="STYLE2">操作</span></td>
    </tr>
    <s:iterator value="pageBean.list" var="sd">
	     <tr align="center" style="font-size:30px">
		     <td><input name="chkItems" value="${sd.id}" onclick="chkItems_click(this)" type="checkbox"  id="chk_${sd.id}" readonly="readonly"  style="cursor:pointer"/></td>
		     <td><img width="80px" height="80px" src="${sd.uploadFile}"></td>	
			 <td>${sd.wuping}</td>
			 <td>${sd.coding}</td>
			 <td>${sd.totalprice}</td>
			 <td>${sd.unitprice}</td>
			 <td>${sd.num}</td>
			  <td>${sd.transportproviders}</td>
			 <td>${sd.time}</td>	  
			 <td><s:property value="getUserId(#sd.caigouyuan)"/></td>	  
			  <td><a href="admin!upStock.do?kucuntable.id=${sd.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}">修改库存</a></td>  
	     </tr>   
     </s:iterator>
     <tr>
     	<td colspan="11">
     		总金额：<s:property value="getTheTotalAmount(time,time1)"/> 
     	</td>
     </tr> 
    <tr>     
         <td colspan="11" align="center">
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
  <br>
</m:frame>