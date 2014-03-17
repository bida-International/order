<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript" src="./inc/js/bianhao.js"></script>
<script type="text/javascript"  src="My97DatePicker/WdatePicker.js"></script>

<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %> 

<m:frame>
<script>
 function mywancheng(){
	 	var items=document.getElementsByTagName("input"); 
	 	 var lujing = "cangkuyuan!daifangquwanchengorder.do?pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
	 	bianhao(items,lujing);
	 }

	 function quan()
	 {
		 var h=document.getElementsByName("checkbox");
		 var da=document.getElementsByName("chkItems");
		 quan1(h,da);
	  }
	  function xuanzedaochu(){
	 	var items=document.getElementsByTagName("input");
		var lujing = "cangkuyuan.do?pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
		bianhao(items,lujing);
	 }
	 function updaifahuo(){
	 	var items=document.getElementsByName("chkItems"); 
	 	var i =0;
		 var lujing = "cangkuyuan!updateAllDaoChu.do?dczt="+i+"&pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
	    bianhao(items,lujing);
	 }
	function page(pageNumber) 
	{
		document.getElementById("pageNumber").value = pageNumber;
		document.getElementById("pager.offset").value = (pageNumber*10-10);
		document.forms.submit();
    }
  
	   //导入已经完成
 	function yjwc(){
	 	var items=document.getElementsByName("chkItems"); 
	 	var lujing = "cangkuyuan!upWcOrder.do?pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
	 	 bianhao(items,lujing);
	 }	
</script>
	${msg}
<s:form action="cangkuyuan!ImportOrders.do" enctype="multipart/form-data" method="post" onsubmit="return as()">
	  <table>
	  <tr>
	  	<td align="right"><SPAN style="color:red">导入</SPAN></td>
	  	<td colspan="1">
	  	<s:file  name="excelfile" accept=".xls"></s:file>
		<s:submit value="导入"></s:submit>
	  	</td>
	  </tr>
	  </table>
  </s:form>
	<form action="cangkuyuan!getReturnofgoods.do" method="post" onsubmit="return as()" name="forms" id="forms">
	<table border="1" width="800px">
        	<tr>
        	<td align="right"><SPAN style="color:red">导出</SPAN></td>
        	<td>
        	<input type="button" style="cursor:pointer"  value="选择导出订单" onClick="xuanzedaochu()">
        	<a href="ceshi.do?dc=0"  onclick="return confirm('确定要导出?')">导出全部订单</a>
        	<input type="button" onclick="updaifahuo()" style="cursor: pointer;" value="批量修改已经导出的状态"/>
        	</td>
        	</tr>
        	<tr><td><input type="button" onclick="yjwc()" style="cursor: pointer;" value="已经完成"/></td></tr>
	</table>
	
  <table border="1" cellspacing="0" width="1100px" style="float:left">
    <tr>
      <td colspan="10" align="center"><strong>退回订单</strong> </td>
    </tr>
    <tr>
    	<td colspan="10">
			国际运输单号： <input type="text" name="danhao" id="danhao" value="${param.danhao}"/>
        	<input type="submit" value="查询"/> 
        	
        	 <s:hidden name="pageNumber" value="1"></s:hidden>
			<s:hidden name="pager.offset" id="pager.offset" value="0"></s:hidden>
			<input type="hidden" value="${tit}" name="tit"> 
        </td>
   </tr>
    <tr align="center">
     <td width="34" height="25">
		<input name="checkbox" onclick="quan()" type="checkbox" style="cursor:pointer"/></td>
     <td width="100"><span class="STYLE2">帐号</span></td>
      <td width="90"><span class="STYLE2">订单号</span></td>
       <td width="80"><span class="STYLE2">国际快递单号</span></td>
       <td width="120"><span class="STYLE2">采购时间</span></td>
       <td width="160"><span class="STYLE2">供应商</span></td>
      <td width="100"><span class="STYLE2">物品</span></td>
      <td width="80"><span class="STYLE2">备注</span></td>
      <td width="100"><span class="STYLE2">导出状态</span></td>
    </tr>
    <s:iterator value="pageBean.list" var="i">
      <tr align="center">
      <td><input name="chkItems" value="${i.id}" onclick="chkItems_click(this)" type="checkbox"  id="chk_${i.id}" readonly="readonly"  style="cursor:pointer"/></td>
       <td><s:property value="getZhangHaoId(#i.zhanghaoId)"/></td>
       <td>${i.orderId}</td>
    	<td><textarea rows="5" cols="10" name="danhaos" id="danhaos">${i.danhao}</textarea></td>
        <td>${i.caigoutime}</td>
         <td>${i.gongyunshang}</td>
	    <td><textarea rows="5" cols="10">${i.wuping}</textarea></td>    
	    <td><textarea rows="5" cols="10">${i.remark}</textarea></td>
	    <td>${(i.daochu==0 || i.daochu == null)?('否'):('是')}</td>
     </tr>    
   </s:iterator>
    <tr> <td colspan="10" align="center">
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
	</td></tr>
  </table>
  <br>
  <s:iterator value="#strsd" var="i">
  ${i}
  </s:iterator>
  </form>

</m:frame>