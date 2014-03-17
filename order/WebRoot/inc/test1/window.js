
Ext.onReady(function(){
Ext.OperatorWindow=Ext.extend(Ext.Window ,{
    xtype:"window",
	title:"速卖通",
	id:"ext-win-myGczry",
	width:400,
	height:350,
	modal:true,
	closable:true,
	resizable:false,
	initComponent: function(){
		this.items=[
			{
				xtype:"form",
				title:"",
				id:"ext-form-myGczry",
				labelWidth:80,
				labelAlign:"right",
				layout:"form",
				height:350,
				frame:true,
				items:[
					{
						xtype:"numberfield",
						fieldLabel:"人员代码",
						allowBlank:false,
						blankText:'人员代码不能为空',
						readOnly:this.cz=='0',
						id:"dmCzy",
						value:this.dmCzy,
						name:"gczry.dmCzy",
						style:"ime-mode:disabled",
						anchor:"93%"
					},
					{
						xtype:"textfield",
						fieldLabel:"人员姓名",
						allowBlank:false,
						blankText:'姓名不能为空',
						value:this.xmCzy,
						id:"xmCzy",
						name:"gczry.xmCzy",
						anchor:"93%"
					},	
					{
						xtype:"textfield",
						value:this.kl,
						fieldLabel:"密码",
						inputType:'password',
						id:"kl",
						name:"gczry.kl",
						anchor:"93%"
					},	
					{
						xtype: "numberfield",
						value:this.dmDq,
						fieldLabel:"地区编码",
						maxLength:3,
						blankText:'地区编码最大只能输入三位',
						style:"ime-mode:disabled",				
						id:"dmDq",
						name:"gczry.dmDq",
						anchor:"93%"
						
					},
					{
						xtype:"numberfield",
						fieldLabel:"部门编码",
						style:"ime-mode:disabled",
						value:this.dmBm,					
						id:"dmBm",
						name:"gczry.dmBm",
						anchor:"93%"
					},
					{
						xtype:"numberfield",
						fieldLabel:"班组编码",
						maxLength:3,
						style:"ime-mode:disabled",
						id:"dmBz",
						value:this.dmBz,
						name:"gczry.dmBz",
						anchor:"93%"
					},
					{
						xtype:"numberfield",
						fieldLabel:"联系电话",
						style:"ime-mode:disabled",
						value:this.lxdh,
						id:"lxdh",
						regex:/^1[3|5][0-9]\d{4,8}$/,
						regexText:"输入手机号不合法",
						name:"gczry.lxdh",
						anchor:"93%"
					},
					{
						xtype:"textfield",
						fieldLabel:"联系地址",
						value:this.lxdz,
						id:"lxdz",
						name:"gczry.lxdz",
						anchor:"93%"
					},
					{
						xtype:"textfield",
						fieldLabel:"电子邮箱",
						style:"ime-mode:disabled",
						value:this.dzYx,
						regex:/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/,
						regexText:"邮箱输入不合法",
						id:"dzYx",
						name:"gczry.dzYx",
						anchor:"93%"
					},
					{
						xtype:"numberfield",
						fieldLabel:"税务人员编码",
						style:"ime-mode:disabled",
						value:this.userid,
						id:"userid",
						name:"gczry.userid",
						anchor:"93%"
					},
					{
						xtype:"hidden",
				  		id:"flag",
						value:this.flag
					}	
				]
			}
		]
		this.buttons=[
			  {
		      text:'保存',
		      handler:function(){      
		        if(Ext.getCmp("flag").getValue()=="insert"){
		        	Ext.getCmp("dmCzy").setReadOnly(false);
		        	Ext.MessageBox.show({
		            title:'提示',
			        msg:'是否确定要增加该记录',
			        buttons:Ext.Msg.YESNO,
			        fn:function(conf){
			          if(conf=='yes'){
			            Ext.getCmp('ext-form-myGczry').getForm().submit({
			              url:'/Gczry/insert.do',	              
			              method:'post',
			              waitMsg:'数据处理中。。',
			              success:function(form,action){		                              
		                     Ext.MessageBox.show({
								title:'提示',
								msg: action.result.msg,
								modal:true,
								buttons: Ext.Msg.OK ,
								fn: function(){	
                          			Ext.getCmp('ext-win-myGczry').destroy();
							     	store.load();	
								},
								animEl: 'elId',
								icon: Ext.MessageBox.WARNING				
							}); 
	                     },
					     failure:function(form,action){		
					     	if((Ext.getCmp("dmCzy").getValue() != '') && (Ext.getCmp("xmCzy").getValue() != ''))
					     	{
               					Ext.Msg.alert("提示",action.result.msg);
               				}else
               				{
               					Ext.Msg.alert("提示",'输入有误请把鼠标移到红色感叹号上');
               					return false;
               				}
				         }               
			           })			            
			         }		          
			       }
		         })
		        }//if end
		        else if(Ext.getCmp("flag").getValue()=="update"){
		            Ext.MessageBox.show({
		            title:'提示',
			        msg:'是否确定要修改该记录',
			        buttons:Ext.Msg.YESNO,
			        fn:function(conf){
			          if(conf=='yes'){
		           		  Ext.getCmp('ext-form-myGczry').getForm().submit({
			              url:'/Gczry/update.do',
			              method:'post',
			              waitMsg:'数据处理中。。',
			              success:function(form,action){		                              
			                   Ext.MessageBox.show({
									title:'提示',
									msg: action.result.msg,
									buttons: Ext.Msg.OK ,
									fn: function(){	
                               			Ext.getCmp('ext-win-myGczry').destroy();
								        store.load();	
									},
									animEl: 'elId',
									icon: Ext.MessageBox.WARNING
							  }); 
		                    },
   							failure:function(form,action){
   							
                           		Ext.Msg.alert("提示",action.result.msg);
                       		}               
			            });		            
			          }		          
			        }
		          })
		        } //else end
		      }
		    },
			{
			  text:'关闭',
			  iconCls:'cancel',
			  handler:function(){
			    Ext.getCmp('ext-win-myGczry').destroy();
			  }
			}
			]
		Ext.OperatorWindow.superclass.initComponent.call(this);
		this.show();}})
	
	
});