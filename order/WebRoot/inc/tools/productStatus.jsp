<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<m:frame>
<style type="text/css">
.status_word {font-size:14px; font-weight:bold;}
.txt_url {width:330px; height:60px; border:0; overflow-y:hidden; font-size:12px;}
</style>

	<s:form action="./tools/productStatus.do" method="post">
		 <table border="1" cellspacing="0" style="width: 100%; float: left">
			<tr>
				<th colspan="3" align="center">产品列表</th>
			</tr>
			<tr>
				<td colspan="3">
					<span>归属账号：</span>
					<s:select name="zhangHaoId" list="zhangHaoList" listKey="id" listValue="account" 
						 headerKey="0" headerValue="全部" value="zhangHaoId"></s:select>
					<span>敦煌状态：</span>
					<s:select name="dhStatus" list="#{-1:'全部', 0: '下架', 1: '上架', 2: '获取失败'}"  value="dhStatus"></s:select>
					<span>速卖通状态：</span>
					<s:select name="aliStatus" list="#{-1:'全部', 0: '下架', 1: '上架', 2: '获取失败'}"  value="aliStatus"></s:select>
					<span>两边状态：</span>
					<s:select name="statusFlag" list="#{-1:'全部', 0: '状态不同步', 1: '状态同步', 2: '获取失败'}"  value="statusFlag"></s:select>
					<span>产品链接地址关键字：</span>
					<input type="text" name="urlKeyword" value="${urlKeyword }"/>
					<span>&nbsp;&nbsp;</span>
					<input type="submit" value="查 询" />
					<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
					<a href="./tools/productStatus!add.do">添加产品</a>
					<span>&nbsp;&nbsp;</span>
					<a href="./tools/productStatusConfig.do">功能设置</a>
					<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
					<a href="./tools/pubProduct.do">发布产品</a>
				</td>
			</tr>
			<tr align="center" style="font-weight: bold;">
				<td>敦煌产品信息</td>
				<td>速卖通产品信息</td>
				<td>备注及操作</td>
			</tr>
			<s:iterator value="pageBean.list" var="i">
				<tr align="center">
					<td>
						<div style="float:left; width:80px; height:80px;"><s:if test="dhImgUrl != null"><img src="${i.dhImgUrl }" width="60" height="60" /></s:if></div>
						<div style="float:left; text-align:left;">
							<span>状态：</span>
							<s:if test="dhStatus == 0"><span class='status_word' style='color:#ccc'>下架</span></s:if>
							<s:elseif test="dhStatus == 1"><span class='status_word' style='color:#ff0000'>上架</span></s:elseif>
							<s:elseif test="dhStatus == 2"><span class='status_word' style='color:#336699'>获取失败</span></s:elseif>
							<br/>
							<span>检测时间：<s:property value="getFormatTime(checkTime)"/></span>
							<br/>
							<textarea class="txt_url">${dhUrl}</textarea>
						</div>
						<div style="clear:left;"></div>
					</td>
					<td>
						<div style="float:left; width:80px; height:80px;"><s:if test="aliImgUrl != null"><img src="${i.aliImgUrl }" width="60" height="60" /></s:if></div>
						<div style="float:left; text-align:left;">
							<span>状态：</span>
							<s:if test="aliStatus == 0"><span class='status_word' style='color:#ccc'>下架</span></s:if>
							<s:elseif test="aliStatus == 1"><span class='status_word' style='color:#ff0000'>上架</span></s:elseif>
							<s:elseif test="aliStatus == 2"><span class='status_word' style='color:#336699'>获取失败</span></s:elseif>
							<br/>
							<span>检测时间：<s:property value="getFormatTime(checkTime)"/></span>
							<br/>
							<textarea class="txt_url">${aliUrl}</textarea>
						</div>
						<div style="clear:left;"></div>
					</td>
					<td>
						<s:if test="statusFlag == 0">
							<s:if test="dhStatus == 0">
								<a href="javascript:upself(${i.id })">上架敦煌产品</a>
							</s:if>
							<s:elseif test="dhStatus == 1">
								<a href="javascript:downself(${i.id })">下架敦煌产品</a>
							</s:elseif>
						</s:if>
						<br/><br/>
						<a href="javascript:doDelete(${i.id })">删除记录</a>
					</td>
				</tr>
			</s:iterator>
			<tr>
				<td colspan="3" align="center">总记录数：${pageBean.allRow}
					共几页：${pageBean.allPage} 当前第 ${pageBean.currentPage} 页 <span
					style="margin-left: 50px;">&nbsp;</span> 
					<pg:pager
						url="./tools/productStatus.do" items="${pageBean.allRow}"
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
<script type="text/javascript">
function doDelete(id) {
	$.ajax({
		url: "./tools/productStatus!delete.do",
		type: "post",
		data: {
			id: id
		},
		dataType: "json",
		success: function(resp) {
			if (resp.success) {
				alert("删除产品成功!");
				location.href = location.href;
			} else {
				alert(resp.msg);
			}
		}
	});
}

function upself(id) {
	$.ajax({
		url: "./tools/productStatus!upself.do",
		type: "post",
		data: {
			id: id
		},
		dataType: "json",
		success: function(resp) {
			if (resp.success) {
				alert("上架产品操作成功!");
				location.href = location.href;
			} else {
				alert("上架产品操作成功!");
			}
		}
	});
}

function downself(id) {
	$.ajax({
		url: "./tools/productStatus!downself.do",
		type: "post",
		data: {
			id: id
		},
		dataType: "json",
		success: function(resp) {
			if (resp.success) {
				alert("下架产品操作成功!");
				location.href = location.href;
			} else {
				alert("下架产品操作成功!");
			}
		}
	});
}
</script>