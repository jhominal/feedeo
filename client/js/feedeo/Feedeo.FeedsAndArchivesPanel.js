Ext.ns('Feedeo');

 
Feedeo.FeedsAndTreePanel = Ext.extend(Ext.TabPanel, {
    initComponent:function() {
        // hard coded - cannot be changed from outside
    
        var config = {
            activeTab: 0,
            items:
            [
                {
                    xtype:'feedstreepanel',
                    title:'Flux'
                },
                {
                    xtype:'archivestreepanel',
                    title : 'Archives'
                }
            ]
        }; // eo config object
        
    
        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        
        // call parent
        Feedeo.FeedsAndTreePanel.superclass.initComponent.apply(this, arguments);
        
    } // eo function initComponent

});
 
//register xtype
Ext.reg('feedsandarchivespanel', Feedeo.FeedsAndTreePanel);
 
// end of file