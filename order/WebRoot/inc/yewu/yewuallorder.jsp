<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
  <script type="text/javascript" src="js/jquery-1.8.2.js"></script>
  <script type="text/javascript"  src="My97DatePicker/WdatePicker.js"></script>
  <script type="text/javascript">
<!--

//-->
function as(){
	var re = document.getElementById("gongyunshang").value;
	var order = document.getElementById("orderId").value;
	if(re != '' && order != '')
	{
		alert("供运商和订单号不能同时查询");
		return false;
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
<form action="yewu!getDeDaoOrderAll.do" method="post"  name="forms" id="forms" onsubmit="return as()">
  <table border="1" width="1143px" cellspacing="0" style="float:left">
    <tr>
      <td colspan="17" align="center"><strong>显示全部订单</strong></td>
    </tr>
    <tr>
    <td colspan="17">
    	账号查询：	
    		<select name="selzhanghao" id="selzhanghao"> 
	      	<option value="">-请选择-</option>
	      	<s:iterator value="getZhangHao()" id = "i">
	      	<option value="${i.id}"
	      	${i.id==selzhanghao?('selected="selected"'):('')}
	      	>${i.name}</option>
	      </s:iterator>
	      </select>  
    	运输单号:<input type="text" name="danhao" id="danhao" value="${param.danhao}"/>
    		时间：<input type="text" name="time" id="time" onfocus="WdatePicker()" value="${param.time}" size="9"/>至<input type="text" name="time1" id="time1"  onfocus="WdatePicker()" value="${param.time1}" size="9"/>
    	供运商:<input type="text" name="gongyunshang" id="gongyunshang" value="${param.gongyunshang}"/>订单号：<input type="text" name="orderId" id="orderId" value="${param.orderId}"/>
	<input type="submit" value="查询 "/>
  	<s:hidden name="pageNumber" value="1"></s:hidden>
	<s:hidden name="pager.offset" id="pager.offset" value="0"></s:hidden>
	<input type="hidden" value="${tit}" name="tit">  
    </td>
    </tr>
    <tr align="center">
     <td width="120"><span class="STYLE2">日期</span></td>
      <td width="100"><span class="STYLE2">订单号</span></td>
      <td width="100"><span class="STYLE2">编码</span></td>
      <td width="54"><span class="STYLE2">金额</span></td>
       <td width="80"><span class="STYLE2">类目</span></td>
      <td width="163"><span class="STYLE2">运输单号</span></td>
       <td width="63"><span class="STYLE2">运费</span></td>
      <td width="54"><span class="STYLE2">货款</span></td>
      <td width="80"><span class="STYLE2">供运商</span></td>
       <td width="80"><span class="STYLE2">速卖通地址</span></td>
      <td width="64"><span class="STYLE2">退款</span></td>
       <td width="64"><span class="STYLE2">汇率</span></td>
       <td width="80"><span class="STYLE2">备注</span></td>
       <td width="120"><span class="STYLE2">账号</span></td>
       <td width="80"><span class="STYLE2">纠纷时间</span></td>
      <td width="80"><span class="STYLE2">采购员</span></td>
        <td width="80"><span class="STYLE2">操作</span></td>
    </tr>
    <s:iterator value="pageBean.list" var="i">
      <tr align="center">
        <td>${i.time}</td>
        <td>${i.orderId}</td>
         <td>${i.bianma}</td>
	    <td>${i.money}</td>	 
	   <s:if test="#i.leimuid == null">
        	<td>
        		 <textarea rows="5" cols="10">${i.yunshu}</textarea>
        	</td>
        </s:if>
        <s:if test="#i.leimuid != null">
        	<td><textarea rows="5" cols="10"><s:property value="getSelLeiMu(#i.leimuid)"/>${i.danhao}</textarea></td>
        </s:if>
	    <td><textarea rows="5" cols="10">${i.danhao}</textarea></td>
	    <td><fmt:formatNumber value="${i.yunfei}" pattern="0.00"></fmt:formatNumber></td>	 
	    <td>${i.huokuan}</td> 
	    <td><textarea rows="5" cols="10">${i.gongyunshang}</textarea></td>
	    <td><textarea rows="5" cols="10">${i.guojia}</textarea></td>	    
	    <td>${i.tuikuan}</td>
	    <td>${i.huilv}</td>
	     <td><textarea rows="5" cols="10">${i.remark}</textarea></td> 
	    <td>
	     	<s:property value="getZhangHaoId(#i.zhanghaoId)"/>
	    </td>
	    <td>${i.jiufentime}</td>
	   	 <td><s:property value="(#i.caigouyuan==0 || #i.caigouyuan==null)?('未分配'):(getUserId(#i.caigouyuan))"/></td>
	   	 <td><a href="yewu!getorderIdAll.do?ordertable.id=${i.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}">修改</a></td>	      
     </tr>    
   </s:iterator>
     <tr>     
         <td colspan="17" align="center">
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
  <br>
  </form>
</m:frame>