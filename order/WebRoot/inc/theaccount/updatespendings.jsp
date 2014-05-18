<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<script type="text/javascript"  src="My97DatePicker/WdatePicker.js"></script>
  <script type="text/javascript" src="js/jquery-1.8.2.js"></script>
	<script type="text/javascript">
	function onlyNumber(obj){
   
      //得到第一个字符是否为负号
      var t = obj.value.charAt(0);  
      //先把非数字的都替换掉，除了数字和. 
      obj.value = obj.value.replace(/[^\d\.]/g,'');   
       //必须保证第一个为数字而不是.   
       obj.value = obj.value.replace(/^\./g,'');   
       //保证只有出现一个.而没有多个.   
       obj.value = obj.value.replace(/\.{2,}/g,'.');   
       //保证.只出现一次，而不能出现两次以上   
       obj.value = obj.value.replace('.','$#$').replace(/\./g,'').replace('$#$','.');      
}	

	</script>
<m:frame>
${msg}
  <form action="theaccount!updateSpendings.do?spendingtable.id=${spending.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}" method="post" id="myform" onsubmit="return as()">
 
  <table border="1" width="500px" cellspacing="0" style="float:left">
    <tr>
      <td colspan="9" align="center"><strong>修改支出</strong></td>
    </tr>
    <tr>
     <td width="70" align="right"><span class="STYLE2">支付项目</span></td>
      <td><input name="spendingtable.paymentproject" type="text" id="paymentproject" size="12" title="支付项目" value="${spending.paymentproject}"/></td>
     </tr>
     <tr>
     	<td width="70" align="right"><span class="STYLE2">金额</span></td>
      	<td><input name="spendingtable.money" type="text" id="money" size="12" title="金额" value="${spending.money}"/></td>
     </tr>
     <tr>
     	<td width="70" align="right"><span class="STYLE2">时间</span></td>
      	<td><input name="spendingtable.time" type="text" id="time" size="12" title="时间" value="${spending.time}"/></td>
     </tr>
      <tr>
     	<td width="70" align="right"><span class="STYLE2">备注</span></td>
      	<td><input name="spendingtable.remark" type="text" id="remark" size="12" title="备注" value="${spending.remark}"/></td>
     </tr>
	<tr>
        <td colspan="9" align="center">
         <input type="submit" id="t" value="保存 ">   
          </td>
    </tr>
  </table>
  <br>
  </form>
</m:frame>