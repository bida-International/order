<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript"  src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="./inc/js/bianhao.js"></script>
<script type="text/javascript">
//完成订单
	function mywancheng(){
	 	  var items=document.getElementsByTagName("input");
		  var lujing = "yewu!wancheng.do?pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
		  bianhao(items,lujing);
	 }
	 //全选 
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
</script>
 
<m:frame>
<form  action="yewu!getDaiFaAll.do" method="post" name="forms" id="forms" onsubmit="return as()">
  <table border="1" cellspacing="0" width="1100px" style="float:left" >
    <tr>
      <td colspan="10" align="center"><strong>代发订单</strong></td><td><input type="button" style="cursor:pointer" onClick="mywancheng()" value="完成订单"/></td>
    </tr>
    <tr>
    	<td colspan="11" align="center">
	  	时间：<input type="text" name="time" id="time" onfocus="WdatePicker()" value="${param.time}"/>至<input type="text" name="time1" id="time1"  onfocus="WdatePicker()" value="${param.time1}"/>  
	  	订单号 <input type="text" name="orderId" id="orderId" value="${param.orderId}"/>
	  	<input type="submit" value="查询"/>
	  	<s:hidden name="pageNumber" value="1"></s:hidden>
		<s:hidden name="pager.offset" id="pager.offset" value="0"></s:hidden>
		<input type="hidden" value="${tit}" name="tit">  
	  	</td>
    </tr>
    <tr align="center">
    <td width="34" height="25">
		<input name="checkbox" onclick="quan()" type="checkbox" style="cursor:pointer"/></td>
     <td width="54"><span class="STYLE2">账号</span></td>
       <td width="90"><span class="STYLE2">订单号</span></td>
     <td width="163"><span class="STYLE2">类目</span></td>
      <td width="130"><span class="STYLE2">采购金额</span></td>
      <td width="130"><span class="STYLE2">订单金额</span></td>
        <td width="163"><span class="STYLE2">备注</span></td>
        <td width="163"><span class="STYLE2">运输单号</span></td>
       <td width="100"><span class="STYLE2">上传时间</span></td>
    	<td width="100"><span class="STYLE2">采购员</span></td>
    	<td width="90"><span class="STYLE2">修改</span></td>
    </tr>
    <s:iterator value="pageBean.list" var="sd">
	     <tr align="center" style="font-size:30px">
	      <td><input name="chkItems" value="${sd.id}" onclick="chkItems_click(this)" type="checkbox"  id="chk_${sd.id}" readonly="readonly"  style="cursor:pointer"/></td>
	     <td>
	   
	     	<s:property value="getZhangHaoId(#sd.zhanghaoId)"/>

	     </td>
	          <td>${sd.orderId}</td>
	          <td>
	          	 <s:if test="#sd.leimuid == null">	         	
	         		 <textarea rows="5" cols="10">${sd.yunshu}</textarea>
	         </s:if>
	         <s:if test="#sd.leimuid != null">
	         	<textarea rows="5" cols="10"><s:property value="getSelLeiMu(#sd.leimuid)"/></textarea>
	         </s:if>
	          </td>
	   		 <td>${sd.huokuan}</td>	  
	   		 <td>${sd.money}</td>  
	   		 <td><textarea>${sd.remark}</textarea></td> 
		     <td><textarea cols="10">${sd.danhao}</textarea></td>
		     <td>${sd.time}</td>
		     <td><s:property value="(#sd.caigouyuan==0 || #sd.caigouyuan == null)?('未分配'):(getUserId(#sd.caigouyuan))"/></td>
		      <td><a href="yewu!updaifa.do?ordertable.id=${sd.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}">修改</a>/
		      <a href="yewu!fanhuidaifa.do?ordertable.id=${sd.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}" onclick="return confirm('确定?')">返回给采购</a></td>
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
  ${msg}
  </form>
</m:frame>