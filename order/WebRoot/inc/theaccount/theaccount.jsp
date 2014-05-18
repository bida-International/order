<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<script type="text/javascript"  src="My97DatePicker/WdatePicker.js"></script>
<m:frame>
<style type="text/css">
<!--

.aa {
	border: 1px solid #CCCCCC;	
}
-->
</style>
<script>
	function chk(t){		
		var shouru = document.getElementById("shouru");
		var zhichu = document.getElementById("zhichu");		
		if("收入" == t.value){
			shouru.style.display="block";
			zhichu.style.display="none";
		}else{
			shouru.style.display="none";
			zhichu.style.display="block";
		}
	}
</script>
	
		<s:form action="theaccount!insert.do" name="myform" theme="simple">	
	<table width="606" height="30" border="0" cellpadding="0" cellspacing="0">
				
		    <tr>
			    <table>
			    	<tr>
					    <td width="109" height="45" align="right">内帐：</td>
					    <td width="326" align="left" scope="col"><s:radio list="{'收入','支出'}" name="rad" value="'收入'" onclick="chk(this)"></s:radio> </td>
					</tr>
				</table>
		    </tr>
		    		   
		   <div id="shouru">
		   <table class="aa">
		   <tr>
			    <td width="109" height="45" align="right">提款日期：</td>
			    <td width="326" align="left" scope="col"><s:textfield name="incometable.withdrawaldate" id="withdrawaldate"  onfocus="WdatePicker()"></s:textfield> </td>
		    </tr>		   	
		    <tr>
			    <td width="109" height="45" align="right">金额：</td>
			    <td width="326" align="left" scope="col"><s:textfield name="incometable.money" id="money"></s:textfield> </td>
		    </tr>
		     <tr>
			    <td width="109" height="45" align="right">汇率：</td>
			    <td width="326" align="left" scope="col">		    
			   <s:textfield name="incometable.huilv" id="huilv"></s:textfield>
			   </td>
		    </tr>
		    <tr>
			    <td width="109" height="45" align="right">备注：</td>
			    <td width="326" align="left" scope="col"><s:textfield name="incometable.remark" id="remark"></s:textfield> </td>
		    </tr>
		    <tr>
			    <td height="45">&nbsp;</td>
			    <td><s:submit value="保存"></s:submit>${msg}</td>
		    </tr>
		    </table>
		    </div> 
	</table>
	  </s:form>
	  
	<div id="zhichu" style="display: none">
	<s:form action="theaccount!save.do" name="myform" theme="simple">			 
		   <table class="aa">
		   <tr>
			    <td width="109" height="45" align="right">支出项目：</td>
			    <td width="326" align="left" scope="col"><s:textfield name="spendingtable.paymentproject" id="paymentproject"></s:textfield> </td>
		    </tr>
		    <tr>
			    <td width="109" height="45" align="right">金额：</td>
			    <td width="326" align="left" scope="col"><s:textfield name="spendingtable.money" id="money"></s:textfield> </td>
		    </tr>
		    <tr>
			    <td width="109" height="45" align="right">备注：</td>
			    <td width="326" align="left" scope="col"><s:textfield name="spendingtable.remark" id="remark"></s:textfield> </td>
		    </tr>
		    <tr>
			    <td height="45">&nbsp;</td>
			    <td><s:submit value="保存"></s:submit></td>
		    </tr>
		    </table></s:form>
		    </div>
</m:frame>