/**
 * Feedeo.FeedsTreepanel Class
 *
 * Events :
 *  -folderselect(folder_id)
 */

// TODO: factoriser le code avec celui d'ArchivesTreePanel

Ext.ns('Feedeo');


 
Feedeo.FeedsTreePanel = Ext.extend(Ext.tree.TreePanel, {
    initComponent:function() {
        // hard coded - cannot be changed from outside
        //treeLoader : charge les données depuis le serveur
        var treeLoader = new Ext.tree.TreeLoader({
            dataUrl:Ext.APPLICATION_URL+'/fake-server/json.php',
            baseParams : {page:'tree'}
        });
        //noeud racine
        var rootNode = new Ext.tree.AsyncTreeNode({
            text:'Flux RSS',
            expanded : true,
            draggable:false
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
            tbar:toolbar,
            autoScroll : true,
            enableDD : true           
            
        };         
    
        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        
        // call parent
        Feedeo.FeedsTreePanel.superclass.initComponent.apply(this, arguments);
       
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
        //expand if possible
        /*if(!node.leaf && !node.expanded)
        {
            console.debug('expand node');
            node.expand();
        }*/
        console.debug('Node clicked',node,event);
        this.fireEvent('folderselect',node.id);
    }

});
 
//register xtype
Ext.reg('feedstreepanel', Feedeo.FeedsTreePanel);
 
// end of file