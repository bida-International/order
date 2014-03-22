<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags"%>
<m:frame>
		<table border="1" cellspacing="0" style="float: left; width: 600px'">
			<tr>
				<th colspan="2" align="center">采集产品链接</th>
			</tr>
			<tr align="center">
				<td style="width:100px">采集地址：</td>
				<td align="left">
					<input type="text" id="targetUrl" style="width:500px"/>
				</td>
			</tr>
			<tr align="center">
				<td>采集条件：</td>
				<td align="left">
					订单数大于<input type="text" id="minOrderNum" value="0" style="width:30px"/>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<button id="startButton" onclick="startGather()">开始采集</button>
				</td>
			</tr>
		</table>
		<p>&nbsp;</p>
		<table id="progress" border="1" cellspacing="0" style="float: left; width: 600px; display:none;">
			<tr>
				<td style="width:150px">采集进度：<span id="percent" style="font-weight:bold;">0.0%</span></td>
				<td align="left" style="width:450px; border:solid 1px #0000ff;">
					<table id="progressBar" style="width:0;">
						<tr>
							<td style="background:#0000ff;">&nbsp;</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="center" colspan="2">
					<button id="stopButton" onclick="stopGather()">结束采集</button>
				</td>
			</tr>
		</table>

</m:frame>

<script type="text/javascript">
var targetUrl;
var minOrderNum;
var createTime;
function startGather() {
	targetUrl = $.trim($("#targetUrl").val());
	minOrderNum = $.trim($("#minOrderNum").val());
	if (targetUrl == "") {
		alert("请输入要采集的地址");
		return;
	} else if (targetUrl.indexOf("http://www.aliexpress.com") == -1) {
		alert("请输入要求的网址");
	} else if (minOrderNum == "") {
		alert("请填写采集条件");
		return;
	}
	minOrderNum = parseInt(minOrderNum);
	$.ajax({
		url : "./tools/gatherData1!startGather.do",
		type: "post",
		data: {
			targetUrl: targetUrl,
			minOrderNum: minOrderNum
		},
		dataType: "json",
		success: function(resp) {
			if (resp.success) {
				createTime = resp.msg;
				getProgress();
			} else {
				alert(resp.msg);
			}
		}
	});
}

function stopGather() {
	$.ajax({
		url: "./tools/gatherData1!stopGather.do",
		dataType: "json",
		success: function(resp) {
			if (resp.success) {
				gotoResultPage();
			}
		}
	});
}

function getProgress() {
	if (!$("#progress").isVisible) {
		$("#progress").show();
		$("#startButton").attr("disabled", true);
	}
	$.ajax({
		url: "./tools/gatherData1!getPercent.do",
		dataType: "json",
		success: function(resp) {
			if (resp.success) {
				var percent = resp.msg;
				$("#percent").html(percent);
				$("#progressBar").css("width", percent);
				if (percent == "100.0%") {
					$("#stopButton").attr("disabled", true);
					window.setTimeout("gotoResultPage()", 500);
				} else {
					window.setTimeout("getProgress()", 1000);
				}
			}
		}
	});
}

function gotoResultPage() {
	$waiting("正在打开采集结果页面，请稍候...");
	location.href = "./tools/gatherData1Result.do?keyCreateTime=" + createTime;
}
</script>
		