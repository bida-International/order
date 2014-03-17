<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<script type="text/javascript">

 	function myshow()
	{
		var usertype = document.getElementById("usertype").value;
		window.location = "admin!xiexin.do?usertype="+usertype;
	}
	function usertype()
	{
		var usertype = document.getElementById("usertype").value;
		var userid = document.getElementById("userid").value;
		var title = document.getElementById("title").value;
		var neirong = document.getElementById("neirong").value;
		window.location = "admin!addXieXin.do?usertype="+usertype+"&userid="+userid+"&title="+title+"&neirong="+neirong;
	}
</script>
<m:frame>
 <div>
  <table border="1"  class="datagrid2" id="list" width="500px" cellspacing="0" style="float:left">
  <tr>
  	<td width="150">请选择要发送的角色:</td>
  	<td> <select name="usertype" id="usertype" onchange="myshow()">
 	<option value="0">-请选择-</option>
 	<option value="1"
 	${usertype==1?('selected="selected"'):('')}
 	>业务</option>
 	<option value="2" ${usertype==2?('selected="selected"'):('')}>采购</option>
 	<option value="3" ${usertype==3?('selected="selected"'):('')}>仓库</option>
 	<option value="4" ${usertype==4?('selected="selected"'):('')}>财务</option>
 	<option value="5" ${usertype==5?('selected="selected"'):('')}>客户</option>
 	<option value="6" ${usertype==6?('selected="selected"'):('')}>采购管理员</option>
 	<option value="7" ${usertype==7?('selected="selected"'):('')}>全部客户</option>
 	<option value="8" ${usertype==8?('selected="selected"'):('')}>全部员工</option>
 	<option value="9" ${usertype==9?('selected="selected"'):('')}>全部客户和员工</option>
 </select></td>
  </tr>
    <tr>
     <td width="54" align="right"><span class="STYLE2">收件人</span></td>
      <td>
		 <select name="userid" id="userid">
		 	<option>-请选择-</option>
		 	<s:iterator value="getShouJianRen(usertype)" var="i">
		 	<option value="${i.userid}">${i.name}</option>
		 	</s:iterator>
		 </select>
	  </td>
	</tr>
	 <tr>
     <td width="54" align="right"><span class="STYLE2">主题</span></td>
      <td><input name="title" type="text" id="title" size="50" title="标题"></td>
	</tr>
	 <tr>
     <td width="54" align="right"><span class="STYLE2">正文</span></td>
      <td><textarea rows="20" cols="50" name="neirong" id="neirong" title = "正文"></textarea></td>
	</tr>
    <tr>    
        <td colspan="8" align="center">
         <input type="button" onclick="usertype()" style="cursor:hand" value="保存 ">   
          </td>
    </tr>
  </table>
  ${msg}
  </div>
  <br>
</m:frame>