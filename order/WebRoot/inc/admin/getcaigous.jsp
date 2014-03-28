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
		 function page(pageNumber) 
		 {
			document.getElementById("pageNumber").value = pageNumber;
			document.getElementById("pager.offset").value = (pageNumber*10-10);
			document.forms.submit();
		 }
		function myshow()
		{
			var usertype = document.getElementById("usertype").value;
			window.location = "admin!getFilter.do?usertype="+usertype;
		}
	</script>
	<form action="admin!getFilter.do" method="post" name="forms" id="forms">
  <table border="1"  class="datagrid2" width="1100px" id="list" cellspacing="0" style="float:left">
    <tr>
      <td colspan="14" align="center"><strong>采购订单</strong></td>
    </tr>
     <tr>
    <td colspan="14">
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
    	订单号：<input type="text" name="orderId" id="orderId" value="${param.orderId}"/>
    	时间：<input type="text" name="time" id="time" onfocus="WdatePicker()" value="${param.time}"/>至<input type="text" name="time1" id="time1"  onfocus="WdatePicker()" value="${param.time1}"/>
    	   <s:hidden name="pageNumber" value="1"></s:hidden>
		<s:hidden name="pager.offset" id="pager.offset" value="0"></s:hidden>
		<input type="hidden" value="${tit}" name="tit"> 
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
   <s:if test="caigouyuan != null">
   <tr>
   	   	<td colspan="14">
   		总金额($)：<s:property value="getCaiGouAllMoney(caigouyuan,orderId,time,time1)" />
   		总运费：<s:property value="getCaiGouAllYunFei(caigouyuan,orderId,time,time1)"/>
   		总采购货款：<s:property value="getCaiGouAllHuoKuan(caigouyuan,orderId,time,time1)"/>
   		退款个数：<s:property value="getCaiGouAllTuiKuan(caigouyuan,orderId,time,time1)"/>
   		纠纷个数：<a href="admin!getJiuFenNum.do?caigouyuan=${param.caigouyuan}&time=${param.time}&time1=${param.time1}"><s:property value="getCaiGouAllJiuFen(caigouyuan,orderId,time,time1)"/></a>
   		退款总金额($): <s:property value="getCaiGouTuiKuan(caigouyuan,orderId,time,time1)"/>
   		纠纷总额($):<s:property value="getCaiGouJiuFenMoney(caigouyuan,orderId,time,time1)"/><br>
   		运费为空：<a href="admin!getChaYunFeiNull.do?caigouyuan=${param.caigouyuan}&time=${param.time}&time1=${param.time1}"><s:property value="getYunFeiAll(caigouyuan,orderId,time,time1)"/></a>
   		货款为空：<a href="admin!getChaHuoKuanNull.do?caigouyuan=${param.caigouyuan}&time=${param.time}&time1=${param.time1}"><s:property value="getHuoKuanAll(caigouyuan,orderId,time,time1)"/></a>
   	</td>
   </tr>
  </s:if>
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
  <br>
</form>
</m:frame>