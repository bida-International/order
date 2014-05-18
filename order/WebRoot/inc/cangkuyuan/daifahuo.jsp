<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %> 
<script type="text/javascript"  src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="./inc/js/bianhao.js"></script>
<m:frame>
<script>
 function mywancheng(){
	 	var items=document.getElementsByTagName("input");
		var lujing = "cangkuyuan!wanchengorder.do?pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
		bianhao(items,lujing);
	 }
	
	 function quan()
	 {
		 var h=document.getElementsByName("checkbox");
		 var da=document.getElementsByName("chkItems");
		quan1(h,da);
	  }
	  function seldaifaqu(){
	  	var guoneidanhao = document.getElementById("guoneidanhao").value;
	  	if(guoneidanhao == ''){
	  		alert("请输入国内单号");
	  		return false;
	  	}
	  	if(confirm("确定？")){
	  		window.location = "cangkuyuan!seldaifangqu.do?guoneidanhao="+guoneidanhao+"&pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
	  	}
	  
	  }
    function page(pageNumber) 
	{
		document.getElementById("pageNumber").value = pageNumber;
		document.getElementById("pager.offset").value = (pageNumber*10-10);
		document.forms.submit();
    }
</script>
	${msg}
	<form action="cangkuyuan!getDaiFaHuo.do" method="post" name="forms" id="forms">
	<table border="1" width="300px">
	   <tr>
		<td>待放区</td>
		<td><input type="button" value= "把查询出来的数据放入待放区" style="cursor: pointer;" onclick="return seldaifaqu()"/></td>
		<td><input type="button" style="cursor:pointer" value="待放区" style="cursor: pointer;" onClick="mywancheng()"></td>
		</tr>
    </table>
  <table border="1" width="1000px" cellspacing="0" style="float:left">
    <tr>
      <td colspan="12" align="center"><strong>待发货订单</strong></td>
    </tr>
    <tr>
    <td colspan="12">
    	国内运输单号：<input type="text" name="guoneidanhao" id="guoneidanhao" value="${param.guoneidanhao}"/>
    	订单查询:<input type="text" name="orderId" value="${param.orderId}"/>
    	物品查询:<input type="text" name="wuping" value="${param.wuping}"/>
        <input type="submit" value="查询"/>
         <s:hidden name="pageNumber" value="1"></s:hidden>
		<s:hidden name="pager.offset" id="pager.offset" value="0"></s:hidden>
		<input type="hidden" value="${tit}" name="tit"> 
    </td>
    </tr>
   <tr align="center">
     <td width="34" height="25">
		<input name="checkbox" onclick="quan()" type="checkbox" style="cursor:pointer"/></td>
     <td width="80"><span class="STYLE2">帐号</span></td>
      <td width="90"><span class="STYLE2">订单号</span></td>
      <td width="120"><span class="STYLE2">哪里采购的</span></td>
      <td width="120"><span class="STYLE2">国内运输单号</span></td>
       <td width="120"><span class="STYLE2">国际快递方式</span></td>
       <td width="120"><span class="STYLE2">国内快递方式</span></td>
       <td width="90"><span class="STYLE2">采购时间</span></td>
       <td width="100"><span class="STYLE2">供应商</span></td>
      <td width="100"><span class="STYLE2">物品</span></td>
      <td width="100"><span class="STYLE2">备注</span></td>
      <td width="163"><span class="STYLE2">操作</span></td>
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
        <td>${i.guoneidanhao}</td>
         <td><s:property value="getKuaiDiFangShi(#i.kuaidifangshiId)"/></td>
          <td><s:property value="getGuoNeiKuaiDiFangShi(#i.guoneikuaidiId)"/></td>
        <td>${i.caigoutime}</td>
         <td><textarea rows="5" cols="13">${i.gongyunshang}</textarea></td>
	    <td><textarea cols="13" rows="5">${i.wuping}</textarea></td>
	    <td><textarea cols="10" rows="5">${i.remark}</textarea></td>
	     <td><a href="cangkuyuan!fanhui.do?ordertable.id=${i.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}" onclick="return confirm('确定?')">返回给采购员</a>
	   	 <a href="cangkuyuan!upguoneiyunshu.do?ordertable.id=${i.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}">修改订单</a></td>	      
     </tr>    
   </s:iterator>
     <tr> <td colspan="12" align="center">
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
  </form>
  <s:iterator value="#stra" var="ii">
  ${ii}
  </s:iterator>
</m:frame>