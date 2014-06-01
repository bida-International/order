<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<m:frame>

	<s:form action="./msg/dhgateMsg.do">
		<table border="1" cellspacing="0" style="width: 95%; float: left">
			<tr>
				<th colspan="9" align="center">敦煌站内信</th>
			</tr>
			<tr>
				<td colspan="9">
					<span>敦煌账号：</span>
					<s:select name="dhAccount" list="dhAccounts" listKey="account"
						listValue="account" headerKey="0" headerValue="全部"
						value="dhAccount"></s:select>
					<span>&nbsp;&nbsp;</span>
					<span>种类：</span> <select name="msgType">
						<option value="0"
							<s:if test="msgType ==  0">selected="selected"</s:if>>全部</option>
						<option value="1"
							<s:if test="msgType ==  1">selected="selected"</s:if>>询盘</option>
						<option value="2"
							<s:if test="msgType ==  2">selected="selected"</s:if>>订单</option>
						<option value="3"
							<s:if test="msgType ==  3">selected="selected"</s:if>>其它</option>
						<option value="4"
							<s:if test="msgType ==  4">selected="selected"</s:if>>订单</option>
						<option value="5"
							<s:if test="msgType ==  5">selected="selected"</s:if>>产品</option>
						<option value="6"
							<s:if test="msgType ==  6">selected="selected"</s:if>>付款</option>
						<option value="7"
							<s:if test="msgType ==  7">selected="selected"</s:if>>促销</option>
						<option value="8"
							<s:if test="msgType ==  8">selected="selected"</s:if>>账户</option>
						<option value="9"
							<s:if test="msgType ==  9">selected="selected"</s:if>>其它</option>
					</select>
					<span>&nbsp;&nbsp;</span>
					<span>状态：</span> <select name="readStatus">
						<option value="-1"
							<s:if test="readStatus ==  -1">selected="selected"</s:if>>全部</option>
						<option value="0"
							<s:if test="readStatus ==  0">selected="selected"</s:if>>未读</option>
						<option value="1"
							<s:if test="readStatus ==  1">selected="selected"</s:if>>已读</option>
					</select>
					<span>&nbsp;&nbsp;</span>
					<span>标记：</span> <select name="marked">
						<option value="-1"
							<s:if test="marked ==  -1">selected="selected"</s:if>>全部</option>
						<option value="0"
							<s:if test="marked ==  0">selected="selected"</s:if>>未标记</option>
						<option value="1"
							<s:if test="marked ==  1">selected="selected"</s:if>>已标记</option>
					</select>
					<span>&nbsp;&nbsp;</span>
					<input type="submit" value="查 询" />
					<span>&nbsp;&nbsp;</span>
					<input type="button" value="置选中为已读" onclick="updateReadStatus()"/>
					<span>&nbsp;&nbsp;</span>
					<input type="button" value="删除选中" onclick="doDelete()"/>
				</td>
			</tr>
			<tr align="center" style="font-weight: bold;">
				<td width="10">
					<input type="checkbox" id="cbx_all" onclick="selectAll()"/>
				</td>
				<td width="5%">状态</td>
				<td width="5%">标记</td>
				<td width="40%">标题</td>
				<td width="8%">种类</td>
				<td width="10%">敦煌账号</td>
				<td width="5%">回复数</td>
				<td width="16%">最后回复时间</td>
				<td width="10%">归属业务用户</td>
			</tr>
			<s:iterator value="pageBean.list" var="i">
				<tr align="center">
					<td>
						<input type="checkbox" name="ids" value="${i.topicId}" onclick="updateCbxAll()" />
					</td>
					<td><s:if test="readStatus == 1">
							<span style="color: #999;">已读</span>
						</s:if> <s:else>
							<span style="color: #ff0000;">未读</span>
						</s:else></td>
					<td>
						<s:if test="marked == 0">
							<img src="./images/star1.png"/>
						</s:if>
						<s:elseif test="marked == 1">
							<img src="./images/star2.png"/>
						</s:elseif>
					</td>
					<td
						style="text-align: left; padding-left: 5px; padding-right: 5px;">
						<a href="./msg/dhgateMsgDetail.do?topicId=${i.topicId}"
						style="text-decoration: underline; color: #000ff0;">${i.title}</a>
					</td>
					<td><s:if test="msgType == 1">询盘</s:if> <s:elseif
							test="msgType == 2">订单</s:elseif> <s:elseif test="msgType == 3">其它</s:elseif>
						<s:elseif test="msgType == 4">订单</s:elseif> <s:elseif
							test="msgType == 5">产品</s:elseif> <s:elseif test="msgType == 6">付款</s:elseif>
						<s:elseif test="msgType == 7">促销</s:elseif> <s:elseif
							test="msgType == 8">账户</s:elseif> <s:elseif test="msgType == 9">其它</s:elseif>
							<s:elseif test="msgType == 10">活动宣传</s:elseif>
							<s:elseif test="msgType == 11">政策通知</s:elseif>
							<s:elseif test="msgType == 12">商品营销</s:elseif>
							<s:elseif test="msgType == 13">其它</s:elseif>
					</td>
					<td>${i.dhAccount }</td>
					<td>${i.msgReplyCount }</td>
					<td><s:property value="getFormatTime(lastReplyTime)" /></td>
					<td><s:if test="bdUserName == null">未分配</s:if> <s:else>${i.bdUserName }</s:else></td>
				</tr>
			</s:iterator>
			<tr>
				<td colspan="25" align="center">总记录数：${pageBean.allRow}
					共几页：${pageBean.allPage} 当前第 ${pageBean.currentPage} 页 <span
					style="margin-left: 50px;">&nbsp;</span> 
					<pg:pager
						url="./msg/dhgateMsg.do" items="${pageBean.allRow}"
						export="currentPageNumber=pageNumber" maxPageItems="10">
						<pg:param name="dhAccount" value="${dhAccount }"/>
						<pg:param name="msgType" value="${msgType }"/>
						<pg:param name="readStatus" value="${readStatus }"/>
						<pg:param name="marked" value="${marked }"/>
						<pg:first>
							<a href="${pageUrl}&pageNumber=1">首页</a>
						</pg:first>
						<pg:prev>
							<a href="${pageUrl}&pageNumber=${pageBean.currentPage-1}">上一页</a>
						</pg:prev>
						<pg:pages>
							<c:choose>
								<c:when test="${currentPageNumber eq pageNumber}">
									<font color="red">${pageNumber}</font>
								</c:when>
								<c:otherwise>
									<a href="${pageUrl}&pageNumber=${pageNumber}">${pageNumber}</a>
								</c:otherwise>
							</c:choose>
						</pg:pages>
						<pg:next>
							<a href="${pageUrl}&pageNumber=${pageBean.currentPage+1}">下一页</a>
						</pg:next>
						<pg:last>
							<a href="${pageUrl}&pageNumber=${pageBean.allPage}">尾页</a>
						</pg:last>
					</pg:pager>
				</td>
			</tr>
		</table>
	</s:form>
</m:frame>
<script type="text/javascript">
	function selectAll() {
		var checked = $("#cbx_all").attr("checked");
		if (checked == null) {
			checked = false;
		}
		$("input[type='checkbox'][name='ids']").attr("checked", checked);
	}
	
	function updateCbxAll() {
		var cbxNum= $("input[type='checkbox'][name='ids']").length;
		var checkedNum = 0;
		$("input[type='checkbox'][name='ids']").each(function() {
			if ($(this).attr("checked")) {
				checkedNum = checkedNum + 1;
			}
		});
		if (cbxNum == checkedNum) {
			$("#cbx_all").attr("checked", "checked");
		} else {
			$("#cbx_all").attr("checked", false);
		}
	}
	
	function doDelete() {
		if ($("input[type='checkbox'][name='ids']:checked").length == 0) {
			alert("您还没有选择站内信");
			return;
		}	
		var ids = getCheckedIds();
		$.ajax({
			url: "./msg/dhgateMsg!delete.do",
			type: "post",
			data: {
				"ids" : ids
			},
			dataType: "json",
			success: function(resp) {
				if (resp.success) {
					location.href = location.href;
				} else {
					alert(resp.msg);
				}
				
			}
		});
	}
	
	function updateReadStatus() {
		if ($("input[type='checkbox'][name='ids']:checked").length == 0) {
			alert("您还没有选择站内信");
			return;
		}	
		var ids = getCheckedIds();
		$.ajax({
			url: "./msg/dhgateMsg!updateReaded.do",
			type: "post",
			data: {
				"ids" : ids
			},
			dataType: "json",
			success: function(resp) {
				if (resp.success) {
					location.href = location.href;
				} else {
					alert(resp.msg);
				}
				
			}
		});
	}
	
	function getCheckedIds() {
		var ids = "";
		$("input[type='checkbox'][name='ids']").each(function() {
			if ($(this).attr('checked')) {
				ids += $(this).val() + ",";
			}
		});
		ids = ids.substr(0, ids.length - 1);
		return ids;
	}
</script>
