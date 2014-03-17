<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %>
<script type="text/javascript"  src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="./inc/js/bianhao.js"></script>
<script type="text/javascript" src="jquery-1.8.2.js"></script>
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
			if(time != '' && time1 == ''){
				alert("查询时间未选");
				return false;
			}
			if(time == '' && time1 != '')
			{
				alert("查询时间未选");
				return false;
			}
			if(time1<time){
				alert("开始时间一定小于结束时间");
				return false;
			}
		}
		//业务已经完成 
		function yewufanhui(){
	 	var items=document.getElementsByTagName("input");
	     var lujing ="yewu!yijingwancheng.do?pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
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
	${msg}
	<form action="yewu!getChaKanOrder.do" method="post" name="forms" id="forms"  onsubmit="return as()">
  <table border="1" width="1135px" cellspacing="0" style="float:left">
    <tr>
      <td colspan="12" align="center"><strong>显示已入单订单</strong></td>
      <td><input type="button" style="cursor:pointer" value="完成订单" onclick="yewufanhui()"/></td>
    </tr>
    <tr>
    <td colspan="13">
    	订单号：<input type="text" name="orderId" id="orderId" value="${param.orderId}"/>
    	时间：<input type="text" name="time" id="time" onfocus="WdatePicker()" value="${param.time}"/>至<input type="text" name="time1" id="time1"  onfocus="WdatePicker()" value="${param.time1}"/>
    	<input type="submit" value="查询"/>
    	<s:hidden name="pageNumber" value="1"></s:hidden>
		<s:hidden name="pager.offset" id="pager.offset" value="0"></s:hidden>
		<input type="hidden" value="${tit}" name="tit">  
    </td>
    </tr>
    <tr align="center">
    <td width="34" height="25">
		<input name="checkbox" onclick="quan()" type="checkbox" style="cursor:pointer"/></td>
     <td width="120"><span class="STYLE2">日期</span></td>
      <td width="90"><span class="STYLE2">订单号</span></td>
      <td width="100"><span class="STYLE2">金额($)</span></td>
       <td width="80"><span class="STYLE2">类目</span></td>
      <td width="80"><span class="STYLE2">运输单号</span></td>
      <td width="80"><span class="STYLE2">货款</span></td>
       <td width="80"><span class="STYLE2">供运商</span></td>
       <td width="120"><span class="STYLE2">速卖通地址</span></td>
       <td width="100"><span class="STYLE2">备注</span></td>
       <td width="120"><span class="STYLE2">账号</span></td>
      <td width="60"><span class="STYLE2">采购员</span></td>
      <td width="100"><span class="STYLE2">操作 </span></td>
    </tr>
    <s:iterator value="pageBean.list" var="i">
      <tr align="center">
       <td><input name="chkItems" value="${i.id}" onclick="chkItems_click(this)" type="checkbox"  id="chk_${i.id}" readonly="readonly"  style="cursor:pointer"/></td>
        <td>${i.time}</td>
        <td>${i.orderId}</td>
	    <td>${i.money}</td>	 
     	<s:if test="#i.leimuid == null">
         		<td>
         			<textarea rows="5" cols="10">${i.yunshu}</textarea>
         		</td>
         	</s:if>
         <s:if test="#i.leimuid != null">
         	<td><textarea rows="5" cols="10"><s:property value="getSelLeiMu(#i.leimuid)"/></textarea></td>
         </s:if> 
	    <td><textarea rows="5" cols="10">${i.danhao}</textarea></td> 
	    <td>${i.huokuan}</td> 
	     <td><textarea rows="5" cols="10">${i.gongyunshang}</textarea></td>
	    <td><textarea rows="5" cols="10">${i.guojia}</textarea></td>	   
	     <td><textarea rows="5" cols="10">${i.remark}</textarea></td>
	    <td><s:property value="getZhangHaoId(#i.zhanghaoId)"/></td>
	   	 <td><s:property value="(#i.caigouyuan==0 || #i.caigouyuan==null)?('未分配'):(getUserId(#i.caigouyuan))"/></td>
	   	 <td width="130"><a href="yewu!fanhuiadmin.do?ordertable.id=${i.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}" onclick="return confirm('确定返回给管理员?')">返回给管理员</a> 
	   	  <a href="yewu!upsumaitong.do?ordertable.id=${i.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}">修改订单</a></td>    
     </tr>    
   </s:iterator>
     <tr>     
         <td colspan="13" align="center">
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