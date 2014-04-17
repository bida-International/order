<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
 <%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<script type="text/javascript"  src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="./inc/js/bianhao.js"></script>
<script type="text/javascript" src="jquery-1.8.2.js"></script>
<m:frame>
	<script type="text/javascript">
	
   //导入已经完成
 	function wcorder(){
	 	var items=document.getElementsByName("chkItems"); 
	 	var lujing = "admin!upWcOrder.do?pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
	 	 bianhao(items,lujing);
	 }	
	  //全选
	 function quan()
	 {
		 var h=document.getElementsByName("checkbox");
		 var da=document.getElementsByName("chkItems");
		 quan1(h,da);
	  }
	
	   function fanhuidaifahuo(){
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
			  location.href="admin!fhdaifahuo.do?bulletinId="+bulletinId+"&pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
			}else{
				return false;
			}
		}
	 }
     //返回待放区
	  function returndaifangqu(){
	  	var items=document.getElementsByTagName("input");
		var lujing = "admin!redaifangqu.do?pageNumber="+${pageNumber}+"&pager.offset="+${pageNumber*10-10};
		bianhao(items,lujing);
	  }
		 function page(pageNumber) 
		 {
			document.getElementById("pageNumber").value = pageNumber;
			document.getElementById("pager.offset").value = (pageNumber*10-10);
			document.forms.submit();
		 }
		 function myshow()
		{
			var usertype = document.getElementById("usertype").value;
			window.location = "admin!getFpAll.do?usertype="+usertype;
		}
	</script>
	
	<div style="WIDTH: 1150px; HEIGHT: 430px;overflow: auto;font: 12px Verdana;color:#000;background-color:#FFF;border: 1px dotted #999;
scrollbar-face-color: #CBDDF1;
scrollbar-highlight-color: #547CBB;
scrollbar-shadow-color: #547CBB;
scrollbar-3dlight-color: #ffffff;
scrollbar-arrow-color:  #547CBB;
scrollbar-track-color: #FFFFFF;
scrollbar-darkshadow-color: #FFFFFF;"> 
	
	 <form action="admin!getAllOrder.do"  name="forms" id="forms" method="post" onsubmit="return as()">
	 <table  align="left"  cellspacing="0" cellpadding="0">
	 	<tr>
	 		<td>
 			    <input type="button" value="已经完成" onclick="wcorder()">
 			</td>
	    	<td>
		      	<input type="button" value= "返回采购得到订单" onclick="fanhuidaifahuo()"/>
	      	</td>
	      	<td>
		        <input type="button" value= "返回给仓库" onclick="returndaifangqu()"/>
	 		</td>
	 	</tr>
	 </table>
  <table border="1"  cellspacing="0" cellpadding="0" align="center" width="1500px" cellspacing="0">
    <tr>
      <td colspan="19" align="center"><strong>显示全部订单</strong>
    
         <s:hidden name="pageNumber" value="1"></s:hidden>
		<s:hidden name="pager.offset" id="pager.offset" value="0"></s:hidden>
		<input type="hidden" value="${tit}" name="tit"> 
      </td>
    </tr>
    <tr>
    <td colspan="19">
    	订单号：<input type="text" name="orderId" id="orderId" value="${param.orderId}" size="9"/>
    	时间：<input type="text" name="time" id="time" onfocus="WdatePicker()" value="${param.time}" size="9"/>至<input type="text" name="time1" id="time1"  onfocus="WdatePicker()" value="${param.time1}" size="9"/>
    	dhgate账号查询：
    	<select name="dhgatezhanghao" id="dhgatezhanghao"> 
	      	<option value="">-请选择-</option>
	      	<s:iterator value="getZhangHao()" id = "i">
	      	<option value="${i.id}"
	      	${i.id==dhgatezhanghao?('selected="selected"'):('')}
	      	>${i.name}</option>
	      </s:iterator>
	      </select>  
	     
    	国际单号查询：<input type="text" name="danhao" id="danhao" value="${param.danhao}" size="9"/>
    	速卖通采购：<input type="radio" name="sumaitong" id="sumaitong" value="0">未完成 <input type="radio" name="sumaitong" id="sumaitong" value="1">完成
    	编码:<input type="text" name="bianma" id="bianma" value="${param.bianma}" size="9"/>
    	<br/>类目查询：
    		<select name="category" id="category" title="类目">
	    		<option value="">-请选择 -</option>
	    		<s:iterator value="getAllLeiMu()" id="i">
	    			<option value="${i.id}"
	    					${i.id==category?('selected="selected"'):('')}
	    			>${i.leimu}</option>
	    		</s:iterator>
	    	</select>
	    	国家查询:
	    	<select name="country" id="country" title="国家">
	    		<option value="">-请选择 -</option>
	    		<s:iterator value="getAllGuoJia()" id="i">
	    			<option value="${i.guojia}"
	    					${i.guojia==country?('selected="selected"'):('')}
	    			>${i.guojia}</option>
	    		</s:iterator>
	    	</select>
    	<input type="submit" value="查询"/>
    </td>
    </tr>
    <tr align="center">
      <td width="34" height="25">
		<input name="checkbox" onclick="quan()" type="checkbox" style="cursor:pointer"/></td>
     <td width="163"><span class="STYLE2">日期</span></td>
     <td width="100"><span class="STYLE2">订单号</span></td>
      <td width="100"><span class="STYLE2">编码</span></td>
      <td width="100"><span class="STYLE2">金额($)</span></td>
       <td width="100"><span class="STYLE2">类目</span></td>
       <td width="100"><span class="STYLE2">运输单号</span></td>
       <td width="63"><span class="STYLE2">运费</span></td>
      <td width="54"><span class="STYLE2">货款</span></td>
      <td width="100"><span class="STYLE2">供运商</span></td> 
      <td width="100"><span class="STYLE2">退款</span></td>  
      <td width="64"><span class="STYLE2"> 汇率</span></td>
       <td width="64"><span class="STYLE2">利润</span></td>
       <td width="80"><span class="STYLE2">纠纷</span></td>
       <td width="80"><span class="STYLE2">是否处理</span></td>
       <td width="80"><span class="STYLE2">账号</span></td>
       <td width="80"><span class="STYLE2">备注</span></td>
      <td width="80"><span class="STYLE2">采购员</span></td>
      <td width="300"><span class="STYLE2">操作</span></td>
    </tr>
    <s:iterator value="pageBean.list" var="i">
      <tr align="center">
      <td><input name="chkItems" value="${i.id}" onclick="chkItems_click(this)" type="checkbox"  id="chk_${i.id}" readonly="readonly"  style="cursor:pointer"/></td>
        <td>${i.time}</td>
        <td><a href="admin!reAllOrders.do?ordertable.id=${i.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}">${i.orderId}</a></td>
        <td>${i.bianma}</td>
	    <td>${i.money}</td>	 
        <td><textarea rows="5" cols="10"><s:if test="#i.leimuid == null">${i.yunshu}</s:if><s:if test="#i.leimuid != null"><s:property value="getSelLeiMu(#i.leimuid)"/></s:if></textarea></td>
	    <td><textarea rows="5" cols="10">${i.danhao}</textarea></td>
	    <td><fmt:formatNumber value="${i.yunfei}" pattern="0.00"></fmt:formatNumber></td>	  	 
	    <td>${i.huokuan}</td>   	 
	    <td><textarea rows="5" cols="10">${i.gongyunshang}</textarea></td>  
	    <td>${i.tuikuan}</td>
	    <td> ${i.huilv}</td>	    
	    <s:if test="#i.tuikuan==null || #i.tuikuan==0">
	    	<td><fmt:formatNumber value="${i.money*i.huilv-(i.huokuan+i.yunfei)}" pattern="0.00"></fmt:formatNumber></td>
	    </s:if>
	    <s:if test="#i.tuikuan!=null && #i.tuikuan!=0">
	    <td><fmt:formatNumber value="${i.money*i.huilv-(i.huokuan+i.yunfei+i.tuikuan*i.huilv)}"  pattern="0.00"></fmt:formatNumber></td>
	    </s:if>
	     <td>${(i.jiufen==0 || i.jiufen == null)?('否'):('是')}</td>
	     <td>
	     	<s:if test="#i.jiufen==1 && (#i.chuli == 0 || #i.chuli == null)">
	     		否
	     	</s:if>
	     	<s:if test="#i.jiufen==1 && #i.chuli == 1">
	     		是
	     	</s:if>		
	     </td>
	     <td><s:property value="getZhangHaoId(#i.zhanghaoId)"/></td>
	     <td><textarea rows="5" cols="10">${i.remark}</textarea></td>
	   	 <td><a href="admin!getCaiGouAll.do?caigouyuan=${i.caigouyuan}"><s:property value="#i.caigouyuan==0?('未分配'):(getUserId(#i.caigouyuan))"/></a></td>	

	   	 <td>
		
	   	 <a href = "admin!delorder.do?ordertable.id=${i.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}" onclick="return confirm('是否删除?')">删除</a><br/>
	   	 /<a href="admin!uporder.do?ordertable.id=${i.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}">修改订单号</a><br/>
	   	 <s:if test="#i.jingji==1">
	   	 	<font color="red">紧急订单(已经被点击)</font><br/>
	   	 </s:if>
	   	 <s:if test="#i.jingji !=1">
	   	 	/<a href="admin!upjinji.do?ordertable.id=${i.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}" onclick="return confirm('确定?')">紧急订单</a><br/>
	   	 </s:if>
	   	 /<a href="admin!getorderId.do?ordertable.id=${i.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}">修改</a><br/>
	  
	   	 </td>      
     </tr>    
   </s:iterator>
 <tr>
   	<td colspan="19">
   
   		总金额($)：<s:property value="getMoney(orderId,time,time1,dhgatezhanghao,danhao,sumaitong,bianma,category,country)"></s:property>
   		总运费：<s:property value="getYunFei(orderId,time,time1,dhgatezhanghao,danhao,sumaitong,bianma,category,country)"/>
   		总采购货款：<s:property value="getHuoKuan(orderId,time,time1,dhgatezhanghao,danhao,sumaitong,bianma,category,country)"/>
   		退款总额($)：<s:property value="getTuiKuan(orderId,time,time1,dhgatezhanghao,danhao,sumaitong,bianma,category,country)"/><br>
   		纠纷个数：<a href="admin!jiufen.do?orderId=${param.orderId}&time=${param.time}&time1=${param.time1}"><s:property value="getJiuFen(orderId,time,time1,dhgatezhanghao,danhao,sumaitong,bianma,category,country)"/></a>
   		运费为空：<a href="admin!getYunFeiNull.do?orderId=${param.orderId}&time=${param.time}&time1=${param.time1}"><s:property value="getYunFeiNullNum(orderId,time,time1,dhgatezhanghao,danhao,sumaitong,bianma,category,country)"/></a>
   		货款为空：<a href="admin!huokuannull.do?orderId=${param.orderId}&time=${param.time}&time1=${param.time1}"><s:property value="getHuoKuanNullNum(orderId,time,time1,dhgatezhanghao,danhao,sumaitong,bianma,category,country)"/></a>
   		纠纷总额($): <s:property value="getCaiGouAllJiuFenMoney(orderId,time,time1,dhgatezhanghao,danhao,sumaitong,bianma,category,country)"/>
   		<a href="admin!adminlirun.do?orderId=${param.orderId}&time=${param.time}&time1=${param.time1}">利润小于0</a> /<a href="admin!liruninterval.do?orderId=${param.orderId}&time=${param.time}&time1=${param.time1}">利润大于0小于30</a>
   		总利润:<s:property value="getZongLiRun(orderId,time,time1,dhgatezhanghao,danhao,sumaitong,bianma,category,country)"/>
   		未入账：<a href="admin!weiruzhang.do?orderId=${param.orderId}&time=${param.time}&time1=${param.time1}&dhgatezhanghao=${param.dhgatezhanghao}&danhao=${param.danhao}"><s:property value="getRuZhang(orderId,time,time1,dhgatezhanghao,danhao,sumaitong,bianma,category,country)"/></a>
   		平均采购速度(小时)<s:property value="getCaiGouTimed(orderId,time,time1,dhgatezhanghao,danhao,sumaitong,bianma,category,country)"/>
  
   		<br>
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
	</div>
  <s:iterator value="#str" var="ss">
  ${ss}
  </s:iterator>
  <br> 
</m:frame>