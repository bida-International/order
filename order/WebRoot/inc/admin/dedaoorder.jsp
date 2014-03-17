<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>

<m:frame>
<s:form theme="simple" action="admin!getDeOrder.do" cssStyle=cellspacing:0;cellpadding:0>
  <table border="1"  class="datagrid2" id="list" cellspacing="0" style="float:left">
    <tr>
      <td colspan="4" align="center"><strong>得到订单</strong></td>
    </tr>
    <tr>
    	<td colspan="4" align="center">
	  	 <input type="text" name="orderId" value="${param.orderId}"/><s:submit value="查询"/>
	  	</td>
    </tr>
    <tr align="center">
      <td width="163"><span class="STYLE2">订单号</span></td>
      <td width="54"><span class="STYLE2">上传时间</span></td>
      <td width="64"><span class="STYLE2">操作</span></td>
    </tr>
    <s:iterator value="deDaoOrder.pagelist" var="sd" >
	     <tr align="center">
	         <td>${sd.orderId}</td>
	   		 <td>${sd.time}</td>	       
		     <td>
		     	<a href="admin!updedao.do?ordertable.id=${sd.id}" onclick="return confirm('是否返回给管理员?')">未完成</a>
		     </td>	
	     </tr>   
     </s:iterator> 
     <tr> 
     	<td colspan="4" align="center">
       		 共${deDaoOrder.count}条数据 共${deDaoOrder.pagecount}条 第${deDaoOrder.pageindex}页 <a href="admin!getDeOrder.do?pageindex=1&orderId=${param.orderId}">首页</a>
            <s:if test="%{deDaoOrder.pageindex>1}"> <a href="admin!getDeOrder.do?pageindex=${deDaoOrder.pageindex-1}&orderId=${param.orderId}">上一页 </a> </s:if>
            <s:if test="%{deDaoOrder.pagecount>deDaoOrder.pageindex}"> <a href="admin!getDeOrder.do?pageindex=${deDaoOrder.pageindex+1}&orderId=${param.orderId}">下一页</a> </s:if>
          <a href="admin!getDeOrder.do?pageindex=${deDaoOrder.pagecount}&orderId=${param.orderId}"> 尾页 </a>
        </td>
     </tr>
  </table>
  ${msg}
  </s:form>
  <br>
</m:frame>