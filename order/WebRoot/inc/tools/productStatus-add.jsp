<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<m:frame>
	<s:form action="./tools/productStatus!save.do" method="post" onsubmit="return checkForm()">
		 <table border="1" cellspacing="0" style="width: 1000px; float: left">
			<tr>
				<th colspan="2" align="center">添加产品</th>
			</tr>
			<tr>
				<td>敦煌产品页面地址：</td>
				<td><input type="text" id="dhUrl" name="dhUrl" value="${dhUrl}" style="width:850px" /></td>
			</tr>
			<tr>
				<td>速卖通产品页面地址：</td>
				<td><input type="text" id="aliUrl" name="aliUrl" value="${aliUrl}" style="width:850px" /></td>
			</tr>
			<tr>
				<td>所属账号：</td>
				<td><s:select id="zhangHaoId" name="zhangHaoId" list="zhangHaoList" listKey="id" listValue="account"
					headerKey="0" headerValue="全部"  value="zhangHaoId"></s:select></td>
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
	
	<s:if test="hasActionErrors()">
	<div style="display:none">
		<s:actionerror/>
	</div>
	</s:if>
</m:frame>
<script type="text/javascript">
	function goBack() {
		location.href='./tools/productStatus.do';
	}

	function checkForm() {
		var dhUrl = $.trim($("#dhUrl").val());
		var aliUrl =  $.trim($("#aliUrl").val());
		var zhangHaoId = $("#zhangHaoId").val();
		if (dhUrl == "") {
			alert("请输入敦煌产品页面地址！");
			return false;
		}
		if (aliUrl == "") {
			alert("请输入速卖通产品页面地址！");
			return false;
		}
		if (zhangHaoId == 0) {
			alert("请选择所属账号！");
			return false;
		}
		return true;
	}
	
	if ($(".errorMessage").length > 0) {
		alert($(".errorMessage").find("span").html());
	}
</script>