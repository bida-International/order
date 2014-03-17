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

  <form action="admin!upyunfei.do?yunfei.id=${yunFeiAll.id}" method="post" id="myform" onsubmit="return as()">
  <table border="1" width="300px" cellspacing="0"  class="datagrid2" id="list" style="float:left">
    <tr>
      <td colspan="4" align="center"><strong>修改订单</strong></td>
    </tr>
    <tr>
     <td width="54" align="right"><span class="STYLE2">区域</span></td>
      <td><input name="yunfei.quyu" type="text" id="quyu" size="12" title="quyu" value="${yunFeiAll.quyu}"></td>
	</tr>
	 <tr>
     <td width="54" align="right"><span class="STYLE2">国家</span></td>
      <td><input name="yunfei.guojia" type="text" id="guojia" size="12" title="国家" value="${yunFeiAll.guojia}"></td>
	</tr>
	 <tr>
     <td width="54" align="right"><span class="STYLE2">金额</span></td>
      <td><input name="yunfei.money" type="text" id="money" size="12" title="金额" value="${yunFeiAll.money}"></td>
	</tr>
    <tr>    
        <td colspan="8" align="center">
         <input type="submit" value="保存 ">   
          </td>
    </tr>
  </table>
  
  <br>
  </form>
</m:frame>