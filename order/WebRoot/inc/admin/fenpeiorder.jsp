<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript" src="./inc/js/bianhao.js"></script>
<script type="text/javascript">
	function myupadmin(){
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
		  window.location ="admin!upcaigouadmin.do?bulletinId="+bulletinId+"&pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
		}else{
			return false;
		}
	}
	}
	 function mytest(){
	 	var items=document.getElementsByTagName("input"); 
	 	var userid = document.getElementsByName("selcg");
	    var lujing = "admin!caigou_fenpei.do?pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
	    fenpei(items,userid,lujing);
	 }
	 function mywenti(){
		  var items=document.getElementsByTagName("input"); 
		  var lujing = "admin!wentiorder.do?pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
		  bianhao(items,lujing);
	 }
	 function quan()
	 {
		 var h=document.getElementsByName("checkbox");
		 var da=document.getElementsByName("chkItems");
		 quan1(h,da);
	  }
	     function page(pageNumber) 
		 {
			document.getElementById("pageNumber").value = pageNumber;
			document.getElementById("pager.offset").value = (pageNumber*10-10);
			document.forms.submit();
		 }
  	    function myshow()
		{
			var usertype = document.getElementById("usertype").value;
			window.location = "admin!getFpAll.do?usertype="+usertype;
		}
	//	//一键分配订单
		//function yjfp(){
		//if(confirm('确定分配吗?')){
		 //	window.location = "admin!yjfp.do";
		 //}
		//}
	</script>  
<m:frame>
<form action="admin!getFpAll.do" name="forms" id="forms" method="post">
${msg}
<table  class="datagrid2" id="list">
	<tr>
		<td>
		<input type="button" style="cursor:pointer" value="分配订单" onClick="mytest()">
	    <!--  <input type="button" style="cursor:pointer" value="一键分配订单" onclick="yjfp()"/> -->
	     <input type="button" style="cursor:pointer" value="问题订单 " onClick="mywenti()">
		</td>
	</tr>
</table>
  <table border="1"  class="datagrid2" id="list" width="800px" cellspacing="0" style="float:left">
    <tr>
      <td colspan="9" align="center"><strong>分配订单</strong>
      请选择分配对象(<font color="blue" size="2">默认为采购</font>)：<select name="usertype" id="usertype" onchange="myshow()">
	       				<option value="0">-请选择-</option>
	       				<option value="1"
	       				${usertype==1?('selected="selected"'):('')}
	       				>采购</option>
	       				<option value="2"
	       				${usertype==2?('selected="selected"'):('')}
	       				>采购管理员</option>
	       			</select>	       
      </td>

    </tr>
    <tr>
      <td colspan="8" align="right">订单号：<input type="text" name="orderId" id="orderId" value="${param.orderId}"/></td>
      <td><input type="submit" value="查询"/>
       <s:hidden name="pageNumber" value="1"></s:hidden>
		<s:hidden name="pager.offset" id="pager.offset" value="0"></s:hidden>
		<input type="hidden" value="${tit}" name="tit"> 
      </td>
    </tr>
    <tr align="center">
    <td width="34" height="25">
		<input name="checkbox" onclick="quan()" type="checkbox" style="cursor:pointer"/></td>
		<td width="130"><span class="STYLE2">账号</span></td>
      <td width="100"><span class="STYLE2">订单号</span></td>
      <td width="100"><span class="STYLE2">上传时间</span></td>
      <td width="100"><span class="STYLE2">订单金额</span></td>
        <td width="80"><span class="STYLE2">类目</span></td>
         <td width="163"><span class="STYLE2">备注</span></td>
      <td width="100"><span class="STYLE2">选择采购名</span></td>
       <td width="130"><span class="STYLE2">操作</span></td>
    </tr>
    <s:iterator value="pageBean.list" var="sd">
	     <tr align="center" style="cursor:pointer">
	     	 <td><input name="chkItems" value="${sd.id}" onclick="chkItems_click(this)" type="checkbox"  id="chk_${sd.id}" readonly="readonly"  style="cursor:pointer"/></td>
	         <td><s:property value="getZhangHaoId(#sd.zhanghaoId)"/></td>
	         <td>${sd.orderId}</td>
	   		 <td>${sd.time}</td>
	   		 <td>${sd.money}</td>		
	         	<td>
	         	<textarea rows="5" cols="10"> <s:if test="#sd.leimuid != null"><s:property value="getSelLeiMu(#sd.leimuid)"/> </s:if></textarea>
	         </td>
	   		 <td>${sd.remark}</td>
		     <td>
		     	<select name="selcg" id="selcg"> 
		     	<option value="0">-请选择-</option>
			      	<s:iterator value="getCaiGousd(usertype)" id = "i">
			      		<option value="${i.userid}">${i.name}</option>
			      	</s:iterator>
		      	</select>
		     </td>  
		      <td><a href="admin!updateAdminRemark.do?ordertable.id=${sd.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}">修改备注</a></td>
	     </tr>   
     </s:iterator> 
     <tr>     
         <td colspan="9" align="center">
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
    <s:iterator value="#strsd" var="d">
  	${d}<br>
  </s:iterator>
   <s:iterator value="#wenti" var="dd">
  	${dd}<br>
  </s:iterator>
  </form>
  <br>
</m:frame>