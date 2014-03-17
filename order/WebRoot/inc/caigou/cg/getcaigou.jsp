<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
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
	</script>
	<s:form action="caigou!getselcaigou.do" method="post" cssStyle="cellspacing:0;cellpadding:0" onsubmit="return as()">
  <table border="1"  class="datagrid2" id="list" cellspacing="0" width="1050px" style="float:left">
    <tr>
      <td colspan="14" align="center"><strong>采购订单</strong></td>
    </tr>
     <tr>
    <td colspan="16">
    
         <input type="hidden" name="ordertable.caigouyuan" value="${sessionScope.caigouyuan}"/>
    	订单号：<input type="text" name="orderId" id="orderId" value="${param.orderId}"/>
    	时间：<input type="text" name="time" id="time" onfocus="WdatePicker()" value="${param.time}"/>至<input type="text" name="time1" id="time1"  onfocus="WdatePicker()" value="${param.time1}"/>
    	<input type="submit" value="查询"/>
  	
    </td>
    </tr>
    <tr align="center">
      <td width="150"><span class="STYLE2">订单号</span></td>
       <td width="150"><span class="STYLE2">时间 </span></td>
      <td width="54"><span class="STYLE2">金额</span></td>
       <td width="163"><span class="STYLE2">类目</span></td>
      <td width="163"><span class="STYLE2">运输单号</span></td>
       <td width="64"><span class="STYLE2">运费</span></td>
      <td width="54"><span class="STYLE2">货款</span></td>
       <td width="163"><span class="STYLE2">国家</span></td>
      <td width="170"><span class="STYLE2">备注</span></td>
      <td width="150"><span class="STYLE2">供运商</span></td>
      <td width="64"><span class="STYLE2">退款</span></td>
      <td width="64"><span class="STYLE2">汇率</span></td>
       <td width="64"><span class="STYLE2">利润</span></td>
       <td width="120"><span class="STYLE2">账号</span></td>
    </tr>
    <s:iterator value="caiGouAll.pagelist" var="i">
      <tr align="center">
        <td>${i.orderId}</td>
        <td>${i.time}</td>
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
	    <td>${i.guojia}</td>
	    <td>${i.remark}</td>	 
	    <td>${i.gongyunshang}</td> 
	    <td>${i.tuikuan}</td>
	    <td>${i.huilv}</td>
	    <td></td>     
	     <td>
	     	<s:property value="getZhangHaoId(#i.zhanghaoId)"/>
	     </td>
	    <td><input type="hidden" name="ordertable.caigouyuan" value="${i.caigouyuan}"/></td> 
     </tr>    
   </s:iterator>
   <tr>
   	   	<td colspan="16">
   		总金额：<s:property value="getMoney(orderId,time,time1)" />
   		总运费：<s:property value="getYunFei(orderId,time,time1)"/>
   		总货款：<s:property value="getHuoKuan(orderId,time,time1)"/>
   		退款：<s:property value="getTuiKuan(orderId,time,time1)"/>
   		平均汇率：<s:property value="getHuiLv(orderId,time,time1)"/>
   		总汇率：<s:property value="getAllHuiLv(orderId,time,time1)"/>
   		纠纷个数：<s:property value="getJiuFen(orderId,time,time1)"/>	
   	</td>
   </tr>
    <tr>    
        <td colspan="14" align="center">
       		 共${caiGouAll.count}条数据 共${caiGouAll.pagecount}页 第${caiGouAll.pageindex}页 <a href="caigou!getCaiGous.do?pageindex=1&orderId=${param.orderId}&time=${param.time}&time1=${param.time1}&ordertable.caigouyuan=${i.caigouyuan}">首页</a>
            <s:if test="%{caiGouAll.pageindex>1}"> <a href="caigou!getCaiGous.do?pageindex=${caiGouAll.pageindex-1}&orderId=${param.orderId}&time=${param.time}&time1=${param.time1}&ordertable.caigouyuan=${i.caigouyuan}">上一页 </a> </s:if>
            <s:if test="%{caiGouAll.pagecount>caiGouAll.pageindex}"> <a href="caigou!getCaiGous.do?pageindex=${caiGouAll.pageindex+1}&orderId=${param.orderId}&time=${param.time}&time1=${param.time1}&ordertable.caigouyuan=${i.caigouyuan}">下一页</a> </s:if>
          <a href="caigou!getCaiGous.do?pageindex=${caiGouAll.pagecount}&orderId=${param.orderId}&time=${param.time}&time1=${param.time1}&ordertable.caigouyuan=${i.caigouyuan}"> 尾页 </a></td>
    </tr>
  </table>
  <br>
  </s:form>
</m:frame>