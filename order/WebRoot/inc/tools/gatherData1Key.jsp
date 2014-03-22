<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<m:frame>

	<s:form action="./tools/gatherData1Key.do">
		<table border="1" cellspacing="0" style="width: 100%; float: left">
			<tr>
				<th colspan="4" align="center">采集记录</th>
			</tr>
			<tr align="center" style="font-weight: bold;">
				<td width="70%">采集网址</td>
				<td width="10%">采集条件</td>
				<td width="12%">采集时间</td>
				<td width="8%">操作</td>
			</tr>
			<s:iterator value="pageBean.list" var="i">
				<tr align="center">
					<td>${i.gatherKey }</td>
					<td>订单数大于 ${i.queryParam }</td>
					<td><s:property value="getFormatTime(createTime)" /></td>
					<td><a href="./tools/gatherData1Result.do?keyCreateTime=${i.createTime }">详细</a>
						<span>&nbsp;&nbsp;|&nbsp;&nbsp;</span>
						<a href="./tools/gatherData1Key!delete.do?id=${i.id}&keyCreateTime=${i.createTime}&pageNumber=${pageNumber}">删除</a></td>
				</tr>
			</s:iterator>
			<tr>
				<td colspan="4" align="center">总记录数：${pageBean.allRow}
					共几页：${pageBean.allPage} 当前第 ${pageBean.currentPage} 页 <span
					style="margin-left: 50px;">&nbsp;</span> 
					<pg:pager
						url="./tools/gatherData1Key.do" items="${pageBean.allRow}"
						export="currentPageNumber=pageNumber" maxPageItems="${pageSize }" maxIndexPages="10">
						<pg:first>
							<a href="${pageUrl}&pageNum=1">首页</a>
						</pg:first>
						<pg:prev>
							<a href="${pageUrl}&pageNum=${pageBean.currentPage-1}">上一页</a>
						</pg:prev>
						<pg:pages>
							<c:choose>
								<c:when test="${currentPageNumber eq pageNum}">
									<font color="red">${pageNumber}</font>
								</c:when>
								<c:otherwise>
									<a href="${pageUrl}&pageNum=${pageNumber}">${pageNumber}</a>
								</c:otherwise>
							</c:choose>
						</pg:pages>
						<pg:next>
							<a href="${pageUrl}&pageNum=${pageBean.currentPage+1}">下一页</a>
						</pg:next>
						<pg:last>
							<a href="${pageUrl}&pageNum=${pageBean.allPage}">尾页</a>
						</pg:last>
					</pg:pager>
				</td>
			</tr>
		</table>
	</s:form>
</m:frame>