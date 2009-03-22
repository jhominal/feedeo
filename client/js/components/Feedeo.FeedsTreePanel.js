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
        var treeLoader = new Feedeo.TreeLoader
        ({
            url:Feedeo.SERVER_URL,
            baseParams :
            {
                type:'simple'
            },
            listeners:
            {
                beforeload : function(tl,node) //format the request
                {
                    console.debug('treeNode getChildren',node)
                    request =
                    {
                        action:"getChildren",
                        object:"folder",
                        folderId:node.id
                    }
                    tl.baseParams.request = Ext.util.JSON.encode(request);
                }
            }
        });
        //noeud racine
        var rootNode = new Ext.tree.AsyncTreeNode
        ({
            text:'Flux RSS',
            id:'0',
            expanded : true,
            draggable:false
        });
        var toolbar =
        [
            {
                icon: Ext.APPLICATION_URL+'/img/icons/rss_add.png',
                cls: 'x-btn-icon',
                tooltip: '<b>Nouveau flux</b><br/>Cliquer pour ajouter un flux à votre arbre !'
            }
        ];
        var config =
        {
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
       this.on
       (
            {
                'click' : this.onFolderClick,
                'contextmenu' : this.onContextMenu,
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
    },
    onContextMenu : function(node, event)
    {
        //create the menu on first right-click
        if(!this.contextMenu)
        {
            this.contextMenu = new Ext.menu.Menu({
                ignoreParentClicks : true,
                items:
                [
                    {
                        text: 'Supprimer ce dossier',
                        icon :  Ext.APPLICATION_URL+'/img/icons/folder_delete.png',
                        handler : function(menuItem)
                        {
                            var folder = menuItem.parentMenu.contextFolder;
                            Feedeo.deleteFolder({folder:folder});
                        }
                    },
                    {
                        text: 'Créer un dossier ici',
                        icon :  Ext.APPLICATION_URL+'/img/icons/folder_add.png',
                        handler : function(menuItem)
                        {
                            Ext.Msg.prompt('Créer un dossier','Nom du dossier :',function(btn, text){
                                if(btn == "ok")
                                {
                                    //get current folderId
                                    var folder = menuItem.parentMenu.contextFolder;
                                    Feedeo.addFolder({name:text,parentFolder:folder});
                                }
                            });
                        }
                    },
                    
                ]
            })
        }
        
        var c = node.getOwnerTree().contextMenu;
        //attach the folder to the menu(for itemclick listener)
        c.contextFolder = node;
        //display the menu
        c.showAt(event.getXY());
        console.debug('context node : ',node);
    }

});
 
//register xtype
Ext.reg('feedstreepanel', Feedeo.FeedsTreePanel);
 
// end of file