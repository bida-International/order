<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<script type="text/javascript">  
var num = 3;  
function addMoreFiles(){  
    //添加一行  
    var newTr = myTable.insertRow();  
    //添加两列  
    var newTd0 = newTr.insertCell();  
    var newTd1 = newTr.insertCell();  
    var newTd2 = newTr.insertCell();
    var newTd3 = newTr.insertCell();
    var newTd4 = newTr.insertCell();
    var newTd5 = newTr.insertCell();
    var newTd6 = newTr.insertCell();  
    var newTd7 = newTr.insertCell(); 
    var newTd8 = newTr.insertCell(); 
    var newTd9 = newTr.insertCell();
    var newTd10 = newTr.insertCell();
    var newTd11 = newTr.insertCell();
    var newTd12 = newTr.insertCell();      
    var newTd13 = newTr.insertCell();
    var newTd14 = newTr.insertCell();    
    //设置列内容和属性  
    newTd14.innerText = '图片';   
    newTd13.innerHTML= '<input type="file" name="uploadFile" value="" id="uploadFile"/>';  
    newTd12.innerHTML='<input type="button" name="delete" value="删除" style="cursor: pointer;" id="delete" onclick="delFile();"/>'
    newTd11.innerText = '编码';
    newTd10.innerHTML= '<input type="text" name="bianma" value="" id="bianma"  size="9"/>';
    newTd9.innerText = '库存数量';
    newTd8.innerHTML= '<input type="text" name="num" value="" id="num"  size="4"/>';
    newTd7.innerText = '总价';
    newTd6.innerHTML= '<input type="text" name="totalprice" value="" id="totalprice"  size="4"/>';
    newTd5.innerText = '单价';
    newTd4.innerHTML= '<input type="text" name="unitprice" value="" id="unitprice"  size="4"/>';
    newTd3.innerText = '物品';
    newTd2.innerHTML= '<input type="text" name="wuping" value="" id="wuping"/>';
    newTd1.innerText = '供运商';
    newTd0.innerHTML= '<input type="text" name="transportproviders" value="" id="transportproviders"/>';
    num++;  
}  
  
function delFile(){  
    var oRow=event.srcElement.parentNode.parentNode;  
    var oTable=oRow.parentNode.parentNode;  
    if(oTable.rows.length>1){  
        oTable.deleteRow(oRow.rowIndex);  
        if(oTable.rows.length==1)  
        {  
           oTable.all("delete").disabled=true;  
        }  
    }  
}  
function as(){
  window.location = "caigou!getStockOrder.do";
}
</script>
<m:frame>
  <form action="caigou!uploadInventoryOrders.do"  enctype="multipart/form-data" method="post">
    <table id="myTable">  
        <tr>  
            <td width="50px;">图片</td>  
            <td><input type="file" name="uploadFile" value="" id="file"/></td>  
            <td><input type="button" name="delete" value="删除" style="cursor: pointer;" id="delete" onclick="delFile();"/></td>
            <td width="30px">编码:</td>
        	<td><input type="text" name="bianma" id="bianma" title="编码" size="9"></td>
            <td  width="60px">库存数量:</td>
        	<td><input type="text" name="num" id="num" title="库存数量 " size="4"></td>
        	<td  width="30px">总价:</td>
        	<td><input type="text" name="totalprice" id="totalprice" title="总价" size="4"></td>
        	<td  width="30px">单价:</td>
        	<td><input type="text" name="unitprice" id="unitprice" title="单价" size="4"></td>
        	<td width="30px">物品:</td>
        	<td><input type="text" name="wuping" id="wuping" title="物品"></td>
        	<td width="50px">供运商:</td>
        	<td><input type="text" name="transportproviders" id="transportproviders" title="供运商"></td>
        </tr>  
        <tr>
			<td colspan="15" style="color:#ff0000;">
					注：
					<ol>
						<li>上传图片时,图片名称要改一下</li>
						<li>图片名称不能是一样的</li>
						<li>每个字段都必须填写</li>
					</ol>
				</td>
			</tr>
    </table>  
    <input type="submit" id="upload" name="upload" value="上传" style="cursor: pointer;"/>  
    <input type="button" value="上传更多" style="cursor: pointer;" onclick="addMoreFiles();">  
    <input type="button" value="返回" onclick="as()" style="cursor: pointer;">
  </form>
    <s:iterator value="#insert" var="d">
  	${d}<br>
  </s:iterator>
</m:frame>