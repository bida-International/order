<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>手风琴效果</title>
<!--引入jquery类库文件-->
<script type="text/javascript" language="javascript" src="../js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" language="javascript" src="../js/jquery-ui-1.8.18.custom.min.js"></script>
<!--引入css样式文件-->
<link rel="stylesheet" href="../css/ui-lightness/jquery-ui-1.8.18.custom.css">

<script type="text/javascript">
  $(function(){
 $("#accordion").accordion({
  //头信息是h2
   header:"h2",
  //面板展开时事件的效果，默认是click
   event:"click",
  //默认展开的面板
   active:1,
  //面板展开时的动画效果
    animated:"bounceslide",
   //面板高度是否自动增高
    autoHeight:false,
   //填充到父元素
    fillSpace:false,
   //图标的设置
   icons:{
    //header默认时的图标
    header:"ui-icon-carat-2-e-w",
    //激活时的效果图标
    headerSelected:"ui-icon-carat-2-n-w"
    }  
  }).sortable({  
     //允许在y轴上拖动排序
      axis:"y",
     //只允许拖动h2
      handle:"h2",
   stop:function(event,ui){
    alert("拖动停止");
    }  
  });
     
 });
</script>

</head>

<body>
<div id="accordion">
   
      <div>
        <h2><a href="#">这是第一个</a></h2>
        <div>this is the first</div>
      </div>
      
      <div>
        <h2><a href="#">这是第二个</a></h2>
        <div>this is second</div>
      </div>
      
      <div>
        <h2><a href="#">这是第三个</a></h2>
        <div>this is threed</div>
      </div>
  
</div>
</body>
</html>
</body></html>