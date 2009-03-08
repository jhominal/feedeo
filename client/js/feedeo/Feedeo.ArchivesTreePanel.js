Ext.ns('Feedeo');


 
Feedeo.ArchivesTreePanel = Ext.extend(Ext.tree.TreePanel, {
    initComponent:function() {
        // hard coded - cannot be changed from outside
        //treeLoader : charge les données depuis le serveur
        var treeLoader = new Ext.tree.TreeLoader({
            dataUrl:Ext.APPLICATION_URL+'/fake-server/json.php?page=tree'
        })
        //noeud racine
        var rootNode = new Ext.tree.AsyncTreeNode({
            text:'Dossier d\'archives',
            expanded : true
        });
        var toolbar = [
            {
                icon: Ext.APPLICATION_URL+'/img/icons/folder_add.png',
                cls: 'x-btn-icon',
                tooltip: '<b>Nouveau dossier</b><br/>Cliquer pour créer un nouveau dossier d\'archive !'
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
       Feedeo.ArchivesTreePanel.superclass.initComponent.apply(this, arguments);

    }, // eo function initComponent

});
 
//register xtype
Ext.reg('archivestreepanel', Feedeo.ArchivesTreePanel);
 
// end of file