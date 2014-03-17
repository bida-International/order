<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<m:frame>
  <table border="1"  class="datagrid2" id="list" width="300px" cellspacing="0" style="float:left">
    <tr>
      <td colspan="4" align="center"><strong>得到订单</strong></td>
    </tr>
    <tr align="center">
      <td width="163"><span class="STYLE2">订单号</span></td>
      <td width="54"><span class="STYLE2">上传时间</span></td>
      <td width="64"><span class="STYLE2">操作</span></td>
    </tr>
    <s:iterator value="adminFenPei.pagelist" var="sd" >
	     <tr align="center">
	         <td>${sd.orderId}</td>
	   		 <td>${sd.time}</td>	      
		     <td>
		     	<a href="caigou!upcaigoudedao.do?ordertable.id=${sd.id}" onclick="return confirm('是否返回给管理员?')">未完成</a>
		        /<a href="caigou!upcaigoudedaoAll.do?ordertable.id=${sd.id}" onclick="return confirm('是否完成此订单?')">完成</a>
		     </td>	
	     </tr>   
     </s:iterator> 
     <tr> 
     	<td colspan="4" align="center">
       		 共${adminFenPei.count}条数据 共${adminFenPei.pagecount}条 第${adminFenPei.pageindex}页 <a href="caigou!adminfenpeiorder.do?pageindex=1">首页</a>
            <s:if test="%{adminFenPei.pageindex>1}"> <a href="caigou!adminfenpeiorder.do?pageindex=${adminFenPei.pageindex-1}">上一页 </a> </s:if>
            <s:if test="%{adminFenPei.pagecount>adminFenPei.pageindex}"> <a href="caigou!adminfenpeiorder.do?pageindex=${adminFenPei.pageindex+1}">下一页</a> </s:if>
          <a href="caigou!adminfenpeiorder.do?pageindex=${adminFenPei.pagecount}"> 尾页 </a>
        </td>
     </tr>
  </table>
  <br>
</m:frame>