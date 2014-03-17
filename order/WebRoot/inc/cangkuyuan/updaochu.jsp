<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<m:frame>
<form action="cangkuyuan!updatedaochu.do?ordertable.id=${cangKuYuanAll.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}" method="post" id="myform" onsubmit="return as()">
<table border="1" width="300px">
	<tr><td colspan="2" align="center">修改导出</td></tr>
	<tr><td align="right">订单号:</td><td><input type="text" value="${cangKuYuanAll.orderId}" readonly="readonly"/></td></tr>
	<tr><td align="right">是否被导出</td>
	
		 <td>
      	 <s:if test="%{cangKuYuanAll.daochu==0 || cangKuYuanAll.daochu==null}">
      	 	<input name="ordertable.daochu" type="radio" id="daochu" style="ime-mode:disabled" value="0" checked="checked">否
      	 	<input name="ordertable.daochu" type="radio" id="daochu" style="ime-mode:disabled" value="1">是
      	 </s:if>
      	 	 <s:if test="%{cangKuYuanAll.daochu==1}">
      	 	<input name="ordertable.daochu" type="radio" id="daochu" style="ime-mode:disabled" value="0">否
      	 	<input name="ordertable.daochu" type="radio" id="daochu" style="ime-mode:disabled" value="1" checked="checked">是
      	 </s:if>
      	 </td>
	</tr>
	 <tr>    
        <td colspan="2" align="center">
         <input type="submit" value="保存 ">   
          </td>
    </tr>
</table>
</form>
</m:frame>