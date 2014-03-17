<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="./inc/js/bianhao.js"></script>
<script type="text/javascript"  src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="jquery-1.8.2.js"></script>
<m:frame>
	<script type="text/javascript">
			
		function as()
		{
			var orderId = document.getElementById("orderId").value;
			var time = document.getElementById("time").value
			var time1 = document.getElementById("time1").value;
			var zhanghao = document.getElementById("dhgatezhanghao").value;
			if(zhanghao != '' && orderId != ''){
				alert("账号和订单号不能同时查询");
				return false;
			}
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
		function upruzhang(){
	 	var items=document.getElementsByName("chkItems"); 
		 var lujing = "yewu!updateAllRuZhang.do?pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
	    bianhao(items,lujing);
	 }
		 function page(pageNumber) 
	     {
			document.getElementById("pageNumber").value = pageNumber;
			document.getElementById("pager.offset").value = (pageNumber*10-10);
			document.forms.submit();
	     }
	</script>
	<form action="yewu!getWeiRuZhang.do" name="forms" id="forms" method="post" onsubmit="return as()">
  <table border="1" width="1150" cellspacing="0" style="float:left">
    <tr>
      <td colspan="14" align="center"><strong>是否入账订单</strong>
      	<input type="button" value="修改是否入账 " onclick="upruzhang()"/>
      </td>
    </tr>
    <tr>
    <td colspan="14">
    	订单号：<input type="text" name="orderId" id="orderId" value="${param.orderId}"/>
    	时间：<input type="text" name="time" id="time" onfocus="WdatePicker()" value="${param.time}"/>至<input type="text" name="time1" id="time1"  onfocus="WdatePicker()" value="${param.time1}"/>
    	dhgate账号查询：
    	<select name="dhgatezhanghao" id="dhgatezhanghao"> 
	      	<option value="">-请选择-</option>
	      	<s:iterator value="getZhangHao()" id = "i">
	      	<option value="${i.id}"
	      	${i.id==dhgatezhanghao?('selected="selected"'):('')}
	      	>${i.name}</option>
	      </s:iterator>
	      </select>  
    	国际单号查询：<input type="text" name="danhao" id="danhao" value="${param.danhao}"/>
    	<input type="submit" value="查询"/>
    	 <s:hidden name="pageNumber" value="1"></s:hidden>
		<s:hidden name="pager.offset" id="pager.offset" value="0"></s:hidden>
		<input type="hidden" value="${tit}" name="tit">  
    </td>
    </tr>
    <tr align="center">
    <td width="34" height="25">
		<input name="checkbox" onclick="quan()" type="checkbox" style="cursor:pointer"/></td>
     <td width="163"><span class="STYLE2">日期</span></td>
      <td width="90"><span class="STYLE2">订单号</span></td>
      <td width="100"><span class="STYLE2">金额($)</span></td>
       <td width="163"><span class="STYLE2">类目</span></td>
      <td width="100"><span class="STYLE2">运输单号</span></td>
       <td width="63"><span class="STYLE2">运费</span></td>
      <td width="54"><span class="STYLE2">货款</span></td>
      <td width="64"><span class="STYLE2">退款</span></td>
       <td width="100"><span class="STYLE2">备注</span></td>
       <td width="80"><span class="STYLE2">纠纷</span></td>
       <td width="80"><span class="STYLE2">账号</span></td>
       <td width="80"><span class="STYLE2">是否入账</span></td>
    </tr>
    <s:iterator value="pageBean.list" var="i">
      <tr align="center">
      <td><input name="chkItems" value="${i.id}" onclick="chkItems_click(this)" type="checkbox"  id="chk_${i.id}" readonly="readonly"  style="cursor:pointer"/></td>
        <td>${i.time}</td>
        <td>${i.orderId}</td>
	    <td>${i.money}</td>	 
    	 <s:if test="#i.leimuid == null">
         		<td>
         			${i.yunshu}
         		</td>
         	</s:if>
         <s:if test="#i.leimuid != null">
         	<td><s:property value="getSelLeiMu(#i.leimuid)"/></td>
         </s:if>
	    <td><textarea rows="5" cols="10">${i.danhao}</textarea></td>
	    <td><fmt:formatNumber value="${i.yunfei}" pattern="0.00"></fmt:formatNumber></td>
	    <td>${i.huokuan}</td>      
	    <td>${i.tuikuan}</td>
	     <td><textarea rows="5" cols="10">${i.remark}</textarea></td>
	     <td>${(i.jiufen==0 || i.jiufen==null)?('否'):('是')}</td>
	    <td><s:property value="getZhangHaoId(#i.zhanghaoId)"/></td>
	     <td>${(i.ruzhang==0 || i.jiufen==null)?('否'):('是')}</td>	
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
  <s:iterator value="#strsd" var="i">
  	${i}
  </s:iterator>
  </form>
  <br>
</m:frame>