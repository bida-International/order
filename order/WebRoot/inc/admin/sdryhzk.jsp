<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<m:frame>
<s:form action="admin!addzkhry.do">
${msg}
<table border="1" width="300px"  class="datagrid2" id="list">
<tr><td colspan="2">设定燃油和折扣</td></tr>
<tr><td>DHL<input type="radio" name="fd" value="0"/></td><td>Fedex<input type="radio" name="fd" value="1"/></td></tr>
<tr><td>设定燃油</td><td><input type="text" name="ry"></td></tr>
<tr><td>设定折扣</td><td><input type="text" name="zk"></td></tr>
<tr><td colspan="2"><s:submit value="设定" align="center" style="cursor:hand"></s:submit></td></tr>
</table>
</s:form>
</m:frame>