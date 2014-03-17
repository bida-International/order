<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript"  src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="./inc/js/bianhao.js"></script>	
<script type="text/javascript">
<!--

//-->
function as(){
	var order = document.getElementById("orderId").value;
	var time = document.getElementById("time").value;
	if(order != '' && time != '')
	{
		alert("时间和订单号不能同时查询");
		return false;
	}
}
	function cgmyfanhui(){
	 	var items=document.getElementsByTagName("input");
		  var lujing = "caigou!fanhuidaifahuo.do?pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
		  bianhao(items,lujing);
	 }
	 function quan()
	 {
		 var h=document.getElementsByName("checkbox");
		 var da=document.getElementsByName("chkItems");
		 quan1(h,da);
	  }
	function tiaozhuan(){
		var pageNumber = document.getElementById("pageNumber").value;
		location.href = "caigou!getDeDaoOrder.do?pager.offset="+(pageNumber*10-10)+"&pageNumber="+pageNumber;
    }
     function page(pageNumber) 
     {
		document.getElementById("pageNumber").value = pageNumber;
		document.getElementById("pager.offset").value = (pageNumber*10-10);
		document.forms.submit();
	 }
</script>
<m:frame>
<form action="caigou!getDeDaoOrder.do"  onsubmit="return as()" name="forms" id="forms" method="post">
  <table  class="datagrid2" id="list" border="1" width="1200px" cellspacing="0" style="float:left">
    <tr>
      <td colspan="20" align="center"><strong>完成订单</strong><input type="button" value="返回到得到订单" onclick="cgmyfanhui()"/></td>
    </tr>
    <tr>
    	<td colspan="20">
	  	时间查询：<input type="text" name="time" id="time" onfocus="WdatePicker()" value="${param.time}"/> 至<input type="text" name="time1" id="time1"  onfocus="WdatePicker()" value="${param.time1}"/>
	  	采购时间查询：<input type="text" name="caigoutime" id="caigoutime" onfocus="WdatePicker()" value="${param.caigoutime}"/> 至<input type="text" name="caigoutime1" id="caigoutime1"  onfocus="WdatePicker()" value="${param.caigoutime1}"/>
	  	订单号 <input type="text" size="8" name="orderId" id="orderId" value="${param.orderId}"/>
	  	产品编码: <input type="text" name="bianma" size="8" id="bianma" value="${param.bianma}"/>
	  	  供应商: <input type="text" name="gongyunshang" size="8" id="gongyunshang" value="${param.gongyunshang}"/><br/>
	  	  物品: <input type="text" name="wuping" size="8" id="wuping" value="${param.wuping}"/>
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
       <td width="90"><span class="STYLE2">编码</span></td>
      <td width="163"><span class="STYLE2">类目</span></td>
      <td width="84"><span class="STYLE2">金额($)</span></td>
      <td width="84"><span class="STYLE2">运费</span></td>
      <td width="84"><span class="STYLE2">重量</span></td>
      <td width="65"><span class="STYLE2">采购金额</span></td>
      <td width="65"><span class="STYLE2">退款</span></td>
       <td width="100"><span class="STYLE2">供运商</span></td>
        <td width="100"><span class="STYLE2">物品</span></td>
        <td width="163"><span class="STYLE2">备注</span></td>
      <td width="110"><span class="STYLE2">操作</span></td>
       <td width="110"><span class="STYLE2">运输单号</span></td>
       <td width="163"><span class="STYLE2">上传到采购时间差(天)</span></td>
       <td width="100"><span class="STYLE2">完成时间</span></td>
       <td width="100"><span class="STYLE2">纠纷时间</span></td>
       <td width="100"><span class="STYLE2">采购时间</span></td>
      <td width="100"><span class="STYLE2">上传时间</span></td>
     
    </tr>
    <s:iterator value="pageBean.list" var="sd">
	     <tr align="center" style="font-size:30px">
	     <td><input name="chkItems" value="${sd.id}" onclick="chkItems_click(this)" type="checkbox"  id="chk_${sd.id}" readonly="readonly"  style="cursor:pointer"/></td>
	     <td><s:property value="getZhangHaoId(#sd.zhanghaoId)"/></td>
	          <td>${sd.orderId}</td>
	          <td>${sd.bianma}</td>
	   		  <s:if test="#sd.leimuid == null">
	         		<td>
	         			${sd.yunshu}
	         		</td>
	         	</s:if>
	         <s:if test="#sd.leimuid != null">
	         	<td><s:property value="getSelLeiMu(#sd.leimuid)"/></td>
	         </s:if>
	   		 <td>${sd.money}</td>
	   		 <td><fmt:formatNumber value="${sd.yunfei}" pattern="0.00"></fmt:formatNumber></td>
	   		 <td>${sd.zhongliang}</td>
	   		 <td>${sd.huokuan}</td>
	   		 <td>${sd.tuikuan}</td>	 	  
	   		 <td><textarea rows="5" cols="10">${sd.gongyunshang}</textarea></td>
	   		 <td><textarea rows="5" cols="10">${sd.wuping}</textarea></td>          
	   		 <td><textarea rows="5" cols="10">${sd.remark}</textarea></td> 
		     <td>	     	
		     	<a href="caigou!updateorder.do?ordertable.id=${sd.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}" onclick="return confirm('是否返回给管理员?')">返回</a>
		     	/<a href="caigou!upfenpeiorder.do?ordertable.id=${sd.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}">修改订单</a> 
		     </td>	
		     <td><textarea rows="5" cols="10">${sd.danhao}</textarea></td>
		       <s:if test="#sd.time==null || #sd.caigoutime == null">
		    	<td></td>
		    </s:if>
		    <s:if test="#sd.time!=null && #sd.caigoutime != null">
		    	<td><s:property value="getTime(#sd.time,#sd.caigoutime)"/></td>
		    </s:if>
		     <td>${sd.wanchengtime}</td>
		     <td>${sd.jiufentime}</td>
		      <td>${sd.caigoutime}</td>
		      <td>${sd.time}</td>	    
	     </tr>   
     </s:iterator> 
      <tr> 
       <td colspan="20">
   		总金额($)：<s:property value="getCaiGouAdminAllMoney(orderId,time,time1)" />
   	          纠纷总金额($):<s:property value="getJiuFenAllMoney(orderId,time,time1)"/>
   	           采购花费平均时间(小时)：<s:property value="getCgTime(orderId,time,time1,caigoutime,caigoutime1,bianma)"/>
   		纠纷个数：<a href="caigou!jiufen.do?orderId=${param.orderId}&time=${param.time}&time1=${param.time1}"><s:property value="getCaiGouAdminAllJiuFen(orderId,time,time1)"/></a>
   		
   		<a href="caigou!lirunzero.do?orderId=${param.orderId}&time=${param.time}&time1=${param.time1}">利润小于0</a>/<a href="caigou!thzero.do?orderId=${param.orderId}&time=${param.time}&time1=${param.time1}">利润大于0小于30</a>
   		</td>
   </tr>
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
  ${msg}
  </form>
  <br>
</m:frame>