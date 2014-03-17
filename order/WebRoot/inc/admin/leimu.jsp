<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript" src="./inc/js/bianhao.js"></script>
<script type="text/javascript">
<!--

//-->
 function fanhuidaifahuo(){
	 	var items=document.getElementsByTagName("input"); 
	 	var flag=0;
	 	if(items!=null){
			for(var i=0;i<items.length;i++){
				if("chkItems"==items[i].name&&items[i].checked){
					flag=1;					
				}
			}
		}
		if(flag<1){
			alert("请选择要的订单！");
			return false;
		}else{
			var bool=confirm("确认吗?");
			if(bool){
			  var bulletinId="";
			  for(var i=0;i<items.length;i++){
				if("chkItems"==items[i].name&&items[i].checked){
				  bulletinId+=items[i].value+"-";
				}
			  }
			  location.href="admin!caigouadmin.do?bulletinId="+bulletinId+"&pageindex="+${pageindex};
			}else{
				return false;
			}
		}
	 }
	 function mytest(){
	 	var items=document.getElementsByTagName("input"); 
	 	var userid = document.getElementsByName("selcaigouyuan");
	    var lujing = "admin!fenpei_leimu.do?pageindex="+${pageindex};
	    fenpei(items,userid,lujing);
	 }
	 function quan()
	 {
		 var h=document.getElementsByName("checkbox");
		 var da=document.getElementsByName("chkItems");
		 if(h[0].checked==true)
		 {
		  for(i=0;i<da.length;i++)
		  {
		   da[i].checked=true;
		  }
		 }
		 else
		 {
		   	 for(i=0;i<da.length;i++)
			  {
			    da[i].checked=false;
			  }
	 	}
	  }
	  function page(){
	  	var pp = document.getElementById("pageindex").value;
	  	window.location = "admin!leimu.do?pageindex="+pp;
	  }
	  function myshow()
		{
			var usertype = document.getElementById("usertype").value;
			window.location = "admin!leimu.do?usertype="+usertype;
		}
</script>
<m:frame>
	<s:form action="admin!leimu.do" method="post" onsubmit="return as()">
	<table>
		<tr>
			<td>使用说明</td>
			<td>需要先分配类目给采购管理员,当采购管理员类目分配完之后,在把其它类目分配给采购</td>
		</tr>
	</table>
  <table border="1"  class="datagrid2" id="list" width="500px" cellspacing="0" style="float:left">
    <tr>
      <td colspan="6" align="center"><strong>全部类目</strong></td>
    </tr>
    <tr>
    <td colspan="6">
    	类目查询：<input type="text" name="leimu" id="leimu" value="${param.leimu}"/>
        <input type="submit" style="cursor:hand" value="查询"/><input type="button" value= "分配类目" style="cursor:hand" onclick="mytest()"/>
             请选择分配对象(<font color="blue" size="2">默认为采购</font>)：<select name="usertype" id="usertype" onchange="myshow()">
	       				<option value="0">-请选择-</option>
	       				<option value="1"
	       				${usertype==1?('selected="selected"'):('')}
	       				>采购</option>
	       				<option value="2"
	       				${usertype==2?('selected="selected"'):('')}
	       				>采购管理员</option>
	       			</select>
    </td>
    </tr>
   <tr align="center">
     <td width="34" height="25">
		<input name="checkbox" onclick="quan()" type="checkbox" style="cursor:pointer"/></td>
     <td width="100"><span class="STYLE2">类目</span></td>
      <td width="90"><span class="STYLE2">采购员</span></td>
    </tr>
    <s:iterator value="leiMuAll.pagelist" var="i">
      <tr align="center">
      <td><input name="chkItems" value="${i.id}" onclick="chkItems_click(this)" type="checkbox"  id="chk_${i.id}" readonly="readonly"  style="cursor:pointer"/></td>
       <td>${i.leimu}</td>
        <td>
	     	<select name="selcaigouyuan" id="selcaigou"> 
	     	<option value="0">-请选择-</option>
		      	<s:iterator value="getCaiGousd(usertype)" id = "i">
		      		<option value="${i.userid}">${i.name}</option>
		      	</s:iterator>
	      	</select>
		 </td>    
     </tr>    
   </s:iterator>
    <tr> <td colspan="6" align="center">
    		转到<select name="pageindex" id="pageindex" onchange="page()">
				<option value="0">-请选择-</option>
				<s:iterator begin="1" end="leiMuAll.pagecount" status="ix">
				<option value="${ix.index+1}">${ix.index+1}</option>
				</s:iterator>
				</select>页
       		 共${leiMuAll.count}条数据 共${leiMuAll.pagecount}页 第${leiMuAll.pageindex}页 <a href="admin!leimu.do?pageindex=1&leimu=${param.leimu}&usertype=${param.usertype}">首页</a>
            <s:if test="%{leiMuAll.pageindex>1}"> <a href="admin!leimu.do?pageindex=${leiMuAll.pageindex-1}&leimu=${param.leimu}&usertype=${param.usertype}">上一页 </a> </s:if>
            <s:if test="%{leiMuAll.pagecount>leiMuAll.pageindex}"> <a href="admin!leimu.do?pageindex=${leiMuAll.pageindex+1}&leimu=${param.leimu}&usertype=${param.usertype}">下一页</a> </s:if>
          <a href="admin!leimu.do?pageindex=${leiMuAll.pagecount}&leimu=${param.leimu}&usertype=${param.usertype}"> 尾页 </a></td></tr>
  </table>
  <br>
  </s:form>
  <s:iterator value="#strsd" var="d">
  ${d}
  </s:iterator>
</m:frame>