<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<meta name="keywords" content="站长,网页特效,网页特效代码,js特效,js脚本,脚本,广告代码,zzjs,zzjs.net,www.zzjs.net,站长特效 网" />
<meta name="description" content="www.zzjs.net,站长特效网，站长必备js特效及广告代码。以质量为核心，以实用为目的,打造一流网页特效站" />
<title>广告代码 纯css实现鼠标经过弹出红色二级菜单 站长特效网</title>
<style type="text/css">
body {
 background: #fff;
 font-family: "Lucida Grande", Helvetica, Arial, sans-serif;
 font-size: 12px;
}
#menu_zzjs_net {list-style-type:none; margin:0 auto 50px auto; padding:0; width:302px;}
#menu_zzjs_net li {padding:0; margin:0; position:relative; width:150px; height:1.7em; z-index:100;}
#menu_zzjs_net li dl {position:absolute; top:0; left:0; padding-bottom:0;}
#menu_zzjs_net li a, #menu_zzjs_net li a:visited {text-decoration:none;}
#menu_zzjs_net li dd {display:none;}
#menu_zzjs_net table {border-collapse:collapse; padding:0; margin:-1px; font-size:1em;}
#menu_zzjs_net dl {width: 150px; margin: 0; padding: 0; background: transparent;}
#menu_zzjs_net dt {margin:0; padding: 0;}
#menu_zzjs_net dd {margin:0; padding:0; color: #fff; font-size: 1em; text-align:left; width:150px; float:right; clear:right;}
#menu_zzjs_net dt a, #menu_zzjs_net dt a:visited {display:block; font-size: 0.9em; color: #c00; text-align:center; border:1px solid #c00; border-width:0 1px 1px 1px; background:#d4d4d4; padding:0.25em 0 0.75em 0;}
#menu_zzjs_net li a:hover {border:0;}
#menu_zzjs_net li:hover dd, #menu_zzjs_net li a:hover dd {display:block;}
#menu_zzjs_net li:hover dl, #menu_zzjs_net li a:hover dl {width:301px;}
#menu_zzjs_net li:hover dt a, #menu_zzjs_net a:hover dt a {color:#000; background:#ddd; border:1px solid #c00; border-width:0 1px 1px 1px;}
#menu_zzjs_net dd a, #menu_zzjs_net dd a:visited {background:#c00; color:#fff; padding:0.5em 0; text-decoration:none; display:block; text-align:center; border-left:1px solid #c00; border-right:1px solid #c00; width:148px;}
#menu_zzjs_net dd a:hover {background: #ddd; color:#000; border-left:1px solid #c00; border-right:1px solid #c00;}
#menu_zzjs_net b {display:block; overflow:hidden; height:1px;}
b.p1_zzjs {background:#c00; margin:0 5px;}
b.p2_zzjs_net {background:#d4d4d4; border:2px solid #c00; border-width:0 2px; margin:0 3px;}
b.p3_wwwzzjsnet {background:#d4d4d4; margin:0 2px; border:1px solid #c00; border-width:0 1px;}
b.p4_zzjsnet {height:2px; background:#d4d4d4; margin:0 1px; border:1px solid #c00; border-width:0 1px;}
b.p5 {background:#c00; margin:0 5px;}
b.p6 {background:#c00; margin:0 3px;}
b.p7_zzjs__net {background:#c00; margin:0 2px;}
b.p8__wwwzzjsnet {height:2px; background:#c00; margin:0 1px;}
#menu_zzjs_net li:hover b.p2_zzjs_net, #menu_zzjs_net a:hover b.p2_zzjs_net {background:#fff;}
#menu_zzjs_net li:hover b.p3_wwwzzjsnet, #menu_zzjs_net a:hover b.p3_wwwzzjsnet {background:#f0f0f0;}
#menu_zzjs_net li:hover b.p4_zzjsnet, #menu_zzjs_net a:hover b.p4_zzjsnet {background:#e8e8e8;}
</style>
</head>
<body>
<a href="http://www.zzjs.net/">站长特效网</a>，以质量为核心，以实用为目的，打造一流网页特效站！zzjs.net，站长js特效。<hr>
<!--欢迎来到站长特效网，我们网站收集大量高质量js特效，提供许多广告代码下载，网址：www.zzjs.net，zzjs@msn.com,用.net打造靓站-->
<ul id="menu_zzjs_net">
<li>
<!--[if lte IE 6]><a href="http://www.zzjs.net"><table><tr><td><![endif]-->
<dl>
 <dt><b class="p1_zzjs"></b><b class="p2_zzjs_net"></b><b class="p3_wwwzzjsnet"></b><b class="p4_zzjsnet"></b><a href="http://www.zzjs.net">站长特效一号</a></dt>
 <dd><a href="http://www.zzjs.net" title="一号菜单一">一号菜单一</a></dd>
 <dd><a href="http://www.zzjs.net" title="一号菜单二">一号菜单二</a></dd>
 <dd><a href="http://www.zzjs.net" title="一号菜单三">一号菜单三</a></dd>
 <dd><a href="http://www.zzjs.net" title="一号菜单四">一号菜单四</a></dd>
 <dd><a href="http://www.zzjs.net" title="一号菜单五">一号菜单五</a></dd>
 <dd><a href="http://www.zzjs.net" title="一号菜单六">一号菜单六</a></dd>
 <dd><a href="http://www.zzjs.net" title="一号菜单七">一号菜单七</a></dd>
 <dd><a href="http://www.zzjs.net" title="一号菜单八">一号菜单八</a></dd>
 <dd><a href="http://www.zzjs.net" title="一号菜单九">一号菜单九</a><b class="p8__wwwzzjsnet"></b><b class="p7_zzjs__net"></b><b class="p6"></b><b class="p5"></b></dd>
</dl>
<!--[if lte IE 6]></td></tr></table></a><![endif]-->
</li>
<li>
<!--[if lte IE 6]><a href="http://www.zzjs.net"><table><tr><td><![endif]-->
<dl>
 <dt><b class="p1_zzjs"></b><b class="p2_zzjs_net"></b><b class="p3_wwwzzjsnet"></b><b class="p4_zzjsnet"></b><a href="http://www.zzjs.net">站长特效二号</a></dt>
 <dd><a href="http://www.zzjs.net" title="二号菜单一">二号菜单一</a></dd>
 <dd><a href="http://www.zzjs.net" title="二号菜单二">二号菜单二</a></dd>
 <dd><a href="http://www.zzjs.net" title="二号菜单三">二号菜单三</a></dd>
 <dd><a href="http://www.zzjs.net" title="二号菜单四">二号菜单四</a></dd>
 <dd><a href="http://www.zzjs.net" title="二号菜单五">二号菜单五</a></dd>
 <dd><a href="http://www.zzjs.net" title="二号菜单六">二号菜单六</a></dd>
 <dd><a href="http://www.zzjs.net" title="二号菜单七">二号菜单七</a></dd>
 <dd><a href="http://www.zzjs.net" title="二号菜单八">二号菜单八</a></dd>
 <dd><a href="http://www.zzjs.net" title="二号菜单九">二号菜单九</a><b class="p8__wwwzzjsnet"></b><b class="p7_zzjs__net"></b><b class="p6"></b><b class="p5"></b></dd>
</dl>
<!--[if lte IE 6]></td></tr></table></a><![endif]-->
</li>
<li>
<!--[if lte IE 6]><a href="http://www.zzjs.net"><table><tr><td><![endif]-->
<dl>
 <dt><b class="p1_zzjs"></b><b class="p2_zzjs_net"></b><b class="p3_wwwzzjsnet"></b><b class="p4_zzjsnet"></b><a href="http://www.zzjs.net">站长特效三号</a></dt>
 <dd><a href="http://www.zzjs.net" title="三号菜单一">三号菜单一</a></dd>
 <dd><a href="http://www.zzjs.net" title="三号菜单二">三号菜单二</a></dd>
 <dd><a href="http://www.zzjs.net" title="三号菜单三">三号菜单三</a></dd>
 <dd><a href="http://www.zzjs.net" title="三号菜单四">三号菜单四</a></dd>
 <dd><a href="http://www.zzjs.net" title="三号菜单五">三号菜单五</a></dd>
 <dd><a href="http://www.zzjs.net" title="三号菜单六">三号菜单六</a></dd>
 <dd><a href="http://www.zzjs.net" title="三号菜单七">三号菜单七</a></dd>
 <dd><a href="http://www.zzjs.net" title="三号菜单八">三号菜单八</a></dd>
 <dd><a href="http://www.zzjs.net" title="三号菜单九">三号菜单九</a><b class="p8__wwwzzjsnet"></b><b class="p7_zzjs__net"></b><b class="p6"></b><b class="p5"></b></dd>
</dl>
<!--[if lte IE 6]></td></tr></table></a><![endif]-->
</li>
<li>
<!--[if lte IE 6]><a href="http://www.zzjs.net"><table><tr><td><![endif]-->
<dl>
 <dt><b class="p1_zzjs"></b><b class="p2_zzjs_net"></b><b class="p3_wwwzzjsnet"></b><b class="p4_zzjsnet"></b><a href="http://www.zzjs.net">站长特效四号</a></dt>
 <dd><a href="http://www.zzjs.net" title="四号菜单一">四号菜单一</a></dd>
 <dd><a href="http://www.zzjs.net" title="四号菜单二">四号菜单二</a></dd>
 <dd><a href="http://www.zzjs.net" title="四号菜单三">四号菜单三</a></dd>
 <dd><a href="http://www.zzjs.net" title="四号菜单四">四号菜单四</a></dd>
 <dd><a href="http://www.zzjs.net" title="四号菜单五">四号菜单五</a></dd>
 <dd><a href="http://www.zzjs.net" title="四号菜单六">四号菜单六</a></dd>
 <dd><a href="http://www.zzjs.net" title="四号菜单七">四号菜单七</a></dd>
 <dd><a href="http://www.zzjs.net" title="四号菜单八">四号菜单八</a></dd>
 <dd><a href="http://www.zzjs.net" title="四号菜单九">四号菜单九</a><b class="p8__wwwzzjsnet"></b><b class="p7_zzjs__net"></b><b class="p6"></b><b class="p5"></b></dd>
</dl>
<!--[if lte IE 6]></td></tr></table></a><![endif]-->
</li>
</ul>
<ul>
	<li>
		<s:iterator begin="1" end="5">
			<dl>
				<dt>
				<b class="p1_zzjs"></b><b class="p2_zzjs_net"></b><b class="p3_wwwzzjsnet"></b><b class="p4_zzjsnet"></b><a href="http://www.zzjs.net">站长特效四号</a>
				</dt>
			
			<s:iterator begin="1" end="9">
				<dd><a href="http://www.zzjs.net" title="四号菜单一">四号菜单一</a></dd>
			</s:iterator>
			</dl>
		</s:iterator>
	</li>
</ul>
</body>
</html>							