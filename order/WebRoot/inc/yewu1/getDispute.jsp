<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript"  src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="./inc/js/bianhao.js"></script>
<script>
 function page(pageNumber) 
 {
	document.getElementById("pageNumber").value = pageNumber;
	document.getElementById("pager.offset").value = (pageNumber*10-10);
	document.forms.submit();
 }
   //全选
	 function quan()
	 {
		 var h=document.getElementsByName("checkbox");
		 var da=document.getElementsByName("chkItems");
		 quan1(h,da);
	  }
   function chulis(){
	 	var items=document.getElementsByName("chkItems"); 
	 	var time = document.getElementById("time").value;
	 	var time1 = document.getElementById("time1").value;
	 	var chuli = document.getElementById("chuli").value;
	 	var zhanghaoId = document.getElementById("zhanghaoId").value;
	 	var lujing = "yewu1!chuli.do?time="+time+"&time1="+time1+"&chuli="+chuli+"&zhanghaoId"+zhanghaoId+"&pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
	 	 bianhao(items,lujing);
	 }	
</script>
<m:frame>
<form action="yewu1!getDispute.do" method="post" onsubmit="return as()" id="forms" name="forms">
  <table border="1" width="900px" cellspacing="0" style="float:left">
    <tr>
      <td colspan="9" align="center"><STRONG>全部纠纷 </STRONG></td> 
    </tr>
    <tr>
      <td colspan="9">订单号：<input type="text" name="orderId" id="orderId" value="${param.orderId}"/>
      	时间查询：<input type="text" name="time" id="time" onfocus="WdatePicker()" value="${param.time}"/> 至<input type="text" name="time1" id="time1"  onfocus="WdatePicker()" value="${param.time1}"/>
      	 纠纷是否处理完毕：
	  	<select name="chuli" id="chuli">
	  		<option value="">-请选择-</option>
	  		<option value="0"
	  		${chuli==0?('selected="selected"'):('')}
	  		>否</option>
	  		<option value="1"
	  		${chuli==1?('selected="selected"'):('')}
	  		>是</option>
	  	</select><br/>
	  	账号: 
	  	<select name="zhanghaoId" id="zhanghaoId"> 
	      	<option value="">-请选择-</option>
	      	<s:iterator value="getZhangHao()" id = "i">
	      	<option value="${i.id}"
	      	${i.id==zhanghaoId?('selected="selected"'):('')}
	      	>${i.name}</option>
	      </s:iterator>
	      </select> 
      
      <input type="submit" value="查询"/>
      <input type="button" value="纠纷是否处理完毕" style="cursor: pointer;" onclick="chulis()"/>
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
      <td width="100"><span class="STYLE2">时间</span></td>
      <td width="100"><span class="STYLE2">运输单号</span></td>
        <td width="130"><span class="STYLE2">采购时间</span></td>
        <td width="130"><span class="STYLE2">是否处理</span></td>
         <td width="100"><span class="STYLE2">备注</span></td>
         <td width="100"><span class="STYLE2">采购员</span></td>
    </tr>
    <s:iterator value="pageBean.list" var="sd">
	     <tr align="center" style="cursor:pointer">
	      <td><input name="chkItems" value="${sd.id}" onclick="chkItems_click(this)" type="checkbox"  id="chk_${sd.id}" readonly="readonly"  style="cursor:pointer"/></td>
	         <td><s:property value="getZhangHaoId(#sd.zhanghaoId)"/></td>
	         <td>${sd.orderId}</td>
	           <td>${sd.time}</td>
	   		 <td><textarea rows="5" cols="10">${sd.danhao}</textarea></td>	
	   		 <td>${sd.caigoutime}</td>
	   		 <td>${sd.chuli==0 || sd.chuli == null?('否'):('是')}</td>
	   		 <td><textarea rows="5" cols="10">${sd.remark}</textarea></td>
	   		  <td><s:property value="#sd.caigouyuan==0?('未分配'):(getUserId(#sd.caigouyuan))"/></td>
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
  </form>
  <br>
</m:frame>