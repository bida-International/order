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
  <form action="yewu!updateruzhang.do?ordertable.id=${updateIds.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}" method="post" id="myform" onsubmit="return as()">
   <input type="hidden" name="ordertable.caigouyuan" value="${updateIds.caigouyuan}"/>
  <table border="1" cellspacing="0" width="370px" style="float:left">
    <tr>
      <td colspan="9" align="center"><strong>修改订单</strong></td>
    </tr>
    <tr>
     <td width="70" align="right"><span class="STYLE2">订单号</span></td>
      <td><input name="ordertable.orderId" type="text" id="orderId" size="12" title="订单号" value="${updateIds.orderId}" readonly="readonly"></td>
     </tr>
       <tr>
      	<td width="100"  align="right"><span class="STYLE2">是否入账</span></td>
      	 <td>
      	 <s:if test="%{updateIds.ruzhang==0}">
      	 	<input name="ordertable.ruzhang" type="radio" id="ruzhang" style="ime-mode:disabled" value="0" checked="checked">否
      	 	<input name="ordertable.ruzhang" type="radio" id="ruzhang" style="ime-mode:disabled" value="1">是
      	 </s:if>
      	 <s:if test="%{updateIds.ruzhang==1}">
      	 	<input name="ordertable.ruzhang" type="radio" id="ruzhang" style="ime-mode:disabled" value="0">否
      	 	<input name="ordertable.ruzhang" type="radio" id="ruzhang" style="ime-mode:disabled" value="1" checked="checked">是
      	 </s:if>
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