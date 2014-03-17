<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %> 

<script type="text/javascript"  src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="./inc/js/bianhao.js"></script>
<m:frame>
	<script type="text/javascript">
		function as()
		{
			var orderId = document.getElementById("orderId").value;
			var time = document.getElementById("time").value
			var time1 = document.getElementById("time1").value;
			if(orderId != '' && (time !='' && time1 !=''))
			{
				alert("订单号和时间不能同时不为空");
				return false;
			}
			if(time1<time){
				alert("开始时间一定小于结束时间");
				return false;
			}
		}
		function quan()
	    {
		 var h=document.getElementsByName("checkbox");
		 var da=document.getElementsByName("chkItems");
		 quan1(h,da);
	   }
	   function updaifahuo(){
	 	var items=document.getElementsByName("chkItems"); 
		 var lujing = "caigou!updateAllDaoChu.do?pageindex="+${pageindex};
	    bianhao(items,lujing);
	  }
	  function page(pageNumber) 
	   {
		document.getElementById("pageNumber").value = pageNumber;
		document.getElementById("pager.offset").value = (pageNumber*10-10);
		document.forms.submit();
	   }
	</script>
	<form action="caigou!jiufen.do" method="post" id="forms" name="forms" onsubmit="return as()">
  <table border="1"  class="datagrid2" id="list" cellspacing="0" width="1100px" style="float:left">
    <tr>
      <td colspan="11" align="center"><strong>显示全部纠纷订单</strong><a href="caigouadmin!jforder.do">导出全部纠纷订单</a>
   
      </td>
    </tr>
    <tr>
    <td colspan="11">
    	订单号：<input type="text" name="orderId" id="orderId" value="${param.orderId}"/>
    	时间：<input type="text" name="time" id="time" onfocus="WdatePicker()" value="${param.time}"/>至<input type="text" name="time1" id="time1"  onfocus="WdatePicker()" value="${param.time1}"/>
    	   类目查询：
		      <select name="leimus" id="leimus" title="类目">
	    		<option value="">-请选择 -</option>
	    		<s:iterator value="getAllLeiMu()" id="i">
	    			<option value="${i.id}"
	    			${i.id==leimus?('selected="selected"'):('')}
	    			>${i.leimu}</option>
	    		</s:iterator>
	    	</select>
	    	<s:hidden name="pageNumber" value="1"></s:hidden>
			<s:hidden name="pager.offset" id="pager.offset" value="0"></s:hidden>
			<input type="hidden" value="${tit}" name="tit">  
    	<input type="submit" value="查询"/>
    </td>
    </tr>
    <tr align="center">
     <td width="100"><span class="STYLE2">纠纷日期</span></td>
      <td width="100"><span class="STYLE2">订单号</span></td>
      <td width="54"><span class="STYLE2">金额</span></td>
       <td width="120"><span class="STYLE2">类目</span></td>
      <td width="120"><span class="STYLE2">运输单号</span></td>
      <td width="120"><span class="STYLE2">供运商</span></td>
      <td width="64"><span class="STYLE2">退款</span></td>
       <td width="100"><span class="STYLE2">备注</span></td>
       <td width="50"><span class="STYLE2">是否纠纷</span></td>
       <td width="100"><span class="STYLE2">账号</span></td>
    </tr>
    <s:iterator value="pageBean.list" var="i">
      <tr align="center">
        <td>${i.jiufentime}</td>
        <td>${i.orderId}</td>
	    <td>${i.money}</td>	 
	    <td>
	   		<c:if test="${i.leimuid == null}">
        		<textarea rows="5" cols="10">
        			${i.yunshu}
        		</textarea>
	        </c:if>
	        <c:if test="${i.leimuid != null}">
	         	<textarea rows="5" cols="10"><s:property value="getSelLeiMu(#i.leimuid)"/></textarea>
	         </c:if> 
	    </td>
	    <td><textarea rows="5" cols="10">${i.danhao}</textarea></td>
	    <td><textarea rows="5" cols="10">${i.gongyunshang}</textarea></td> 
	    <td>${i.tuikuan}</td>
	     <td><textarea rows="5" cols="10">${i.remark}</textarea></td>
	     <td>${(i.jiufen==0 || i.jiufen==null)?('否'):('是')}</td>
	    <td><s:property value="getZhangHaoId(#i.zhanghaoId)"/></td>      
     </tr>    
   </s:iterator>
    <tr>     
      <td colspan="14" align="center"> 
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