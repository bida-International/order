<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript" src="./inc/js/bianhao.js"></script>
<script type="text/javascript">
<!--

//-->
	 function quan()
	 {
		 var h=document.getElementsByName("checkbox");
		 var da=document.getElementsByName("chkItems");
		 quan1(h,da);
	  }
       function singleaudit(){
		var items=document.getElementsByTagName("input"); 
	     var lujing = "caigouadmin!getPayment.do?pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
	     bianhao(items,lujing);
     
     }
      function page(pageNumber) 
	 {
		document.getElementById("pageNumber").value = pageNumber;
		document.getElementById("pager.offset").value = (pageNumber*10-10);
		document.forms.submit();
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
			  location.href="caigouadmin!fhdaifahuo.do?bulletinId="+bulletinId+"&pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
			}else{
				return false;
			}
		}
	 }
</script>
<m:frame>
  ${msg}
<form  action="caigouadmin!getSingleAudit.do" method="post" onsubmit="return as()" id="forms" name="forms">
 <table border="1"  class="datagrid2" id="list" width="180px">
		<tr>
			<td> <input type="button" style="cursor:pointer" onclick="singleaudit()" value="传给财务付款"/></td>
			<td> <input type="button" style="cursor:pointer" onclick="fanhuidaifahuo()" value="返回给采购"/></td>
		</tr>	
	</table>
	
  <table border="1"  width="1180" cellspacing="0" style="float:left" >
    <tr>
      <td colspan="16" align="center"><strong>审核订单</strong></td>
    </tr>
    <tr>
    	<td colspan="16" align="center">
		  	订单号: <input type="text" name="orderId" id="orderId" value="${param.orderId}"/>
	        <s:hidden name="pageNumber" value="1"></s:hidden>
			<s:hidden name="pager.offset" id="pager.offset" value="0"></s:hidden>
			<input type="hidden" value="${tit}" name="tit">  
		  	<input type="submit" value="查询"/>
	  	</td>
    </tr>
    <tr align="center">
    <td width="34" height="25">
		<input name="checkbox" onclick="quan()" type="checkbox" style="cursor:pointer"/></td>
     <td width="54"><span class="STYLE2">账号</span></td>
       <td width="90"><span class="STYLE2">订单号</span></td>
        <td width="90"><span class="STYLE2">编码</span></td>
           <td width="90"><span class="STYLE2">国家</span></td>
     <td width="163"><span class="STYLE2">类目</span></td>
      <td width="130"><span class="STYLE2">采购金额</span></td>
      <td width="130"><span class="STYLE2">订单金额</span></td>
       <td width="100"><span class="STYLE2">供运商</span></td>
        <td width="100"><span class="STYLE2">备注</span></td>
        <td width="100"><span class="STYLE2">物品</span></td>
        <td width="100"><span class="STYLE2">完成情况</span></td>
      <td width="163"><span class="STYLE2">采购员</span></td>
        <td width="80"><span class="STYLE2">运输单号</span></td>
       <td width="100"><span class="STYLE2">上传时间</span></td>
    </tr>
    <s:iterator value="pageBean.list" var="sd">
	     <tr align="center" style="font-size:30px">
	     <td><input name="chkItems" value="${sd.id}" onclick="chkItems_click(this)" type="checkbox"  id="chk_${sd.id}" readonly="readonly"  style="cursor:pointer"/></td>
	     <td>	
	     	<s:property value="getZhangHaoId(#sd.zhanghaoId)"/> 	  
	     </td>
	     <td>
	     	<font color="blue">${sd.orderId}</font>
	      </td>
	          <td>
	          		${sd.bianma}
	          </td>
	          <td><s:property value="#sd.country"/></td>
	          <td>
	         	<s:if test="#sd.leimuid == null">
	         		${sd.yunshu}
	         	</s:if>
	         <s:if test="#sd.leimuid != null">
	         	<s:property value="getSelLeiMu(#sd.leimuid)"/>
	         </s:if>
	         </td>
	   		 <td>${sd.huokuan}</td>	  
	   		 <td>${sd.money}</td>
	   		<td><textarea rows="5" cols="10">${sd.gongyunshang}</textarea></td>     
	   		 <td><textarea rows="5" cols="10">${sd.remark}</textarea></td> 
	   		  <td><textarea rows="5">${sd.wuping}</textarea></td>
	   		 <td>${(sd.wancheng==0 || sd.wancheng==null)?('未完成'):('已完成')}</td>
		     <td>
		     	<s:property value="getUserId(#sd.caigouyuan)"/>
		     </td>	
		     <td><textarea rows="5" cols="10">${sd.danhao}</textarea></td>
		     <td>${sd.time}</td>	    
	     </tr>   
     </s:iterator> 
    <tr>     
         <td colspan="16" align="center">
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
  ${fanhui}
  <s:iterator value="#str" var="d">
  ${d}<br/>
  </s:iterator>
  <s:iterator value="#strsd" var="i">
  ${i}<br/>
  </s:iterator>
  <br>
</m:frame>