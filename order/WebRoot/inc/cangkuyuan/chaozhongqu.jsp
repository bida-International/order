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
	 function quan()
	 {
		 var h=document.getElementsByName("checkbox");
		 var da=document.getElementsByName("chkItems");
		quan1(h,da);
	  }
	  function daifahuo(){
	 	var items=document.getElementsByTagName("input"); 
		var lujing = "cangkuyuan!czdaifahuo.do?pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
		bianhao(items,lujing);
	 }
	 //待放区
	 function daifangqu(){
	 	var items=document.getElementsByName("chkItems"); 
		var lujing = "cangkuyuan!czdaifangqu.do?pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
		bianhao(items,lujing);
	 }
	 //是否发完
	 function myfawan(){
	 	var items=document.getElementsByName("chkItems"); 
	 	var userid = document.getElementsByName("fawan"); 
		var lujing = "cangkuyuan!myfawan.do?pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
		  myfenpei(items,userid,lujing);
	 }
	function page(pageNumber) 
	{
		document.getElementById("pageNumber").value = pageNumber;
		document.getElementById("pager.offset").value = (pageNumber*10-10);
		document.forms.submit();
    }
</script>
	${msg}
	<form action="cangkuyuan!getChaoZhongQu.do" method="post" id="forms" name="forms" onsubmit="return as()">
	<table>
		<tr>
			<td><input type="button" onclick="mychaozhong()" style="cursor:pointer" value="把超重订单传给财务"/></td>
			<td><input type="button" onclick="daifangqu()" style="cursor: pointer;" value="待放区"/></td>
			<td><input type="button" onclick="daifahuo()" style="cursor: pointer;" value="待发货"/></td>
			<td><input type="button" onclick="myfawan()" style="cursor: pointer;" value="是否发完"/></td>
		</tr>
	</table>
	<table border="1" width="1150px" cellspacing="0" style="float:left">
    <tr>
      <td colspan="10" align="center"><strong>超重区订单</strong></td>
    </tr>
    <tr>
    <td colspan="11">
    	国内运输单号：<input type="text" name="guoneidanhao" id="guoneidanhao" value="${param.guoneidanhao}"/>
    	订单查询:<input type="text" name="orderId" value="${param.orderId}"/>
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
      <td width="120"><span class="STYLE2">国内运输单号</span></td>
       <td width="120"><span class="STYLE2">国际快递方式</span></td>
       <td width="120"><span class="STYLE2">采购时间</span></td>
       <td width="160"><span class="STYLE2">供应商</span></td>
      <td width="163"><span class="STYLE2">物品</span></td>
       <td width="100"><span class="STYLE2">备注</span></td>
       <td width="100"><span class="STYLE2">是否发完</span></td>
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
        <td><s:property value="getGuoNeiWangZhan(#i.guoneiwangzhanId)"/></td>
         <td><textarea name="guoneiyunshu" id="guoneiyunshu" cols="10" rows="5">${i.guoneidanhao}</textarea></td>
          <td><s:property value="getKuaiDiFangShi(#i.kuaidifangshiId)"/></td>
          <td>${i.caigoutime}</td>
         <td>${i.gongyunshang}</td>
	    <td>${i.wuping}</td>
	    <td><textarea name="remark" cols="10" rows="5">${i.remark}</textarea></td>
	    <td>
	      <select name="fawan" >				
					<option value=0
						${(i.fawan == null || i.fawan == 0)?('selected="selected"'):('')} 
					>否</option>
					<option value=1
						${i.fawan==1?('selected="selected"'):('')}
					>是</option>
		 </select>
	   </td>
     </tr>    
   </s:iterator>
  <tr> 
	    <td colspan="11" align="center">
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
   <s:iterator value="#strsd" var="d">
  	${d}<br>
  </s:iterator>
  </form>
</m:frame>