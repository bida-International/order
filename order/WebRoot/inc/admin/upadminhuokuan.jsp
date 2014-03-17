<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>

<m:frame>
<s:form theme="simple" action="admin!getupadminhuokuan.do">
${msg}
  <table border="1" cellspacing="0"  class="datagrid2" id="list" style="float:left">
    <tr>
      <td colspan="3" align="center"><strong>修改订单</strong></td>
    </tr>
      <tr>
	  	<td colspan="3">
	  	 <input type="text" name="orderId" value="${param.orderId}"/><input type="submit" value="查询"/>
	  	
	  	</td>
	  </tr>
    <tr align="center">
      <td width="163"><span class="STYLE2">订单号</span></td>
      <td width="100"><span class="STYLE2">上传时间</span></td>
      <td width="54"><span class="STYLE2">操作</span></td>
    </tr>
    <s:iterator value="allOrder.pagelist" var="i">
      <tr align="center">
        <td><s:property value="#i.orderId"/></td>
	    <td><s:property value="#i.time"/></td>	  
	    <td><a href="admin!getorderIdsd.do?ordertable.id=${i.id}">修改</a></td>   
     </tr>    
   </s:iterator>
   <tr> <td colspan="3" align="center">
       		 共${allOrder.count}条数据 共${allOrder.pagecount}页 第${allOrder.pageindex}页 <a href="admin!getupadminhuokuan.do?pageindex=1&orderId=${param.orderId}">首页</a>
            <s:if test="%{allOrder.pageindex>1}"> <a href="admin!getupadminhuokuan.do?pageindex=${allOrder.pageindex-1}&orderId=${param.orderId}">上一页 </a> </s:if>
            <s:if test="%{allOrder.pagecount>allOrder.pageindex}"> <a href="admin!getupadminhuokuan.do?pageindex=${allOrder.pageindex+1}&orderId=${param.orderId}">下一页</a> </s:if>
          <a href="admin!getupadminhuokuan.do?pageindex=${allOrder.pagecount}&orderId=${param.orderId}"> 尾页 </a></td></tr>
  </table>
  </s:form>
  <br>
</m:frame>