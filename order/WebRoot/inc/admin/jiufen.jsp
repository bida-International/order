<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript"  src="My97DatePicker/WdatePicker.js"></script>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %>
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
		 function myshow()
		{
			var usertype = document.getElementById("usertype").value;
			window.location = "admin!jiufen.do?usertype="+usertype;
		}
		 function page(pageNumber) 
		 {
			document.getElementById("pageNumber").value = pageNumber;
			document.getElementById("pager.offset").value = (pageNumber*10-10);
			document.forms.submit();
		 }
	</script>
	<form action="admin!jiufen.do" method="post" onsubmit="return as()" name="forms" id="forms">
  <table border="1" cellspacing="0" width="1150px" class="datagrid2" id="list" style="float:left">
    <tr>
      <td colspan="16" align="center"><strong>全部纠纷订单</strong></td>
    </tr>
    <tr>
    <td colspan="16">
    
    	订单号：<input type="text" name="orderId" id="orderId" value="${param.orderId}"/>
    	时间：<input type="text" name="time" id="time" onfocus="WdatePicker()" value="${param.time}"/>至<input type="text" name="time1" id="time1"  onfocus="WdatePicker()" value="${param.time1}"/>
    	 请选择分配对象(<font color="blue" size="2">默认为采购</font>)：<select name="usertype" id="usertype" onchange="myshow()">
	       				<option value="0">-请选择-</option>
	       				<option value="1"
	       				${usertype==1?('selected="selected"'):('')}
	       				>采购</option>
	       				<option value="2"
	       				${usertype==2?('selected="selected"'):('')}
	       				>采购管理员</option>
	       			</select>
	       	   <select name="selcaigouyuan" id="selcaigou"> 
		     	  <option value="0">-请选择-</option>
			      	<s:iterator value="getCaiGousd(usertype)" id = "i">
			      		<option value="${i.userid}">${i.name}</option>
			      	</s:iterator>
		      	</select>
		      类目查询：
		      <select name="leimus" id="leimus" title="类目">
	    		<option value="">-请选择 -</option>
	    		<s:iterator value="getAllLeiMu()" id="i">
	    			<option value="${i.id}">${i.leimu}</option>
	    		</s:iterator>
	    	</select>
	  	   纠纷是否处理完毕：
	  		    <select name="disputes" id="disputes">
	    		<option value="">-请选择 -</option>
	    		<option value="0">否</option>
	    		<option value="1">是</option>
	    	</select>
    	<input type="submit" value="查询"/>
         <s:hidden name="pageNumber" value="1"></s:hidden>
		<s:hidden name="pager.offset" id="pager.offset" value="0"></s:hidden>
		<input type="hidden" value="${tit}" name="tit"> 
    </td>
    </tr>
    <tr align="center">
     <td width="154"><span class="STYLE2">纠纷日期</span></td>
      <td width="163"><span class="STYLE2">订单号</span></td>
      <td width="54"><span class="STYLE2">金额</span></td>
       <td width="163"><span class="STYLE2">类目</span></td>
      <td width="163"><span class="STYLE2">运输单号</span></td>
       <td width="63"><span class="STYLE2">运费</span></td>
      <td width="54"><span class="STYLE2">货款</span></td>
       <td width="163"><span class="STYLE2">速卖通地址</span></td>
      <td width="120"><span class="STYLE2">供运商</span></td>
      <td width="64"><span class="STYLE2">退款</span></td>
       <td width="64"><span class="STYLE2">汇率</span></td>
       <td width="64"><span class="STYLE2">利润</span></td>
       <td width="160"><span class="STYLE2">备注</span></td>
       <td width="160"><span class="STYLE2">是否纠纷</span></td>
       <td width="120"><span class="STYLE2">账号</span></td>
      <td width="163"><span class="STYLE2">采购员</span></td>
    </tr>
    <s:iterator value="pageBean.list" var="i">
      <tr align="center">
        <td>${i.jiufentime}</td>
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
	    <td><textarea rows="5" cols="10">${i.guojia}</textarea></td>	 
	    <td><textarea rows="5" cols="10">${i.gongyunshang}</textarea></td> 
	    <td>${i.tuikuan}</td>
	    <td>${i.huilv}</td>
	    <td><c:set var="total" value="${total+i.money*i.huilv-(i.huokuan+i.yunfei+i.tuikuan)}"></c:set></td>
	     <td><textarea rows="5" cols="10">${i.remark}</textarea></td>
	     <td>${(i.jiufen==0 || i.jiufen==null)?('否'):('是')}</td>
	    <td>
	     	<s:property value="getZhangHaoId(#i.zhanghaoId)"/>	 
	    </td>
	  
	   	 <td><s:property value="(#i.caigouyuan==0 || #i.caigouyuan==null)?('未分配'):(getUserId(#i.caigouyuan))"/></td>	      
     </tr>    
   </s:iterator>
   <tr>
   	<td colspan="16" > 
	   		纠纷总金额：<s:property value="getAllMoney(orderId,time,time1,selcaigouyuan,leimus,disputes)"/>
	   		纠纷总货款：<s:property value="getAllHuoKuan(orderId,time,time1,selcaigouyuan,leimus,disputes)"/>
	    	纠纷总运费：<s:property value="getAllYunFei(orderId,time,time1,selcaigouyuan,leimus,disputes)"/>
    </td>
   </tr>
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
  <br>
</m:frame>