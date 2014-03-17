<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %>
<script type="text/javascript" src="./inc/js/bianhao.js"></script>
<script type="text/javascript"  src="My97DatePicker/WdatePicker.js"></script>

<m:frame>
<script>
//导入已经完成
 	function mywancheng(){
	 	var items=document.getElementsByTagName("input"); 
	 	var lujing = "caiwu!czdaifangquwanchengorder.do?pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
	    bianhao(items,lujing);
	 }
	 //返回待发货
	   function daifahuo(){
	 	var items=document.getElementsByTagName("input"); 
		var lujing ="caiwu!czfanhuidaifahuo.do?pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
		bianhao(items,lujing);
	 }
	 //全选
	 function quan()
	 {
		 var h=document.getElementsByName("checkbox");
		 var da=document.getElementsByName("chkItems");
		 quan1(h,da);
	  }
	  //返回给仓库待放区
	   function cangkudaifangqu(){
	 	var items=document.getElementsByTagName("input"); 
	    var lujing ="caiwu!czcangkudaifangqu.do?pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
		bianhao(items,lujing);	
	 }
	 //返回给顾客已经发货
	  function gukeyifahuo(){
	 	var items=document.getElementsByTagName("input"); 
	    var lujing ="caiwu!czgukeyifahuo.do?pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
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
	<form action="caiwu!getChaoZhong.do" method="post" name="forms" id="forms" onsubmit="return as()">
	<table>
		<tr>
			<td>
				<input type="button" style="cursor:pointer" value="已经完成" onClick="mywancheng()"> 
				<input type="button" style="cursor:pointer" value="待发货" onClick="daifahuo()">
				<input type="button" style="cursor:pointer" value="返回给仓库待放区" onClick="cangkudaifangqu()">
        		<input type="button" style="cursor:pointer" value="返回给顾客已经发货" onClick="gukeyifahuo()">
			</td>
		</tr>
	</table>
  <table border="1" cellspacing="0" style="float:left">
    <tr>
      <td colspan="13" align="center"><strong>超重订单</strong></td>
    </tr>
    <tr>
    	<td colspan="13">
    		订单查询:<input type="text" name="orderId" value="${param.orderId}"/>
    		国内单号搜索：<input type="text" name="guoneidanhao" value="${param.guoneidanhao}"/>
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
      <td width="100"><span class="STYLE2">哪里采购的</span></td>
      <td width="100"><span class="STYLE2">国内运输单号</span></td>
       <td width="100"><span class="STYLE2">国际运输单号</span></td>
       <td width="100"><span class="STYLE2">国际快递方式</span></td>
       <td width="100"><span class="STYLE2">国内快递方式</span></td>
       <td width="80"><span class="STYLE2">采购时间</span></td>
       <td width="80"><span class="STYLE2">是否发完</span></td>
      <td width="100"><span class="STYLE2">物品</span></td>
      <td width="100"><span class="STYLE2">备注</span></td>
      <td width="100"><span class="STYLE2">操作</span></td>
    </tr>
    <s:iterator value="pageBean.list" var="i">
      <tr align="center">
      <td><input name="chkItems" value="${i.id}" onclick="chkItems_click(this)" type="checkbox"  id="chk_${i.id}" readonly="readonly"  style="cursor:pointer"/></td>
         <s:if test="#i.zhanghaoId==15">
       	<td>${i.name}</td>
       </s:if>
       <s:if test="#i.zhanghaoId != 15">
       	<td><s:property value="getZhangHaoId(#i.zhanghaoId)"/></td>
       </s:if>
       <td>${i.orderId}</td>
        <td><textarea cols="10" rows="5"><s:property value="getGuoNeiWangZhan(#i.guoneiwangzhanId)"/></textarea></td>
        <td><textarea rows="5" cols="10">${i.guoneidanhao}</textarea></td>
        <td><textarea rows="5" cols="10">${i.danhao}</textarea></td>
         <td><s:property value="getKuaiDiFangShi(#i.kuaidifangshiId)"/></td>
          <td><s:property value="getGuoNeiKuaiDiFangShi(#i.guoneikuaidiId)"/></td>
        <td>${i.caigoutime}</td>
         <td>${(i.fawan==0 || i.fawan == null)?('否'):('是')}</td>
	    <td><textarea cols="10" rows="5">${i.wuping}</textarea></td>    
	    <td><textarea cols="10" rows="5">${i.remark}</textarea></td>
	    <td><a href="caiwu!czupdatedairudan.do?ordertable.id=${i.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}">修改订单</a>
	    /<a href="caiwu!czupdatedanhao.do?ordertable.id=${i.id}&ordertable.dengluId=${i.dengluId}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}">修改单号</a>
	    </td>
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
  <br>
  </form>
  <s:iterator value="#str" var="i">
  ${i}
  </s:iterator>
  <s:iterator value="#stred" var="j">
  ${j}
  </s:iterator>
</m:frame>