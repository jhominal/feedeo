
Ext.ns('Feedeo');

 
Feedeo.ArticlesGridPanel = Ext.extend(Ext.grid.GridPanel, {
    initComponent:function() {
        //create the store to load and store data
        var jsonStore = new Ext.data.JsonStore({
            url:this.initialConfig.url, //load Json from url
            root:'content.articles',
            autoLoad:true, // auto call the .load() method
            fields:[
            {name: 'author'},
            {name: 'title'},
            {name: 'content'},
            {name: 'url'}
            ]
        });

        // hard coded - cannot be changed from outside
        var config = {
            tbar:
            [{
                icon: Ext.APPLICATION_URL+'/img/icons/page_white.png', // icons can also be specified inline
                cls: 'x-btn-icon',
                tooltip: '<b>Quick Tips</b><br/>Icon only button with tooltip'
            }],
            store: jsonStore,
            columns:[
                {header: "Auteur", width: 20, sortable: true, dataIndex: 'author'},
                {header: "Titre", width: 40, sortable: true, dataIndex:'title'}
            ]
            ,viewConfig:{forceFit:true}
            ,loadMask:true //add a mask while loading the data
        }; // eo config object
    

        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        
        // call parent
        Feedeo.ArticlesGridPanel.superclass.initComponent.apply(this, arguments);
        
        //register events & listeners
        this.addEvents('articleselect');
        var sm = this.getSelectionModel();
        sm.addListener('rowselect',this.onRowSelect,this); //this = scope
    }, // eo function initComponent
    onRowSelect : function(sm,rowindex,record)
    {
        console.debug('Event rowselect captured, fire "articleselect" with :',record);
        this.fireEvent('articleselect',record);
    }
});
 
//register xtype
Ext.reg('articlesgridpanel', Feedeo.ArticlesGridPanel);
 
// end of file