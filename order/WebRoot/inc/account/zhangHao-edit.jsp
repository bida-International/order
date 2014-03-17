<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags"%>
<m:frame>
		<table border="1" cellspacing="0" style="float: left">
			<tr>
				<th colspan="2" align="center">卖家账号编辑</th>
			</tr>
			<tr align="center">
				<td width="150">账号类型：</td>
				<td width="500" align="left">
					<select id="accountType" name="accountType">
						<option value="-1">请选择</option>
						<option value="dh" <s:if test="editZhangHao.account_type.equals('dh')">selected="selected"</s:if>>敦煌网账号</option>
						<option value="ali" <s:if test="editZhangHao.account_type.equals('ali')">selected="selected"</s:if>>速卖通账号</option>
					</select>
				</td>
			</tr>
			<tr align="center">
				<td>账号：</td>
				<td align="left">
					<input type="text" id="account" name="account" value="${editZhangHao.account }" />
					<input type="hidden" id="id" name="id" value="${editZhangHao.id }"/>
				</td>
			</tr>
			<tr align="center">
				<td>App Key：</td>
				<td align="left">
					<input type="text" id="appKey" name="appKey" value="${editZhangHao.app_key }" />
					<br/>
					<span>（速卖通账号此项必填，每一个速卖通账号都要到速卖通开放平台申请一个appkey）</span>
				</td>
			</tr>
			<tr align="center">
				<td>App Secret：</td>
				<td align="left">
					<input type="text" id="appSecret" name="appSecret" value="${editZhangHao.app_secret }" />
					<br/>
					<span>（速卖通账号此项必填）</span>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="button" onclick="doSave()" value="保 存"/>
					<span>&nbsp;&nbsp;</span>
					<input type="button" onclick="history.go(-1)" value="返 回"/>
				</td>
			</tr>
		</table>
</m:frame>
<script type="text/javascript">
	function doSave() {
		var id = $("#id").val();
		var accountType = $("#accountType").val();
		var account = $.trim($("#account").val());
		var appKey = $.trim($("#appKey").val());
		var appSecret = $.trim($("#appSecret").val());

		if (accountType == "-1") {
			alert("请选择账号类型！");
			return;
		} else if (account == "") {
			alert("请输入账号！");
			return;
		}
		if (accountType == "ali") {
			if (appKey == "") {
				alert("请输入App Key！");
				return;
			} else if (appSecret == "") {
				alert("请输入App Secret！");
				return;
			}
		}
		
		
		$waiting("正在请求数据，请稍候...");
		$.ajax({
			url: "./account/zhangHao!save.do",
			type: "post",
			data: {
				id: id,
				accountType: accountType,
				account: account,
				appKey: appKey,
				appSecret: appSecret
			},
			dataType: "json",
			success: function(resp) {
				$remove_wariting();
				if (resp.success) {
					alert("账号保存成功！");
					location.href = "./account/zhangHao!list.do";
				} else {
					alert(resp.msg);
				}
			}
		});
	}
</script>