 var uname = new Ext.form.TextField( {  
        id :'username',  
        fieldLabel : '�û���',  
        name : 'username',//Ԫ������  
        style:'background:none; border-right: 0px solid;border-top: 0px solid;border-left: 0px solid;border-bottom: #000000 1px solid;',
        //anchor:'95%',//Ҳ���ô˶�������Ӧ���  
        allowBlank : false,//������Ϊ��  
        
        blankText : '�û�������Ϊ��'//������ʾ����  
    });  
    var pwd = new Ext.form.TextField( {  
        id : 'pwd',  
        //xtype: 'textfield',  
        inputType : 'password',  
        fieldLabel : '�ܡ���',  
        style:'background:none; border-right: 0px solid;border-top: 0px solid;border-left: 0px solid;border-bottom: #000000 1px solid;',
        //anchor:'95%',  
        maxLength : 10,  
        name : 'pwd',  
        allowBlank : false,  
        
        blankText : '���벻��Ϊ��'  
    });  
      
    Ext.onReady(function() {  
        //ʹ�ñ���ʾ  
            Ext.QuickTips.init();  
            Ext.form.Field.prototype.msgTarget = 'side';  
  
            //�����  
            var simple = new Ext.FormPanel( {  
            	baseCls:'x-plain',
        labelWidth : 75,  
        defaults : {  
            width : 150  
        },  
        defaultType : 'textfield',//Ĭ���ֶ�����  
        bodyStyle : 'background: #cdcdcd;padding:30 0 0 20;',  
        border : false,  
        buttonAlign : 'center',  
        border : false,  
        id : "form",  
        //�����Ԫ��  
        items : [ uname, pwd ],  
        buttons : [ {  
            text : '��¼',  
            type : 'submit',  
            id : 'sb',  
            //������ύ�¼�  
            handler : save  
        }, {  
            text : '����',  
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
            	// ���ƽ����ٶ�
				var f = function(v) {
					return function() {
						var i = v / 11;
						Ext.MessageBox.updateProgress(i, '');
					};
				};

				for (var i = 1; i < 12; i++) {
					setTimeout(f(i), i * 150);
				}
                //��֤�Ϸ���ʹ�ü��ؽ�����  
                if (simple.form.isValid()) {  
                    //�ύ������������  
                    simple.form.submit({  
                        waitMsg : '���ڽ��е�½��֤,���Ժ�...',  
                        animEl : 'loading',
                        
                        url : 'login!login.do',  
                        method : 'post',
                       
                        //�ύ�ɹ��Ļص�����  
                        success : function(form, action) {
                    	if (action.result.msg == 'ok') {
                    		 window.location="login!main.do";  
						} else {
							Ext.Msg.alert('��½ʧ��',action.result.msg);
						    document.getElementById("username").value='';
			                document.getElementById("pwd").value='';
						}
                               
                        },  
                        //�ύʧ�ܵĻص�����  
                        failure : function(form, action) {  
                            switch (action.failureType) {    
                            case Ext.form.Action.CLIENT_INVALID:    
                                Ext.Msg.alert('������ʾ', '�����ݷǷ����ʵ���������룡');    
                                break;    
                            case Ext.form.Action.CONNECT_FAILURE:    
                                Ext.Msg.alert('������ʾ', '���������쳣��');    
                                break;    
                            case Ext.form.Action.SERVER_INVALID:    
                               Ext.Msg.alert('������ʾ', "���������û���Ϣ�������ʵ���������룡");    
                               simple.form.reset();      
                            }  
                        }  
                    });  
                }  
            };  
            //���崰��  
            var win = new Ext.Window({  
            	 title :"��¼����",
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