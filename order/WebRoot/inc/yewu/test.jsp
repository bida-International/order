<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
 
 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
.wrap-17track { width:200px }

.buttons-17track INPUT{
padding:0 16px;height:30px;border:0;color:#fff;
background-color:#283955;cursor:pointer}
</style>
</head>
<body>
<div>
<!--引入脚本, 输出一个查询框-->
<script language="javascript" type="text/javascript"
>
function _17track_lang(){
var s=(navigator.language||navigator.browserLanguage||navigator.systemLanguage||navigator.userLanguage||"").toLowerCase();
if(s=="zh-tw"||s=="zh-hk"){
return{a:"hk",b:"查询"}
}else{
if(/^zh/.test(s)){
return{a:"cn",b:"查询"}
}else{
return{a:"en",b:"Search"}}
}}function _17track_search(){
function trim(input){
var reExtraSpace=/^\s*(.*?)\s+$/;
return input.replace(reExtraSpace,"$1")
}
var input=trim(document.getElementById("nums17Track").value);
if(input==""){
return
}
var results=input.split("\n");
var resultString="";
for(var i=0;i<results.length;i++){
	var temp=trim(results[i].toString());
	if(temp==""){
		continue
	}
	resultString=resultString.concat(temp+",")
}
if(resultString.lastIndexOf(",")==resultString.length-1)
{
	resultString=resultString.substring(0,resultString.length-1)
}
var url="http://www.17track.net/"+_17track_lang().a+"/result/post.shtml?nums="+escape(resultString);
window.open(url)}document.write('<div class="wrap-17track"><textarea id="nums17Track" cols="30" rows="8"><s:iterator begin="1" end="30" value="getWrzWqs()" var="i">${i.danhao}\n</s:iterator></textarea><div class="buttons-17track"><input type="button" onclick="_17track_search(this)" value="'+_17track_lang().b+'" /></div></div>');
</script>
最多只能输入40个
<s:form action="yewu!getTrack.do">
<s:submit value="刷新"></s:submit>
</s:form>
<s:form action="yewu!getUpTrack.do">
<s:submit value="修改"></s:submit>
</s:form>

</div>
</body>
</html>
