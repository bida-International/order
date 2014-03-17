//取编号
function bianhao(items,lujing){
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
		  window.location =""+lujing+"&bulletinId="+bulletinId;
		}else{
			return false;
		}
	}
}
//取编号 和 下拉框的值
function fenpei(items,userid,lujing){
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
		  var sel = "";
		  for(var i=0;i<items.length;i++){
			if("chkItems"==items[i].name&&items[i].checked){
			  bulletinId+=items[i].value+"-";
			}
		  }
		    for(var i=0;i<userid.length;i++){
		    if(userid[i].value!=0){
			  sel+=userid[i].value+"-";
			  }			
		  }
		  location.href=""+lujing+"&bulletinId="+bulletinId+"&seluserid="+sel;
		}else{
			return false;
		}
	}
}
function myfenpei(items,userid,lujing){
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
		var bool=confirm("确认吗???");
		if(bool){
		  var bulletinId="";
		  var sel = "";
		  var selshi="";
		  for(var i=0;i<items.length;i++){
			if("chkItems"==items[i].name&&items[i].checked){
			
			  bulletinId+=items[i].value+"-";
			}
		  }
		  
		    for(var i=0;i<items.length;i++){
				if("chkItems"==items[i].name&&items[i].checked){
				
					sel+=userid[i].value+"-";
				}
			  }
		  location.href=""+lujing+"&bulletinId="+bulletinId+"&seluserid="+sel;
		}else{
			return false;
		}
	}
}

//全选
function quan1(h,da){
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
