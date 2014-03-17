<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
${msg}
<m:frame>
<s:form theme="simple" action="admin!getuphuokuan.do" cssStyle="cellspacing:0;cellpadding:0">
  <table border="1"  class="datagrid2" id="list" cellspacing="0" style="float:left">
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
      <td width="150"><span class="STYLE2">上传时间</span></td>
      <td width="54"><span class="STYLE2">操作</span></td>
    </tr>
    <s:iterator value="deDaoOrder.pagelist" var="i">
      <tr align="center">
        <td><s:property value="#i.orderId"/></td>
	    <td><s:property value="#i.time"/></td>	  
	    <td><a href="admin!getorderIds.do?ordertable.id=${i.id}">修改</a></td>   
     </tr>    
   </s:iterator>
   <tr> <td colspan="3" align="center">
       		 共${deDaoOrder.count}条数据 共${deDaoOrder.pagecount}页 第${deDaoOrder.pageindex}页 <a href="admin!getuphuokuan.do?pageindex=1&orderId=${param.orderId}">首页</a>
            <s:if test="%{deDaoOrder.pageindex>1}"> <a href="admin!getuphuokuan.do?pageindex=${deDaoOrder.pageindex-1}&orderId=${param.orderId}">上一页 </a> </s:if>
            <s:if test="%{deDaoOrder.pagecount>deDaoOrder.pageindex}"> <a href="admin!getuphuokuan.do?pageindex=${deDaoOrder.pageindex+1}&orderId=${param.orderId}">下一页</a> </s:if>
          <a href="admin!getuphuokuan.do?pageindex=${deDaoOrder.pagecount}&orderId=${param.orderId}"> 尾页 </a></td></tr>
  </table>
  <br>
  </s:form>
</m:frame>