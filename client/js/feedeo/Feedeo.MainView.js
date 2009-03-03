Ext.ns('Feedeo');

 
Feedeo.MainView = Ext.extend(Ext.TabPanel, {
    initComponent:function() {
    // hard coded - cannot be changed from outside

    var config = {
        activeTab: 0,
        defaults : {xtype:'articlefullpanel'},
        items:
        [
            {
                xtype:'mainpanel',
                title:'Articles'
            }
        ]
    }; // eo config object
    

    // apply config
    Ext.apply(this, Ext.apply(this.initialConfig, config));
    
    // call parent
    Feedeo.MainView.superclass.initComponent.apply(this, arguments);
    } // eo function initComponent
    
});
 
//register xtype
Ext.reg('mainview', Feedeo.MainView);
 
// end of file