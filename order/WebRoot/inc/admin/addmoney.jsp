<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<m:frame>
${msg}
  <form action="admin!czMoney.do?pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}" method="post" id="myform" onsubmit="return as()">
  <input type="hidden" name="gukeId" value="${guKeId.id}"/>
  <table border="1"  class="datagrid2" id="list" cellspacing="0" style="float:left">
    <tr>
      <td colspan="4" align="center"><strong>充值</strong></td>
    </tr>
    <tr>
     <td width="54" align="right"><span class="STYLE2">姓名</span></td>
      <td><input name="username" type="text" id="name" size="12" title="姓名" value="${guKeId.name}" readonly="readonly"></td>
	</tr>
	 <tr>
     <td width="54" align="right"><span class="STYLE2">金额</span></td>
      <td><input type="text" name="money" id="money" title="金额 " size="12"></td>
	</tr>
    <tr>    
        <td colspan="8" align="center">
         <input type="submit" value="保存 ">   
          </td>
    </tr>
  </table>
  
  <br>
  </form>
</m:frame>