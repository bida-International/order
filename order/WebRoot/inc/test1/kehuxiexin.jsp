<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<m:frame>
<s:form action="test!addXin.do" method="post">
  <table border="1" width="500px" cellspacing="0" style="float:left">
    <tr>
     <td width="200" align="right"><span class="STYLE2">收件人(账号名)</span></td>
      <td width="300"><input type="text" name="zhanghao" id="zhanghao"/></td>
	</tr>
	 <tr>
     <td width="200" align="right"><span class="STYLE2">主题</span></td>
      <td width="300"><input name="title" type="text" id="title" size="50" title="标题"></td>
	</tr>
	 <tr>
     <td width="200" align="right"><span class="STYLE2">正文</span></td>
      <td width="300"><textarea rows="20" cols="57" name="neirong" id="neirong" title = "正文"></textarea></td>
	</tr>
    <tr>    
        <td colspan="2" align="center">
         <input type="submit" style="cursor:hand" value="保存 ">   
          </td>
    </tr>
  </table>
  </s:form>
  ${msg}
  <br>
</m:frame>