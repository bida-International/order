<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript"  src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="./inc/js/bianhao.js"></script>
<m:frame>
<script type="text/javascript">
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
		alert("");
		var finish = document.getElementById("finish").value;
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
			window.location = "caigouadmin!oldOrder.do?finish="+finish+"&username="+username+"&pwd="+pwd+"";
		}
	}
	function myfahui(){
	 	var items=document.getElementsByTagName("input");
			var lujing = "caigouadmin!fanhuidaifahuo.do?pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
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
     		location.href = "caigouadmin!getWanChengOrder.do?pager.offset="+(pageNumber*10-10)+"&pageNumber="+pageNumber;
     }
     function page(pageNumber) 
	 {
		document.getElementById("pageNumber").value = pageNumber;
		document.getElementById("pager.offset").value = (pageNumber*10-10);
		document.forms.submit();
     }
</script>
${msg}
	<input type="button" name="finish" id="finish" style="cursor: hand" value="finish" onclick="myshow()">
	<div style="display:none" id="div1"> 
	<table width="500px">
		<tr>
			<td>账号:</td><td><input type="text" name="username" id="username"/></td>
			<td>密码:</td><td><input type="password" name="pwd" id="pwd"/></td>
			<td colspan="2"><input type="button" value="提交"  style="cursor: hand"  onclick="return as()"/><input type="button" value="取消" onclick="myshow()"/></td>
		</tr>
	</table>
	</div>
<form name="forms" id="forms" method="post" action="caigouadmin!getWanChengOrder.do" >
  <table border="1" width="1200px" cellspacing="0" style="float:left">
    <tr>
      <td colspan="20" align="center"><strong>完成订单</strong><input type="button" value="返回到得到订单" onclick="myfahui()"/></td>
    </tr>
      <tr>
    	<td colspan="20">
    	订单号 <input type="text" name="orderId" size="8" id="orderId" value="${param.orderId}"/>
	  	时间查询：<input type="text" name="time" id="time" onfocus="WdatePicker()" value="${param.time}"/> 至<input type="text" name="time1" id="time1"  onfocus="WdatePicker()" value="${param.time1}"/>
	  	采购时间查询：<input type="text" name="caigoutime" id="caigoutime" onfocus="WdatePicker()" value="${param.caigoutime}"/> 至<input type="text" name="caigoutime1" id="caigoutime1"  onfocus="WdatePicker()" value="${param.caigoutime1}"/>
	  	  产品编码: <input type="text" name="bianma" size="8" id="bianma" value="${param.bianma}"/>  
	  	  供应商: <input type="text" name="gongyunshang" size="8" id="gongyunshang" value="${param.gongyunshang}"/><br/>
	  	  物品: <input type="text" name="wuping" size="8" id="wuping" value="${param.wuping}"/>
  	  	<s:hidden name="pageNumber" value="1"></s:hidden>
		<s:hidden name="pager.offset" id="pager.offset" value="0"></s:hidden>
		<input type="hidden" value="${tit}" name="tit">  
	  	<input type="submit" value="查询"/>
	  	</td>
    </tr>
    <tr align="center">
     <td width="34" height="25">
		<input name="checkbox" onclick="quan()" type="checkbox" style="cursor:pointer"/></td>
      <td width="100"><span class="STYLE2">账号</span></td>
       <td width="100"><span class="STYLE2">订单号</span></td>
        <td width="100"><span class="STYLE2">产品编码 </span></td>
      <td width="100"><span class="STYLE2">上传时间</span></td>
      <td width="120"><span class="STYLE2">运输单号</span></td>
      <td width="100"><span class="STYLE2">金额(单位/$)</span></td>
       <td width="65"><span class="STYLE2">退款</span></td>
       <td width="80"><span class="STYLE2">类目</span></td>
       <td width="80"><span class="STYLE2">重量</span></td>
       <td width="80"><span class="STYLE2">供运商</span></td>
       <td width="80"><span class="STYLE2">物品</span></td>    
       <td width="80"><span class="STYLE2">备注</span></td>
      <td width="100"><span class="STYLE2">采购金额</span></td>
      <td width="100"><span class="STYLE2">运费</span></td>
      <td width="100"><span class="STYLE2">国内单号</span></td>
       <td width="100"><span class="STYLE2">采购时间</span></td>
      <td width="100"><span class="STYLE2">完成时间</span></td>
      <td width="100"><span class="STYLE2">纠纷时间</span></td>
       <td width="163"><span class="STYLE2">上传到采购时间差(天)</span></td>
      <td width="130"><span class="STYLE2">操作</span></td>
    </tr>
    <s:iterator value="pageBean.list" var="sd" >
	     <tr align="center">
	      <td><input name="chkItems" value="${sd.id}" onclick="chkItems_click(this)" type="checkbox"  id="chk_${sd.id}" readonly="readonly"  style="cursor:pointer"/></td>
	     <td><s:property value="getZhangHaoId(#sd.zhanghaoId)"/></td>
	          <td>${sd.orderId}</td>
	          <td>${sd.bianma}</td>
	   		 <td>${sd.time}</td>	    
	   		 <td><textarea rows="5" cols="10">${sd.danhao}</textarea></td>
	   		 <td>${sd.money}</td>
	   		 <td>${sd.tuikuan}</td>
	   		 <td>
	   		  <s:if test="#sd.leimuid == null">
	         			${sd.yunshu}
	         	</s:if>
	         <s:if test="#sd.leimuid != null">
	         	<s:property value="getSelLeiMu(#sd.leimuid)"/>
	         </s:if>
	         </td>
	         <td>${sd.zhongliang}</td>
	   		 <td><textarea rows="5" cols="10">${sd.gongyunshang}</textarea></td>
	   		  <td><textarea rows="5" cols="10">${sd.wuping}</textarea></td>
	   		 <td><textarea rows="5" cols="10">${sd.remark}</textarea></td>
	   		 <td>${sd.huokuan}</td>	
	   		 <td><fmt:formatNumber value="${sd.yunfei}" pattern="0.00"></fmt:formatNumber></td>
	   		 <td>${sd.guoneidanhao}</td> 
	   		 <td>${sd.caigoutime}</td>
	   		 <td>${sd.wanchengtime}</td>		       
	   		  <td>${sd.jiufentime}</td>
	   		  <td>
	   		   <s:if test="#sd.time==null || #sd.caigoutime == null">
		    </s:if>
		    <s:if test="#sd.time!=null && #sd.caigoutime != null">
		    	<s:property value="getTime(#sd.time,#sd.caigoutime)"/>
		    </s:if>
		    </td>
		     <td>
		     	<a href="caigouadmin!fanhui.do?ordertable.id=${sd.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}" onclick="return confirm('是否返回给管理员?')">返回</a>
		     	<a href="caigouadmin!upcgadminwenchengorder.do?ordertable.id=${sd.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}">修改订单</a> 
		     </td>	
	     </tr>   
     </s:iterator> 
      <tr>
   	   	<td colspan="19">
   		总金额($)：<s:property value="getCaiGouAdminAllMoney(orderId,time,time1)" />
   		纠纷总金额($):<s:property value="getJiuFenAllMoney(orderId,time,time1)"/>
   		 纠纷个数：<a href="caigouadmin!jiufen.do?orderId=${param.orderId}&time=${param.time}&time1=${param.time1}"><s:property value="getCaiGouAdminAllJiuFen(orderId,time,time1)"/></a>
   		 采购花费平均时间(小时)：<s:property value="getCgTime(orderId,time,time1,caigoutime,caigoutime1,bianma)"/>
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
  </form>
  ${msg}
  <br>
</m:frame>