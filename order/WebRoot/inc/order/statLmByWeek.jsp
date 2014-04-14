<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags"%>
<style type="text/css">
a.lbtn {display:block; float:left; margin-right:10px; width: 80px; height:20px; line-height:20px; text-align:center; border:solid 1px #ccc; text-decoration: none;}
a.selected  {background:#0000ff; color:#fff; cursor:default;}
</style>
<m:frame>
	<s:form action="./order/statLmByWeek.do">
		<table border="1" cellspacing="0" style="width: 99%; float: left">
			<tr>
				<th colspan="5" align="center">类目订单统计数据</th>
			</tr>
			<tr>
				<td colspan="5">
					<span style="float:left; margin-right:5px;">统计方式：</span>
					<a href="./order/statLmByDay.do?leimuId=${leimuId}" class="lbtn">按日统计</a>
					<a href="javascript:void(0)" class="lbtn selected">按周统计</a>
					<a href="./order/statLmByMonth.do?leimuId=${leimuId}" class="lbtn">按月统计</a>	
					<div style="float:left;">
						<span>统计账号：</span>
						<s:select name="leimuId" list="leimuList" listKey="id" listValue="leimu" value="leimuId"></s:select>
						<span>&nbsp;&nbsp;</span>
						<span>统计时间：</span>
						<s:select name="statYear" list="dates" value="statYear"></s:select>
						<span>&nbsp;&nbsp;</span>
						<input type="submit" value="查 询" />
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="5" align="center" style="padding:5px;">
					<img src="./displayChart?filename=${totalMoneyChart }" />
				</td>
			</tr>
			<tr>
				<td colspan="5" align="center" style="padding:5px;">
					<img src="./displayChart?filename=${orderAmountChart }" />
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
							<s:property value="getFormatMonth(statBeginDate)" />
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