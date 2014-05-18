<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %> 
<script type="text/javascript"  src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="jquery-1.8.2.js"></script>
<script type="text/javascript" src="./inc/js/bianhao.js"></script>
<script type="text/javascript">
function as(){
	var order = document.getElementById("orderId").value;
	var time = document.getElementById("time").value;
	var time1 = document.getElementById("time1").value;
	if(order != '' && (time != '' && time != ''))
	{
		alert("时间和订单号不能同时查询");
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
	  function page(pageNumber) 
	 {
		document.getElementById("pageNumber").value = pageNumber;
		document.getElementById("pager.offset").value = (pageNumber*10-10);
		document.forms.submit();
	 }
    function fanhuiorder()
    {
	 	var items=document.getElementsByTagName("input");
	 	var lujing="caigouadmin!yewufanhui.do?pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
	 	bianhao(items,lujing);
    }
    function quan(){
    	 var h=document.getElementsByName("checkbox");
		 var da=document.getElementsByName("chkItems");
		 quan1(h,da);
    }
	</script>
<m:frame>
	${msg}
	<form action="caigouadmin!getIntoaSingle.do" name="forms" id="forms" method="post" onsubmit="return as()">
  <table border="1"  class="datagrid2" id="list" width="1143px" cellspacing="0" style="float:left">
    <tr>
      <td colspan="19" align="center"><strong>速卖通录单</strong></td>
      
    </tr>
    <tr>
    <td colspan="19">
    	订单号：<input type="text" name="orderId" id="orderId" value="${param.orderId}"/>
    	时间：<input type="text" name="time" id="time" onfocus="WdatePicker()" value="${param.time}"/>至<input type="text" name="time1" id="time1"  onfocus="WdatePicker()" value="${param.time1}"/>
    	  采购员:
	  	  <select name="caigouyuan" id="caigouyuan"> 
	       	  <option value="">-请选择-</option>
		      	<s:iterator value="getToView()" id = "i">
		      		<option value="${i.userid}"
		      		${i.userid==caigouyuan?('selected="selected"'):('')}
		      		>${i.name}</option>
		      	</s:iterator>
		   </select>
    	<input type="submit" value="查询"/>  <input type="button" style="cursor:pointer" value="将速卖通的产品分配给跟单" onclick="fanhuiorder()"/>
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
      <td width="90"><span class="STYLE2">编码</span></td>
      <td width="60"><span class="STYLE2">金额($)</span></td>
       <td width="120"><span class="STYLE2">类目</span></td>
      <td width="163"><span class="STYLE2">运输单号</span></td>
       <td width="63"><span class="STYLE2">运费</span></td>
      <td width="54"><span class="STYLE2">货款</span></td>
       <td width="120"><span class="STYLE2">速卖通地址</span></td>
      <td width="120"><span class="STYLE2">供运商</span></td>
      <td width="64"><span class="STYLE2">退款</span></td>
       <td width="64"><span class="STYLE2">汇率</span></td>
       <td width="64"><span class="STYLE2">利润</span></td>
       <td width="160"><span class="STYLE2">备注</span></td>
       <td width="160"><span class="STYLE2">是否纠纷</span></td>
       <td width="120"><span class="STYLE2">账号</span></td>
      <td width="100"><span class="STYLE2">采购员</span></td>
      <td width="160"><span class="STYLE2">操作</span></td>
    </tr>
    <s:iterator value="pageBean.list" var="i">
      <tr align="center">
       <td><input name="chkItems" value="${i.id}" onclick="chkItems_click(this)" type="checkbox"  id="chk_${i.id}" readonly="readonly"  style="cursor:pointer"/></td>
        <td>${i.time}</td>
        <td>${i.orderId}</td>
        <td>${i.bianma}</td>
	    <td>${i.money}</td>	 
	    <td>
	    	  <s:if test="#i.leimuid == null">
	         		${i.yunshu}
	         	</s:if>
	         <s:if test="#i.leimuid != null">
	         	<s:property value="getSelLeiMu(#i.leimuid)"/>
	         </s:if> 
	      </td>
	    <td><textarea rows="5" cols="10">${i.danhao}</textarea></td>
	    <td>${i.yunfei}</td>	 
	    <td>${i.huokuan}</td> 
	    <td><textarea rows="5" cols="10">${i.guojia}</textarea></td>	 
	    <td><textarea rows="5" cols="10">${i.gongyunshang}</textarea></td>       
	    <td>${i.tuikuan}</td>
	    <td>${i.huilv}</td>
	    <td><c:set var="total" value="${total+i.money*i.huilv-(i.huokuan+i.yunfei+i.tuikuan)}"></c:set></td>
	     <td><textarea rows="5" cols="10">${i.remark}</textarea></td>
	     <td>${(i.jiufen==0 || i.jiufen==null)?('否'):('是')}</td>
	    <td><s:property value="getZhangHaoId(#i.zhanghaoId)"/></td>
	   	 <td><s:property value="(#i.caigouyuan==0 || #i.caigouyuan==null)?('未分配'):(getUserId(#i.caigouyuan))"/></td>
	   	 <td><a href="admin!fanhuicaigou.do?ordertable.id=${i.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}" id="fanhui" onclick="return confirm('确定返回给采购?')">返回给采购</a>
	   	 /<a href="admin!updateremark.do?ordertable.id=${i.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}">修改备注</a>
	   	 </td>	      
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
  </form>
  <br>
</m:frame>