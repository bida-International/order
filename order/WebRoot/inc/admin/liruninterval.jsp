<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
		}
	   function page(){
	  	var pp = document.getElementById("pageindex").value;
	  	window.location = "admin!liruninterval.do?pageindex="+pp;
	  }
	</script>
	<s:form action="admin!liruninterval.do" cssStyle="cellspacing:0;cellpadding:0" onsubmit="return as()">
  <table border="1" width="1143px"  class="datagrid2" id="list" cellspacing="0" style="float:left">
    <tr>
      <td colspan="16" align="center"><strong>显示全部利润大于0小于30订单</strong></td>
    </tr>
     <tr>
    <td colspan="16">
    	
	   	订单号：<input type="text" name="orderId" id="orderId" value="${param.orderId}"/>
	   	时间：<input type="text" name="time" id="time" onfocus="WdatePicker()" value="${param.time}"/>至<input type="text" name="time1" id="time1"  onfocus="WdatePicker()" value="${param.time1}"/>
	   	<input type="submit" value="查询"/>
    </td>
    </tr>
    <tr align="center">
     <td width="163"><span class="STYLE2">日期</span></td>
      <td width="90"><span class="STYLE2">订单号</span></td>
      <td width="100"><span class="STYLE2">金额($)</span></td>
       <td width="163"><span class="STYLE2">类目</span></td>
      <td width="163"><span class="STYLE2">运输单号</span></td>
       <td width="63"><span class="STYLE2">运费</span></td>
      <td width="54"><span class="STYLE2">货款</span></td>
       <td width="163"><span class="STYLE2">速卖通地址</span></td>
      <td width="120"><span class="STYLE2">供运商</span></td>
      <td width="64"><span class="STYLE2">退款</span></td>
       <td width="64"><span class="STYLE2">汇率</span></td>
       <td width="160"><span class="STYLE2">备注</span></td>
       <td width="160"><span class="STYLE2">是否纠纷</span></td>
       <td width="120"><span class="STYLE2">账号</span></td>
      <td width="163"><span class="STYLE2">采购员</span></td>
      <td width="163"><span class="STYLE2">利润</span></td>
    </tr>
    <s:iterator value="liRunInterval.pagelist" var="i">
      <tr align="center">
        <td>${i.time}</td>
        <td>${i.orderId}</td>
	    <td>${i.money}</td>	 
	   <s:if test="#i.leimuid == null">
	         		<td>
	         			${i.yunshu}
	         		</td>
	         	</s:if>
	         <s:if test="#i.leimuid != null">
	         	<td><s:property value="getSelLeiMu(#i.leimuid)"/></td>
	         </s:if>  
	    <td>${i.danhao}</td>
	    <td>${i.yunfei}</td>	 
	    <td>${i.huokuan}</td> 
	    <td><textarea rows="5" cols="10">${i.guojia}</textarea></td>	 
	    <td>${i.gongyunshang}</td>       
	    <td>${i.tuikuan}</td>
	    <td>${i.huilv}</td>
	     <td>${i.remark}</td>
	     <td>${(i.jiufen==0 || i.jiufen==null)?('否'):('是')}</td>
	    <td>
	     	<s:property value="getZhangHaoId(#i.zhanghaoId)"/>
	    </td>
	   	 <td><s:property value="(#i.caigouyuan==0 || #i.caigouyuan == null)?('未分配'):(getUserId(#i.caigouyuan))"/></td>	
	   	 <td><fmt:formatNumber value="${i.money*i.huilv-(i.huokuan+i.yunfei+i.tuikuan*i.huilv)}" pattern="0.00"></fmt:formatNumber></td>      
     </tr>    
   </s:iterator>
    <tr> <td colspan="16" align="center">
   				 转到<select name="pageindex" id="pageindex" onchange="page()">
				<option value="0">-请选择-</option>
				<s:iterator begin="1" end="liRunInterval.pagecount" status="ix">
				<option value="${ix.index+1}">${ix.index+1}</option>
				</s:iterator>
				</select>页
       		 共${liRunInterval.count}条数据 共${liRunInterval.pagecount}页 第${liRunInterval.pageindex}页 <a href="admin!liruninterval.do?pageindex=1&orderId=${param.orderId}&time=${param.time}&time1=${param.time1}">首页</a>
            <s:if test="%{liRunInterval.pageindex>1}"> <a href="admin!liruninterval.do?pageindex=${liRunInterval.pageindex-1}&orderId=${param.orderId}&time=${param.time}&time1=${param.time1}">上一页 </a> </s:if>
            <s:if test="%{liRunInterval.pagecount>liRunInterval.pageindex}"> <a href="admin!liruninterval.do?pageindex=${liRunInterval.pageindex+1}&orderId=${param.orderId}&time=${param.time}&time1=${param.time1}">下一页</a> </s:if>
          <a href="admin!liruninterval.do?pageindex=${liRunInterval.pagecount}&orderId=${param.orderId}&time=${param.time}&time1=${param.time1}"> 尾页 </a></td></tr>
  </table>
  </s:form>
  <br>
</m:frame>