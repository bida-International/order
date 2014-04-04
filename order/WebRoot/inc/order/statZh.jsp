<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags"%>
<m:frame>
	<s:form action="./order/statZh.do">
		<table border="1" cellspacing="0" style="width: 99%; float: left">
			<tr>
				<th colspan="5" align="center">账号订单统计数据</th>
			</tr>
			<tr>
				<td colspan="5">
					<span>请选择统计时间段：</span> 
					<s:select name="beginDate" list="dates" value="beginDate"></s:select>
					<span> 到 </span>
					<s:select name="endDate" list="dates" value="endDate"></s:select>
					<span>&nbsp;&nbsp;</span>
					<input type="submit" value="查 询" />
				</td>
			</tr>
			<tr align="center" style="font-weight: bold;">
				<td width="20%">账号</td>
				<td width="20%">订单总个数</td>
				<td width="20%">纠纷订单个数</td>
				<td width="20%">纠纷率</td>
				<td width="20%">订单总金额</td>
			</tr>
			<s:iterator value="orderStatZhs" var="i">
				<tr align="center">
					<td>${i.zhanghaoAccount }</td>
					<td>${i.orderAmount }</td>
					<td>${i.jiufenAmount }</td>
					<td>${i.jiufenRate }%</td>
					<td>${i.totalMoney }</td>
				</tr>
			</s:iterator>
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
		</table>
	</s:form>
</m:frame>