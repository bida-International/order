<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript" src="./inc/js/bianhao.js"></script>
<script type="text/javascript">
	function updaifahuo(){
	 	var items=document.getElementsByName("chkItems"); 
		 var lujing = "caigou!updateAllChuLi.do?pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
	    bianhao(items,lujing);
	 }
	// function tiaozhuan(){
		//var pageNumber = document.getElementById("pageNumber").value;
		//location.href = "caigou!getTuiHuo.do?pager.offset="+(pageNumber*10-10)+"&pageNumber="+pageNumber;
	//}
	function page(pageNumber) 
	{
		document.getElementById("pageNumber").value = pageNumber;
		document.getElementById("pager.offset").value = (pageNumber*10-10);
		document.forms.submit();
    }
</script>
<m:frame>
<form  action="caigou!getTuiHuo.do" name="forms" id="forms" method="post">
  <table width="1200px"  class="datagrid2" id="list" border="1" cellspacing="0" style="float:left" >
    <tr>
      <td colspan="13" align="center"><strong>退货订单</strong></td>
    </tr>
    <tr>
    	<td colspan="13" align="center">
	  	订单号 <input type="text" name="orderId" id="orderId" value="${param.orderId}"/>
	  	 国际运输单号：<input type="text" name="danhao" id="danhao" value="${param.danhao}"/>
	  	是否处理：
    	<select name="chuli" id="chuli">
	  		<option value="">-请选择-</option>
	  		<option value="0"
	  		${chuli==0?('selected="selected"'):('')}
	  		>否</option>
	  		<option value="1"
	  		${chuli==1?('selected="selected"'):('')}
	  		>是</option>
	  	</select>
	  	<input type="submit" value="查询"/>
	  		<input type="button" value="修改是否处理 " onclick="updaifahuo()"/>
	  	<s:hidden name="pageNumber" value="1"></s:hidden>
		<s:hidden name="pager.offset" id="pager.offset" value="0"></s:hidden>
		<input type="hidden" value="${tit}" name="tit"> 
	  	</td>
    </tr>
    <tr align="center">
    <td width="34" height="25">
		<input name="checkbox" onclick="quan()" type="checkbox" style="cursor:pointer"/></td>
      <td width="70"><span class="STYLE2">账号</span></td>
       <td width="50"><span class="STYLE2">订单号</span></td>
      <td width="80"><span class="STYLE2">国内运输方式</span></td>
      <td width="100"><span class="STYLE2">国内运输单号</span></td>
     <td width="80"><span class="STYLE2">物品</span></td>
      <td width="100"><span class="STYLE2">地址</span></td>
       <td width="100"><span class="STYLE2">备注</span></td>
        <td width="100"><span class="STYLE2">上传时间</span></td>
        <td width="100"><span class="STYLE2">是否退货</span></td>
        <td width="80"><span class="STYLE2">是否处理</span></td>
      <td width="80"><span class="STYLE2">国际运输方式</span></td>
      <td width="80"><span class="STYLE2">国际运输单号</span></td>
    </tr>
    <s:iterator value="pageBean.list" var="sd">
	     <tr align="center" style="font-size:30px">
	     <td><input name="chkItems" value="${sd.id}" onclick="chkItems_click(this)" type="checkbox"  id="chk_${sd.id}" readonly="readonly"  style="cursor:pointer"/></td>
	     <td>
	     	<s:property value="getZhangHaoId(#sd.zhanghaoId)"/>
	     </td>
	          <td>${sd.orderId}</td>
	         <td><s:property value="getGuoNeiKuaiDiFangShi(#sd.guoneikuaidiId)"/></td>
	   		 <td>${sd.guoneidanhao}</td>	  
	   		 <td><textarea cols="10" rows="5">${sd.wuping}</textarea></td>
	   		<td><textarea cols="10" rows="5">${sd.guowaidizhi}</textarea></td>     
	   		 <td><textarea cols="10" rows="5">${sd.remark}</textarea></td> 
	   		 <td>${sd.time}</td>
	   		 <td>${(sd.tuihuo==0 || sd.tuihuo==null)?('否'):('是')}</td>
	   		 <td>${(sd.chuli==0 || sd.chuli==null)?('否'):('是')}</td>
		    <td><s:property value="getKuaiDiFangShi(#sd.kuaidifangshiId)"/></td>
		     <td><textarea rows="5" cols="10">${sd.danhao}</textarea></td>		    
	     </tr>   
     </s:iterator> 
    <tr>     
	    <td colspan="13" align="center">
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
  ${msg}
  </form>
</m:frame>