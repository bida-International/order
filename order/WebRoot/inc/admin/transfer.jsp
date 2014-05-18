<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript" src="./inc/js/bianhao.js"></script>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<m:frame>
<script>
function as(){
var kefu = document.getElementById("kefu").value;
var kefu1 = document.getElementById("kefu1").value;
if(kefu == 0){
alert("请选择要转移数据的客服账号");
return false;
}
if(kefu1 == 0){
alert("请选择要接受数据的客服账号");
return false;
}
if(kefu == kefu1){
alert("客服账号不能一样的");
return false;
}
}

</script>
<form action="admin!updateTransfer.do" method="post" onsubmit = "return as()">
${msg}
  <table border="1"  class="datagrid2" id="list" width="500px" cellspacing="0" style="float:left">
    <tr>
      <td colspan="6" align="center"><strong>转移客服站内信和纠纷订单全部数据</strong></td>
    </tr>
    <tr>
    	<td>
    		把客服 :
    		<select name="kefu" id="kefu"> 
		     	<option value="0">-请选择-</option>
			      	<s:iterator value="getYeWu1All()" id = "i">
			      		<option value="${i.userid}"
			      			${i.userid==kefu?('selected="selected"'):('')}
			      		>${i.name}</option>
			      	</s:iterator>
		      	</select>账号里面的全部数据转移到：
    			<select name="kefu1" id="kefu1"> 
		     	<option value="0">-请选择-</option>
			      	<s:iterator value="getYeWu1All()" id = "i">
			      		<option value="${i.userid}"
			      		${i.userid==kefu1?('selected="selected"'):('')}
			      		>${i.name}</option>
			      	</s:iterator>
		      	</select>账号里面
		      	<input type="submit" onclick="confirm('确定转移?')" value="开始转移" style="cursor: pointer;">
    	</td>
    </tr>
  </table>
  <br>
 </form>
  <s:iterator value="#strsd" var="d">
  ${d}
  </s:iterator>
</m:frame>