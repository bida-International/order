<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="./inc/js/bianhao.js"></script>
<script type="text/javascript"  src="My97DatePicker/WdatePicker.js"></script>
	
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
	 function mysumaitong(){
	
	 	var items=document.getElementsByTagName("input");
	    var lujing = "caigou!updateSuMaiTong.do?pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
		bianhao(items,lujing);	
	 }
	  function zhupdate(){
		var items=document.getElementsByTagName("input");
	 	var userid=document.getElementsByName("myzhanghao");
	    var lujing = "caigou!myupzhanghao.do?pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
		 myfenpei(items,userid,lujing);
	 }
	  function quan()
	 {
		 var h=document.getElementsByName("checkbox");
		 var da=document.getElementsByName("chkItems");
		 quan1(h,da);
	  }
   function tiaozhuan(){
		var pageNumber = document.getElementById("pageNumber").value;
		location.href = "caigou!getDaiFaHuo.do?pager.offset="+(pageNumber*10-10)+"&pageNumber="+pageNumber;
    }
     function page(pageNumber) 
    {
		document.getElementById("pageNumber").value = pageNumber;
		document.getElementById("pager.offset").value = (pageNumber*10-10);
		document.forms.submit();
	}
</script>
<m:frame>
<form action="caigou!getDaiFaHuo.do"  onsubmit="return as()" name="forms" id="forms" method="post">
<table  class="datagrid2" id="list"><tr><td><input type="button" style="cursor:pointer" value="速卖通返回给管理员" onclick="mysumaitong()"/></td></tr></table>
  <table border="1"  class="datagrid2" id="list" width="1380px" cellspacing="0" style="float:left">
    <tr>
      <td colspan="18" align="center"><strong>待发货</strong></td>
    </tr>
    <tr>
    	<td colspan="18" align="center">
	  	时间查询：<input type="text" name="time" id="time" onfocus="WdatePicker()" value="${param.time}"/> 至<input type="text" name="time1" id="time1"  onfocus="WdatePicker()" value="${param.time1}"/>
	  	采购时间查询：<input type="text" name="caigoutime" id="caigoutime" onfocus="WdatePicker()" value="${param.caigoutime}"/> 至<input type="text" name="caigoutime1" id="caigoutime1"  onfocus="WdatePicker()" value="${param.caigoutime1}"/>
	  	订单号 <input type="text" name="orderId" id="orderId" value="${param.orderId}"/>
	  	  物品: <input type="text" name="wuping" size="8" id="wuping" value="${param.wuping}"/>
	  	 供运商: <input type="text" name="gongyunshang" size="8" id="gongyunshang" value="${param.gongyunshang}"/>
	    <s:hidden name="pageNumber" value="1"></s:hidden>
		<s:hidden name="pager.offset" id="pager.offset" value="0"></s:hidden>
		<input type="hidden" value="${tit}" name="tit"> 
	  	<input type="submit" value="查询" name="Inquiry"/>
	  	</td>
    </tr>
    <tr align="center">
     <td width="34" height="25">
		<input name="checkbox" onclick="quan()" type="checkbox" style="cursor:pointer"/></td>
     <td width="54"><span class="STYLE2">账号</span></td>
     <td width="54"><span class="STYLE2">编码</span></td>
       <td width="70"><span class="STYLE2">订单号</span></td>
         <td width="90"><span class="STYLE2">国家</span></td>
         <td width="90"><span class="STYLE2">物品</span></td>
      <td width="163"><span class="STYLE2">类目</span></td>
      <td width="84"><span class="STYLE2">金额($)</span></td>
      <td width="84"><span class="STYLE2">运费</span></td>
      <td width="80"><span class="STYLE2">采购金额</span></td>
      <td width="65"><span class="STYLE2">退款</span></td>
       <td width="80"><span class="STYLE2">供运商</span></td>
        <td width="80"><span class="STYLE2">备注</span></td>
        <td width="50"><span class="STYLE2">操作</span></td>
       <td width="80"><span class="STYLE2">运输单号</span></td>
      <td width="100"><span class="STYLE2">上传时间</span></td>
       <td width="140"><span class="STYLE2">订单状态</span></td>
     <td width="50"><span class="STYLE2">操作</span></td>
    </tr>
    <s:iterator value="pageBean.list" var="sd">
	     <tr align="center" style="font-size:30px">
	      <td><input name="chkItems" value="${sd.id}" onclick="chkItems_click(this)" type="checkbox"  id="chk_${sd.id}" readonly="readonly"  style="cursor:pointer"/></td>
	     <td>
	      <s:if test="#sd.zhanghaoId != null">
	     	<s:property value="getZhangHaoId(#sd.zhanghaoId)"/>
	     </s:if>
	     </td>
	     <td>
	        	${sd.bianma}
	     </td>
	          <td>${sd.orderId}</td>
	             <td><s:property value="#sd.country"/></td>
	             <td><textarea rows="5" cols="10">${sd.wuping}</textarea></td>
	         	<s:if test="#sd.leimuid == null">
	         		<td>${sd.yunshu}</td>
	         	</s:if>
	         <s:if test="#sd.leimuid != null">
	         	<td><s:property value="getSelLeiMu(#sd.leimuid)"/></td>
	         </s:if>
	   		 <td>${sd.money}</td>
	   		 <td><fmt:formatNumber value="${sd.yunfei}" pattern="0.00"></fmt:formatNumber></td>
	   		 <td>${sd.huokuan}</td>
	   		 <td>${sd.tuikuan}</td>	 	  
	   		 <td><textarea  rows="5" cols="10">${sd.gongyunshang}</textarea></td>     
	   		 <td><textarea  rows="5" cols="10">${sd.remark}</textarea></td> 
	   		 <td><a href="caigou!updaifahuo.do?ordertable.id=${sd.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}">修改</a></td>
		     <td><textarea  rows="5" cols="10">${sd.danhao}</textarea></td>
		      <td>${sd.time}</td>	   
		       <td>
		        <s:if test="(#sd.daifahuo == 1 && (guoneidanhao == null || guoneidanhao == ''))">
		       		仓库单号为空
		       	</s:if>
		       	<s:if test="(#sd.daifahuo == 1 && (guoneidanhao != null && guoneidanhao != ''))">
		       		仓库单号不为空
		       	</s:if>
		       	<s:if test="(#sd.daifahuo == 2) && (#sd.daochu != 2)">
		       		仓库待放区
		       	</s:if>
		       	<s:if test="(#sd.daifahuo == 2) && (#sd.daochu == 2)">
		       		财务待录单
		       	</s:if>
		       	<s:if test="(#sd.daochu == 3)">
		       		财务退回订单
		       	</s:if>
		       	<s:if test="#sd.tuihuo==2">
		       		仓库退件菜单
		       	</s:if>
		       	<s:if test="#sd.getordersId==2">
		       		仓库库存订单
		       	</s:if>
		       	<s:if test="#sd.tuihuo==1">
		       		业务查看退件
		       	</s:if>
		       	<s:if test="#sd.fenpei==2">
		       		问题订单
		       	</s:if>
		       	<s:if test="#sd.sumaitong==1">
		       		管理员速卖通录单
		       	</s:if>
		       	<s:if test="#sd.sumaitong==2 && (#sd.gongyunshang == null || #sd.gongyunshang == '')">
		       		速卖通未录单
		       	</s:if>
		       	 <s:if test="#sd.sumaitong==2 && (#sd.gongyunshang != null && #sd.gongyunshang != '')">
		       		速卖通已录单
		       	</s:if>
		       	<s:if test="#sd.denghuixin == 1">
		       		代发订单
		       	</s:if> 
		       	<s:if test="#sd.daifahuo == 3">
		       		财务付款订单
		       	</s:if>
		       	<s:if test="#sd.daifahuo == 4">
		       		采购代发订单
		       	</s:if>
		       	</td>
		      <td><a href="caigouadmin!updatezhanghao.do?ordertable.id=${sd.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}">修改账号</a></td> 
	     </tr>   
     </s:iterator> 
      <tr>     
         <td colspan="18" align="center">
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
  <s:iterator value="#str" var="d">
  ${d}
  </s:iterator>
  </form>
  <br>
</m:frame>