<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<script type="text/javascript"  src="My97DatePicker/WdatePicker.js"></script>
<m:frame>
	<s:form action="caigou!sumaitong.do" theme="simple">
  <table border="1" width="1000px"  class="datagrid2" id="list" cellspacing="0" style="float:left">
    <tr>
      <td colspan="9" align="center"><strong>速卖通</strong></td>
    </tr>
    <tr>
    	<td colspan="10" align="center">
    	查询页数<input type="text" size="5" name="pageindex"/>
	  	时间查询：<input type="text" name="time" id="time" onfocus="WdatePicker()" value="${param.time}"/>订单号 <input type="text" name="orderId" id="orderId" value="${param.orderId}"/>
	  	<input type="submit" value="查询"/>
	  	</td>
    </tr>
    <tr align="center">
     <td width="54"><span class="STYLE2">账号</span></td>
       <td width="100"><span class="STYLE2">订单号</span></td>  
      <td width="54"><span class="STYLE2">货款</span></td>
       <td width="54"><span class="STYLE2">供运商</span></td>
        <td width="54"><span class="STYLE2">备注</span></td>
        <td width="100"><span class="STYLE2">操作</span></td>
       <td width="163"><span class="STYLE2">运输单号</span></td>
      <td width="100"><span class="STYLE2">上传时间</span></td>
    </tr>
    <s:iterator value="deDaoSuMaiTong.pagelist" var="sd">
	     <tr align="center" style="font-size:30px">
	     <td>
	     	<s:property value="getZhangHaoId(#sd.zhanghaoId)"/>
	     </td>
	          <td>${sd.orderId}</td>	
	   		 <td>${sd.huokuan}</td>	  
	   		<td><textarea>${sd.gongyunshang}</textarea></td>     
	   		 <td><textarea>${sd.remark}</textarea></td> 
	   		 <td>
	   		 <a href="caigou!upUnfinished.do?ordertable.id=${sd.id}" onclick="return confirm('是否返回给管理员?')">未完成</a>
		     	/<a href="caigou!updateSuMaitong.do?ordertable.id=${sd.id}&pageindex=${pageindex}">修改订单</a> 
	   		 </td>
		     <td>${sd.danhao}</td>
		     <td>${sd.time}</td>	    
	     </tr>   
     </s:iterator> 
     <tr> 
     	<td colspan="9" align="center">
       		 共${deDaoSuMaiTong.count}条数据 共${deDaoSuMaiTong.pagecount}页 第${deDaoSuMaiTong.pageindex}页 <a href="caigou!sumaitong.do?pageindex=1&orderId=${param.orderId}&time=${param.time}">首页</a>
            <s:if test="%{deDaoSuMaiTong.pageindex>1}"> <a href="caigou!sumaitong.do?pageindex=${deDaoSuMaiTong.pageindex-1}&orderId=${param.orderId}&time=${param.time}">上一页 </a> </s:if>         
            <s:if test="%{deDaoSuMaiTong.pagecount>deDaoSuMaiTong.pageindex}"> <a href="caigou!sumaitong.do?pageindex=${deDaoSuMaiTong.pageindex+1}&orderId=${param.orderId}&time=${param.time}">下一页</a> </s:if>
          <a href="caigou!sumaitong.do?pageindex=${deDaoSuMaiTong.pagecount}&orderId=${param.orderId}&time=${param.time}"> 尾页 </a>
        </td>
     </tr>
  </table>
  ${msg}
  </s:form>
  <br>
</m:frame>