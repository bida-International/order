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
  <form action="admin!addfq.do" method="post" id="myform">
  <table border="1" width="500px" cellspacing="0" style="float:left"  class="datagrid2" id="list">
    <tr>
      <td colspan="3" align="center"><strong>上传分区</strong></td>
    </tr>
    <tr align="center">
      <td width="120"><span class="STYLE2">区域</span></td>
      <td width="54"><span class="STYLE2">国家</span></td>
    </tr>
    <s:iterator begin="1" end="50" status="t">
      <tr align="center">
	    <td><input name="guojiaquyu" type="text" id="quyu" title="区域"/></td>
	    <td><input name="guojia" type="text" id="guojia" title="国家"/></td>	
     </tr>    
   </s:iterator>
    <tr>    
        <td colspan="3" align="center">
         <input type="submit" id="t" value=" 添 加 ">   
          </td>
    </tr>
  </table>
  <s:iterator value="#insert" var="d">
  	${d}<br>
  </s:iterator>
  <br>
  </form>
</m:frame>