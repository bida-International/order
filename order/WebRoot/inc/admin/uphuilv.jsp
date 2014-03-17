<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<script type="text/javascript"  src="My97DatePicker/WdatePicker.js"></script>
<m:frame>
<form action="admin!upHuiLv.do" method="post">
<table border="1" width="500px"  class="datagrid2" id="list">
<tr><td colspan="2" align="center">修改汇率</td></tr>
  <tr>
      <td align="right">日期选择：</td>
      <td>
      <input type="text" onfocus="WdatePicker()" name="time" />至<input type="text" onfocus="WdatePicker()" name="time1" />
    </td>
    </tr>
    <tr>
    <td align="right">汇率：</td>
      <td>
      	<input type="text" name="huilv" />
    </td>
    </tr>
    <tr>
     <td colspan="2" align="center">
     <input type="submit" value="提交" style="cursor:hand"/>
     </td>
     </tr>
</table>
${msg}
</form>
</m:frame>