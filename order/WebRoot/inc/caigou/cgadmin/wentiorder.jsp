<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript"  src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="./inc/js/bianhao.js"></script>
<script type="text/javascript">

	 function mytest(){
	 	var items=document.getElementsByTagName("input"); 
	 	var userid = document.getElementsByTagName("select");
	    var lujing = "caigouadmin!upfenpei.do?pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
	     fenpei(items,userid,lujing);
	 }
	 function quan()
	 {
		 var h=document.getElementsByName("checkbox");
		 var da=document.getElementsByName("chkItems");
		quan1(h,da);
	  }
	function mysuccessful(){
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
		  window.location ="caigouadmin!mysuccessful.do?bulletinId="+bulletinId+"&pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
		}else{
			return false;
		}
	}
	}
 	  function tiaozhuan(){
   		var pageNumber = document.getElementById("pageNumber").value;
   		location.href = "caigouadmin!getWenTiOrder.do?pager.offset="+(pageNumber*10-10)+"&pageNumber="+pageNumber;
     }
       function page(pageNumber) 
	   {
		document.getElementById("pageNumber").value = pageNumber;
		document.getElementById("pager.offset").value = (pageNumber*10-10);
		document.forms.submit();
       }
	</script>    
<m:frame>
<form action="caigouadmin!getWenTiOrder.do" name="forms" id="forms" method="post">
  <table border="1" cellspacing="0" style="float:left">
    <tr>
      <td colspan="11" align="center"><strong>全部问题订单</strong><input type="button" style="cursor:pointer" value="分配订单" onClick="mytest()">
       <input type="button" style="cursor:pointer" value="已经完成 " onClick="mysuccessful()">
      </td>
    </tr>
    <tr>
      <td colspan="10" align="right">
      	订单号：<input type="text" name="orderId" id="orderId" value="${param.orderId}"/>
      	时间查询：<input type="text" name="time" id="time" onfocus="WdatePicker()" value="${param.time}"/> 至<input type="text" name="time1" id="time1"  onfocus="WdatePicker()" value="${param.time1}"/>
      	</td>
      <td><input type="submit" value="查询"/>
      <s:hidden name="pageNumber" value="1"></s:hidden>
		<s:hidden name="pager.offset" id="pager.offset" value="0"></s:hidden>
		<input type="hidden" value="${tit}" name="tit">  
      </td>
    </tr>
    <tr align="center">
    <td width="34" height="25">
		<input name="checkbox" onclick="quan()" type="checkbox" style="cursor:pointer"/></td>
		 <td width="100"><span class="STYLE2">账号</span></td>
      <td width="100"><span class="STYLE2">订单号</span></td>
      <td width="100"><span class="STYLE2">编码</span></td>
      <td width="100"><span class="STYLE2">上传时间</span></td>
        <td width="130"><span class="STYLE2">类目</span></td>
        <td width="130"><span class="STYLE2">国家</span></td>
        <td width="130"><span class="STYLE2">金额</span></td>
         <td width="163"><span class="STYLE2">备注</span></td>
         <td width="130"><span class="STYLE2">采购员</span></td>
      <td width="100"><span class="STYLE2">选择采购名</span></td>
    </tr>
    <s:iterator value="pageBean.list" var="sd">
	     <tr align="center" style="cursor:pointer">
	     	 <td><input name="chkItems" value="${sd.id}" onclick="chkItems_click(this)" type="checkbox"  id="chk_${sd.id}" readonly="readonly"  style="cursor:pointer"/></td>
	         <td>
		     	<s:property value="getZhangHaoId(#sd.zhanghaoId)"/>
	         </td>
	         <td>${sd.orderId}</td>
	          <td>${sd.bianma}</td>
	   		 <td>${sd.time}</td>	
	   		 <td>
	   		 <s:if test="#sd.leimuid == null">
	         			${sd.yunshu}	
	         	</s:if>
	         <s:if test="#sd.leimuid != null">
	         	<s:property value="getSelLeiMu(#sd.leimuid)"/>
	         </s:if>
	         </td>
	          <td><s:property value="#sd.country"/></td>
	          <td>${sd.money}</td>
	   		 <td>${sd.remark}</td>
	   		 <td><s:property value="#sd.caigouyuan==0?('未分配'):(getUserId(#sd.caigouyuan))"/></td>
		     <td>
		     	<select name="selcgall" id="selcgall"> 
		     	<option value="0">-请选择-</option>
			      	<s:iterator value="getCaiGousd(usertype)" id = "i">
			      		<option value="${i.userid}">${i.name}</option>
			      	</s:iterator>
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
  </form>
    <s:iterator value="#str" var="d">
  	${d}<br>
  </s:iterator>
  <br>
</m:frame>