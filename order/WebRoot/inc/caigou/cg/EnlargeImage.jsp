<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript"  src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="./inc/js/bianhao.js"></script>
<script src="jquery-1.8.2.js" type="text/javascript"></script>
<script type="text/javascript">
	 function quan()
	 {
		 var h=document.getElementsByName("checkbox");
		 var da=document.getElementsByName("chkItems");
		 quan1(h,da);
	 }
     function page(pageNumber) 
	 {
		document.getElementById("pageNumber").value = pageNumber;
		document.getElementById("pager.offset").value = (pageNumber*10-10);
		document.forms.submit();
	 }

</script>
<m:frame>

<form  action="caigou!getStockOrder.do" method="post" onsubmit="return as()" id="forms" name="forms">
	
  <table border="1"  width="700px" cellspacing="0" style="float:left" >
	     <tr align="center" style="font-size:30px">		
			 <td>${sd.wuping}</td>  	    
	     </tr>   

  </table>
  </form>
</m:frame>