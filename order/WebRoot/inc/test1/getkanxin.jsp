<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>

<m:frame>
 <div>
  <table border="1" width="500px" cellspacing="0" style="float:left">
   <tr>
     <td width="54" align="right"><span class="STYLE2">发件人</span></td>
      <td><input name="fajianren" type="text" id="fajianren" size="50" title="发件人" value="${xieXinAll.fajianren}"></td>
	</tr>
	 <tr>
     <td width="54" align="right"><span class="STYLE2">主题</span></td>
      <td><input name="title" type="text" id="title" size="50" title="标题" value="${xieXinAll.title}"></td>
	</tr>
	 <tr>
     <td width="54" align="right"><span class="STYLE2">正文</span></td>
      <td><textarea rows="30" cols="60" name="neirong" id="neirong" title = "正文">${xieXinAll.neirong}</textarea></td>
	</tr>
  </table>
  <a href="test!rekx.do?pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}">返回</a>
  </div>
  <br>
</m:frame>