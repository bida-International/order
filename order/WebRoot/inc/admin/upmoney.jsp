<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<m:frame>
  <form action="admin!updateMoney.do?yuCunId=${yuCunId.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}" method="post" id="myform" onsubmit="return as()">
  
  <table border="1" cellspacing="0"  class="datagrid2" id="list" style="float:left">
    <tr>
      <td colspan="4" align="center"><strong>修改金额</strong></td>
    </tr>
    <tr>
     <td width="54" align="right"><span class="STYLE2">姓名</span></td>
      <td>
      <s:iterator value="yuCunId" var="i">
      <input name="username" type="text" id="name" size="12" title="姓名" value="<s:property value="getSelName(#i.gukeId)"/>" readonly="readonly">
       </s:iterator>
      </td>
     
	</tr>
	 <tr>
     <td width="54" align="right"><span class="STYLE2">金额</span></td>
      <td>
		   <input type="text" name="money" title="金额" value="${yuCunId.money}" id="money" size="12"/>  	
	  </td>
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