<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
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
  <form action="caigouadmin!cgupdateorder.do?ordertable.id=${cgUpOrderId.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}" method="post" id="myform" onsubmit="return as()">
  <input type="hidden" name="ordertable.caigouyuan" value="${cgUpOrderId.caigouyuan}"/>
  <table border="1" cellspacing="0" width="250px" style="float:left">
    <tr>
      <td colspan="5" align="center"><strong>修改订单</strong></td>
    </tr>
    <tr>
     <td width="54" align="right"><span class="STYLE2">订单号</span></td>
      <td><input name="ordertable.orderId" type="text" id="orderId" size="12" title="订单号" value="${cgUpOrderId.orderId}" readonly="readonly"></td>
	</tr>
	 <tr>
     <td width="54" align="right"><span class="STYLE2">运输单号</span></td>
      <td><input name="ordertable.danhao" type="text" id="danhao" size="12" title="运输单号" value="${cgUpOrderId.danhao}"></td>
	</tr>
	 <tr>
     <td width="54" align="right"><span class="STYLE2">货款</span></td>
      <td><input name="ordertable.huokuan" type="text" id="huokuan" size="12" title="货款" value="${cgUpOrderId.huokuan}"></td>
	</tr>
	<tr>
     <td width="54" align="right"><span class="STYLE2">供运商</span></td>
      <td><textarea name="ordertable.gongyunshang" id="gongyunshang" title="供运商">${cgUpOrderId.gongyunshang}</textarea></td>
	</tr>
	 <tr>
     <td width="54" align="right"><span class="STYLE2">运费</span></td>
      <td><input name="ordertable.yunfei" type="text" id="yunfei" size="12" title="运费" value="${cgUpOrderId.yunfei}"></td>
	</tr>
    <tr>    
        <td colspan="5" align="center">
         <input type="submit" value="保存 ">   
          </td>
    </tr>
  </table>
  <br>
  </form>
</m:frame>