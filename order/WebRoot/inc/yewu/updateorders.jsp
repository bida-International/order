<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<script type="text/javascript"  src="My97DatePicker/WdatePicker.js"></script>
  <script type="text/javascript" src="js/jquery-1.8.2.js"></script>
	<script type="text/javascript">
	function onlyNumber(obj){
   
      //得到第一个字符是否为负号
      var t = obj.value.charAt(0);  
      //先把非数字的都替换掉，除了数字和. 
      obj.value = obj.value.replace(/[^\d\.]/g,'');   
       //必须保证第一个为数字而不是.   
       obj.value = obj.value.replace(/^\./g,'');   
       //保证只有出现一个.而没有多个.   
       obj.value = obj.value.replace(/\.{2,}/g,'.');   
       //保证.只出现一次，而不能出现两次以上   
       obj.value = obj.value.replace('.','$#$').replace(/\./g,'').replace('$#$','.');      
}	

	</script>
<m:frame>
${msg}
  <form action="yewu!updatedingdanAll.do?ordertable.id=${updateIds.id}&pageNumber=${pageNumber}&pager.offset=${pageNumber*10-10}" method="post" id="myform" onsubmit="return as()">
   <input type="hidden" name="ordertable.caigouyuan" value="${updateIds.caigouyuan}"/>
  <table border="1" width="500px" cellspacing="0" style="float:left">
    <tr>
      <td colspan="9" align="center"><strong>修改订单</strong></td>
    </tr>
    <tr>
     <td width="70" align="right"><span class="STYLE2">订单号</span></td>
      <td><input name="ordertable.orderId" type="text" id="orderId" size="12" title="订单号" value="${updateIds.orderId}" readonly="readonly"></td>
      <td width="100" align="right"><span class="STYLE2">类目</span></td>
      <td><input name="ordertable.yunshu" type="text" id="yunshu" title="类目"  value="${updateIds.yunshu}"></td>
     </tr>
     <tr>
      <td width="64"  align="right"><span class="STYLE2">运费</span></td>
       <td><input name="ordertable.yunfei" type="text" id="yunfei" size="7" title="运费" style="ime-mode:disabled" value="${updateIds.yunfei}"></td>
       <td width="54"  align="right"><span class="STYLE2">速卖通</span></td>
       <td><input name="ordertable.guojia" type="text" id="guojia" title="速卖通"  value="${updateIds.guojia}"></td>
     </tr>
      <tr>
      <td width="54"  align="right"><span class="STYLE2">备注</span></td>
       <td><textarea name="ordertable.remark">${updateIds.remark}</textarea></td>
       <td width="54"  align="right"><span class="STYLE2">汇率</span></td>
       <td><input name="ordertable.huilv" type="text" id="huilv" size="7" title="汇率" style="ime-mode:disabled" value="${updateIds.huilv}"></td>
      </tr>
      
      <tr>
       <td width="54"  align="right"><span class="STYLE2">退款</span></td>
      <td><input name="ordertable.tuikuan" type="text" id="tuikuan" size="7" title="退款" style="ime-mode:disabled" value="${updateIds.tuikuan}"></td>
      <td width="54"  align="right"><span class="STYLE2">供运商</span></td>
      <td><input name="ordertable.gongyunshang" type="text" id="gongyunshang" size="7" title="供运商" style="ime-mode:disabled" value="${updateIds.gongyunshang}"></td>
      </tr>
      <tr>
      <td width="100"  align="right"><span class="STYLE2">是否上网</span></td>
      <td>
      <s:if test="%{updateIds.shangwang==0 || updateIds.shangwang == null}">
      	<input name="ordertable.shangwang" type="radio" id="shangwang" style="ime-mode:disabled" value="0" checked="checked">否
      	<input name="ordertable.shangwang" type="radio" id="shangwang" style="ime-mode:disabled" value="1">是
      </s:if>
      <s:if test="%{updateIds.shangwang==1}">
      	<input name="ordertable.shangwang" type="radio" id="shangwang" style="ime-mode:disabled" value="0">否
      	<input name="ordertable.shangwang" type="radio" id="shangwang" style="ime-mode:disabled" value="1" checked="checked">是
      </s:if>
      </td>
      	<td width="100"  align="right"><span class="STYLE2">是否签收</span></td>
      	<td>
      	<s:if test="%{updateIds.qianshou==0 || updateIds.qianshou == null}">
      		<input name="ordertable.qianshou" type="radio" id="qianshou" size="7" style="ime-mode:disabled" value="0" checked="checked">否
      		<input name="ordertable.qianshou" type="radio" id="qianshou" size="7" style="ime-mode:disabled" value="1">是
      	</s:if>
      	<s:if test="%{updateIds.qianshou==1}">
      		<input name="ordertable.qianshou" type="radio" id="qianshou" size="7" style="ime-mode:disabled" value="0">否
      		<input name="ordertable.qianshou" type="radio" id="qianshou" size="7" style="ime-mode:disabled" value="1" checked="checked">是
      	</s:if>
      	</td>
      </tr>
       <tr>
      	<td width="100"  align="right"><span class="STYLE2">是否入账</span></td>
      	 <td>
      	 <s:if test="%{updateIds.ruzhang==0 || updateIds.ruzhang==null}">
      	 	<input name="ordertable.ruzhang" type="radio" id="ruzhang" style="ime-mode:disabled" value="0" checked="checked">否
      	 	<input name="ordertable.ruzhang" type="radio" id="ruzhang" style="ime-mode:disabled" value="1">是
      	 </s:if>
      	 	 <s:if test="%{updateIds.ruzhang==1}">
      	 	<input name="ordertable.ruzhang" type="radio" id="ruzhang" style="ime-mode:disabled" value="0">否
      	 	<input name="ordertable.ruzhang" type="radio" id="ruzhang" style="ime-mode:disabled" value="1" checked="checked">是
      	 </s:if>
      	 </td>
      	 <td width="100" align="right"><span class="STYLE2">运输单号</span></td>
     	 <td><input name="ordertable.danhao" type="text" id="danhao" size="12" title="运输单号" style="ime-mode:disabled" value="${updateIds.danhao}"></td>
       </tr>  
       <tr>
     
       	<td width="100"  align="right"><span class="STYLE2">是否纠纷</span></td>
      	 <td>
      	 <s:if test="%{updateIds.jiufen==0 || updateIds.jiufen == null}">
      	 	<input name="ordertable.jiufen" type="radio" id="jiufen" style="ime-mode:disabled" value="0" checked="checked">否
      	 	<input name="ordertable.jiufen" type="radio" id="jiufen" style="ime-mode:disabled" value="1">是
      	 </s:if>
      	 	 <s:if test="%{updateIds.jiufen==1}">
      	 	<input name="ordertable.jiufen" type="radio" id="jiufen" style="ime-mode:disabled" value="0">否
      	 	<input name="ordertable.jiufen" type="radio" id="jiufen" style="ime-mode:disabled" value="1" checked="checked">是
      	 </s:if>
      	 </td>
      	 <td  align="right">
      	 	纠纷时间:
      	 </td>
      	 <td><input type="text"  onfocus="WdatePicker()" name="ordertable.jiufentime" title="纠纷时间"/></td>
       </tr>
         <tr>
          <td width="100"  align="right"><span class="STYLE2">是否退货</span></td>
	      <td>
		      <s:if test="%{updateIds.tuihuo==0 || updateIds.tuihuo == null}">
		      	<input name="ordertable.tuihuo" type="radio" id="tuihuo" style="ime-mode:disabled" value="0" checked="checked">否
		      	<input name="ordertable.tuihuo" type="radio" id="tuihuo" style="ime-mode:disabled" value="1">是
		      </s:if>
		      <s:if test="%{updateIds.tuihuo==1}">
		      	<input name="ordertable.tuihuo" type="radio" id="tuihuo" style="ime-mode:disabled" value="0">否
		      	<input name="ordertable.tuihuo" type="radio" id="tuihuo" style="ime-mode:disabled" value="1" checked="checked">是
		      </s:if>
	      </td>
	      	<td width="64"  align="right"><span class="STYLE2">货款</span></td>
	       	<td>
	       	<input name="ordertable.huokuan" value="${updateIds.huokuan}" type="text" id="huokuan" size="7"  title="货款" style="ime-mode:disabled" onkeyup="onlyNumber(this)"  onblur="onlyNumber(this)" onKeyPress="if((event.keyCode<48 || event.keyCode>57) && event.keyCode!=46 || /\.\d\d$/.test(value))event.returnValue=false">
	      </td>
	      
     </tr>
      <tr>
     	<td width="64" align="right"><span class="STYLE2">金额</span></td>
       <td>
       <input name="ordertable.money" value="${updateIds.money}" type="text" id="huokuan" size="7"  title="金额" style="ime-mode:disabled" onkeyup="onlyNumber(this)"  onblur="onlyNumber(this)" onKeyPress="if((event.keyCode<48 || event.keyCode>57) && event.keyCode!=46 || /\.\d\d$/.test(value))event.returnValue=false">
       </td>
       	<td width="64"  align="right"><span class="STYLE2">编码</span></td>
	       	<td>
	       	<input name="ordertable.bianma" value="${updateIds.bianma}" type="text" id="huokuan" size="7"  title="产品编码" style="ime-mode:disabled">
	      </td>
     </tr>
    <tr>   
     
        <td colspan="9" align="center">
         <input type="submit" id="t" value="保存 ">   
          </td>
    </tr>
  </table>
  <br>
  </form>
</m:frame>