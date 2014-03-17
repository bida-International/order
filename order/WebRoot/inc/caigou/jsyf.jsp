<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<m:frame>
<s:form action="test!cgjsyf.do">
<table>
	<tr>
		<td colspan="2">
			目的地： 	
			<select name="guojia" id="guojia">
	    		<option value="">-请选择-</option>
		    		<s:iterator value="getAllGuoJia()" var="i" status="j">	
		    			<option>
		    				<s:if test="#i.fenqu==#i.zimu">
			    				------------------${i.zimu}------------------
			    			</s:if>
		    			</option>
		    			<option value="${i.guojia}">${i.guojia}</option>
		    		</s:iterator>
	    	</select>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			重量：<input type="text" name="zl" size="10"/>KG
		</td>
	</tr>
	<tr>
	 <td>体积：</td>
	 <td>长<input name="c" size="5">CM宽<input name="k" size="5">CM高<input name="g" size="5">CM</td>
	</tr>
	<tr>
		<td><input type="submit" value="计算" style="cursor: pointer;"> </td>
	</tr>
</table>
<table width="1000px">
<tr>
<td>
<h>说明</h><br/>
DHL:只能查询20KG以内<BR/>
Fedex:只能查询20KG以内<BR/>
水陆小包:限重2KG<BR/>
空路小包:限重2KG<BR/>

EMS:限重30KG<BR/>
中国邮政:限重2KG<BR/>
如果输入国家和重量或体积没有查询出对应的运费，那就表示此快递没有到这个国家，也有可能输入重量大于限重
</td>
</tr>
</table>
 <table border="1" width="1100" cellspacing="0" style="float:left">
    <tr>
      <td colspan="12" align="center"><strong>显示运费</strong> </td>
    </tr>
    <tr align="center">
        <td width="54"><span class="STYLE2">快递</span></td>
       <td width="90"><span class="STYLE2">运费</span></td>
    </tr>

    <s:iterator value="getDhlAll()" var="sd" >
	     <tr align="center" style="cursor:pointer">
		     <td>DHL</td>
		     <td><fmt:formatNumber value="${dd}" pattern="0.00"></fmt:formatNumber></td>		
	     </tr>   
     </s:iterator> 
 
      <s:iterator value="getFedExIeAllGbj()" var="s" >
	     <tr align="center" style="cursor:pointer">
		     <td>联邦ie</td>
		     <td><fmt:formatNumber value="${edd}" pattern="0.00"></fmt:formatNumber></td>		
	     </tr>   
     </s:iterator>

      <s:iterator value="getFedExIpGbj()" var="s" >
	     <tr align="center" style="cursor:pointer">
		      <td>联邦ip</td>
		     <td><fmt:formatNumber value="${pdd}" pattern="0.00"></fmt:formatNumber></td>		
	     </tr>   
     </s:iterator>
 
      <s:iterator value="getGjsl()" var="s" >
	     <tr align="center" style="cursor:pointer">
		     <td>国际水陆</td>
		     <td><fmt:formatNumber value="${s.zifei+8}" pattern="0.00"></fmt:formatNumber></td>		
	     </tr>   
     </s:iterator>
     <s:iterator value="getHkdb()" var="s" >
	     <tr align="center" style="cursor:pointer">
		    <td>航空大包</td>
		     <td><fmt:formatNumber value="${hkdb}" pattern="0.00"></fmt:formatNumber></td>		
	     </tr>   
     </s:iterator>
     <s:iterator value="gethydb()" var="s">
 		<tr align="center" style="cursor:pointer">
		     <td>海运大包 </td>
		     <td><fmt:formatNumber value="${hydb}" pattern="0.00"></fmt:formatNumber></td>		
	     </tr>
	</s:iterator>
     <s:iterator value="getKygbj()" var="s" >
	     <tr align="center" style="cursor:pointer">
		     <td>空运水陆小包</td>
		     <td><fmt:formatNumber value="${s.zifei+8}" pattern="0.00"></fmt:formatNumber></td>		
	     </tr>   
     </s:iterator>

	
      <s:iterator value="getSALdbs()" var="s" >
	     <tr align="center" style="cursor:pointer">
		     <td>SAL</td>
		     <td><fmt:formatNumber value="${sal}" pattern="0.00"></fmt:formatNumber></td>		
	     </tr>   
     </s:iterator>
  
      <s:iterator value="getEmsGbj()" var="s" >
	     <tr align="center" style="cursor:pointer">
		    <td>EMS</td>
		     <td><fmt:formatNumber value="${ss}" pattern="0.00"></fmt:formatNumber></td>		
	     </tr>        
     </s:iterator>
     <s:iterator value="getYzxb()" var="s">
      <tr align="center" style="cursor:pointer">
      	<td>邮政小包</td>
      	<td><fmt:formatNumber value="${aa}" pattern="0.00"></fmt:formatNumber></td>
      </tr>
     </s:iterator>
  </table>
</s:form>
</m:frame>