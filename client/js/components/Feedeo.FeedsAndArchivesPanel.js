/**
 * Feedeo.FeedsAndTreePanel Class
 *
 * Events :
 *  -folderselect(folder_id) (relayed from children)
 */

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
                    title:'Réception'
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
        
        //relay events
        this.feedsPanel = this.items.itemAt(0);
        this.archivesPanel = this.items.itemAt(1);
        this.relayEvents(this.feedsPanel,['folderselect']);
        this.relayEvents(this.archivesPanel,['folderselect']);
        
    } // eo function initComponent

});
 
//register xtype
Ext.reg('feedsandarchivespanel', Feedeo.FeedsAndTreePanel);
 
// end of file