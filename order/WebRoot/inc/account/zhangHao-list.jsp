<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<m:frame>

	<s:form action="./account/zhangHao!list.do">
		<table border="1" cellspacing="0" style="float: left">
			<tr>
				<th colspan="6" align="center">卖家账号列表</th>
			</tr>
			<tr>
				<td colspan="6">
					<span>账号类型：</span> <select name="accountType">
						<option value="all"
							<s:if test="accountType.equals('all')">selected="selected"</s:if>>全部</option>
						<option value="dh"
							<s:if test="accountType.equals('dh')">selected="selected"</s:if>>敦煌网</option>
						<option value="ali"
							<s:if test="accountType.equals('ali')">selected="selected"</s:if>>速卖通</option>
					</select>
					<span>&nbsp;&nbsp;</span>
					<input type="submit" value="查 询" />
					<span>&nbsp;&nbsp;</span>
					<span>&nbsp;&nbsp;</span>
					<a href="./account/zhangHao!edit.do">添加账号</a>
				</td>
			</tr>
			<tr align="center" style="font-weight: bold;">
				<td width="200">账号</td>
				<td width="100">类型</td>
				<td width="150">订单同步时间</td>
				<td width="150">站内信同步时间</td>
				<td width="100">授权状态</td>
				<td width="200">操作</td>
			</tr>
			<s:iterator value="pageBean.list" var="i">
				<tr align="center">
					<td>${i.account }</td>
					<td><s:if test="account_type.equals('dh')">敦煌网</s:if> <s:elseif
							test="account_type.equals('ali')">速卖通</s:elseif>
					</td>
					<td>
						<s:if test="order_update_time != null">
							<s:property value="getFormatTime(order_update_time)"/>
						</s:if>
					</td>
					<td>
						
						<s:if test="msg_update_time != null">
							<s:property value="getFormatTime(msg_update_time)"/>
						</s:if>
					</td>
					<td>
						<s:if test="access_token == null"><span style="color:#ff0000;">未授权</span></s:if>
						<s:else>已授权</s:else>
					</td>
					<td>
						<s:if test="account_type.equals('dh')">
							<a href="javascript:showDhgateAuthDialog('${i.account }')">现在授权</a>
							<span>&nbsp;|&nbsp;</span>
						</s:if>
						<s:elseif test="account_type.equals('ali') && app_key != null">
							<a href="javascript:showAliAuthDialog('${i.account }')">现在授权</a>
							<span>&nbsp;|&nbsp;</span>
						</s:elseif>
						<a href="./account/zhangHao!edit.do?id=${i.id }">编辑</a>
						<span>&nbsp;|&nbsp;</span>
						<a href="javascript:doDelete(${i.id}, '${i.account }')">删 除</a>
					</td>
				</tr>
			</s:iterator>
			<tr>
				<td colspan="6" align="center">总记录数：${pageBean.allRow}
					共几页：${pageBean.allPage} 当前第 ${pageBean.currentPage} 页 <span
					style="margin-left: 50px;">&nbsp;</span> 
					<pg:pager
						url="./account/zhangHao!list.do" items="${pageBean.allRow}"
						export="currentPageNumber=pageNumber" maxPageItems="10">
						<pg:param name="accountType" value="${accountType }"/>
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
			<tr>
				<td colspan="6" style="color:#ff0000;">
					注：
					<ol>
						<li>账号需要授权后才能自动同步数据</li>
						<li>如果账号未授权，或者发现账号没有同步最新数据（可能授权已失效），请点击操作栏“现在授权”进行手动授权</li>
						<li>提示授权成功后需要刷新页面才能看到最新授权状态</li>
					</ol>
				</td>
			</tr>
		</table>
	</s:form>
	
	<div id="dhgateAuthDialog" style="display:none;">
		<p class="dialog_msg" style="color:#ff0000; padding-bottom:10px;"></p>
		<p>敦煌网账号：<span class="authAccount"></span></p>
		<p>敦煌网密码：<input type="password" class="authPass" /></p>
	</div>
	<div id="aliAuthDialog" style="display:none;">
		<iframe src="" width="700" height="500" style="border:0;"></iframe>
	</div>
</m:frame>
<script type="text/javascript">
	function doDelete(id, account) {
		if (!confirm('确定要删除账号' + account+ '吗？')) {
			return false;
		}
		$.ajax({
			url: "./account/zhangHao!delete.do",
			type: "post",
			data: {
				id : id
			},
			dataType: "json",
			success: function(resp) {
				if (resp.success) {
					location.href = location.href;
				}
			}
		});
	}
</script>