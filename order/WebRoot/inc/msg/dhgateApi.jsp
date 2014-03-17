<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags"%>
<m:frame>
		<table border="1" cellspacing="0" style="float: left">
			<tr>
				<th colspan="2" align="center">敦煌网站内信更新API</th>
			</tr>
			<tr align="center">
				<td style="width:100px">敦煌网账号</td>
				<td align="left">
					<s:select id="account" name="account" list="#request.accounts" listKey="account" listValue="account" 
						headerKey="-1" headerValue="请选择" value="-1"></s:select>
				</td>
			</tr>
			<tr align="center">
				<td>站内信类型</td>
				<td align="left">
					<input type="checkbox" id="cbx_checkAllType" checked="checked"  onclick="checkAllMsgType(this)"/>&nbsp;全部
					<span>&nbsp;&nbsp;</span>
					<input type="checkbox" name="msgType" value="001" checked="checked" onclick="checkMsgType()"/>&nbsp;询盘
					<span>&nbsp;&nbsp;</span>
					<input type="checkbox" name="msgType" value="002" checked="checked" onclick="checkMsgType()"/>&nbsp;订单
					<span>&nbsp;&nbsp;</span>
					<input type="checkbox" name="msgType" value="003" checked="checked" onclick="checkMsgType()"/>&nbsp;其它
					<span>&nbsp;&nbsp;</span>
					<input type="checkbox" name="msgType" value="004" checked="checked" onclick="checkMsgType()"/>&nbsp;订单
					<span>&nbsp;&nbsp;</span>
					<input type="checkbox" name="msgType" value="005" checked="checked" onclick="checkMsgType()"/>&nbsp;产品
					<span>&nbsp;&nbsp;</span>
					<input type="checkbox" name="msgType" value="006" checked="checked" onclick="checkMsgType()"/>&nbsp;付款
					<span>&nbsp;&nbsp;</span>
					<input type="checkbox" name="msgType" value="007" checked="checked" onclick="checkMsgType()"/>&nbsp;促销
					<span>&nbsp;&nbsp;</span>
					<input type="checkbox" name="msgType" value="008" checked="checked" onclick="checkMsgType()"/>&nbsp;账户
					<span>&nbsp;&nbsp;</span>
					<input type="checkbox" name="msgType" value="009" checked="checked" onclick="checkMsgType()"/>&nbsp;其它
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
		
		<div id="dhgateAuthDialog" style="display:none;">
			<p class="dialog_msg" style="color:#ff0000; padding-bottom:10px;"></p>
			<p>敦煌网账号：<span class="authAccount"></span></p>
			<p>敦煌网密码：<input type="password" class="authPass" /></p>
		</div>
</m:frame>
<script type="text/javascript">
	function changeAccount(account) {
		$("#authAccount").html(account);
	}
	
	function checkAllMsgType(obj) {
		if ($(obj).attr("checked")) {
			$(obj).siblings("input:checkbox[name='msgType']").attr("checked", true);
		} else {
			$(obj).siblings("input:checkbox[name='msgType']").attr("checked", false);
		}
	}
	
	function checkMsgType() {
		if ($("input:checkbox[name='msgType']:checked").length == $("input:checkbox[name='msgType']").length) {
			$("#cbx_checkAllType").attr("checked", true);
		} else {
			$("#cbx_checkAllType").attr("checked", false);
		}
	}
	
	function doDownload() {
		var account = $("#account").val();
		var msgType = getCheckedValue('msgType');
		var beforeDay = $("input:radio[name='beforeDay']:checked").val();

		if (account == "-1") {
			alert("请选择敦煌网账号！");
			return;
		} else if (msgType == "") {
			alert("请至少选择一种站内信类型！");
			return;
		}
		
		$waiting("正在请求数据，请稍候...");
		$.ajax({
			url: "./msg/dhgateApi!downMsg.do",
			type: "post",
			data: {
				account: account,
				msgType: msgType,
				beforeDay: beforeDay
			},
			dataType: "json",
			success: function(resp) {
				downing = false;
				$remove_wariting();
				if (resp.success) {
					alert("站内信数据更新成功!\n\n" + resp.msg);
				} else if (resp.msg != null && resp.msg.indexOf("授权") >= 0) {
					showDhgateAuthDialog($("#account").val());
				} else {
					alert(resp.msg);
				}
			}
		});
	}
</script>