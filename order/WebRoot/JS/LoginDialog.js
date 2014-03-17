 var uname = new Ext.form.TextField( {  
        id :'username',  
        fieldLabel : '用户名',  
        name : 'username',//元素名称  
        style:'background:none; border-right: 0px solid;border-top: 0px solid;border-left: 0px solid;border-bottom: #000000 1px solid;',
        //anchor:'95%',//也可用此定义自适应宽度  
        allowBlank : false,//不允许为空  
        
        blankText : '用户名不能为空'//错误提示内容  
    });  
    var pwd = new Ext.form.TextField( {  
        id : 'pwd',  
        //xtype: 'textfield',  
        inputType : 'password',  
        fieldLabel : '密　码',  
        style:'background:none; border-right: 0px solid;border-top: 0px solid;border-left: 0px solid;border-bottom: #000000 1px solid;',
        //anchor:'95%',  
        maxLength : 10,  
        name : 'pwd',  
        allowBlank : false,  
        
        blankText : '密码不能为空'  
    });  
      
    Ext.onReady(function() {  
        //使用表单提示  
            Ext.QuickTips.init();  
            Ext.form.Field.prototype.msgTarget = 'side';  
  
            //定义表单  
            var simple = new Ext.FormPanel( {  
            	baseCls:'x-plain',
        labelWidth : 75,  
        defaults : {  
            width : 150  
        },  
        defaultType : 'textfield',//默认字段类型  
        bodyStyle : 'background: #cdcdcd;padding:30 0 0 20;',  
        border : false,  
        buttonAlign : 'center',  
        border : false,  
        id : "form",  
        //定义表单元素  
        items : [ uname, pwd ],  
        buttons : [ {  
            text : '登录',  
            type : 'submit',  
            id : 'sb',  
            //定义表单提交事件  
            handler : save  
        }, {  
            text : '重置',  
            handler : function() {  
                simple.form.reset();  
            }  
        } ],  
        keys : [ {  
            key : Ext.EventObject.ENTER,  
            fn : save,  
            scope : this  
        } ]  
    });  
            function save() {  
                var userName = uname.getValue();  
                var userPass = pwd.getValue();  
            	// 控制进度速度
				var f = function(v) {
					return function() {
						var i = v / 11;
						Ext.MessageBox.updateProgress(i, '');
					};
				};

				for (var i = 1; i < 12; i++) {
					setTimeout(f(i), i * 150);
				}
                //验证合法后使用加载进度条  
                if (simple.form.isValid()) {  
                    //提交到服务器操作  
                    simple.form.submit({  
                        waitMsg : '正在进行登陆验证,请稍后...',  
                        animEl : 'loading',
                        
                        url : 'login!login.do',  
                        method : 'post',
                       
                        //提交成功的回调函数  
                        success : function(form, action) {
                    	if (action.result.msg == 'ok') {
                    		 window.location="login!main.do";  
						} else {
							Ext.Msg.alert('登陆失败',action.result.msg);
						    document.getElementById("username").value='';
			                document.getElementById("pwd").value='';
						}
                               
                        },  
                        //提交失败的回调函数  
                        failure : function(form, action) {  
                            switch (action.failureType) {    
                            case Ext.form.Action.CLIENT_INVALID:    
                                Ext.Msg.alert('错误提示', '表单数据非法请核实后重新输入！');    
                                break;    
                            case Ext.form.Action.CONNECT_FAILURE:    
                                Ext.Msg.alert('错误提示', '网络连接异常！');    
                                break;    
                            case Ext.form.Action.SERVER_INVALID:    
                               Ext.Msg.alert('错误提示', "您的输入用户信息有误，请核实后重新输入！");    
                               simple.form.reset();      
                            }  
                        }  
                    });  
                }  
            };  
            //定义窗体  
            var win = new Ext.Window({  
            	 title :"登录窗口",
                 layout:'fit',
                 width:290,
                 height:150,
                 plain:true,
                 bodyStyle:"background-color:red;padding:55px 5px 0",
                 bodyStyle:'padding:10px',
                 maximizable:false,
                 closeActon:'close',
                 closable:false,
                 collapsible:true,
                 buttonAlign:'center',
                 items:simple
             });
             win.show();
        });  