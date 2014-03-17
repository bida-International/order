<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript"  src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="jquery-1.8.2.js"></script>
<m:frame>
	<script type="text/javascript">
			
		function as()
		{
			var orderId = document.getElementById("orderId").value;
			var time = document.getElementById("time").value
			var time1 = document.getElementById("time1").value;
			var zhanghao = document.getElementById("dhgatezhanghao").value;
			if(zhanghao != '' && orderId != ''){
				alert("账号和订单号不能同时查询");
				return false;
			}
			if(orderId != '' && (time !='' && time1 !=''))
			{
				alert("订单号和时间不能同时不为空");
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
     		location.href = "admin!guoJiaYunFei.do?pager.offset="+(pageNumber*10-10)+"&pageNumber="+pageNumber;
     	}
	</script>
	${msg}
	<s:form action="admin!guoJiaYunFei.do" cssStyle="cellspacing:0;cellpadding:0" onsubmit="return as()">
  <table border="1"  class="datagrid2" id="list" width="600" cellspacing="0" style="float:left">
    <tr>
      <td colspan="17" align="center"><strong>显示运费</strong></td>
    </tr>
    <tr>
    <td colspan="17">
    	国家：<input type="text" name="guojia" id="guojia" value="${param.guojia}"/>
    	区域：<input type="text" name="quyu" id="quyu" value="${param.quyu}"/>
    	<input type="submit" value="查询"/>
    </td>
    </tr>
    <tr align="center">     
       <td width="80"><span class="STYLE2">区域</span></td>
       <td width="80"><span class="STYLE2">国家</span></td>
      <td width="80"><span class="STYLE2">金额</span></td>
      <td width="80"><span class="STYLE2">操作</span></td>
    </tr>
    <s:iterator value="pageBean.list" var="i">
      <tr align="center">
        <td>${i.quyu}</td>
        <td>${i.guojia}</td>
	    <td>${i.money}</td>	 	   
	   	 <td>
	   	 /<a href="admin!updateGuoJia.do?yunfei.id=${i.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}">修改</a>
	   	 /<a href="admin!delGuoJia.do?yunfei.id=${i.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}" onclick="return confirm('确定?')">删除</a>
	   	 </td>      
     </tr>    
   </s:iterator>
   <tr>
   </tr>
    <tr> 
	    <td colspan="17" align="center">
	        总记录数：${pageBean.allRow} 共几页：${pageBean.allPage} 当前第 ${pageBean.currentPage} 页
        <pg:pager url="admin!guoJiaYunFei.do" items="${pageBean.allRow}" export="currentPageNumber=pageNumber" maxPageItems="10"> 
 		 <pg:first> 
    		<a href="${pageUrl}&pageNumber=1&guojia=${param.guojia}&quyu=${param.quyu}">首页</a> 
  		 </pg:first> 
  	   <pg:prev> 
   		 <a href="${pageUrl}&pageNumber=${pageBean.currentPage-1}&guojia=${param.guojia}&quyu=${param.quyu}">上一页</a> 
  	   </pg:prev> 
  	 <pg:pages> 
    	<c:choose> 
      	  <c:when test="${currentPageNumber eq pageNumber}"> 
       		 <font color="red">${pageNumber}</font> 
      	  </c:when> 
       <c:otherwise> 
        <a href="${pageUrl}&pageNumber=${pageNumber}&guojia=${param.guojia}&quyu=${param.quyu}">${pageNumber}</a> 
      </c:otherwise> 
    </c:choose> 
  </pg:pages> 
  <pg:next> 
    <a href="${pageUrl}&pageNumber=${pageBean.currentPage+1}&guojia=${param.guojia}&quyu=${param.quyu}">下一页</a> 
  </pg:next> 
  <pg:last> 
    <a href="${pageUrl}&pageNumber=${pageBean.allPage}&guojia=${param.guojia}&quyu=${param.quyu}">尾页</a> 
  </pg:last>
  <pg:page> 
  <input type="text" name="pageNumber" id="pageNumber" size="5"/>
  <input type="button" onclick="tiaozhuan()" value="跳转"/>
  </pg:page>
</pg:pager> 
	    </td>   		
    </tr>
  </table>
  </s:form>
  <br>
</m:frame>