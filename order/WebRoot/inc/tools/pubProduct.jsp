<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<m:frame>
<style type="text/css">
th, td { height: 30px; vertical-align: middle;}
</style>
<script src="./JS/jquery.form.js" type="text/javascript"></script>
<script charset="utf-8" src="./JS/kindeditor/kindeditor.js"></script>
<script charset="utf-8" src="./JS/kindeditor/lang/zh_CN.js"></script>
	
 <table id="step1" border="1" cellspacing="0" style="width: 1000px; float: left">
	<tr>
		<th colspan="2" align="center">发布产品</th>
	</tr>
	<tr>
		<td align="right">请选择发布账号：</td>
		<td><s:select id="sel_zhangHao"  list="zhangHaoList" listKey="id" listValue="account"
			headerKey="0" headerValue="请选择" ></s:select></td>
	</tr>
	<tr align="center" style="font-weight: bold;">
		<td colspan="2">
			<input type="button" value=" 返 回 " onclick="goBack()" />
			<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
			<input type="button" value=" 下一步 " onclick="next()" />
		</td>
	</tr>
</table>

<s:form id="pubform" action="./tools/pubProduct!doPublish.do" method="post" 
	enctype="multipart/form-data">
	 <table id="step2" border="1" cellspacing="0" style="width: 95%; float: left; display:none;">
		<tr>
			<th colspan="2" align="center">发布产品</th>
		</tr>
		<tr>
			<td align="right" style="width:120px">发布账号：</td>
			<td>
				<span id="zhangHaoName"></span>
				<input type="hidden" id="zhangHaoId" name="zhangHaoId" />
			</td>
		</tr>
		<tr>
			<td align="right">发布类目：</td>
			<td id="sel_cate_box">
				<input type="hidden" id="pubCateId" name="pubCateId" value="0" />
				<select id="sel_cate_1" onchange='onCateSelected(this)'>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right">运费模板：</td>
			<td>
				<select id="sel_template" name="shippingTemplateId">
				</select>
			</td>
		</tr>
		<tr>
			<td align="right">产品组(选填)：</td>
			<td>
				<select id="sel_group" name="productGroupId">
				</select>
			</td>
		</tr>
		<tr>
			<td align="right">发布模式：</td>
			<td>
				<input type="radio" name="pubMode" value="2" checked="checked" onclick="changeMode()" />
				<span>批量发布产品&nbsp;&nbsp;&nbsp;&nbsp;</span>
				<input type="radio" name="pubMode" value="1" onclick="changeMode()"/>
				<span>发布单个产品</span>
			</td>
		</tr>
		<tr class="pubMode">
			<td align="right">请选择Excel文件：</td>
			<td><input type="file" name="excel" />
				<br/>
				<span style="color:#999">
					&nbsp;&nbsp;&nbsp;&nbsp;（该Excel文件从“工具箱 > 采集产品链接记录”页面中导出，导出的文件可能格式不正确，请先另存为新Excel文件后上传，每次都多发布1000条）
				</span>
			</td>
		</tr>
		<tr class="pubMode" style="display:none;">
			<td align="right">速卖通产品页面地址：</td>
			<td><input type="text" id="aliUrl" name="aliUrl"  style="width:800px" /></td>
		</tr>
		<tr>
			<td align="right">自定义设置：</td>
			<td>
				<table style="width:100%">
					<tr>
						<td width="100" align="right">产品名称：</td>
						<td>
							<p>
								增加<span>&nbsp;&nbsp;</span>
								前缀<input type="text" name="pubConfig.prodNamePrefix"/>
								后缀<input type="text" name="pubConfig.prodNameSuffix" />
							</p>
							<p>
								替换<span>&nbsp;&nbsp;</span>
								将<input type="text" name="pubConfig.prodNameOrgin"/>
								替换为<input type="text" name="pubConfig.prodNameReplaced"/>
							</p>
						</td>
					</tr>
					<tr>
						<td>详细描述：</td>
						<td>
							<div style="width:99%; height:150px;">
								<span style="float:left;">增加&nbsp;&nbsp;</span>
								<div style="float:left;width:300px;height:150px;">
									<span>首部</span>
									<textarea name="pubConfig.prodDescPrefix" style="width:300px;height:100px;font-size:12px;"></textarea>
									<br/>
									<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
									<button type="button" onclick="openHtmlEditor('pubConfig.prodDescPrefix')">使用html编辑器</button>
								</div>
								<div style="float:left;width:300px;height:150px;margin-left:10px;">
									<span>尾部</span>
									<textarea name="pubConfig.prodDescSuffix" style="width:300px;height:100px;font-size:12px;"></textarea>
									<br/>
									<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
									<button type="button" onclick="openHtmlEditor('pubConfig.prodDescSuffix')">使用html编辑器</button>
								</div>
							</div>
							<div style="width:99%; height:150px;">
								<span style="float:left;">替换&nbsp;&nbsp;</span>
								<div style="float:left;width:300px;height:150px;">
									<span>将</span>
									<textarea name="pubConfig.prodDescOrgin" style="width:300px;height:100px;font-size:12px;"></textarea>
									<br/>
								</div>
								<div style="float:left;width:300px;height:150px;margin-left:10px;">
									<span>替换为</span>
									<textarea name="pubConfig.prodDescReplaced" style="width:300px;height:100px;font-size:12px;"></textarea>
									<br/>
									<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
									<button type="button" onclick="openHtmlEditor('pubConfig.prodDescReplaced')">使用html编辑器</button>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td>批发价设置：</td>
						<td>
							<p>一行一条批发价，每个批发价的表现形式是“3,4”，其中3表示购买，4表示降低，它们之间用英文逗号隔开，在网页后台表示购买3件及以上时，在零售价的基础上降低4%，即9.60折。</p>
							<textarea name="pubConfig.prodBulkPriceSet" style="width:300px;height:100px;font-size:12px;"></textarea>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr align="center" style="font-weight: bold;">
			<td colspan="2">
				<input type="button" value=" 上一步 " onclick="prev()" />
				<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
				<input type="submit" value=" 发 布 " />
			</td>
		</tr>
	</table>
</s:form>
	<p>&nbsp;</p>
	<table id="progress" border="1" cellspacing="0" style="float: left; width: 600px; display:none;">
		<tr>
			<td align="center" colspan="2">
				<span id="t1" style="font-weight:bold">正在发布产品...</span>
				<button id="stopButton" onclick="stopPublish()">结束发布</button>
				<button id="backButton" onclick="goBack()" style="display:none">返 回</button>
			</td>
		</tr>
		<tr>
			<td style="width:150px">发布进度：<span id="percent" style="font-weight:bold;">0.0%</span></td>
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
				发布成功<span id="okNum">0</span>个，发布失败<span id="errNum">0</span>个
			</td>
		</tr>
	</table>
	<p>&nbsp;</p>
	<div id="log" style="width:1000px;height:350px;color:#ff0000;font-size:12px;line-height:160%;padding-top:10px;border:solid 1px #ccc;overflow:scroll;display:none;">
	
	</div>
	<p id="errmsg" style="color:#ff0000;"></p>
	
	<div id="dialog" style="display:none;">
		<textarea id="editor" style="width:650px;height:350px;"></textarea>
	</div>
	
</m:frame>
<script type="text/javascript">
	function goBack() {
		location.href='./tools/productStatus.do';
	}
	function next() {
		var zhangHaoId = $("#sel_zhangHao").val();
		var zhangHaoName = $("#sel_zhangHao").find("option:selected").text();
		if (zhangHaoId == null || zhangHaoId == 0) {
			alert("请选择一个发布账号");
			return;
		}

		if ($("#zhangHaoId").val() != zhangHaoId) {
			loadData(zhangHaoId);
		}
		$("#zhangHaoId").val(zhangHaoId);
		$("#zhangHaoName").html(zhangHaoName);
		
		$("#step1").hide();
		$("#step2").show();
	}

	function prev() {
		$("#step2").hide();
		$("#step1").show();
	}

	function loadData(zhangHaoId) {
		loadCates("sel_cate_1", "", zhangHaoId);
		loadTemplates(zhangHaoId);
		loadGroups(zhangHaoId);
	}

	function loadCates(selectBoxId, parentId, zhangHaoId) {
		var sel = $("#" + selectBoxId);
		if (sel == null || sel.length == 0) {
			$("#sel_cate_box").append("<select id='" + selectBoxId + "' onchange='onCateSelected(this)'></select>");
			sel = $("#" + selectBoxId);
		}
		sel.empty().append("<option value='0'>正在加载数据，请稍候...</option>");
		$.ajax({
			url: "./tools/pubProduct!getCategorys.do?parentId=" + parentId + "&zhangHaoId=" + zhangHaoId,
			dataType: "json",
			success: function(respJson) {
				if (respJson.success) {
					sel.empty().append("<option value='0'>请选择</option>");
					var jsonString = respJson.msg;
					jsonString = jsonString.substring(jsonString.indexOf("{"), jsonString.lastIndexOf("}") + 1);
					var jsonData = eval("(" +jsonString + ")");
					var cates = jsonData.categoryPublishList;
					for (var i = 0; i < cates.length; i++) {
						if (cates[i].valid == 1) {
							sel.append("<option value='" + cates[i].catePubId + "' isLeaf='" + cates[i].leaf + "'>" +  cates[i].pubName + "</option>");
						}
					}
				} else {
					sel.empty().append("<option value='0'>" + respJson.msg + "</option>");
				}
			}
		});
	}
	
	function onCateSelected(obj) {
		var sel = $(obj);
		var cateId = sel.find("option:selected").val();
		var cateName = sel.find("option:selected").text();
		var isLeaf = sel.find("option:selected").attr("isLeaf");
		if (isLeaf == 1) {
			$("#pubCateId").val(cateId);
		} else {
			$("#pubCateId").val("0");
			while(sel.next("select").length == 1) {
				sel.next("select").remove();
			}
			var zhangHaoId = $("#zhangHaoId").val();
			var nextLevel = parseInt(sel.attr("id").split("_")[2]) + 1;
			var nextSelectBoxId = "sel_cate_" + nextLevel;
			loadCates(nextSelectBoxId, cateId, zhangHaoId);
		}
	}

	function loadTemplates(zhangHaoId) {
		var sel = $("#sel_template");
		sel.empty().append("<option value='0'>正在加载数据，请稍候...</option>");
		$.ajax({
			url: "./tools/pubProduct!getShippingTemplates.do?zhangHaoId=" + zhangHaoId,
			dataType: "json",
			success: function(respJson) {
				if (respJson.success) {
					sel.empty().append("<option value='0'>请选择</option>");
					var jsonString = respJson.msg;
					jsonString = jsonString.substring(jsonString.indexOf("{"), jsonString.lastIndexOf("}") + 1);
					var jsonData = eval("(" +jsonString + ")");
					var templates = jsonData.shippingModelList;
					for (var i = 0; i < templates.length; i++) {
						var selected = "";
						if (templates[i].isdefault == 0) {
							selected ="selected=true";
						}
						sel.append("<option value='" + templates[i].shippingmodelid + "' " + selected + ">" +  templates[i].modelname + "</option>");
					}
				} else {
					sel.empty().append("<option value='0'>" + respJson.msg + "</option>");
				}
			}
		});
	}
	
	function loadGroups(zhangHaoId) {
		var sel = $("#sel_group");
		sel.empty().append("<option value='0'>正在加载数据，请稍候...</option>");
		$.ajax({
			url: "./tools/pubProduct!getProductGroups.do?zhangHaoId=" + zhangHaoId,
			dataType: "json",
			success: function(respJson) {
				if (respJson.success) {
					sel.empty().append("<option value='0'>请选择</option>");
					var jsonString = respJson.msg;
					jsonString = jsonString.substring(jsonString.indexOf("{"), jsonString.lastIndexOf("}") + 1);
					var jsonData = eval("(" +jsonString + ")");
					var groups = jsonData.productGroupList;
					for (var i = 0; i < groups.length; i++) {
						sel.append("<option value='" + groups[i].groupId + "'>" +  groups[i].groupEnName + "</option>");
					}
				} else {
					sel.empty().append("<option value='0'>分组列表加载失败</option>");
				}
			}
		});
	}
	
	function changeMode() {
		$(".pubMode").toggle();
	}

	var logQueryStartTime = null;
	$("#pubform").submit(function() {
		if (!checkForm()) {
			return false;
		}
		
		$waiting("正在提交数据，请稍候...");
		$("#pubform").ajaxSubmit({
			url: "./tools/pubProduct!doPublish.do",
			type: "post",
			dataType: "json",
			success: function(resp) {
				$remove_wariting();
				if (resp.success) {
					var pubMode = $("input[type='radio']:checked").val();
					if (pubMode == 1) {
						alert("产品发布成功！");
						goBack();
					} else if (pubMode == 2) {
						logQueryStartTime = resp.msg;
						$("#step2").hide();
						getProgress();
						getLog();
					}
				} else {
					alert(resp.msg);
				}
			}
		});
		return false;
	});
	
	function checkForm() {
		var zhangHaoId = $("#zhangHaoId").val();
		var pubCateId = $("#pubCateId").val();
		var shippingTemplateId = $("#sel_template").val();
		var pubMode = $("input[type='radio']:checked").val();
		var aliUrl =  $.trim($("#aliUrl").val());
		var excelFile = $("input[type='file']").val();
		
		if (zhangHaoId == "" || zhangHaoId == 0) {
			alert("请选择所属账号！");
			return false;
		}
		if (pubCateId == "0") {
			alert("请选择发布类目！");
			return false;
		}
		if (shippingTemplateId == "0") {
			alert("请选择运费模板！");
			return false;
		}
		if (pubMode == "1" && aliUrl == "") {
			alert("请输入速卖通产品页面地址！");
			return false;
		} else if (pubMode == "2" && excelFile == "") {
			alert("请选择Excel文件！");
			return false;
		}
		return true;
	}
	
	function stopPublish() {
		$.ajax({
			url: "./tools/pubProduct!stopPublish.do",
			dataType: "json",
			success: function(resp) {
				if (resp.success) {
					$("#t1").html("正在结束发布，请稍候...");
					$("#startButton").hide();
				}
			}
		});
		return false;
	}

	function getProgress() {
		if (!$("#progress").isVisible) {
			$("#progress").show();
			$("#startButton").attr("disabled", true);
		}
		$.ajax({
			url: "./tools/pubProduct!getPercent.do",
			dataType: "json",
			success: function(resp) {
				if (resp.success) {
					var data = resp.msg.split(",");
					var percent = data[0];
					var okNum = data[1];
					var errNum = data[2];
					
					$("#percent").html(percent);
					$("#okNum").html(okNum);
					$("#errNum").html(errNum);
					$("#progressBar").css("width", percent);
					if (percent == "100.0%") {
						$("#t1").html("发布产品结束");
						$("#stopButton").hide();
						$("#backButton").show();
						//window.setTimeout("goBack()", 500);
					} else {
						window.setTimeout("getProgress()", 1000);
					}
				}
			}
		});
	}
	
	function getLog() {
		if (!$("#log").isVisible) {
			$("#log").show();
		}
		$.ajax({
			url: "./tools/pubProduct!getLog.do?logQueryStartTime=" + logQueryStartTime,
			dataType: "json",
			success: function(resp) {
				if (resp.success) {
					var data = resp.msg;
					var dataArr = data.split(",");
					var errMsg = "第" + dataArr[0] + "行（" + dataArr[1] + "）：" + dataArr[2] + "</br>";
					logQueryStartTime = dataArr[3];
					$("#log").prepend(errMsg);
				}
				
				var percent = $("#percent").html();
				if (percent != "100.0%") {
					window.setTimeout("getLog()", 1000);
				}
			}
		});
	}
	
	if ($(".errorMessage").length > 0) {
		alert($(".errorMessage").find("span").html());
	}
	
	var srcTextareaName = null;
	var editor = null;
	/*KindEditor.ready(function(K) {
        editor = K.create('#editor', {
        	items: ['source', 'selectall', 'quickformat', '|', 'fontname', 'fontsize', '|', 'undo', 'redo', 'cut', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 'lineheight', '|', 'justifyleft', 'justifycenter', 'justifyright', '|', 'link']
        });
	});*/
	function openHtmlEditor(_srcTextareaName) {
		openDialog();
		if (editor != null) {
			editor.remove();
		}
		editor = KindEditor.create('#editor', {
        	items: ['source', 'selectall', 'quickformat', '|', 'fontname', 'fontsize', '|', 'undo', 'redo', 'cut', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 'lineheight', '|', 'justifyleft', 'justifycenter', 'justifyright', '|', 'link']
        });
		srcTextareaName = _srcTextareaName;
		var srcHtml = $("textarea[name='" + srcTextareaName + "']").val();
		//$("#editor").val(srcHtml);
		//editor.sync();
		editor.html(srcHtml);
	}
	
	function openDialog() {
		var $dialog = $("#dialog");
		$dialog.dialog({
			width: 700,
			height: 450,
			modal: true,
			title: "html编辑器",
			buttons: {
				'确定': function() {
					var html = editor.html();
					$("textarea[name='" + srcTextareaName + "']").val(html);
					$(this).dialog("close");
				},
				'取消': function() {
					editor.remove();
					$(this).dialog("close");
				}
			}
		});
	}
</script>