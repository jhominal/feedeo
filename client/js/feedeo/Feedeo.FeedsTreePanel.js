Ext.ns('Feedeo');


 
Feedeo.FeedsTreePanel = Ext.extend(Ext.tree.TreePanel, {
    initComponent:function() {
        // hard coded - cannot be changed from outside
        var treeLoader = new Ext.tree.TreeLoader({
            dataUrl:Ext.APPLICATION_URL+'/fake-server/json.php?page=tree'
        })
        var rootNode = new Ext.tree.AsyncTreeNode({
            text:'Flux RSS'
        });
        var config = {
            loader:treeLoader,
            root:rootNode
        }; /*
        var config = {
        tbar:
        [{
            text:'Ouvrir dans un onglet',
            tooltip: 'Ouvre l\'article dans un onglet',
            handler: this.openInTabClick,
            scope:this //comme ça, le "this" dans le handler sera "articlePanel" au lieu de "button"
        }],
        autoScroll:true,
        bodyStyle:'padding:15px',
        html:'Ce flux ne dispose pas d\'informations'
        }; // eo config object */
        
    
        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        
        // call parent
       Feedeo.FeedsTreePanel.superclass.initComponent.apply(this, arguments);

    }, // eo function initComponent

});
 
//register xtype
Ext.reg('feedstreepanel', Feedeo.FeedsTreePanel);
 
// end of file