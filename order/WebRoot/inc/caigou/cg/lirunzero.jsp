<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript"  src="My97DatePicker/WdatePicker.js"></script>
<m:frame>
	<script type="text/javascript">
			
		function as()
		{
			var orderId = document.getElementById("orderId").value;
			var time = document.getElementById("time").value
			var time1 = document.getElementById("time1").value;
			var zhanghao = document.getElementById("dhgatezhanghao").value;
			if(zhanghao != '' && orderId != ''){
				alert("账号和订单号不能同时查询");
				return false;
			}
			if(orderId != '' && (time !='' && time1 !=''))
			{
				alert("订单号和时间不能同时不为空");
				return false;
			}
			if(time != '' && time1 == ''){
				alert("查询时间未选");
				return false;
			}
			if(time == '' && time1 != '')
			{
				alert("查询时间未选");
				return false;
			}
		}
	  function page(){
	  	var pp = document.getElementById("pageindex").value;
	  	window.location = "caigou!lirunzero.do?pageindex="+pp;
	  }
	</script>
	<s:form action="caigou!lirunzero.do" cssStyle="cellspacing:0;cellpadding:0" onsubmit="return as()">
  <table border="1"  class="datagrid2" id="list" width="1143px" cellspacing="0" style="float:left">
    <tr>
      <td colspan="16" align="center"><strong>订单利润小于0</strong></td>
    </tr>
    <tr>
    <td colspan="16">
    	
	   	订单号：<input type="text" name="orderId" id="orderId" value="${param.orderId}" size="10"/>
	   	时间：<input type="text" name="time" id="time" onfocus="WdatePicker()" value="${param.time}"/>至<input type="text" name="time1" id="time1"  onfocus="WdatePicker()" value="${param.time1}"/>
	   	dhgate账号查询：
	   	  <select name="zhanghaoId"> 
	      	<option value="">-请选择-</option>
	      	<s:iterator value="getZhangHao()" id = "i">
	      	<option value="${i.id}">${i.name}</option>
	        </s:iterator>
	      </select> 
	   	<input type="submit" value="查询"/>
    </td>
    </tr>
    <tr align="center">
     <td width="163"><span class="STYLE2">订单号</span></td>
      <td width="90"><span class="STYLE2">日期</span></td>
      <td width="100"><span class="STYLE2">金额($)</span></td>
       <td width="163"><span class="STYLE2">类目</span></td>
      <td width="163"><span class="STYLE2">运输单号</span></td>
       <td width="63"><span class="STYLE2">运费</span></td>
      <td width="54"><span class="STYLE2">货款</span></td>
       <td width="163"><span class="STYLE2">速卖通地址</span></td>
      <td width="120"><span class="STYLE2">供运商</span></td>
      <td width="64"><span class="STYLE2">退款($)</span></td>
       <td width="64"><span class="STYLE2">汇率</span></td>
       <td width="160"><span class="STYLE2">备注</span></td>
       <td width="160"><span class="STYLE2">是否纠纷</span></td>
       <td width="120"><span class="STYLE2">账号</span></td>
      <td width="163"><span class="STYLE2">采购员</span></td>
      <td width="163"><span class="STYLE2">利润</span></td>
    </tr>
    <s:iterator value="liRunZero.pagelist" var="i">
      <tr align="center">
       <td>${i.orderId}</td>
       <td>${i.time}</td>
       
	    <td>${i.money}</td>	 
	    <td>
	    	 <s:if test="#i.leimuid == null">
	         		<td>
	         			${i.yunshu}
	         		</td>
	         	</s:if>
	         <s:if test="#i.leimuid != null">
	         	<td><s:property value="getSelLeiMu(#i.leimuid)"/></td>
	         </s:if>
	    </td>  
	    <td><textarea rows="5" cols="10">${i.danhao}</textarea></td>
	    <td><fmt:formatNumber value="${i.yunfei}" pattern="0.00"></fmt:formatNumber></td>	 
	    <td>${i.huokuan}</td> 
	    <td><textarea rows="5" cols="10">${i.guojia}</textarea></td>	 
	    <td><textarea rows="5" cols="10">${i.gongyunshang}</textarea></td>       
	    <td>${i.tuikuan}</td>
	    <td>${i.huilv}</td>
	     <td><textarea rows="5" cols="10">${i.remark}</textarea></td>
	     <td>${(i.jiufen==0 || i.jiufen==null)?('否'):('是')}</td>
	    <td>
	     	<s:property value="getZhangHaoId(#i.zhanghaoId)"/>
	    </td>
	   	 <td><s:property value="(#i.caigouyuan==0 || #i.caigouyuan==null)?('未分配'):(getUserId(#i.caigouyuan))"/></td>	
	   	 <td><fmt:formatNumber value="${i.money*i.huilv-(i.huokuan+i.yunfei+i.tuikuan*i.huilv)}" pattern="0.00"></fmt:formatNumber> </td>
     </tr>    
   </s:iterator>
    <tr> <td colspan="16" align="center">
   			  转到<select name="pageindex" id="pageindex" onchange="page()">
				<option value="0">-请选择-</option>
				<s:iterator begin="1" end="liRunZero.pagecount" status="ix">
				<option value="${ix.index+1}">${ix.index+1}</option>
				</s:iterator>
				</select>页
       		 共${liRunZero.count}条数据 共${liRunZero.pagecount}页 第${liRunZero.pageindex}页 <a href="caigou!lirunzero.do?pageindex=1&orderId=${param.orderId}&time=${param.time}&time1=${param.time1}&zhanghaoId=${param.zhanghaoId}">首页</a>
            <s:if test="%{liRunZero.pageindex>1}"> <a href="caigou!lirunzero.do?pageindex=${liRunZero.pageindex-1}&orderId=${param.orderId}&time=${param.time}&time1=${param.time1}&zhanghaoId=${param.zhanghaoId}">上一页 </a> </s:if>
            <s:if test="%{liRunZero.pagecount>liRunZero.pageindex}"> <a href="caigou!lirunzero.do?pageindex=${liRunZero.pageindex+1}&orderId=${param.orderId}&time=${param.time}&time1=${param.time1}&zhanghaoId=${param.zhanghaoId}">下一页</a> </s:if>
          <a href="caigou!lirunzero.do?pageindex=${liRunZero.pagecount}&orderId=${param.orderId}&time=${param.time}&time1=${param.time1}&zhanghaoId=${param.zhanghaoId}"> 尾页 </a></td></tr>
  </table>
  </s:form>
  <br>
</m:frame>