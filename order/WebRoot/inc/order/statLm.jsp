<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags"%>
<m:frame>
	<s:form action="./order/statLm.do">
		<table border="1" cellspacing="0" style="width: 99%; float: left">
			<tr>
				<th colspan="6" align="center">类目订单统计数据</th>
			</tr>
			<tr>
				<td colspan="6">
					<span>统计时间段：</span> 
					<s:select name="beginDate" list="dates" value="beginDate"></s:select>
					<span> 到 </span>
					<s:select name="endDate" list="dates" value="endDate"></s:select>
					<span>&nbsp;&nbsp;</span>
					<input type="submit" value="查 询" />
				</td>
			</tr>
			<tr align="center" style="font-weight: bold;">
				<td width="25%">类目</td>
				<td width="15%">订单总个数</td>
				<td width="15%">纠纷订单个数</td>
				<td width="15%">纠纷率</td>
				<td width="15%">订单总金额</td>
				<td width="15%">详细统计数据</td>
			</tr>
			<s:iterator value="orderStatLms" var="i">
				<tr align="center">
					<td>${i.leimuName }</td>
					<td>${i.orderAmount }</td>
					<td>${i.jiufenAmount }</td>
					<td>${i.jiufenRate }%</td>
					<td>${i.totalMoney }</td>
					<td>
						<s:if test="leimuId != 0">
							<a href="./order/statLmByDay.do?leimuId=${i.leimuId }">查看</a>
						</s:if>
					</td>
				</tr>
			</s:iterator>
			<tr>
				<td colspan="6" align="center">
					<table width="100%">
						<tr>
							<td width="50%"><img src="./displayChart?filename=${totalMoneyChart }" /></td>
							<td width="50%"><img src="./displayChart?filename=${orderAmountChart }" /></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</s:form>
</m:frame>