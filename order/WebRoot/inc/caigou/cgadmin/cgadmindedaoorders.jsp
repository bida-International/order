<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<script type="text/javascript" src="./inc/js/bianhao.js"></script>
<m:frame>
<script>
	function dedao(){
		var items=document.getElementsByTagName("input"); 
		 var lujing = "caigouadmin!caigouadmindedao.do?pager.offset="+(pageNumber*10-10)+"&pageNumber="+pageNumber;
		 bianhao(items,lujing);
	}	
	 function quan()
	 {
		 var h=document.getElementsByName("checkbox");
		 var da=document.getElementsByName("chkItems");
		  quan1(h,da);
	  }
	  function tiaozhuan(){
		var pageNumber = document.getElementById("pageNumber").value;
		location.href = "caigouadmin!getAdminDeDaoOrder.do?pager.offset="+(pageNumber*10-10)+"&pageNumber="+pageNumber;
  	  }
</script>

<s:form  action="caigouadmin!getAdminDeDaoOrder.do" cssStyle="cellspacing:0;cellpadding:0" onsubmit="return as()">
${wancheng}
  <table border="1" cellspacing="0" style="float:left"  class="datagrid2" id="list">
    <tr>
      <td colspan="10" align="center"><strong>得到订单</strong></td> 
      <td><input type="button" style="cursor:pointer" value="采购管理员得到订单 " onClick="dedao()"></td>
    </tr>
      <tr>
    	<td colspan="11" align="center">
    	查询页数<input type="text" size="5" name="pageindex"/>
	  	订单号 <input type="text" name="orderId" id="orderId" value="${param.orderId}"/>
	  	时间查询：<input type="text" name="time" id="time" onfocus="WdatePicker()" value="${param.time}"/> 
	  	<input type="submit" value="查询"/>
	  	</td>
    </tr>
    <tr align="center">
       <td width="34" height="25">
		<input name="checkbox" onclick="quan()" type="checkbox" style="cursor:pointer"/></td>
        <td width="54"><span class="STYLE2">账号</span></td>
       <td width="90"><span class="STYLE2">订单号</span></td>
     <td width="163"><span class="STYLE2">类目</span></td>
      <td width="130"><span class="STYLE2">采购金额</span></td>
      <td width="130"><span class="STYLE2">订单金额</span></td>
       <td width="100"><span class="STYLE2">供运商</span></td>
        <td width="163"><span class="STYLE2">备注</span></td>
        <td width="100"><span class="STYLE2">完成情况</span></td>
        <td width="163"><span class="STYLE2">运输单号</span></td>
       <td width="100"><span class="STYLE2">上传时间</span></td>
    </tr>
    <s:iterator value="pageBean.list" var="sd" >
	     <tr align="center" style="cursor:pointer">
	     <td><input name="chkItems" value="${sd.id}" onclick="chkItems_click(this)" type="checkbox"  id="chk_${sd.id}" readonly="readonly"  style="cursor:pointer"/></td>
	         <td><s:property value="getZhangHaoId(#sd.zhanghaoId)"/></td>
	          <td>${sd.orderId}</td>
	          <td>
	         	<c:if test="${sd.leimuid == null}">
	         		<textarea rows="5" cols="10">
	         			${sd.yunshu}
	         		</textarea>
	        </c:if>
	        <c:if test="${sd.leimuid != null}">
	         	<textarea rows="5" cols="10"><s:property value="getSelLeiMu(#sd.leimuid)"/></textarea>
	         </c:if> 
	         </td>
	   		 <td>${sd.huokuan}</td>	  
	   		 <td>${sd.money}</td>
	   		 <td><textarea>${sd.gongyunshang}</textarea></td>     
	   		 <td><textarea>${sd.remark}</textarea></td> 
	   		  <td>${(sd.wancheng==0 || sd.wancheng==null)?('未完成'):('已完成')}</td>       
		     <td>${sd.danhao}</td>
		     <td>${sd.time}</td>		
	     </tr>   
     </s:iterator> 
	 <tr>     
         <td colspan="17" align="center">
	        总记录数：${pageBean.allRow} 共几页：${pageBean.allPage} 当前第 ${pageBean.currentPage} 页
        <pg:pager url="caigouadmin!getAdminDeDaoOrder.do" items="${pageBean.allRow}" export="currentPageNumber=pageNumber" maxPageItems="10"> 
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
  <br>
</m:frame>