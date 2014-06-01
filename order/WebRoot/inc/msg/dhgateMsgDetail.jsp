<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags"%>
<m:frame>
		<table border="1" cellspacing="0" style="float: left; width:80%">
			<tr align="center">
				<td style="width:100px; font-weight:bold">发信人</td>
				<td align="left">
					<s:if test="msgTopic.senderNickname  != null">${msgTopic.senderNickname  }</s:if>
					<s:else>用户(${msgTopic.senderId })</s:else>
				</td>
			</tr>
			<tr align="center">
				<td style="font-weight:bold">收信人</td>
				<td align="left">
					<s:if test="msgTopic.recieverNickname  != null">${msgTopic.recieverNickname  }</s:if>
					<s:else>用户(${msgTopic.recieverId })</s:else>
				</td>
			</tr>
			<tr align="center" >
				<td style="font-weight:bold">主题</td>
				<td align="left">
					${msgTopic.title }
				</td>
			</tr>
		</table>
		
		<div style="float: left; width:80%; margin-top:10px; border-top:solid 1px #999">
		<s:iterator value="msgInfos" var="msgInfo">
			<div style="padding: 5px 10px;background:#ccc; border:solid 1px #999; border-top:0; height:15px; font-weight:bold">
					<span style="float:right"><s:property value="getFormatTime(#msgInfo.createTime)" /></span>
					<span style="float:left">
						<s:if test="#msgInfo.senderNickname != null">${msgInfo.senderNickname }：</s:if>
						<s:else>用户(${msgInfo.senderId })：</s:else>
					</span>
			</div>
			<div style="padding:15px 10px; line-height:160%; border:solid 1px #999; border-top:0;">${msgInfo.infoContent }</div>
			</s:iterator>
			<div style="text-align:center; height:25px; border:solid 1px #999; border-top:0;">
				<button onclick="synchData()">同步最新数据</button>
					<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
					<button onclick="goBack()">返 回</button>
			</div>
		</div>
		
		<table border="1" cellspacing="0" style="float: left; width:80%; margin-top:10px;">
			<tr style="background:#ccc;">
				<th style="text-align:left; padding-left:10px;">
					发表回复
				</th>
			</tr>
			<tr>
				<td style="padding:10px;" valign="middle">
					<textarea id="replyContent" style="width:90%; height:80px; border:solid 1px #999; font-size:13px;"></textarea>
					<p style="width:90%; text-align:right;">
						<button style="width: 150px;" onclick="reply()">回 复</button>
					</p>
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
	function synchData() {
		var topicId = ${topicId};
		$waiting("正在同步数据，请稍候...");
		$.ajax({
			url: "./msg/dhgateMsgDetail!synchData.do",
			type: "post",
			data: {
				topicId: topicId
			},
			dataType: "json",
			success: function(resp) {
				$remove_wariting();
				if (resp.success) {
					alert("数据同步成功！");
					location.href = location.href;
				} else if (resp.msg != null && resp.msg.indexOf("授权") >= 0) {
					showDhgateAuthDialog('${msgTopic.dhAccount}');
				}  else {
					alert(resp.msg);
				}
			}
		});
	}
	
	function reply() {
		var topicId = ${topicId};
		var replyContent = $("#replyContent").val();
		if ($.trim(replyContent) == "") {
			alert("请输入回复内容!");
			return;
		}
		
		$waiting("正在提交数据，请稍候...");
		$.ajax({
			url: "./msg/dhgateMsgDetail!reply.do",
			type: "post",
			data: {
				topicId: topicId,
				replyContent: replyContent
			},
			dataType: "json",
			success: function(resp) {
				$remove_wariting();
				if (resp.success) {
					synchData();
				} else if (resp.msg != null && resp.msg.indexOf("授权") >= 0) {
					showDhgateAuthDialog('${msgTopic.dhAccount}');
				}  else {
					alert(resp.msg);
				}
			}
		});
	}
	
	function goBack() {
		var referer = "${referer}";
		if (referer != "" && referer != location.href) {
			location.href = referer;
		} else {
			location.href = "./msg/dhgateMsg.do";
		}
	}
</script>