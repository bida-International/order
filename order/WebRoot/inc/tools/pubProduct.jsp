<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<m:frame>
	
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

<s:form action="./tools/pubProduct!doPublish.do" method="post" onsubmit="return checkForm()">
	 <table id="step2" border="1" cellspacing="0" style="width: 95%; float: left; display:none;">
		<tr>
			<th colspan="2" align="center">发布产品</th>
		</tr>
		<tr>
			<td align="right">发布账号(必填)：</td>
			<td>
				<span id="zhangHaoName"></span>
				<input type="hidden" id="zhangHaoId" name="zhangHaoId" />
			</td>
		</tr>
		<tr>
			<td align="right">发布类目(必填)：</td>
			<td id="sel_cate_box">
				<input type="hidden" id="pubCateId" name="pubCateId" value="0" />
				<select id="sel_cate_1" onchange='onCateSelected(this)'>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right">运费模板(必填)：</td>
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
			<td align="right">速卖通产品页面地址(必填)：</td>
			<td><input type="text" id="aliUrl" name="aliUrl"  style="width:800px" /></td>
		</tr>
		<tr align="center" style="font-weight: bold;">
			<td colspan="2">
				<input type="button" value=" 上一步 " onclick="prev()" />
				<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
				<input type="button" value=" 发 布 " onclick="doPublish()" />
			</td>
		</tr>
	</table>
	<p id="errmsg" style="color:#ff0000;"></p>
</s:form>
	
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

	function doPublish() {
		if (!checkForm()) {
			return;
		}
		$waiting("正在提交数据，请稍候...");
		$.ajax({
			url: "./tools/pubProduct!doPublish.do",
			type: "post",
			data: {
				zhangHaoId: $("#zhangHaoId").val(),
				pubCateId: $("#pubCateId").val(),
				shippingTemplateId: $("#sel_template").val(),
				productGroupId: $("#sel_group").val(),
				aliUrl: $.trim($("#aliUrl").val())
			},
			dataType: "json",
			success: function(respJson) {
				$remove_wariting();
				if (respJson.success) {
					alert("产品发布成功！");
					goBack();
				} else {
					alert(respJson.msg);
				}
			}
		});
	}
	
	function checkForm() {
		var zhangHaoId = $("#zhangHaoId").val();
		var pubCateId = $("#pubCateId").val();
		var shippingTemplateId = $("#sel_template").val();
		var aliUrl =  $.trim($("#aliUrl").val());
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
		if (aliUrl == "") {
			alert("请输入速卖通产品页面地址！");
			return false;
		}
		return true;
	}
	
	if ($(".errorMessage").length > 0) {
		alert($(".errorMessage").find("span").html());
	}
</script>