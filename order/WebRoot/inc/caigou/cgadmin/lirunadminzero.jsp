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
function as(){
	var order = document.getElementById("orderId").value;
	var time = document.getElementById("time").value;
	if(order != '' && time != '')
	{
		alert("时间和订单号不能同时查询");
		return false;
	}
}
	function tiaozhuan(){
		var pageNumber = document.getElementById("pageNumber").value;
		location.href = "caigouadmin!liRunAdmin.do?pager.offset="+(pageNumber*10-10)+"&pageNumber="+pageNumber;
    }
</script>
<m:frame>
<s:form action="caigouadmin!liRunAdmin.do"  onsubmit="return as()" >
  <table border="1" cellspacing="0" style="float:left">
    <tr>
      <td colspan="14" align="center"><strong>利润小于0订单</strong></td>
    </tr>
    <tr>
    	<td colspan="14" align="center">
	  	时间查询：<input type="text" name="time" id="time" onfocus="WdatePicker()" value="${param.time}"/> 至<input type="text" name="time1" id="time1"  onfocus="WdatePicker()" value="${param.time1}"/>
	  	订单号 <input type="text" name="orderId" id="orderId" value="${param.orderId}"/>
	  	<input type="submit" value="查询"/>
	  	</td>
    </tr>
    <tr align="center">
     <td width="54"><span class="STYLE2">账号</span></td>
       <td width="90"><span class="STYLE2">订单号</span></td>
      <td width="163"><span class="STYLE2">类目</span></td>
      <td width="84"><span class="STYLE2">金额($)</span></td>
      <td width="84"><span class="STYLE2">运费</span></td>
      <td width="65"><span class="STYLE2">采购金额</span></td>
      <td width="65"><span class="STYLE2">退款</span></td>
       <td width="100"><span class="STYLE2">供运商</span></td>
        <td width="163"><span class="STYLE2">备注</span></td>
       <td width="110"><span class="STYLE2">运输单号</span></td>
       <td width="100"><span class="STYLE2">完成时间</span></td>
      <td width="100"><span class="STYLE2">上传时间</span></td>
     
    </tr>
    <s:iterator value="pageBean.list" var="sd">
	     <tr align="center" style="font-size:30px">
	     <td>
	     	<s:property value="getZhangHaoId(#sd.zhanghaoId)"/>
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
	   		 <td>${sd.money}</td>
	   		 <td>${sd.yunfei}</td>
	   		 <td>${sd.huokuan}</td>
	   		 <td>${sd.tuikuan}</td>	 	  
	   		 <td><textarea>${sd.gongyunshang}</textarea></td>     
	   		 <td><textarea>${sd.remark}</textarea></td> 
		     <td>${sd.danhao}</td>
		     <td>${sd.wanchengtime}</td>
		      <td>${sd.time}</td>	    
	     </tr>   
     </s:iterator> 
      <tr>     
         <td colspan="17" align="center">
	        总记录数：${pageBean.allRow} 共几页：${pageBean.allPage} 当前第 ${pageBean.currentPage} 页
        <pg:pager url="caigouadmin!liRunAdmin.do" items="${pageBean.allRow}" export="currentPageNumber=pageNumber" maxPageItems="10"> 
 		 <pg:first> 
    		<a href="${pageUrl}&pageNumber=1&orderId=${param.orderId}&time=${param.time}&time1=${param.time1}">首页</a> 
  		 </pg:first> 
  	   <pg:prev> 
   		 <a href="${pageUrl}&pageNumber=${pageBean.currentPage-1}&orderId=${param.orderId}&time=${param.time}&time1=${param.time1}">上一页</a> 
  	   </pg:prev> 
  	 <pg:pages> 
    	<c:choose> 
      	  <c:when test="${currentPageNumber eq pageNumber}"> 
       		 <font color="red">${pageNumber}</font> 
      	  </c:when> 
       <c:otherwise> 
        <a href="${pageUrl}&pageNumber=${pageNumber}&orderId=${param.orderId}&time=${param.time}&time1=${param.time1}">${pageNumber}</a> 
      </c:otherwise> 
    </c:choose> 
  </pg:pages> 
  <pg:next> 
    <a href="${pageUrl}&pageNumber=${pageBean.currentPage+1}&orderId=${param.orderId}&time=${param.time}&time1=${param.time1}">下一页</a> 
  </pg:next> 
  <pg:last> 
    <a href="${pageUrl}&pageNumber=${pageBean.allPage}&orderId=${param.orderId}&time=${param.time}&time1=${param.time1}">尾页</a> 
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