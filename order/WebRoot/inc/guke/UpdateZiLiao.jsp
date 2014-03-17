<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<m:frame>
${msg}
  <form action="guke!updateZiLiao.do?user.id=${logininfo.userId}&guKeTable.id=${selGuKe.id}" method="post" id="myform" onsubmit="return as()">
	
  <table border="1" cellspacing="0"  style="float:left">
    <tr>
      <td colspan="8" align="center"><strong>修改个人信息</strong></td>
    </tr>
    <tr align="center">
      <td width="163"><span class="STYLE2">账号</span></td>
    </tr>
      <tr align="center">
        <td><input name="user.username" value="${logininfo.account}"></td>         
     </tr>
      <tr align="center">
      <td width="163"><span class="STYLE2">密码</span></td>
    </tr>
      <tr align="center">
        <td><input name="user.pwd" value="${logininfo.pwd}"></td>         
     </tr>
      <tr align="center">
      <td width="163"><span class="STYLE2">手机</span></td>
    </tr>
      <tr align="center">
        <td><input name="guKeTable.phone" value="${selGuKe.phone}"></td>         
     </tr>
      <tr align="center">
      <td width="163"><span class="STYLE2">姓名</span></td>
    </tr>
      <tr align="center">
        <td><input name="guKeTable.name" value="${selGuKe.name}"></td>         
     </tr>
      <tr align="center">
      <td width="163"><span class="STYLE2">QQ</span></td>
    </tr>
      <tr align="center">
        <td><input name="guKeTable.gukeQQ" value="${selGuKe.gukeQQ}"></td>         
     </tr>
         
    <tr>    
        <td colspan="8" align="center">
         <input type="submit"  value=" 修改 ">             
          </td>
    </tr>
  </table>
  <br>
  </form>
</m:frame>