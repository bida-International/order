<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>

<m:frame>
  <form action="guke!addAllOrder.do" method="post" id="myform" onsubmit="return as()">
  <table border="1" cellspacing="0" style="float:left">
    <tr>
      <td colspan="9" align="center"><strong>上传订单</strong></td>
    </tr>
    <tr align="center">
      <td width="70"><span class="STYLE2">名字</span></td>
       <td width="80"><span class="STYLE2">订单号</span></td>
      <td width="100"><span class="STYLE2">国内运输方式</span></td>
      <td width="120"><span class="STYLE2">国内运输单号(可以添加多个)</span></td>
     <td width="80"><span class="STYLE2">物品</span></td>
      <td width="80"><span class="STYLE2">地址</span></td>
       <td width="60"><span class="STYLE2">国家</span></td>
      <td width="100"><span class="STYLE2">国际运输方式</span></td>
      <td width="100"><span class="STYLE2">备注</span></td>
    </tr>
    <s:iterator begin="1" end="5" status="t">
      <tr align="center">
        <td><input name="name" size="10" type="text" id="name" title="名字" value="${logininfo.name}"></td>
        <td><input type="text" name="orderId" size="11" title="订单号"/></td>
        <td><select name="guoneikuaidi" id="guoneikuaidi" >
				<option value="">-请选择-</option>
				<s:iterator value="guoNeiKuaiDiAll" var="j">							
					<option value="${j.id}">${j.name}</option>
				</s:iterator>
			</select></td>	
        <td>
       
        	<input type="text" name="guoneidanhao" title="国内单号"/>
        </td>
	    <td><textarea cols="10" rows="5" name="wuping" title="物品 "></textarea> </td>	
	    <td><textarea cols="10" rows="5" name="guowaidizhi" id="guowaidizhi" title="国外地址"></textarea></td>
	    <td align="left">
	 	<select name="guojiaId" id="guojiaId">
	    		<option value="">-请选择-</option>
		    		<s:iterator value="getAllGuoJia()" var="i" status="j">	
		    			<option>
		    				<s:if test="#i.fenqu==#i.zimu">
			    				------------------${i.zimu}------------------
			    			</s:if>
		    			</option>
		    			<option value="${i.id}">${i.guojia}</option>
		    		</s:iterator>
	    	</select>
	    </td>
	    <td>
			<s:select list="loaditems('KuaiDiFangShi','name','id')"  name="kuaidifangshiId"></s:select>
		</td>
	    <td><textarea cols="10" rows="5" name="remark" title="备注"></textarea> </td>
     </tr>    
   </s:iterator>
    <tr>    
        <td colspan="9" align="center">
         <input type="submit" id="t" value=" 添 加 ">
          </td>
    </tr>
  </table>
  <s:iterator value="#insert" var="d">
  	${d}<br>
  </s:iterator>
  <br>
  </form>
</m:frame>