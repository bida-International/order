<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
	 function tiaozhuan(){
		var pageNumber = document.getElementById("pageNumber").value;
		location.href = "guke!getYiJingFaHuo.do?pager.offset="+(pageNumber*10-10)+"&pageNumber="+pageNumber;
	}
	function fanhui(){
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
			  location.href="guke!cangkudaifangqu.do?bulletinId="+bulletinId+"&pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
			}else{
				return false;
			}
		}
	}
	function quan()
	 {
		 var h=document.getElementsByName("checkbox");
		 var da=document.getElementsByName("chkItems");
		 if(h[0].checked==true)
		 {
		  for(i=0;i<da.length;i++)
		  {
		   da[i].checked=true;
		  }
		 }
		 else
		 {
		   	 for(i=0;i<da.length;i++)
			  {
			    da[i].checked=false;
			  }
	 	}
	  }
	    function page(pageNumber) 
	 	{
			document.getElementById("pageNumber").value = pageNumber;
			document.getElementById("pager.offset").value = (pageNumber*10-10);
			document.forms.submit();
	 	}
</script>
<m:frame>
<form  action="guke!getYiJingFaHuo.do" name="forms" id="forms" method="post" onsubmit="return as()">
  <table border="1" width="1300px" cellspacing="0" style="float:left" >
    <tr>
      <td colspan="16" align="center"><strong>完成订单</strong></td>
    </tr>
    <tr>
    	<td colspan="16" align="center">  
	  	订单号 <input type="text" name="orderId" id="orderId" value="${param.orderId}"/>
	  	<input type="submit" value="查询"/> 
	  	<input type="button" value="返回给仓库" onclick="fanhui()"/>
	  	   <s:hidden name="pageNumber" value="1"></s:hidden>
		<s:hidden name="pager.offset" id="pager.offset" value="0"></s:hidden>
		<input type="hidden" value="${tit}" name="tit">  
	  	</td>
    </tr>
    <tr align="center">
    <td width="34" height="25">
		<input name="checkbox" onclick="quan()" type="checkbox" style="cursor:pointer"/></td>
      <td width="70"><span class="STYLE2">名字</span></td>
       <td width="50"><span class="STYLE2">订单号</span></td>
      <td width="80"><span class="STYLE2">国内运输方式</span></td>
      <td width="100"><span class="STYLE2">国内运输单号</span></td>
     <td width="80"><span class="STYLE2">物品</span></td>
      <td width="100"><span class="STYLE2">地址</span></td>
      <td width="100"><span class="STYLE2">运费</span></td>
       <td width="100"><span class="STYLE2">备注</span></td>
        <td width="100"><span class="STYLE2">上传时间</span></td>
      <td width="80"><span class="STYLE2">国际运输方式</span></td>
      <td width="80"><span class="STYLE2">国际运输单号</span></td>
      <td width="80"><span class="STYLE2">是否付款</span></td>
      <td width="80"><span class="STYLE2">是否退货</span></td>
       <td width="100"><span class="STYLE2">完成时间</span></td>
      <td width="60"><span class="STYLE2">操作</span></td>
     
    </tr>
    <s:iterator value="pageBean.list" var="sd">
	     <tr align="center" style="font-size:30px">
	      <td><input name="chkItems" value="${sd.id}" type="checkbox"  id="chk_${sd.id}" readonly="readonly"  style="cursor:pointer"/></td>
	     <td>${sd.name}</td>
	          <td>${sd.orderId}</td>
	         <td><s:property value="getGuoNeiKuaiDiFangShi(#sd.guoneikuaidiId)"/></td>
	   		 <td>${sd.guoneidanhao}</td>	  
	   		 <td><textarea cols="10" rows="5">${sd.wuping}</textarea></td>
	   		<td><textarea cols="10" rows="5">${sd.guowaidizhi}</textarea></td>     
	   		<td><fmt:formatNumber value="${sd.yunfei}" pattern="0.00"></fmt:formatNumber></td>
	   		 <td><textarea cols="10" rows="5">${sd.remark}</textarea></td> 
	   		 <td>${sd.time}</td>
		    <td><s:property value="getKuaiDiFangShi(#sd.kuaidifangshiId)"/></td>
		     <td>${sd.danhao}</td>
		     <td>${sd.ruzhang==0?('否'):('是')}</td>
		     <td>${sd.tuihuo==0?('否'):('是')}</td>
		     <td>${sd.wanchengtime}</td>
		     <td><a href="guke!upyijingfahuo.do?orderTable.id=${sd.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}">修改订单</a></td>
	     </tr>   
     </s:iterator> 
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
  ${msg}
  </form>
</m:frame>