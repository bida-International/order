<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
 <<script type="text/javascript">
<!--

//-->
function as(){
	var zhanghao = document.getElementById("zhanghao").value;
	if(zhanghao == '')
	{
		alert("请输入账号");
		return false;	
	}
}
</script>
<m:frame>
${msg}
  <form action="admin!addzhanghao.do" method="post" id="myform" onsubmit="return as()">

  <table border="1"  class="datagrid2" id="list" cellspacing="0"  style="float:left">
    <tr>
      <td colspan="8" align="center"><strong>添加账号</strong></td>
    </tr>
    <tr align="center">
      <td width="163"><span class="STYLE2">账号</span></td>
    </tr>
      <tr align="center">
        <td><input name="zhname" type="text" id="zhanghao" title="账号"></td>         
     </tr>    
    <tr>    
        <td colspan="8" align="center">
         <input type="submit"  value=" 添 加 ">
         <input type="reset"  value="重置">               
         <input type="button" value="取消" onClick="location=''">     
          </td>
    </tr>
  </table>
  <br>
  </form>
</m:frame>