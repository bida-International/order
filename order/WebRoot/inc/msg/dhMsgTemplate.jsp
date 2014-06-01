<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags"%>
<m:frame>
	<table border="1" cellspacing="0" style="float: left; margin-left:10px; width:80%">
		<tr>
			<th colspan="2" align="center">站内信内容模板管理</th>
		</tr>
		<tr align="center">
			<td style="width:100px; font-weight:bold">站内信类型：</td>
			<td align="left">
				<s:select id="type" list="#{1:'运输途中通知', 2:'到达待取通知', 3:'妥投通知' }" value="type" onchange="changeType(this.value)"></s:select>
			</td>
		</tr>
		<tr align="center">
			<td style="font-weight:bold">站内信标题：</td>
			<td align="left">
				<input type="text" id="title" name="dhMsgTemplate.title" value="${dhMsgTemplate.title }" style="width:400px" />
			</td>
		</tr>
		<tr align="center" >
			<td style="font-weight:bold">站内信内容：</td>
			<td align="left">
				<textarea id="content" style="width:90%; height:100px; border:solid 1px #999; font-size:13px;">${dhMsgTemplate.content }</textarea>
			</td>
		</tr>
		<tr>
			<td style="font-weight:bold">模板状态：</td>
			<td align="left">
				<s:select id="status" list="#{0:'关闭', 1:'开启'}" value="dhMsgTemplate.status"></s:select>
				<span style="color:#ff0000">（为关闭状态时，后台将不会自动发送该状态通知给客户）</span>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<button onclick="doSave()">保 存</button>
			</td>
		</tr>
	</table>
	<s:if test="dhMsgTemplate != null">
	<table border="1" cellspacing="0" style="float: left; margin-left:10px; margin-top:10px; width:80%">
		<tr>
			<th colspan="2" align="center">效果预览：（有修改时需要保存后才能看到最新效果）</th>
		</tr>
		<tr align="center">
			<td style="width:100px; font-weight:bold">站内信标题：</td>
			<td align="left">
				<input type="text" value="${previewTitle }" style="width:400px" />
			</td>
		</tr>
		<tr align="center" >
			<td style="font-weight:bold">站内信内容：</td>
			<td align="left">
				<textarea  style="width:90%; height:140px; border:solid 1px #999; font-size:13px;">${previewContent }</textarea>
			</td>
		</tr>
	</table>
	<!-- 
	<div style="float:left; width:80%; padding-top:10px; margin-left:20px;">
		<p style="font-weight:bold;"><br/>效果预览：</p>
		<div style="padding:15px 10px; line-height:160%; border:solid 1px #999;">${previewTitle }</div>
		<div style="padding:15px 10px; min-height:100px; line-height:160%; border:solid 1px #999; border-top:0;">${previewContent }</div>
	</div>
	 -->
	</s:if>
	<div style="float:left; width:80%; padding-top:10px; margin-left:20px;">
		<p style="font-weight:bold;"><br/>参数说明：</p>
		<ul>
			<li>#orderId#：订单号</li>
			<li>#danhao#：物流单号</li>
			<li>#trackDetail#：详细物流记录</li>
		</ul>
	</div>
</m:frame>

<script type="text/javascript">
function changeType(type) {
	location.href = "./msg/dhMsgTemplate.do?type=" + type;
}

function doSave() {
	var type = $("#type").val();
	var title = $("#title").val();
	var content = $("#content").val();
	var status = $("#status").val();
	
	if (title == "") {
		alert("请填写站内信标题！");
		return;
	}
	if (content == "") {
		alert("请填写站内信内容！");
		return;
	}
	
	$waiting("正在提交数据，请稍候...");
	$.ajax({
		url: "./msg/dhMsgTemplate!save.do",
		type: "post",
		data: {
			type: type,
			title: title,
			content: content,
			status: status
		},
		dataType: "json",
		success: function(resp) {
			$remove_wariting();
			if (resp.success) {
				alert("站内信模板保存成功！");
				location.href = location.href;
			} else {
				alert(resp.msg);
			}
		}
	});
}
</script>