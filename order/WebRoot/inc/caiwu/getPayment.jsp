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

	 //全选
	 function quan()
	 {
		 var h=document.getElementsByName("checkbox");
		 var da=document.getElementsByName("chkItems");
		 quan1(h,da);
	  }
	function daifahuo(){
	
		var items=document.getElementsByTagName("input"); 
	     var lujing = "caiwu!updedaos.do?pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
	     bianhao(items,lujing);
	}	
	   function fanhuidaifahuo(){
	 	var items=document.getElementsByTagName("input"); 
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
			  for(var i=0;i<items.length;i++){
				if("chkItems"==items[i].name&&items[i].checked){
				  bulletinId+=items[i].value+"-";
				}
			  }
			  location.href="caiwu!fhdaifahuo.do?bulletinId="+bulletinId+"&pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
			}else{
				return false;
			}
		}
	 }
	function daifa(){
		var items=document.getElementsByTagName("input"); 
	     var lujing = "caiwu!daifa.do?pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
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
	<form action="caiwu!getPayMent.do" name="forms" id="forms" method="post" onsubmit="return as()">
	<table>
		<tr>
			<td>  
				<input type="button" style="cursor:pointer" value="待发货" onClick="daifahuo()">
			</td>
			
			<td>  
				 <input type="button" style="cursor:pointer" value="代发订单" onClick="daifa()">
				 <input type="button" style="cursor:pointer" value="返回给采购" onClick="fanhuidaifahuo()">
			</td>
		</tr>
	</table>
  <table border="1" width="1000px" cellspacing="0" style="float:left">
    <tr>
      <td colspan="8" align="center"><strong>付款订单</strong></td>
       <td>
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
      <td width="100"><span class="STYLE2">产品编码</span></td>
       <td width="100"><span class="STYLE2">金额</span></td>
       <td width="100"><span class="STYLE2">货款</span></td>
       <td width="100"><span class="STYLE2">备注</span></td>
      <td width="100"><span class="STYLE2">供应商</span></td>
      
      <td width="100"><span class="STYLE2">采购员</span></td>
    </tr>
    <s:iterator value="pageBean.list" var="i">
      <tr align="center">
      <td><input name="chkItems" value="${i.id}" onclick="chkItems_click(this)" type="checkbox"  id="chk_${i.id}" readonly="readonly"  style="cursor:pointer"/></td>
       	<td><s:property value="getZhangHaoId(#i.zhanghaoId)"/></td>
       <td>${i.orderId}</td>
        <td>${i.bianma}</td>
        <td>${i.money}</td>
         <td><fmt:formatNumber value="${i.huokuan}" pattern="0.00"></fmt:formatNumber></td>
       <td><textarea rows="5" cols="10">${i.remark}</textarea> </td>
	    <td><textarea cols="10" rows="5">${i.gongyunshang}</textarea></td>
	   
	     <td><s:property value="#i.caigouyuan==0?('未分配'):(getUserId(#i.caigouyuan))"/></td>
     </tr>    
   </s:iterator>
     <tr>     
         <td colspan="19" align="center">
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
  <s:iterator value="#strss" var="ll">
  ${ll}
  </s:iterator>
</m:frame>