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

  <form action="admin!updatedhgate.do?zhanghaoId=${zhangId.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}" method="post" id="myform" onsubmit="return as()">
	
  <table border="1"  class="datagrid2" id="list" cellspacing="0"  style="float:left">
    <tr>
      <td colspan="8" align="center"><strong>修改账号</strong></td>
    </tr>
    <tr align="center">
      <td width="163"><span class="STYLE2">账号</span></td>
    </tr>
      <tr align="center">
        <td><input name="name1" value="${zhangId.name}"></td>         
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