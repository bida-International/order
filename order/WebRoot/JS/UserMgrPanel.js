Ext.define('my.UserMgrPanel', {
	extend:'Ext.grid.Panel',	
	alias: 'widget.mypanel',
	constructor:function(config){
		//如果config为空则其为false
		if(!config)config={};
		/*var store = Ext.create('Ext.data.Store', {
		    fields:['firstname', 'lastname', 'seniority', 'dep', 'hired'],
		    data:[
		        {firstname:"Michael", lastname:"Scott", seniority:7, dep:"Management", hired:"01/10/2004"},
		        {firstname:"Dwight", lastname:"Schrute", seniority:2, dep:"Sales", hired:"04/01/2004"},
		        {firstname:"Jim", lastname:"Halpert", seniority:3, dep:"Sales", hired:"02/22/2006"},
		        {firstname:"Kevin", lastname:"Malone", seniority:4, dep:"Accounting", hired:"06/10/2007"},
		        {firstname:"Angela", lastname:"Martin", seniority:5, dep:"Accounting", hired:"10/21/2008"}
		    ]
		});*/
		var store = Ext.create('Ext.data.Store', {
		     fields:['firstname', 'lastname', 'seniority', 'dep', 'hired'],
		     proxy: {
		         type: 'ajax',
		         url: 'users.jsp',
		         reader: {
		             type: 'json',
		             root: 'alldata'
		         }
		     },
		     autoLoad: true
		 });
		//如果config中不存在指定的属时，则拷，相同于设置默认值
		Ext.applyIf(config, {
			title:'我的模块',
			store:store,
			columns: [
				Ext.create('Ext.grid.RowNumberer'),
		        {text: 'First Name',  dataIndex:'firstname'},
		        {text: 'Last Name',  dataIndex:'lastname'},
		        {text: 'Hired Month',  dataIndex:'hired', xtype:'datecolumn', format:'M'},
		        {text: 'Department (Yrs)', xtype:'templatecolumn', tpl:'{dep} ({seniority})',flex:1}
		    ],
		    tbar:[{
		    	text:"新加记录",
		    	handler:function(){
		    		store.add({
		    			firstname:"李白", lastname:"Scott", seniority:7, dep:"Management", hired:"01/10/2004"
		    		});
		    	}
		    }],
		    bbar:{
		        xtype: 'pagingtoolbar',
		        store: store,
		        displayInfo: true
		    }
		});	
		//调用父类的构造函数
		this.callParent(arguments);
	}
});