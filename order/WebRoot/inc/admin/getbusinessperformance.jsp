<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
 <%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<script type="text/javascript"  src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="./inc/js/bianhao.js"></script>
<script type="text/javascript" src="jquery-1.8.2.js"></script>
<m:frame>
	<script type="text/javascript">
	  //全选
	 function quan()
	 {
		 var h=document.getElementsByName("checkbox");
		 var da=document.getElementsByName("chkItems");
		 quan1(h,da);
	  }
		 function page(pageNumber) 
		 {
			document.getElementById("pageNumber").value = pageNumber;
			document.getElementById("pager.offset").value = (pageNumber*10-10);
			document.forms.submit();
		 }
	</script>	
	 <form action="admin!getBusinessCompletion.do"  name="forms" id="forms" method="post" onsubmit="return as()">
	 
  <table border="1"  cellspacing="0" cellpadding="0" align="center" width="1500px" cellspacing="0">
    <tr>
      <td colspan="18" align="center"><strong>业务订单</strong>
    
         <s:hidden name="pageNumber" value="1"></s:hidden>
		<s:hidden name="pager.offset" id="pager.offset" value="0"></s:hidden>
		<input type="hidden" value="${tit}" name="tit"> 
      </td>
    </tr>
    <tr>
    <td colspan="18">
    	订单号：<input type="text" name="orderId" id="orderId" value="${param.orderId}" size="9"/>
    	时间：<input type="text" name="time" id="time" onfocus="WdatePicker()" value="${param.time}" size="9"/>至<input type="text" name="time1" id="time1"  onfocus="WdatePicker()" value="${param.time1}" size="9"/>
    	业务:
    		<select name="yewuid" id="yewuid"> 
	      	<option value="">-请选择-</option>
	      	<s:iterator value="getAllYeWu()" id = "i">
	      	<option value="${i.userid}"
	      	${i.userid==yewuid?('selected="selected"'):('')}
	      	>${i.name}</option>
	      </s:iterator>
	      </select>  
    	<input type="submit" value="查询"/>
    </td>
    </tr>
    <tr align="center">
      <td width="34" height="25">
		<input name="checkbox" onclick="quan()" type="checkbox" style="cursor:pointer"/></td>
	<td width="80"><span class="STYLE2">图片</span></td>
     <td width="163"><span class="STYLE2">日期</span></td>
     <td width="100"><span class="STYLE2">订单号</span></td>
      <td width="100"><span class="STYLE2">编码</span></td>
      <td width="100"><span class="STYLE2">金额($)</span></td>
       <td width="100"><span class="STYLE2">类目</span></td>
       <td width="100"><span class="STYLE2">运输单号</span></td>
       <td width="63"><span class="STYLE2">运费</span></td>
      <td width="54"><span class="STYLE2">货款</span></td>
      <td width="100"><span class="STYLE2">供运商</span></td> 
      <td width="64"><span class="STYLE2"> 国家</span></td>
       <td width="80"><span class="STYLE2">纠纷</span></td>
       <td width="80"><span class="STYLE2">账号</span></td>
       <td width="80"><span class="STYLE2">备注</span></td>
      <td width="80"><span class="STYLE2">业务员</span></td>
    </tr>
    <s:iterator value="pageBean.list" var="i">
      <tr align="center">
      <td><input name="chkItems" value="${i.id}" onclick="chkItems_click(this)" type="checkbox"  id="chk_${i.id}" readonly="readonly"  style="cursor:pointer"/></td>
        <td><img width="80px" height="80px" src="http://qiantingdi.gicp.net:49644/order/WebRoot/${sd.uploadFile}"></td>
        <td>${i.time}</td>
        <td><a href="admin!reAllOrders.do?ordertable.id=${i.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}">${i.orderId}</a></td>
        <td>${i.bianma}</td>
	    <td>${i.money}</td>	 
        <td><textarea rows="5" cols="10"><s:if test="#i.leimuid == null">${i.yunshu}</s:if><s:if test="#i.leimuid != null"><s:property value="getSelLeiMu(#i.leimuid)"/></s:if></textarea></td>
	    <td><textarea rows="5" cols="10">${i.danhao}</textarea></td>
	    <td><fmt:formatNumber value="${i.yunfei}" pattern="0.00"></fmt:formatNumber></td>	  	 
	    <td>${i.huokuan}</td>   	 
	    <td><textarea rows="5" cols="10">${i.gongyunshang}</textarea></td>  
	
	    <td>${i.country}</td>	
	     <td>${(i.jiufen==0 || i.jiufen == null)?('否'):('是')}</td>
	     <td><s:property value="getZhangHaoId(#i.zhanghaoId)"/></td>
	     <td><textarea rows="5" cols="10">${i.remark}</textarea></td>
	   	 <td><s:property value="getUserId(#i.yewuid)"/></td>	  
     </tr>    
   </s:iterator>
 <tr>
   	<td colspan="19">
   		总金额：<s:property value="getTheTotalAmount(yewuid,orderId,time,time1)"></s:property>
   	</td>
   </tr> 

    <tr> 
	    <td colspan="18" align="center">
	        总记录数：${pageBean.allRow} 共几页：${pageBean.allPage} 当前第 ${pageBean.currentPage} 页
       <pg:pager url="" items="${pageBean.allRow}" export="currentPageNumber=pageNumber" maxPageItems="10"> 
 	 		 <a href="javascript:page(1)">首页</a>
            <s:if test="%{pageBean.currentPage>1}">
             <a href ="javascript:page(${pageBean.currentPage-1})">上一页</a> 
             </s:if>
	       <pg:pages> 
			   	<c:choose> 
			     	  <c:when test="${currentPageNumber eq pageNumber}"> 
			      		 <font color="red">${pageNumber}</font> 
			     	  </c:when> 
			      <c:otherwise> 
			       <a href="javascript:page(${pageNumber})" id="page">${pageNumber}</a> 
			     </c:otherwise> 
			   </c:choose> 
	 		</pg:pages> 
            <s:if test="%{pageBean.allPage>pageBean.currentPage}"> 
            <a href="javascript:page(${pageBean.currentPage+1})">下一页</a>		 			 
            </s:if>
          <a href="javascript:page(${pageBean.allPage})">尾页</a>
	</pg:pager> 
	    </td>   		
    </tr>
  </table>
  </form>
  <br> 
</m:frame>