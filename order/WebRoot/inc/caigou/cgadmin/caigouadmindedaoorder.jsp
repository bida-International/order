<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="./inc/js/bianhao.js"></script>
<script type="text/javascript"  src="My97DatePicker/WdatePicker.js"></script>
<m:frame>
<script>
	function wancheng(){
	var zhanghaoId = document.getElementById("zhanghaoId").value;
	var category = document.getElementById("category").value;
		var items=document.getElementsByTagName("input"); 
	     var lujing = "caigouadmin!updedaos.do?category="+category+"&zhanghaoId="+zhanghaoId+"&pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
	     bianhao(items,lujing);
	}	
	 function quan()
	 {
		 var h=document.getElementsByName("checkbox");
		 var da=document.getElementsByName("chkItems");
		 quan1(h,da);
	  }
	 function myshow()
	{
		var value = document.getElementById("div1").style.display;
		if(value=="none")
		{
			document.getElementById("div1").style.display="block";
				
		}
		else{
			document.getElementById("div1").style.display="none";
		}
	}
	function as(){
		var username = document.getElementById("username").value;
		var pwd = document.getElementById("pwd").value;
		if(username == ''){
			alert("请输入账号");
			return false;
		}
		if(pwd == ''){
			alert("请输入密码");
			return false;
		}
		if(confirm("确定？")){
			window.location = "caigouadmin!oldUnFinishedOrder.do?username="+username+"&pwd="+pwd+"";
		}
	}
		function daochu(){
		var zhanghaoId = document.getElementById("zhanghaoId").value;
		var category = document.getElementById("category").value;
		 	var items=document.getElementsByTagName("input");
		    var lujing = "caigouadmin!xuanzhe.do?category="+category+"&zhanghaoId="+zhanghaoId+"&pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
		    bianhao(items,lujing);
	 	}
	  function mysumaitong(){
	  var zhanghaoId = document.getElementById("zhanghaoId").value;
	  var category = document.getElementById("category").value;
	 	var items=document.getElementsByTagName("input");
	    var lujing = "caigouadmin!adminsumaitong.do?category="+category+"&zhanghaoId="+zhanghaoId+"&pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
		bianhao(items,lujing);	
	 }
	 function daifa(){
	 var zhanghaoId = document.getElementById("zhanghaoId").value;
	 var category = document.getElementById("category").value;
	 	var items=document.getElementsByTagName("input");
		var lujing = "caigouadmin!daifa.do?category="+category+"&zhanghaoId="+zhanghaoId+"&pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
		bianhao(items,lujing);
	 }
	 function daifaqu(){
	 var zhanghaoId = document.getElementById("zhanghaoId").value;
	 var category = document.getElementById("category").value;
	 	var items=document.getElementsByTagName("input");
	    var lujing = "caigouadmin!daifangqu.do?category="+category+"&zhanghaoId="+zhanghaoId+"&pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
		bianhao(items,lujing);		
	 }
     function page(){
	  	var pp = document.getElementById("pageindex").value;
	  	window.location = "caigouadmin!getCaiGouAdminDeDaoOrder.do?pageindex="+pp;
	  }
	    function tiaozhuan(){
	    	var orderId = document.getElementById("orderId").value;
	    	var time = document.getElementById("time").value;
	    	var time1 = document.getElementById("time1").value;
	    	var category = document.getElementById("category").value;
    		var pageNumber = document.getElementById("pageNumber").value;
    		location.href = "caigouadmin!getCaiGouAdminDeDaoOrder.do?pager.offset="+(pageNumber*10-10)+"&pageNumber="+pageNumber+"&orderId="+orderId+"&time="+time+"&time1="+time1+"&category="+category;
     }
     function getPayment(){
    	 var zhanghaoId = document.getElementById("zhanghaoId").value;
    	 var category = document.getElementById("category").value;
		var items=document.getElementsByTagName("input"); 
	     var lujing = "caigouadmin!getPayment.do?category="+category+"&zhanghaoId="+zhanghaoId+"&pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
	     bianhao(items,lujing);
     }
      function page(pageNumber) 
	 {
		document.getElementById("pageNumber").value = pageNumber;
		document.getElementById("pager.offset").value = (pageNumber*10-10);
		document.forms.submit();
	 }
</script>
<input type="button" name="finish" id="finish" style="cursor: hand" value="Unfinished" onclick="myshow()">
	<div style="display:none" id="div1"> 
	<table width="500px"  class="datagrid2" id="list">
		<tr>
			<td>账号:</td><td><input type="text" name="username" id="username"/></td>
			<td>密码:</td><td><input type="password" name="pwd" id="pwd"/></td>
			<td colspan="2"><input type="button" value="提交"  style="cursor: hand"  onclick="return as()"/><input type="button" value="取消" onclick="myshow()"/></td>
		</tr>
	</table>
	</div>
<form  action="caigouadmin!getCaiGouAdminDeDaoOrder.do" method="post" name="forms" id="forms">
    <table border="1" width="1200px"  class="datagrid2" id="list">
		<tr>
			<td><span style="color:red">传给仓库:</span></td>
			<td>
				<input type="button" style="cursor: pointer;" value="把库存订单全部传给仓库"  onclick="daifaqu()"/>  
				<!-- <input type="button" style="cursor:pointer" value="待发货 " onClick="wancheng()"> -->
			</td>
			<td><span style="color:red">采购导出:</span></td>
			<td>
				 <input type="button" style="cursor:pointer" onclick="daochu()" value="选择导出订单"/>
			</td>
			<td><span style="color:red">传给财务:</span></td>
			<td> <input type="button" style="cursor:pointer" onclick="getPayment()" value="给财务付款订单"/></td>
			</tr>
			<tr>
			<td><span style="color:red">传给业务:</span></td>
			<td> <input type="button" style="cursor:pointer" value="将代发产品传给业务" onClick="daifa()"></td>
			
			<td><span style="color:red">速卖通下单:</span></td>
			<td> <input type="button" style="cursor:pointer" value="速卖通下单审核" onclick="mysumaitong()"/></td>
			</tr>
			
	</table>
  <table border="1" width="1180" cellspacing="0" style="float:left">
    <tr>
      <td colspan="14" align="center"><strong>采购管理员得到订单</strong> 
      <a href="caigouadmin!adminweiwancheng.do">导出全部未完成订单</a>
     </td>
    </tr>
      <tr>
    	<td colspan="14" align="center">
	  	订单号 <input type="text" name="orderId" id="orderId" value="${param.orderId}"/>
	  	时间：<input type="text" name="time" id="time" onfocus="WdatePicker()" value="${param.time}"/>至<input type="text" name="time1" id="time1"  onfocus="WdatePicker()" value="${param.time1}"/>
	    	类目查询：
    		<select name="category" id="category" title="类目">
	    		<option value="">-请选择 -</option>
	    		<s:iterator value="getAllLeiMu()" id="i">
	    			<option value="${i.id}"
	    			${i.id==category?('selected="selected"'):('')}
	    			>${i.leimu}</option>
	    		</s:iterator>
	    	</select>
	  	账号查询: 	
	  	<select name="zhanghaoId" id="zhanghaoId"> 
	      	<option value="">-请选择-</option>
	      	<s:iterator value="getZhangHao()" id = "i">
	      	<option value="${i.id}"
	      	${i.id==zhanghaoId?('selected="selected"'):('')}
	      	>${i.name}</option>
	      </s:iterator>
	      </select> 	
	      	<s:hidden name="pageNumber" value="1"></s:hidden>
			<s:hidden name="pager.offset" id="pager.offset" value="0"></s:hidden>
			<input type="hidden" value="${tit}" name="tit">    
	  		<input type="submit" value="查询"/>
	  	</td>
    </tr>
    <tr align="center">
       <td width="34" height="25">
		<input name="checkbox" onclick="quan()" type="checkbox" style="cursor:pointer"/></td>
        <td width="54"><span class="STYLE2">账号</span></td>
       <td width="90"><span class="STYLE2">订单号</span></td>
        <td width="90"><span class="STYLE2">产品编码</span></td>
        <td width="90"><span class="STYLE2">国家</span></td>
     <td width="163"><span class="STYLE2">类目</span></td>
      <td width="130"><span class="STYLE2">采购金额</span></td>
      <td width="130"><span class="STYLE2">订单金额</span></td>
       <td width="100"><span class="STYLE2">供运商</span></td>
        <td width="100"><span class="STYLE2">备注</span></td>
        <td width="100"><span class="STYLE2">完成情况</span></td>
      <td width="163"><span class="STYLE2">操作</span></td>
        <td width="100"><span class="STYLE2">运输单号</span></td>
       <td width="100"><span class="STYLE2">上传时间</span></td>
    </tr>
    <s:iterator value="pageBean.list" var="sd" >
	     <tr align="center" style="cursor:pointer">
	     <td><input name="chkItems" value="${sd.id}" onclick="chkItems_click(this)" type="checkbox"  id="chk_${sd.id}" readonly="readonly"  style="cursor:pointer"/></td>
	         <td><s:property value="getZhangHaoId(#sd.zhanghaoId)"/></td>
	       <s:if test="#sd.jingji==2">
	     	<td><font color="blue">${sd.orderId}</font></td>
	     </s:if>
	     	<s:if test="#sd.jingji==1">
	     		<td><font color="red">${sd.orderId}</font></td>
	     	</s:if>
	     	<s:if test="#sd.jingji!=1 && #sd.jingji!=2">
	          <td>${sd.orderId}</td>
	         </s:if>
	         <s:if test="#sd.fawan == 2">
	         <font color="#00cc00">${sd.orderId}</font> 
	         </s:if>
	         <td>
          		 <s:if test="getCoding(#sd.bianma) != null">
	          		<font color="red">${sd.bianma}</font>
	          	</s:if>
	          	<s:if test="getCoding(#sd.bianma) == null">
	          		${sd.bianma}
	          	</s:if>
			</td>
	   		<td><s:property value="#sd.country"/></td>
	   		<td>
	         	<s:if test="#sd.leimuid == null">
	         		${sd.yunshu}
	         	</s:if>
	         <s:if test="#sd.leimuid != null">
	         	<s:property value="getSelLeiMu(#sd.leimuid)"/>
	         </s:if>
      		 </td>
	   		 <td>${sd.huokuan}</td>	  
	   		 <td>${sd.money}</td>
	   		 <td><textarea rows="5" cols="10">${sd.gongyunshang}</textarea></td>     
	   		 <td><textarea rows="5" cols="10">${sd.remark}</textarea></td> 
	   		  <td>${(sd.wancheng==0 || sd.wancheng==null)?('未完成'):('已完成')}</td>       
		     <td>
		     	<a href="caigouadmin!fanhuiorder.do?ordertable.id=${sd.id}&category=${param.category}&zhanghaoId=${param.zhanghaoId}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}" onclick="confirm('确定？')">返回</a>
		     	<a href="caigouadmin!caigouadminuporder.do?ordertable.id=${sd.id}&category=${param.category}&zhanghaoId=${param.zhanghaoId}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}">修改订单</a>
		     </td>
		     <td><textarea rows="5" cols="10">${sd.danhao}</textarea></td>
		     <td>${sd.time}</td>		
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
  ${msg}
  </form>
  <s:iterator value="#strsd" var="d">
  	${d}<br/>
  </s:iterator>
  <s:iterator value="#str" var="ss">
  	${ss}
  </s:iterator>
  <br>
</m:frame>