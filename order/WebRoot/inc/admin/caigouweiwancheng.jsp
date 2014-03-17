<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript"  src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
  function page(pageNumber) 
 {
	document.getElementById("pageNumber").value = pageNumber;
	document.getElementById("pager.offset").value = (pageNumber*10-10);
	document.forms.submit();
 }
 function myshow()
{
	var usertype = document.getElementById("usertype").value;
	window.location = "admin!getCaiGouWeiOrder.do?usertype="+usertype;
}
</script>
<m:frame>
<form action="admin!getCaiGouWeiOrder.do" method="post"  onsubmit="return as()" name="forms" id="forms">
  <table border="1"  class="datagrid2" id="list" cellspacing="0" style="float:left">
    <tr>
      <td colspan="10" align="center"><strong>采购未完成订单</strong></td>
    </tr>
    <tr>
      <td colspan="8" align="right">
      	订单号：<input type="text" name="orderId" id="orderId" value="${param.orderId}"/>
      	  请选择查询对象(<font color="blue" size="2">默认为采购</font>)：<select name="usertype" id="usertype" onchange="myshow()">
	       				<option value="">-请选择-</option>
	       				<option value="1"
	       				${usertype==1?('selected="selected"'):('')}
	       				>采购</option>
	       				<option value="2"
	       				${usertype==2?('selected="selected"'):('')}
	       				>采购管理员</option>
	       			</select>	 
	       	 <select name="caigouyuan" id="caigouyuan"> 
		     	<option value="">-请选择-</option>
			      	<s:iterator value="getCaiGousd(usertype)" id = "i">
			      		<option value="${i.userid}"
			      		${i.userid==caigouyuan?('selected="selected"'):('')}
			      		>${i.name}</option>
			      	</s:iterator>
		      	</select>
      	</td>
      
      <td><input type="submit" value="查询"/>
        <s:hidden name="pageNumber" value="1"></s:hidden>
		<s:hidden name="pager.offset" id="pager.offset" value="0"></s:hidden>
		<input type="hidden" value="${tit}" name="tit">  
      </td>
    </tr>
    <tr align="center">
     <td width="54"><span class="STYLE2">账号</span></td>
       <td width="90"><span class="STYLE2">订单号</span></td>
      <td width="163"><span class="STYLE2">类目</span></td>
      <td width="65"><span class="STYLE2">货款</span></td>
       <td width="100"><span class="STYLE2">供运商</span></td>
        <td width="163"><span class="STYLE2">备注</span></td>
       <td width="110"><span class="STYLE2">运输单号</span></td>
      <td width="100"><span class="STYLE2">上传时间</span></td>
     <td width="100"><span class="STYLE2">采购员</span></td>
    </tr>
    <s:iterator value="pageBean.list" var="sd">
	     <tr align="center" style="font-size:30px">
	     <td>
	      <s:if test="#sd.zhanghaoId != null">
	     	<s:property value="getZhangHaoId(#sd.zhanghaoId)"/>
	     </s:if>

	     </td>
	          <td>${sd.orderId}</td>
	         	<td>  
	         	<s:if test="#sd.leimuid == null">
	         		${sd.yunshu}
	         	</s:if>
	         <s:if test="#sd.leimuid != null">
	         	<s:property value="getSelLeiMu(#sd.leimuid)"/>
	         </s:if> 
	         </td>
	   		 <td>${sd.huokuan}</td>	  
	   		 <td><textarea>${sd.gongyunshang}</textarea></td>     
	   		 <td><textarea>${sd.remark}</textarea></td> 
		     <td>${sd.danhao}</td>
		      <td>${sd.time}</td>	    
	   		 <td><s:property value="(#sd.caigouyuan==0 || #sd.caigouyuan==null)?('未分配'):(getUserId(#sd.caigouyuan))"/></td>
	     </tr>   
     </s:iterator> 
     <tr> 
	    <td colspan="17" align="center">
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