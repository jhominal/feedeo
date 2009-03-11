Ext.ns('Feedeo');


 
Feedeo.FeedsTreePanel = Ext.extend(Ext.tree.TreePanel, {
    initComponent:function() {
        // hard coded - cannot be changed from outside
        //treeLoader : charge les données depuis le serveur
        var treeLoader = new Ext.tree.TreeLoader({
            dataUrl:Ext.APPLICATION_URL+'/fake-server/json.php?page=tree'
        })
        //noeud racine
        var rootNode = new Ext.tree.AsyncTreeNode({
            text:'Flux RSS',
            expanded : true
        });
        var toolbar = [
            {
                icon: Ext.APPLICATION_URL+'/img/icons/rss_add.png',
                cls: 'x-btn-icon',
                tooltip: '<b>Nouveau flux</b><br/>Cliquer pour ajouter un flux à votre arbre !'
            }
        ];
        var config = {
            loader:treeLoader,
            root:rootNode,
            tbar:toolbar
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