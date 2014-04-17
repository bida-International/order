<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<m:frame>
	<form action="./tools/gatherData1Result.do" method="post" name="forms" id="forms">
		<table border="1" cellspacing="0" style="width: 100%; float: left">
			<tr>
				<th colspan="4" align="center">
					<a href="./tools/gatherData1Key.do" style="float:right">返回采集记录列表</a>
					<span>采集结果</span>
				</th>
			</tr>
			<tr>
				<td colspan="4">
					导出时间：<input type="text" name="exporttime" onfocus="WdatePicker()" id="exporttime" value="${param.exporttime}"/>至<input type="text" name="exporttime1" onfocus="WdatePicker()" id="exporttime1" value="${param.exporttime1}"/>
					<input type="submit" value="查询"/>
		        	<s:hidden name="pageNumber" value="1"></s:hidden>
					<s:hidden name="pager.offset" id="pager.offset" value="0"></s:hidden>
					<input type="hidden" value="${tit}" name="tit"> 
				</td>
			</tr>
			<tr align="center" style="font-weight: bold;">
				<td width="76%">产品链接</td>
				<td width="8%">订单数</td>
				<td width="8%">是否导出</td>
				<td width="8%">导出时间</td>
			</tr>
			<s:iterator value="pageBean.list" var="i">
				<tr align="center">
					<td>${i.link }</td>
					<td>${i.orderNum }</td>
					<td>${(i.sfexport==0 || i.sfexport==null)?('否'):('是') }</td>
					<td>${i.exporttime}</td>
				</tr>
			</s:iterator>
			<tr>
				<td colspan="4" align="center">总记录数：${pageBean.allRow}
					共几页：${pageBean.allPage} 当前第 ${pageBean.currentPage} 页 <span
					style="margin-left: 50px;">&nbsp;</span> 
					<pg:pager
						url="./tools/gatherData1Result.do" items="${pageBean.allRow}"
						export="currentPageNumber=pageNumber" maxPageItems="${pageSize }" maxIndexPages="10" >
						<pg:param name="keyCreateTime" value="${keyCreateTime }"/>
						<pg:first>
							<a href="${pageUrl}&pageNum=1&exporttime=${param.exporttime}&exporttime1=${param.exporttime1}">首页</a>
						</pg:first>
						<pg:prev>
							<a href="${pageUrl}&pageNum=${pageBean.currentPage-1}&exporttime=${param.exporttime}&exporttime1=${param.exporttime1}">上一页</a>
						</pg:prev>
						<pg:pages>
							<c:choose>
								<c:when test="${currentPageNumber eq pageNumber}">
									<font color="red">${pageNumber}</font>
								</c:when>
								<c:otherwise>
									<a href="${pageUrl}&pageNum=${pageNumber}&exporttime=${param.exporttime}&exporttime1=${param.exporttime1}">${pageNumber}</a>
								</c:otherwise>
							</c:choose>
						</pg:pages>
						<pg:next>
							<a href="${pageUrl}&pageNum=${pageBean.currentPage+1}&exporttime=${param.exporttime}&exporttime1=${param.exporttime1}">下一页</a>
						</pg:next>
						<pg:last>
							<a href="${pageUrl}&pageNum=${pageBean.allPage}&exporttime=${param.exporttime}&exporttime1=${param.exporttime1}">尾页</a>
						</pg:last>
					</pg:pager>
				</td>
			</tr>
		</table>
	</form>
</m:frame>