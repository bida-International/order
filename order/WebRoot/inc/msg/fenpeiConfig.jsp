<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags"%>
<style type="text/css">
.a_username {color:#0000ff; text-decoration:underline;}
</style>
<m:frame>
	<form action="./msg/fenpeiConfig!save.do" method="post">
		<table border="1" cellspacing="0" style="float: left">
			<tr>
				<th colspan="2" align="center">
					站内信自动分配设置
				</th>
			</tr>
			<tr align="center" style="font-weight:bold;">
				<td style="width:400px;">敦煌账号</td>
				<td style="width:150px;">分配的（业务）用户</td>
			</tr>
			<s:iterator value="dhAccounts" var="dhAccount">
			<tr>
				<td style="line-height:150%; text-align:center;">${dhAccount.account }</td>
				<td style="line-height:150%; text-align:center;">
					<a href="javascript:void(0)" class="a_username" onclick="selectUser(this)">
					<s:if test="#dhAccount.bd_user_id == null">请选择</s:if>
					<s:else>${dhAccount.bd_user_name}</s:else>
					</a>
					<input type="hidden" name="id" value="${dhAccount.id}" />
					<input type="hidden" name="msgFenpeiUserId" value="${dhAccount.bd_user_id}" />
					<input type="hidden" name="msgFenpeiUserName" value="${dhAccount.bd_user_name}" />
					<s:select cssClass="sel_yewu" list="#request.yeWus" listKey="userid" listValue="name" headerKey=""  headerValue="请选择"  
						value="dhAccount.bd_user_id" style="display:none;" onchange="changeFenpeiUser(this)"></s:select>
				</td>
			</tr>
			</s:iterator>
			<tr>
				<td colspan="2" align="center">
					<p style="color:#ff0000; font-weight:100;"><input type="submit" value="保存设置" /></p>
				</td>
			</tr>
		</table>
	</form>	
</m:frame>
<script type="text/javascript">
	$(function() {
		$(document).click(function() {
			$(".sel_yewu:visible").each(function() {
				$(this).hide().siblings("a").show();
			});
		});
		
		$("a.a_username").click(function(e) {
			var curUserId = $(this).siblings("input[name='msgFenpeiUserId']").val();
			$(this).hide();
			$(this).siblings(".sel_yewu").val(curUserId).show();
			e.stopPropagation();
		});
		
		$(".sel_yewu").click(function(e) {
			e.stopPropagation();
		});
	});
	
	function selectUser(obj) {
		var curUserId = $(obj).siblings("input[name='msgFenpeiUserId']").val();
		$(obj).hide();
		$(obj).siblings(".sel_yewu").val(curUserId).show();
		return false;
	}
	
	function changeFenpeiUser(obj) {
		var selectedUserId = $(obj).val();
		var selectedUserName = $(obj).find("option:selected").text();
		$(obj).siblings("input[name='msgFenpeiUserId']").val(selectedUserId);
		$(obj).siblings("input[name='msgFenpeiUserName']").val(selectedUserName);
		$(obj).siblings("a").html(selectedUserName).show();
		$(obj).hide();
		return false;
	}
	
	var alertMsg = "${sessionScope.alertMsg}";
	if (alertMsg != "") {
		alert(alertMsg);
	}
	<%
	request.getSession().removeAttribute("alertMsg");
	%>
</script>