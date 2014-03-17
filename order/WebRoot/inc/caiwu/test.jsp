<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<m:frame>
	<s:form action="caiwu!ImportOrders.do" enctype="multipart/form-data" method="post" onsubmit="return as()">
	  <table>
	  <tr>
	  	<td align="right"><SPAN style="color:red">导入</SPAN></td>
	  	<td colspan="1">
	  	<s:file  name="excelfile" accept=".xls"></s:file>
		<s:submit value="导入"></s:submit>
	  	</td>
	  </tr>
	  </table>
  </s:form>
</m:frame>