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
			<td>�Ƿ�����:</td>
			<td>
				<input name="shangwang" type="radio" id="shangwang" size="7" style="ime-mode:disabled" value="0">��
				<input name="shangwang" type="radio" id="shangwang" size="7" style="ime-mode:disabled" value="1">��
			 </td>
			 <td>�Ƿ�ǩ��:</td>
			 <td>
			 	<input name="qianshou" type="radio" id="qianshou" size="7" style="ime-mode:disabled" value="0">��
				<input name="qianshou" type="radio" id="qianshou" size="7" style="ime-mode:disabled" value="1">��
			 </td>
			  <td>�Ƿ�����:</td>
			  <td>
			  	<input name="ruzhang" type="radio" id="ruzhang" size="7" style="ime-mode:disabled" value="0">��
			  	<input name="ruzhang" type="radio" id="ruzhang" size="7" style="ime-mode:disabled" value="1">��
			  </td>
		</tr>
		<tr><td colspan="6" align="center"><s:submit value="�����û�����"></s:submit></td></tr>		
	</table>
</s:form>
</mytag:frame>