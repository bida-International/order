
$(function() {
	$$ = function(id) {
        if (typeof (id) == "string") return document.getElementById(id);
        return id;
    };
    
    $waiting = function(msg, el) {
        if ($$('waiting-mask'))
            return;
        msg = msg || '正在请求数据...';
        var hideMask = '<div id="waiting" style="text-align:center;margin-top:40px;">' +
					   '<img src="./CSS/images/loading.gif" /><br/>'+
					   '<span style="color:#23508E;font-weight:bold;">' + msg + '</span>' +
					   '</div>';
       var $d = $(hideMask).appendTo(document.body).dialog({
    	   autoOpen: false,
    	   bgiframe: true,
    	   resizable:false,
            modal: true,
            width: 400,
            close:function(){
        		$(this).dialog("destroy");
        		$(this).remove();
        	}
        });
       $d.prev().remove();
       $d.dialog("open");
    };

    $remove_wariting = function() {
    	$('#waiting').dialog("close");
    	return;
    };
});

//取复选框的值
function getCheckedValue(checkboxName) {
	var checked = [];
	$('input:checkbox[name="' + checkboxName + '"]:checked').each(function() { 
		checked.push($(this).val());
	});
	if (checked.length != 0) {
		return checked.join(",");
	}
	return "";
}

// 弹出敦煌授权对话框
function showDhgateAuthDialog(account) {
	var $dialog = $("#dhgateAuthDialog");
	$dialog.find(".dialog_msg").html("请输入密码后点击获取授权按钮！");
	$dialog.find(".authAccount").html(account);
	$dialog.find(".authPass").val("");
	$dialog.dialog({
		width: 400,
		height: 300,
		modal: true,
		title: "获取敦煌网授权",
		buttons: {
			'获取授权': function() {
				getDhgateToken();
			},
			'取消': function() {
				$(this).dialog("close");
			}
		}
	});
}
//弹出显示大图
function showEnlargeImage(show) {
	var $dialog = $("#dhgateAuthDialog");
	$dialog.find(".authAccount").html("<img src="+show+">");
	$dialog.dialog({
		width: 1000,
		height: 500,
		modal: true,
		buttons: {
			'取消': function() {
				$(this).dialog("close");
			}
		}
	});
}
// 发送授权请求
function getDhgateToken() {
	var $dialog = $("#dhgateAuthDialog")
	var authAccount = $dialog.find(".authAccount").html();
	var authPass = $dialog.find(".authPass").val();
	if (authPass == "") {
		alert("请输入密码！");
		return;
	}
	$dialog.find(".dialog_msg").html("正在获取授权，请稍候...");
	$.ajax({
		url: "./msg/dhgateApi!getToken.do",
		type: "post",
		data: {
			authAccount: authAccount,
			authPass: authPass
		},
		dataType: "json",
		success: function(resp) {
			if (resp.success) {
				alert("获取授权成功！");
				$dialog.dialog("close");
			} else {
				$dialog.find(".dialog_msg").html(resp.msg);
			}
		}
	});
}

// 弹出速卖通授权对话框
function showAliAuthDialog(account) {
	$.ajax({
		url: "./aliAuth!authUrl.do",
		type: "post",
		data: {
			account: account
		},
		dataType: "json",
		success: function(resp) {
			if (resp.success) {
				openAliAuthDialog(resp.msg);
			} else {
				alert(resp.msg);
			}
		}
	});
}

function openAliAuthDialog(authUrl) {
	$("#aliAuthDialog").find("iframe").attr("src", authUrl);
	$("#aliAuthDialog").dialog({
		width: 650,
		height: 450,
		modal: true,
		title: "获取速卖通授权"
	});
}

function closeAliAuthDialog() {
	var $dialog = $("#aliAuthDialog");
	if ($dialog.length == 0) {
		$dialog = $("#aliAuthDialog", parent.document);
	}
	$dialog.dialog("close");
}


