<%@ page language="java" pageEncoding="gbk"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="mytag" tagdir="/WEB-INF/tags"%>
<mytag:frame>
<style type="text/css">
<!--

.aa {
	border: 1px solid #CCCCCC;
}
-->
</style>
<script>
	function daochu(){
		var shangwang = document.getElementById("shangwang").value;
		var qianshou = document.getElementById("qianshou").value;
		var ruzhang = document.getElementById("ruzhang").value;
		if(shangwang.checked == true && shangwang.value == 0)
		{
			
		}
	}
</script>
<s:form action="exportOrder!execute.do" theme="simple">
	<table border="1" class="aa" width="500px">
		<tr>
			<td>是否上网:</td>
			<td>
				<input name="shangwang" type="radio" id="shangwang" size="7" style="ime-mode:disabled" value="0">否
				<input name="shangwang" type="radio" id="shangwang" size="7" style="ime-mode:disabled" value="1">是
			 </td>
			 <td>是否签收:</td>
			 <td>
			 	<input name="qianshou" type="radio" id="qianshou" size="7" style="ime-mode:disabled" value="0">否
				<input name="qianshou" type="radio" id="qianshou" size="7" style="ime-mode:disabled" value="1">是
			 </td>
			  <td>是否入账:</td>
			  <td>
			  	<input name="ruzhang" type="radio" id="ruzhang" size="7" style="ime-mode:disabled" value="0">否
			  	<input name="ruzhang" type="radio" id="ruzhang" size="7" style="ime-mode:disabled" value="1">是
			  </td>
		</tr>
		<tr><td colspan="6" align="center"><s:submit value="导出用户数据"></s:submit></td></tr>		
	</table>
</s:form>
</mytag:frame>