<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %>
<script type="text/javascript"  src="My97DatePicker/WdatePicker.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript" src="./inc/js/bianhao.js"></script>
<script type="text/javascript">
function myupadmin(){
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
		  window.location ="admin!cgadminwenti.do?bulletinId="+bulletinId+"&pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
		}else{
			return false;
		}
	}
	}
	 function mytest(){
	 	var items=document.getElementsByTagName("input"); 
	 	var userid = document.getElementsByName("selcgall");
	 	var usertype = document.getElementById("usertype").value;
	    var lujing = "admin!upfenpei.do?pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10}+"&usertype="+usertype;
	    fenpei(items,userid,lujing);			
	 }
	 function quan()
	 {
		 var h=document.getElementsByName("checkbox");
		 var da=document.getElementsByName("chkItems");
		 quan1(h,da);
	  }
 	  function tiaozhuan(){
   		var pageNumber = document.getElementById("pageNumber").value;
   		location.href = "admin!getWenTiOrder.do?pager.offset="+(pageNumber*10-10)+"&pageNumber="+pageNumber;
     }
      function myshow()
	 {
		var usertype = document.getElementById("usertype").value;
		window.location = "admin!getWenTiOrder.do?usertype="+usertype;
	 }
	   //导入已经完成
	 	function wcorder(){
		 	var items=document.getElementsByName("chkItems"); 
		 	var lujing = "admin!upWtWcOrder.do?pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
		 	 bianhao(items,lujing);
		 }	
	</script>    
<m:frame>
<s:form action="admin!getWenTiOrder.do" theme="simple" method="post">
  <table border="1"  class="datagrid2" id="list" cellspacing="0" style="float:left">
    <tr>
      <td colspan="11" align="center"><strong>问题订单分配</strong>
            请选择分配对象(<font color="blue" size="2">默认为采购</font>)：<select name="usertype" id="usertype" onchange="myshow()">
	       				<option value="0">-请选择-</option>
	       				<option value="1"
	       				${usertype==1?('selected="selected"'):('')}
	       				>采购</option>
	       				<option value="2"
	       				${usertype==2?('selected="selected"'):('')}
	       				>采购管理员</option>
	       			</select>
      </td><td><input type="button" style="cursor:pointer" value="分配订单" onClick="mytest()">
      </td>
    </tr>
    <tr>
      <td colspan="11" align="right"><input type="button" value="已经完成" onclick="wcorder()">
      	 订单号：<input type="text" name="orderId" id="orderId" value="${param.orderId}"/>
      	时间查询：<input type="text" name="time" id="time" onfocus="WdatePicker()" value="${param.time}"/> 至<input type="text" name="time1" id="time1"  onfocus="WdatePicker()" value="${param.time1}"/>	 
      </td>
      <td><input type="submit" value="查询"/></td>
    </tr>
    <tr align="center">
    <td width="34" height="25">
		<input name="checkbox" onclick="quan()" type="checkbox" style="cursor:pointer"/></td>
		 <td width="100"><span class="STYLE2">账号</span></td>
     	 <td width="100"><span class="STYLE2">订单号</span></td>
     	 <td width="100"><span class="STYLE2">上传时间</span></td>
       	 <td width="100"><span class="STYLE2">订单金额</span></td>
         <td width="130"><span class="STYLE2">类目</span></td>
         <td width="130"><span class="STYLE2">国家</span></td>
         <td width="130"><span class="STYLE2">订单金额</span></td>
         <td width="163"><span class="STYLE2">备注</span></td>
         <td width="130"><span class="STYLE2">采购员</span></td>
      <td width="100"><span class="STYLE2">选择采购名</span></td>
      <td width="100"><span class="STYLE2">操作</span></td>
    </tr>
    <s:iterator value="pageBean.list" var="sd">
	     <tr align="center" style="cursor:pointer">
	     	 <td><input name="chkItems" value="${sd.id}" onclick="chkItems_click(this)" type="checkbox"  id="chk_${sd.id}" readonly="readonly"  style="cursor:pointer"/></td>
	         <td><s:property value="getZhangHaoId(#sd.zhanghaoId)"/></td>
	         <td>${sd.orderId}</td>
	   		 <td>${sd.time}</td>
	   		 <td>${sd.money}</td>		
	   		 <s:if test="#sd.leimuid == null">
	         		<td>
	         			${sd.yunshu}
	         		</td>
	         	</s:if>
	         <s:if test="#sd.leimuid != null">
	         	<td><s:property value="getSelLeiMu(#sd.leimuid)"/></td>
	         </s:if>
	         <td><s:property value="#sd.country"/></td>
	         <td>${sd.money}</td>
	   		 <td>${sd.remark}</td>
	   		 <td><s:property value="(#sd.caigouyuan==0 || #sd.caigouyuan==null)?('未分配'):(getUserId(#sd.caigouyuan))"/></td>
		     <td>
		     	<select name="selcgall" id="selcgall"> 
		     	<option value="0">-请选择-</option>
			      	<s:iterator value="getCaiGousd(usertype)" id = "i">
			      		<option value="${i.userid}">${i.name}</option>
			      	</s:iterator>
		      	</select>
		     </td>  
		     <td width="100"><a href="admin!wenTiRemark.do?ordertable.id=${sd.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}"> 修改备注 </a></td>
	     </tr>   
     </s:iterator> 
      <tr>     
         <td colspan="12" align="center">
	        总记录数：${pageBean.allRow} 共几页：${pageBean.allPage} 当前第 ${pageBean.currentPage} 页
        <pg:pager url="admin!getWenTiOrder.do" items="${pageBean.allRow}" export="currentPageNumber=pageNumber" maxPageItems="10"> 
 		 <pg:first> 
    		<a href="${pageUrl}&pageNumber=1&orderId=${param.orderId}&usertype=${param.usertype}">首页</a> 
  		 </pg:first> 
  	   <pg:prev> 
   		 <a href="${pageUrl}&pageNumber=${pageBean.currentPage-1}&orderId=${param.orderId}&usertype=${param.usertype}">上一页</a> 
  	   </pg:prev> 
  	 <pg:pages> 
    	<c:choose> 
      	  <c:when test="${currentPageNumber eq pageNumber}"> 
       		 <font color="red">${pageNumber}</font> 
      	  </c:when> 
       <c:otherwise> 
        <a href="${pageUrl}&pageNumber=${pageNumber}&orderId=${param.orderId}&usertype=${param.usertype}">${pageNumber}</a> 
      </c:otherwise> 
    </c:choose> 
  </pg:pages> 
  <pg:next> 
    <a href="${pageUrl}&pageNumber=${pageBean.currentPage+1}&orderId=${param.orderId}&usertype=${param.usertype}">下一页</a> 
  </pg:next> 
  <pg:last> 
    <a href="${pageUrl}&pageNumber=${pageBean.allPage}&orderId=${param.orderId}&usertype=${param.usertype}">尾页</a> 
  </pg:last>
  <pg:page> 
  <input type="text" name="pageNumber" id="pageNumber" size="5"/>
  <input type="button" onclick="tiaozhuan()" value="跳转"/>
  </pg:page>
	</pg:pager> 
	    </td>   		
    </tr>
  </table>
  </s:form>
    <s:iterator value="#str" var="d">
  	${d}<br>
  </s:iterator>
  <br>
</m:frame>