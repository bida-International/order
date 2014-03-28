 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.net.URLEncoder"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %> 
<script type="text/javascript" src="./inc/js/bianhao.js"></script>
<script type="text/javascript"  src="My97DatePicker/WdatePicker.js"></script>


<script>
 function myguonei(){
	 	var items=document.getElementsByTagName("input"); 
	 	var guoneiyunshu=document.getElementsByName("guoneiyunshu");
	 	var userid = document.getElementsByTagName("select");
	 	var flag=0;
	 	if(items!=null){
			for(var i=0;i<items.length;i++){
				if("chkItems"==items[i].name&&items[i].checked){
					flag=1;					
				}
			}
		}
		if(flag<1){
			alert("请选择要的订单！");
			return false;
		}else{
			var bool=confirm("确认吗?");
			if(bool){
			  var bulletinId="";
			  var sel="";
			  var user="";
			  for(var i=0;i<items.length;i++){
				if("chkItems"==items[i].name&&items[i].checked){
				  bulletinId+=items[i].value+"-";
				}
			  }
			    for(var i=0;i<guoneiyunshu.length;i++){
			    if(guoneiyunshu[i].value != ''){
				  sel+=guoneiyunshu[i].value+"-";
				  }		
			  }
			  for(var i=0;i<userid.length;i++){
			    if(userid[i].value!=0){
				  user+=userid[i].value+"-";
				  }		
			  }
			  location.href="cangkuyuan!upweikong.do?bulletinId="+bulletinId+"&guoneiyunshu="+sel+"&userid="+user+"&pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
			}else{
				return false;
			}
		}
	 }
	
	 function quan()
	 {
		 var h=document.getElementsByName("checkbox");
		 var da=document.getElementsByName("chkItems");
		quan1(h,da);
	  }
	  function mywancheng(){
	 	var items=document.getElementsByTagName("input"); 
		var lujing = "cangkuyuan!zhijiefangru.do?pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
		bianhao(items,lujing);
	 }
	function page(pageNumber) 
	{
		document.getElementById("pageNumber").value = pageNumber;
		document.getElementById("pager.offset").value = (pageNumber*10-10);
		document.forms.submit();
    }
     //下拉框值不为空时复选框自动选中
 function checkedSel(){
	var ss = document.getElementsByTagName("select");
	var chk = document.getElementsByName("chkItems");
	for(var i=0;i<ss.length;i++){
		if(ss[i].value != 0){
			chk[i].checked=true;
		}else{
			chk[i].checked=false;
		}
	}
 }
</script>

<m:frame>
	<s:form action="cangkuyuan!getImportOrders.do" enctype="multipart/form-data" method="post">
		<table>
			<tr>
				<td><a href="ceshi.do?dc=2" onclick="return confirm('确定要导出?')">导出</a></td>
				<td colspan="1">
				  	<s:file  name="excelfile" accept=".xls"></s:file>
					<s:submit value="导入"></s:submit>
			  	</td>
			</tr>
		</table>
	</s:form>
	<form action="cangkuyuan!getWeiKong.do" name="forms" id="forms" method="post" onsubmit="return as()">
	<table border="1">
		<tr>
			<td><input type="button" onclick="myguonei()" style="cursor:pointer" value="批量修改国内单号"/></td>
			 <td><input type="button" onclick="mywancheng()" style="cursor: pointer;" value="待放区"/></td>
		</tr>
	</table>
  <table border="1" width="1150px" cellspacing="0" style="float:left">
    <tr>
      <td colspan="12" align="center"><strong>单号为空订单</strong></td>
    </tr>
    <tr>
    <td colspan="12">
    	国内运输单号：<input type="text" name="guoneidanhao" id="guoneidanhao" value="${param.guoneidanhao}"/>
    	订单查询:<input type="text" name="orderId" id="orderId" value="${param.orderId}"/>
    	物品查询:<input type="text" name="wuping" id="wuping" value="${param.wuping}"/>
      	<s:hidden name="pageNumber" value="1"></s:hidden>
		<s:hidden name="pager.offset" id="pager.offset" value="0"></s:hidden>
		<input type="hidden" value="${tit}" name="tit"> 
        <input type="submit" value="查询"/>
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
        <td width="120"><span class="STYLE2">国内快递方式</span></td>
       <td width="120"><span class="STYLE2">采购时间</span></td>
       <td width="160"><span class="STYLE2">供应商</span></td>
      <td width="163"><span class="STYLE2">物品</span></td>
       <td width="100"><span class="STYLE2">备注</span></td>
      <td width="128"><span class="STYLE2">操作</span></td>
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
          <td>
			<select name="guoneikuaidi" id="guoneikuaidi" onchange="checkedSel()">
				<option value="0">-请选择-</option>
				<s:iterator value="guoNeiKuaiDiAll" var="j">
					<option value="${j.id}">${j.name}</option>
				</s:iterator>
			</select>
			</td>
          <td>${i.caigoutime}</td>
         <td>${i.gongyunshang}</td>
	    <td>${i.wuping}</td>
	    <td><textarea name="remark" cols="10" rows="5">${i.remark}</textarea></td>
	     <td><a href="cangkuyuan!guoneifanhui.do?ordertable.id=${i.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}" onclick="return confirm('确定?')">返回给采购员</a>
	   	<a href="cangkuyuan!upyundanhao.do?ordertable.id=${i.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}">修改订单</a> </td>	      
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
          </td>
          </tr>
  </table>
  <br>
   <s:iterator value="#str" var="d">
  	${d}<br>
  </s:iterator>
  </form>
</m:frame>