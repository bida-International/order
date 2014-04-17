<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags"%>
<m:frame>
	<s:form action="./order/statZhDetail.do">
		<table border="1" cellspacing="0" style="width: 99%; float: left">
			<tr>
				<th colspan="5" align="center">账号订单统计数据</th>
			</tr>
			<tr>
				<td colspan="5">
					<span>统计账号：</span>
					<s:select name="zhanghaoId" list="zhangHaoList" listKey="id" listValue="account" value="zhanghaoId"></s:select>
					<span>&nbsp;&nbsp;</span>
					<span>统计单位：</span>
					<s:select name="statType" list="#{1:'日', 2:'周', 3:'月' }" value="statType"/>
					<span>&nbsp;&nbsp;</span>
					<span>统计时间段：</span> 
					<input type="text" name="beginDate" id="beginDate" onfocus="WdatePicker()" value="${beginDate}"/>
					<span>至</span>
					<input type="text" name="endDate" id="endDate"  onfocus="WdatePicker()" value="${endDate}"/>
					<span>&nbsp;&nbsp;</span>
					<input type="submit" value="查 询" />
				</td>
			</tr>
			<tr>
				<td colspan="5" align="center" style="padding:5px;">
					<img src="./${totalMoneyChart }" />
				</td>
			</tr>
			<tr>
				<td colspan="5" align="center" style="padding:5px;">
					<img src="./${orderAmountChart }" />
				</td>
			</tr>
			<tr>
				<td colspan="5" align="center" style="padding:5px;">
					<img src="./${jiufenRateChart }" />
				</td>
			</tr>
			<tr align="center" style="font-weight: bold;">
				<td width="20%">统计时间</td>
				<td width="20%">订单总个数</td>
				<td width="20%">纠纷订单个数</td>
				<td width="20%">纠纷率</td>
				<td width="20%">订单总金额</td>
			</tr>
			<s:iterator value="statList" var="i">
				<tr align="center">
					<td>
						<s:if test="statType == 1">
							<s:property value="getFormatDate(statDate)" />
						</s:if>
						<s:elseif test="statType == 2">
							${i.statYear }年第${i.statWeek }周
						</s:elseif>
						<s:elseif test="statType == 3">
							<s:property value="getFormatDate(statBeginDate)" />
						</s:elseif>
					</td>
					<td>${i.orderAmount }</td>
					<td>${i.jiufenAmount }</td>
					<td>${i.jiufenRate }%</td>
					<td>${i.totalMoney }</td>
				</tr>
			</s:iterator>
		</table>
	</s:form>
</m:frame>
<script type="text/javascript">
	function checkForm() {
		var beginDate = document.getElementById("beginDate").value
		var endDate = document.getElementById("endDate").value;
		if(beginDate == '' || endDate == '') {
			alert("请选择统计时间段");
			return false;
		}
		if(endDate <= beginDate){
			alert("开始时间不能大于结束时间");
			return false;
		}
	}
</script>