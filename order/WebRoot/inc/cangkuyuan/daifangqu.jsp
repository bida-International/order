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
	   function daifahuo(){
	 	var items=document.getElementsByTagName("input"); 
	    var lujing = "cangkuyuan!fanhuidaifahuo.do?pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
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
	 	var i =1;
		 var lujing = "cangkuyuan!updateAllDaoChu.do?dczt="+i+"&pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
	    bianhao(items,lujing);
	 }
	  function daochuyewu(){
	 	var items=document.getElementsByTagName("input"); 
		var lujing = "cangkuyuan!daochuyewu.do?pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
		bianhao(items,lujing);
	 }

	function page(pageNumber) 
	{
		document.getElementById("pageNumber").value = pageNumber;
		document.getElementById("pager.offset").value = (pageNumber*10-10);
		document.forms.submit();
    }
</script>
	${msg}
	<form action="cangkuyuan!daifangqu.do" method="post" onsubmit="return as()" name="forms" id="forms">
	<table border="1" width="800px">
		<tr>
			<td align="right"><SPAN style="color:red">导出之后的操作</SPAN></td>
			<td>
			<input type="button" onclick="updaifahuo()" style="cursor: pointer;" value="批量修改已经导出的状态"/>
			</td>
			<td>
        	<input type="button" onclick="daochuyewu()" style="cursor: pointer;" value="选择导出订单传给财务"/>
        	</td>
        	<td>
        	<a href="cangkuyuan!daochuall.do" onclick="return confirm('确定?')">把全部导出订单传给财务</a>
        	</td>
        </tr>
        	<tr>
        	<td align="right"><span  style="color:red">待发货</span></td>
        	<td colspan="3"><input type="button" style="cursor:pointer" value="待发货" onClick="daifahuo()"></td>
        	</tr>
        	<tr>
        	<td align="right"><SPAN style="color:red">导出</SPAN></td>
        	<td>
        	<input type="button" style="cursor:pointer"  value="选择导出订单" onClick="xuanzedaochu()">
        	</td>
        	<td colspan="3">
        	<a href="ceshi.do?dc=1"  onclick="return confirm('确定要导出?')">导出全部订单</a>
        	</td>
        	</tr>
	</table>
  <table border="1" cellspacing="0" width="1300px" style="float:left">
    <tr>
      <td colspan="14" align="center"><strong>待放区订单</strong> </td>
    </tr>
    <tr>
    	<td colspan="14">
    		订单查询:<input type="text" name="orderId" value="${param.orderId}"/>
    		国内单号搜索：<input type="text" name="guoneidanhao" value="${param.guoneidanhao}"/>
    		采购时间：<input type="text" name="time" id="time" onfocus="WdatePicker()" value="${param.time}" size="9"/>至<input type="text" name="time1" id="time1"  onfocus="WdatePicker()" value="${param.time1}" size="9"/>
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
      <td width="130"><span class="STYLE2">哪里采购的</span></td>
      <td width="163"><span class="STYLE2">国内运输单号</span></td>
       <td width="163"><span class="STYLE2">国际快递方式</span></td>
       <td width="163"><span class="STYLE2">国内快递方式</span></td>
       <td width="120"><span class="STYLE2">采购时间</span></td>
       <td width="160"><span class="STYLE2">供应商</span></td>
      <td width="100"><span class="STYLE2">物品</span></td>
      <td width="100"><span class="STYLE2">是否被导出</span></td>
      <td width="100"><span class="STYLE2">导出时间</span></td>
      <td width="100"><span class="STYLE2">备注</span></td>
       <td width="100"><span class="STYLE2">操作</span></td>
    </tr>
    <s:iterator value="pageBean.list" var="i">
      <tr align="center">
      <td><input name="chkItems" value="${i.id}" onclick="chkItems_click(this)" type="checkbox"  id="chk_${i.id}" readonly="readonly"  style="cursor:pointer"/></td>
        <s:if test="#i.zhanghaoId==15">
       	<td>${i.name}</td>
       </s:if>
       <s:if test="#i.zhanghaoId!=15">
       <td><s:property value="getZhangHaoId(#i.zhanghaoId)"/></td>
       </s:if>
       <td>${i.orderId}</td>
        <td><s:property value="getGuoNeiWangZhan(#i.guoneiwangzhanId)"/></td>
        <td>${i.guoneidanhao}</td>
         <td><s:property value="getKuaiDiFangShi(#i.kuaidifangshiId)"/></td>
          <td><s:property value="getGuoNeiKuaiDiFangShi(#i.guoneikuaidiId)"/></td>
        <td>${i.caigoutime}</td>
         <td>${i.gongyunshang}</td>
	    <td><textarea rows="5" cols="10">${i.wuping}</textarea></td>    
	    <td>${i.daochu == 0 || i.daochu == null?('否'):('是')} </td>
	    <td>${i.dcsj}</td>
	    <td><textarea rows="5" cols="10">${i.remark}</textarea></td>
	    <td><a href="cangkuyuan!xiugaidaochu.do?ordertable.id=${i.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}">修改导出情况</a></td>
     </tr>    
   </s:iterator>
    <tr> <td colspan="14" align="center">
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