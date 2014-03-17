<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags"%>
<m:frame>
		<table border="1" cellspacing="0" style="float: left">
			<tr>
				<th colspan="2" align="center">速卖通订单更新API</th>
			</tr>
			<tr align="center">
				<td style="width:100px">速卖通账号</td>
				<td align="left">
					<s:select id="account" name="account" list="#request.accounts" listKey="account" listValue="account" 
						headerKey="-1" headerValue="请选择" value="-1"></s:select>
				</td>
			</tr>
			<tr align="center">
				<td>取前几天的数据</td>
				<td align="left">
					<input type="radio" name="beforeDay" value="1" checked="checked" />&nbsp;1天
					<span>&nbsp;&nbsp;</span>
					 <input type="radio" name="beforeDay" value="2" />&nbsp;2天
					<span>&nbsp;&nbsp;</span>
					 <input type="radio" name="beforeDay" value="3" />&nbsp;3天
					<span>&nbsp;&nbsp;</span>
					 <input type="radio" name="beforeDay" value="4" />&nbsp;4天
					<span>&nbsp;&nbsp;</span>
					 <input type="radio" name="beforeDay" value="5" />&nbsp;5天
					<span>&nbsp;&nbsp;</span>
					 <input type="radio" name="beforeDay" value="6" />&nbsp;6天
					<span>&nbsp;&nbsp;</span>
					 <input type="radio" name="beforeDay" value="7" />&nbsp;7天
					<span>&nbsp;&nbsp;</span>
					 <input type="radio" name="beforeDay" value="15" />&nbsp;15天
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="button" onclick="doDownload()" value="开始更新"/>
				</td>
			</tr>
		</table>
		
		<div id="aliAuthDialog" style="display:none;">
			<iframe src="" width="700" height="500" style="border:0;"></iframe>
		</div>
</m:frame>
<script type="text/javascript">

	function doDownload() {
		var account = $("#account").val();
		var beforeDay = $("input:radio[name='beforeDay']:checked").val();

		if (account == "-1") {
			alert("请选择速卖通账号！");
			return;
		}
		
		$waiting("正在请求数据，请稍候...");
		$.ajax({
			url: "./order/aliApi!update.do",
			type: "post",
			data: {
				account: account,
				beforeDay: beforeDay
			},
			dataType: "json",
			success: function(resp) {
				downing = false;
				$remove_wariting();
				if (resp.success) {
					alert("订单数据更新成功!\n\n" + resp.msg);
				} else if (resp.msg != null && resp.msg.indexOf("授权") >= 0) {
					showAliAuthDialog($("#account").val());
				} else {
					alert(resp.msg);
				}
			}
		});
	}
</script>