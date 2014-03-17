<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<script type="text/javascript" src="./inc/js/bianhao.js"></script>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript">
//跳转
	 function tiaozhuan(){
		var pageNumber = document.getElementById("pageNumber").value;
		location.href = "cangkuyuan!getFaHuoOrder.do?pager.offset="+(pageNumber*10-10)+"&pageNumber="+pageNumber;
	}
	//修改退货 
	 function fanhui(){
	 	var items=document.getElementsByName("chkItems"); 
		 var lujing = "cangkuyuan!updateTuiHuo.do?pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
	     bianhao(items,lujing);
	 }
	//全选
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
<form  action="cangkuyuan!getFaHuoOrder.do" name="forms" id="forms" method="post" onsubmit="return as()">
  <table border="1" width="1200px" cellspacing="0" style="float:left" >
    <tr>
      <td colspan="11" align="center"><strong>修改退货订单</strong></td>
    </tr>
    <tr>
    	<td colspan="11" align="center">  
	  	国际单号: <input type="text" name="danhao" id="danhao" value="${param.danhao}"/>
	  	<input type="submit" value="查询"/> 
	  	 <s:hidden name="pageNumber" value="1"></s:hidden>
		<s:hidden name="pager.offset" id="pager.offset" value="0"></s:hidden>
		<input type="hidden" value="${tit}" name="tit"> 
	  	<input type="button" value="修改是否退货" onclick="fanhui()"/>
	  	</td>
    </tr>
    <tr align="center">
    <td width="34" height="25">
		<input name="checkbox" onclick="quan()" type="checkbox" style="cursor:pointer"/></td>
      <td width="70"><span class="STYLE2">名字</span></td>
       <td width="50"><span class="STYLE2">订单号</span></td>
      <td width="100"><span class="STYLE2">国内运输单号</span></td>
     <td width="80"><span class="STYLE2">物品</span></td>
      <td width="100"><span class="STYLE2">地址</span></td>
       <td width="100"><span class="STYLE2">备注</span></td>
        <td width="100"><span class="STYLE2">上传时间</span></td>
      <td width="80"><span class="STYLE2">国际运输单号</span></td>
      <td width="80"><span class="STYLE2">是否退货</span></td>
      <td width="80"><span class="STYLE2">操作</span></td>
    </tr>
    <s:iterator value="pageBean.list" var="sd">
	     <tr align="center" style="font-size:30px">
	      <td><input name="chkItems" value="${sd.id}" type="checkbox"  id="chk_${sd.id}" readonly="readonly"  style="cursor:pointer"/></td>
	    	 <s:if test="#sd.zhanghaoId==15">
	       	<td>${sd.name}</td>
	       </s:if>
	       <s:if test="#sd.zhanghaoId != 15">
	       	<td><s:property value="getZhangHaoId(#sd.zhanghaoId)"/></td>
	       </s:if>
	          <td>${sd.orderId}</td>
	   		 <td>${sd.guoneidanhao}</td>	  
	   		 <td><textarea cols="10" rows="5">${sd.wuping}</textarea></td>
	   		<td><textarea cols="10" rows="5">${sd.guowaidizhi}</textarea></td>     
	   		 <td><textarea cols="10" rows="5">${sd.remark}</textarea></td> 
	   		 <td>${sd.time}</td>
		     <td>${sd.danhao}</td>
		     <td>
		     ${(sd.tuihuo == 0 || sd.tuihuo == null)?('否'):('是')}
		     </td>
		      <td><a href="cangkuyuan!upremark.do?ordertable.id=${sd.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}">修改备注</a></td>
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
<s:iterator value="#strsd" var="d">
${d}
</s:iterator>
  </form>
</m:frame>