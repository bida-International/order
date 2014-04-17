<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<script type="text/javascript">  

</script>
<m:frame>
  <form action="admin!updateStock.do?kucuntable.id=${kuCunIds.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}"  method="post">
    <table id="myTable">  
		<tr><td align="center" colspan="2">修改库存</td></tr>
         <tr>
            <td width="30px">编码:</td>
        	<td><input type="text" name="kucuntable.coding" id="coding" title="编码" size="9" value="${kuCunIds.coding}"></td>
        	 </tr>
        	 <tr>
            <td  width="60px">库存数量:</td>
        	<td><input type="text" name="kucuntable.num" id="num" title="库存数量 " size="4" value="${kuCunIds.num}"></td>
        	</tr>
        	<tr>
        	<td  width="30px">总价:</td>
        	<td><input type="text" name="kucuntable.totalprice" id="totalprice" title="总价" size="4" value="${kuCunIds.totalprice}"></td>
        	</tr>
        	<tr>
        	<td  width="30px">单价:</td>
        	<td><input type="text" name="kucuntable.unitprice" id="unitprice" title="单价" size="4" value="${kuCunIds.unitprice}"></td>
        	</tr>
        	<tr>
        	<td width="30px">物品:</td>
        	<td><input type="text" name="kucuntable.wuping" id="wuping" title="物品" value="${kuCunIds.wuping}"></td>
        	</tr>
        	<tr>
        	<td width="50px">供运商:</td>
        	<td><input type="text" name="kucuntable.transportproviders" id="transportproviders" title="供运商" value="${kuCunIds.transportproviders}"></td>
        </tr> 
        <tr>
        	<td colspan="2" align="center"><input type="submit" id="upload" name="upload" value="修改" style="cursor: pointer;"/></td>
        </tr> 
    </table>      
  </form>
    <s:iterator value="#insert" var="d">
  	${d}<br>
  </s:iterator>
</m:frame>