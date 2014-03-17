<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript"  src="My97DatePicker/WdatePicker.js"></script>
<m:frame>
	<script type="text/javascript">
		function as()
		{
			var orderId = document.getElementById("orderId").value;
			var time = document.getElementById("time").value
			var time1 = document.getElementById("time1").value;
			if(orderId != '' && (time !='' && time1 !=''))
			{
				alert("订单号和时间不能同时不为空");
				return false;
			}
			if(time1<time){
				alert("开始时间一定小于结束时间");
				return false;
			}
		}
		function tiaozhuan(){
     		var pageNumber = document.getElementById("pageNumber").value;
     		location.href = "admin!getCaiGouAll.do?pager.offset="+(pageNumber*10-10)+"&pageNumber="+pageNumber+"&caigouyuan="+${sessionScope.caigouyuan};
     	}
	</script>
	<form action="admin!getCaiGouAll.do" method="post">
  <table border="1"  class="datagrid2" width="1100px" id="list" cellspacing="0" style="float:left">
    <tr>
      <td colspan="14" align="center"><strong>采购订单</strong></td>
    </tr>
     <tr>
    <td colspan="16">
         <input type="hidden" name="caigouyuan" id="caigouyuan" value="${sessionScope.caigouyuan}"/>
    	订单号：<input type="text" name="orderId" id="orderId" value="${param.orderId}"/>
    	时间：<input type="text" name="time" id="time" onfocus="WdatePicker()" value="${param.time}"/>至<input type="text" name="time1" id="time1"  onfocus="WdatePicker()" value="${param.time1}"/>
    	<input type="submit" value="查询"/>
  		
    </td>
    </tr>
    <tr align="center">
      <td width="150"><span class="STYLE2">订单号</span></td>
       <td width="150"><span class="STYLE2">时间 </span></td>
      <td width="80"><span class="STYLE2">金额($)</span></td>
       <td width="163"><span class="STYLE2">类目</span></td>
      <td width="163"><span class="STYLE2">运输单号</span></td>
       <td width="64"><span class="STYLE2">运费</span></td>
      <td width="54"><span class="STYLE2">货款</span></td>
       <td width="163"><span class="STYLE2">速卖通地址</span></td>
      <td width="170"><span class="STYLE2">备注</span></td>
      <td width="150"><span class="STYLE2">供运商</span></td>
      <td width="64"><span class="STYLE2">退款</span></td>
      <td width="64"><span class="STYLE2">汇率</span></td>
       <td width="64"><span class="STYLE2">利润</span></td>
       <td width="120"><span class="STYLE2">账号</span></td>
    </tr>
    <s:iterator value="pageBean.list" var="i">
    <s:if test="(#i.huokuan == null) || (#i.yunfei == null)">
      <tr align="center" style="color: red">
        <td>${i.orderId}</td>
        <td>${i.time}</td>
	    <td>${i.money}</td>	 
	    <td><s:property value="getSelLeiMu(#i.leimuid)"/></td>  
	    <td>${i.danhao}</td>
	    <td><fmt:formatNumber value="${i.yunfei}" pattern="0.00"></fmt:formatNumber></td>
	    <td>${i.huokuan}</td>
	    <td>${i.guojia}</td>
	    <td>${i.remark}</td>	 
	    <td>${i.gongyunshang}</td> 
	    <td>${i.tuikuan}</td>
	    <td>${i.huilv}</td>
	    <td></td>     
	     <td><s:property value="getZhangHaoId(#i.zhanghaoId)"/></td> 
     </tr>   
      </s:if> 
      <s:if test="(#i.huokuan != null)&&(#i.yunfei != null)">
        <tr align="center">
        <td>${i.orderId}</td>
        <td>${i.time}</td>
	    <td>${i.money}</td>	 
	    <s:if test="#i.leimuid == null">
	         		<td>${i.yunshu}</td>
	         	</s:if>
	         <s:if test="#i.leimuid != null">
	         	<td><s:property value="getSelLeiMu(#i.leimuid)"/></td>
	         </s:if>  
	    <td>${i.danhao}</td>
	    <td><fmt:formatNumber value="${i.yunfei}" pattern="0.00"></fmt:formatNumber></td>
	    <td>${i.huokuan}</td>
	    <td>${i.guojia}</td>
	    <td>${i.remark}</td>	 
	    <td>${i.gongyunshang}</td> 
	    <td>${i.tuikuan}</td>
	    <td>${i.huilv}</td>
	    <td></td>     
	     <td>
	     <s:if test="#i.zhanghaoId != null">
	    	 <s:property value="getZhangHaoId(#i.zhanghaoId)"/>
	     </s:if>
	      <s:if test="(#i.zhanghaoId == null && #i.time !=null)">
	     	 <s:property value="getDhgateId(#i.dhgateid)"/>
	     </s:if>
	     </td> 
     </tr>   
      </s:if>
   </s:iterator>
   <tr>
   	   	<td colspan="16">
   		总金额($)：<s:property value="getCaiGouAllMoney(caigouyuan,orderId,time,time1)" />
   		总运费：<s:property value="getCaiGouAllYunFei(caigouyuan,orderId,time,time1)"/>
   		总采购货款：<s:property value="getCaiGouAllHuoKuan(caigouyuan,orderId,time,time1)"/>
   		退款个数：<s:property value="getCaiGouAllTuiKuan(caigouyuan,orderId,time,time1)"/>
   		纠纷个数：<a href="admin!getJiuFenNum.do?caigouyuan=${sessionScope.caigouyuan}&time=${param.time}&time1=${param.time1}"><s:property value="getCaiGouAllJiuFen(caigouyuan,orderId,time,time1)"/></a>
   		退款总金额($): <s:property value="getCaiGouTuiKuan(caigouyuan,orderId,time,time1)"/>
   		纠纷总额($):<s:property value="getCaiGouJiuFenMoney(caigouyuan,orderId,time,time1)"/><br>
   		运费为空：<a href="admin!getChaYunFeiNull.do?caigouyuan=${sessionScope.caigouyuan}&time=${param.time}&time1=${param.time1}"><s:property value="getYunFeiAll(caigouyuan,orderId,time,time1)"/></a>
   		货款为空：<a href="admin!getChaHuoKuanNull.do?caigouyuan=${sessionScope.caigouyuan}&time=${param.time}&time1=${param.time1}"><s:property value="getHuoKuanAll(caigouyuan,orderId,time,time1)"/></a>
   	</td>
   </tr>
  <tr> 
	    <td colspan="17" align="center">
	        总记录数：${pageBean.allRow} 共几页：${pageBean.allPage} 当前第 ${pageBean.currentPage} 页
        <pg:pager url="admin!getCaiGouAll.do" items="${pageBean.allRow}" export="currentPageNumber=pageNumber" maxPageItems="10"> 
 		 <pg:first> 
    		<a href="${pageUrl}&pageNumber=1&orderId=${param.orderId}&time=${param.time}&time1=${param.time1}&caigouyuan=${sessionScope.caigouyuan}">首页</a> 
  		 </pg:first> 
  	   <pg:prev> 
   		 <a href="${pageUrl}&pageNumber=${pageBean.currentPage-1}&orderId=${param.orderId}&time=${param.time}&time1=${param.time1}&caigouyuan=${sessionScope.caigouyuan}">上一页</a> 
  	   </pg:prev> 
  	 <pg:pages> 
    	<c:choose> 
      	  <c:when test="${currentPageNumber eq pageNumber}"> 
       		 <font color="red">${pageNumber}</font> 
      	  </c:when> 
       <c:otherwise> 
        <a href="${pageUrl}&pageNumber=${pageNumber}&orderId=${param.orderId}&time=${param.time}&time1=${param.time1}&caigouyuan=${sessionScope.caigouyuan}">${pageNumber}</a> 
      </c:otherwise> 
    </c:choose> 
  </pg:pages> 
  <pg:next> 
    <a href="${pageUrl}&pageNumber=${pageBean.currentPage+1}&orderId=${param.orderId}&time=${param.time}&time1=${param.time1}&caigouyuan=${sessionScope.caigouyuan}">下一页</a> 
  </pg:next> 
  <pg:last> 
    <a href="${pageUrl}&pageNumber=${pageBean.allPage}&orderId=${param.orderId}&time=${param.time}&time1=${param.time1}&caigouyuan=${sessionScope.caigouyuan}">尾页</a> 
  </pg:last>
  <pg:page> 
  <input type="text" name="pageNumber" id="pageNumber" size="5"/>
  <input type="button" onclick="tiaozhuan()" value="跳转"/>
  </pg:page>
</pg:pager> 
	    </td>   		
    </tr>
  </table>
  <br>
</form>
</m:frame>