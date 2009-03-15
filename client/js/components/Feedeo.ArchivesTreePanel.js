/**
 * Feedeo.ArchivesTreepanel Class
 *
 * Events :
 *  -folderselect(folder_id)
 */

// TODO: factoriser le code avec celui d'FeedsTreePanel

Ext.ns('Feedeo');
 
Feedeo.ArchivesTreePanel = Ext.extend(Ext.tree.TreePanel, {
    initComponent:function() {
        // hard coded - cannot be changed from outside
        //treeLoader : charge les données depuis le serveur
        var treeLoader = new Ext.tree.TreeLoader({
            dataUrl:Ext.APPLICATION_URL+'/fake-server/json.php',
            baseParams : {page:'tree'}
        });
        //noeud racine
        var rootNode = new Ext.tree.AsyncTreeNode({
            text:'Dossier d\'archives',
            expanded : true,
            draggable: false
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
            tbar:toolbar,
            autoScroll : true,
            enableDD : true
        }; 
        
    
        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        
        // call parent
       Feedeo.ArchivesTreePanel.superclass.initComponent.apply(this, arguments);

       //listeners
       this.on(
            {
                'click' : this.onFolderClick,
                scope : this
            }
       );
       //custom events
       this.addEvents('folderselect');

    }, // eo function initComponent
    onFolderClick : function(node,event)
    {
        console.debug('Node clicked',node,event);
        this.fireEvent('folderselect',node.id);
    }

});
 
//register xtype
Ext.reg('archivestreepanel', Feedeo.ArchivesTreePanel);
 
// end of file