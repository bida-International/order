<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<m:frame>
	<s:form action="./tools/productStatusConfig!save.do" method="post">
		 <table border="1" cellspacing="0" style="width: 500px; float: left">
			<tr>
				<th colspan="2" align="center">产品状态同步功能设置</th>
			</tr>
			<tr>
				<td>自动同步状态：</td>
				<td><s:radio name="autoSynchProductStatus" list="#{0: '关', 1: '开'}" value="siteConfig.autoSynchProductStatus"></s:radio></td>
			</tr>
			<tr align="center" style="font-weight: bold;">
				<td colspan="2">
					<input type="button" value=" 返 回 " onclick="goBack()" />
					<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
					<input type="submit" value=" 保 存 " />
				</td>
			</tr>
		</table> 
	</s:form>
	<s:if test="hasActionMessages()">
	<div style="display:none">
		<s:actionmessage/>
	</div>
	</s:if>
</m:frame>
<script type="text/javascript">
function goBack() {
	location.href='./tools/productStatus.do';
}

if ($(".actionMessage").length > 0) {
	alert($(".actionMessage").find("span").html());
	location.href = "./tools/productStatusConfig.do";
}
</script>