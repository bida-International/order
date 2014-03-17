<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<m:frame>

  <form action="cangkuyuan!updateremark.do?ordertable.id=${cangKuYuanAll.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}" method="post" id="myform" onsubmit="return as()">
  <input type="hidden" name="ordertable.caigouyuan" value="${cangKuYuanAll.caigouyuan}"/>
  <table border="1" width="300px" cellspacing="0" style="float:left">
    <tr>
      <td colspan="4" align="center"><strong>修改订单</strong></td>
    </tr>
    <tr>
     <td width="54" align="right"><span class="STYLE2">订单号</span></td>
      <td><input name="ordertable.orderId" type="text" id="orderId" size="12" title="订单号" value="${cangKuYuanAll.orderId}" readonly="readonly"></td>
	</tr>
	<tr>
     <td width="54" align="right"><span class="STYLE2">备注</span></td>
      <td><textarea name="ordertable.remark" title="备注">${cangKuYuanAll.remark}</textarea> </td>
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