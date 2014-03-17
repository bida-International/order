
Ext.onReady(function(){
Ext.OperatorWindow=Ext.extend(Ext.Window ,{
    xtype:"window",
	title:"����ͨ",
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
						fieldLabel:"��Ա����",
						allowBlank:false,
						blankText:'��Ա���벻��Ϊ��',
						readOnly:this.cz=='0',
						id:"dmCzy",
						value:this.dmCzy,
						name:"gczry.dmCzy",
						style:"ime-mode:disabled",
						anchor:"93%"
					},
					{
						xtype:"textfield",
						fieldLabel:"��Ա����",
						allowBlank:false,
						blankText:'��������Ϊ��',
						value:this.xmCzy,
						id:"xmCzy",
						name:"gczry.xmCzy",
						anchor:"93%"
					},	
					{
						xtype:"textfield",
						value:this.kl,
						fieldLabel:"����",
						inputType:'password',
						id:"kl",
						name:"gczry.kl",
						anchor:"93%"
					},	
					{
						xtype: "numberfield",
						value:this.dmDq,
						fieldLabel:"��������",
						maxLength:3,
						blankText:'�����������ֻ��������λ',
						style:"ime-mode:disabled",				
						id:"dmDq",
						name:"gczry.dmDq",
						anchor:"93%"
						
					},
					{
						xtype:"numberfield",
						fieldLabel:"���ű���",
						style:"ime-mode:disabled",
						value:this.dmBm,					
						id:"dmBm",
						name:"gczry.dmBm",
						anchor:"93%"
					},
					{
						xtype:"numberfield",
						fieldLabel:"�������",
						maxLength:3,
						style:"ime-mode:disabled",
						id:"dmBz",
						value:this.dmBz,
						name:"gczry.dmBz",
						anchor:"93%"
					},
					{
						xtype:"numberfield",
						fieldLabel:"��ϵ�绰",
						style:"ime-mode:disabled",
						value:this.lxdh,
						id:"lxdh",
						regex:/^1[3|5][0-9]\d{4,8}$/,
						regexText:"�����ֻ��Ų��Ϸ�",
						name:"gczry.lxdh",
						anchor:"93%"
					},
					{
						xtype:"textfield",
						fieldLabel:"��ϵ��ַ",
						value:this.lxdz,
						id:"lxdz",
						name:"gczry.lxdz",
						anchor:"93%"
					},
					{
						xtype:"textfield",
						fieldLabel:"��������",
						style:"ime-mode:disabled",
						value:this.dzYx,
						regex:/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/,
						regexText:"�������벻�Ϸ�",
						id:"dzYx",
						name:"gczry.dzYx",
						anchor:"93%"
					},
					{
						xtype:"numberfield",
						fieldLabel:"˰����Ա����",
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
		      text:'����',
		      handler:function(){      
		        if(Ext.getCmp("flag").getValue()=="insert"){
		        	Ext.getCmp("dmCzy").setReadOnly(false);
		        	Ext.MessageBox.show({
		            title:'��ʾ',
			        msg:'�Ƿ�ȷ��Ҫ���Ӹü�¼',
			        buttons:Ext.Msg.YESNO,
			        fn:function(conf){
			          if(conf=='yes'){
			            Ext.getCmp('ext-form-myGczry').getForm().submit({
			              url:'/Gczry/insert.do',	              
			              method:'post',
			              waitMsg:'���ݴ����С���',
			              success:function(form,action){		                              
		                     Ext.MessageBox.show({
								title:'��ʾ',
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
               					Ext.Msg.alert("��ʾ",action.result.msg);
               				}else
               				{
               					Ext.Msg.alert("��ʾ",'���������������Ƶ���ɫ��̾����');
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
		            title:'��ʾ',
			        msg:'�Ƿ�ȷ��Ҫ�޸ĸü�¼',
			        buttons:Ext.Msg.YESNO,
			        fn:function(conf){
			          if(conf=='yes'){
		           		  Ext.getCmp('ext-form-myGczry').getForm().submit({
			              url:'/Gczry/update.do',
			              method:'post',
			              waitMsg:'���ݴ����С���',
			              success:function(form,action){		                              
			                   Ext.MessageBox.show({
									title:'��ʾ',
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
   							
                           		Ext.Msg.alert("��ʾ",action.result.msg);
                       		}               
			            });		            
			          }		          
			        }
		          })
		        } //else end
		      }
		    },
			{
			  text:'�ر�',
			  iconCls:'cancel',
			  handler:function(){
			    Ext.getCmp('ext-win-myGczry').destroy();
			  }
			}
			]
		Ext.OperatorWindow.superclass.initComponent.call(this);
		this.show();}})
	
	
});