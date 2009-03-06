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
        
        this.mainPanel = this.items.itemAt(0);
        this.articlePanel = this.mainPanel.items.itemAt(1);
        

        //handle openInTab event
        this.articlePanel.on({
            openintab : this.onOpenInTab,
            scope : this //comme ça, le "this" dans openInTab sera mainView
        });
    }, // eo function initComponent
    onOpenInTab : function(){
        console.log('event captured',this);
        //add a fake panel for debug purpose
        this.add({xtype:'panel',title:'new tab',html:'<h2>Ttoo</h2><p>youpi</p>',closable:true});
    }
});
 
//register xtype
Ext.reg('mainview', Feedeo.MainView);
 
// end of file