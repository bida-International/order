<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript"  src="My97DatePicker/WdatePicker.js"></script>
	
<script type="text/javascript">
<!--

//-->
function as(){
	var order = document.getElementById("orderId").value;
	var time = document.getElementById("time").value;
	var time1 = document.getElementById("time1").value;
	var chuli = document.getElementById("chuli").value;
	if(order != '' && (time != '' && time != ''))
	{
		alert("时间和订单号不能同时查询");
		return false;
	}
	if(time != '' && time1 == ''){
		alert("查询时间未选");
		return false;
	}
	if(time == '' && time1 != '')
	{
		alert("查询时间未选");
		return false;
	}
	if(time1<time){
		alert("开始时间一定小于结束时间");
		return false;
	}
}
	function tiaozhuan(){
  		var pageNumber = document.getElementById("pageNumber").value;
  		location.href = "admin!getTuiKuanOrder.do?pager.offset="+(pageNumber*10-10)+"&pageNumber="+pageNumber;
   	}
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
<form action="admin!getTuiKuanOrder.do"  onsubmit="return as()" name="forms" id="forms" method="post">
  <table border="1" width="800px"  class="datagrid2" id="list" cellspacing="0" style="float:left">
    <tr>
      <td colspan="8" align="center"><strong>退货管理</strong></td>
    </tr>
    <tr>
    	<td colspan="8">
    	订单号：<input type="text" name="orderId" id="orderId" value="${param.orderId}"/>
        国际运输单号：<input type="text" name="danhao" id="danhao" value="${param.danhao}"/>
    	是否处理：
    	<select name="chuli" id="chuli">
	  		<option value="">-请选择-</option>
	  		<option value="0"
	  			${chuli==0?('selected="selected"'):('')}
	  		>否</option>
	  		<option value="1"
	  			${chuli==1?('selected="selected"'):('')}
	  		>是</option>
	  	</select>
    	<input type="submit" value="查询"/>
    	  <s:hidden name="pageNumber" value="1"></s:hidden>
		<s:hidden name="pager.offset" id="pager.offset" value="0"></s:hidden>
		<input type="hidden" value="${tit}" name="tit">  
    </td>
    </tr>
    <tr align="center">
     <td width="84"><span class="STYLE2">账号</span></td>
       <td width="90"><span class="STYLE2">订单号</span></td>
        <td width="60"><span class="STYLE2">是否处理</span></td>
       <td width="60"><span class="STYLE2">运输单号</span></td>
       <td width="80"><span class="STYLE2">供运商</span></td>
      <td width="100"><span class="STYLE2">上传时间</span></td>
      <td width="100"><span class="STYLE2">采购员</span></td>
      <td width="60"><span class="STYLE2">备注</span></td>
    </tr>
    <s:iterator value="pageBean.list" var="sd">
	     <tr align="center" style="font-size:30px">
	     <td>
	      <s:if test="#sd.zhanghaoId==15">
       		${sd.name}
       	  </s:if>
           <s:if test="#sd.zhanghaoId != 15">
       			<s:property value="getZhangHaoId(#sd.zhanghaoId)"/>
            </s:if>
  			</td>
	          <td>
	          ${sd.orderId}
	          <s:if test="#sd.tuihuo==1">
	        		<font color="red">此单为退货订单</font>
	         </s:if>
	          </td>
	          <td>${(sd.chuli==0||sd.chuli==null)?('否'):('是')}</td>
		     <td><textarea rows="5" cols="10">${sd.danhao}</textarea></td>
		     <td><textarea rows="5" cols="10">${sd.gongyunshang}</textarea></td>
		      <td>${sd.time}</td>	   
		        <td><s:property value="#sd.caigouyuan==0?('未分配'):(getUserId(#sd.caigouyuan))"/></td> 
		      <td><textarea rows="5" cols="10">${sd.remark}</textarea></td>
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
  ${msg}
  </form>
  <br>
</m:frame>