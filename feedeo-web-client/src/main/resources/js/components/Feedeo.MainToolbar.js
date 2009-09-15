
Ext.ns('Feedeo');
 
Feedeo.MainToolbar = Ext.extend(Ext.Toolbar, {
    initComponent:function() {

        var testHandler = function(target, event)
        {
            Ext.Msg.alert('Menu handler','Vous avez cliqué sur le bouton '+target.text);
        };

        // hard coded - cannot be changed from outside
        var config = {
            items : [                
                {
                    text: 'Flux',
                    menu: {
                        ignoreParentClicks : true,
                        items: [
                            {
                                text: 'Ajouter',
                                handler: function()
                                {
                                    Ext.Msg.prompt('Ajouter un flux RSS','URL du flux :',function(btn, text){
                                        if(btn == "ok")
                                        {
                                            //get current folderId
                                            var folderTree = Ext.ComponentMgr.get('folderstree');
                                            var folderId = folderTree.getSelectionModel().getSelectedNode().id;
                                            Feedeo.addFeed({feedUrl:text,folderId:folderId});
                                            
                                        }
                                    });
                                },
                                icon: Feedeo.ICONS_URL+'/feed_add.png'
                            },
                            {text: 'Importer', handler: testHandler, icon: Feedeo.ICONS_URL+'/feed_go.png'},
                            {text: 'Exporter', handler: testHandler, icon: Feedeo.ICONS_URL+'/feed_go.png'}
                        ]
                    }
                },
                {
                    text: 'Dossier',
                    id:'folderMenu',
                    menu: {
                        ignoreParentClicks : true,
                        items: [
                            {
                                text: 'Créer',
                                handler: function()
                                {
                                    Ext.Msg.prompt('Créer un dossier','Nom du dossier :',function(btn, text){
                                        if(btn == "ok")
                                        {
                                            //get current folderId
                                            var folderTree = Ext.ComponentMgr.get('folderstree');
                                            var folder = folderTree.getSelectionModel().getSelectedNode();
                                            Feedeo.addFolder({name:text,parentFolder:folder});
                                            
                                        }
                                    });
                                },
                                icon: Feedeo.ICONS_URL+'/folder_add.png'
                            },
                            {
                                text: 'Supprimer',
                                handler: function()
                                {
                                    //get current folderId
                                    var folderTree = Ext.ComponentMgr.get('folderstree');
                                    var folderId = folderTree.getSelectionModel().getSelectedNode().id;
                                    Feedeo.deleteFolder({folderId:folderId});
                                },
                                icon: Feedeo.ICONS_URL+'/folder_delete.png'
                            },
                            {text: 'Renommer', handler: testHandler, icon: Feedeo.ICONS_URL+'/folder_edit.png'},
                            {text: 'Marquer tout comme lu', handler: testHandler}
                        ]
                    }
                },
                {
                    text: 'Article',
                    id:'article',
                    menu: {
                        ignoreParentClicks : true,
                        items: [
                            {text: 'Ouvrir l\'article dans un onglet', handler: testHandler, icon: Feedeo.ICONS_URL+'/tab_go.png'},
                            {text: 'Ouvrir la sélection dans des onglets', handler: testHandler, icon: Feedeo.ICONS_URL+'/tab_go.png'},
                            {text: 'Ouvrir l\'article dans le navigateur', handler: testHandler, icon: Feedeo.ICONS_URL+'/world_go.png'},
                            {text: 'Ouvrir la sélection dans le navigateur', handler: testHandler, icon: Feedeo.ICONS_URL+'/world_go.png'},
                            
                            '-',
                            {text: 'Marquer l\'article comme lu', handler: testHandler},
                            {text: 'Marquer la sélection comme lue', handler: testHandler},
                            '-',
                            {text: 'Marquer l\'article comme important', handler: testHandler, icon: Feedeo.ICONS_URL+'/flag_red.png'},
                            {text: 'Marquer la sélection comme importante', handler: testHandler, icon: Feedeo.ICONS_URL+'/flag_red.png'},
                            '-',
                            {text: 'Supprimer l\'article', handler: testHandler, icon: Feedeo.ICONS_URL+'/page_white_delete.png'},
                            {text: 'Supprimer la sélection', handler: testHandler, icon: Feedeo.ICONS_URL+'/delete.png'}
                            
                            
                        ]
                    }
                },
                {
                    text: 'Configuration',
                    menu: {
                        ignoreParentClicks : true,
                        items: [
                            {
                                text: 'Configurer l\'apparence du lecteur',
                                handler: function()
                                {
                                    new Ext.Window(Feedeo.windows.config.appearence).show();
                                },
                                icon: Feedeo.ICONS_URL+'/layout_edit.png'
                            },
                            {
                                text: 'Configurer les raccourcis clavier',
                                handler: function()
                                {
                                    new Ext.Window(Feedeo.windows.config.keyboard).show();
                                }
                            },
                            {
                                text: 'Configurer les notifications',
                                handler: function()
                                {
                                    new Ext.Window(Feedeo.windows.config.notifications).show();
                                },
                                icon: Feedeo.ICONS_URL+'/email_edit.png'
                            }
                        ]
                    }
                },
                { //it may be implemented as a plugin, doesn't it ?
                    text: 'Aide',
                    menu: {
                        ignoreParentClicks : true,
                        items: [
                            {
                                text: 'Manuel',
                                handler: function()
                                {
                                    new Ext.Window(Feedeo.windows.help.manual).show();
                                },
                                icon: Feedeo.ICONS_URL+'/help.png'
                            },
                            '-',
                            {
                                text: 'Rapporter un bug',
                                handler: function()
                                {
                                    new Ext.Window(Feedeo.windows.help.bugReport).show();
                                },
                                icon: Feedeo.ICONS_URL+'/bug.png'
                            },
                            '-',
                            {
                                text: 'A propos de Feedeo',
                                icon: Feedeo.ICONS_URL+'/vcard.png',
                                handler : function()
                                {
                                    new Ext.Window(Feedeo.windows.help.about).show();
                                }
                            }
                        ]
                    }
                },
                '->', //remplit l'espace restant
                {
                    text:'Se déconnecter',
                    icon : Feedeo.ICONS_URL+'/door_out.png',
                    cls:"x-btn-text-icon", //pour ne pas repeter le background
                    handler : testHandler
                }
            ] //eo items
        }; // eo config object
    

        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        
        // call parent
        Feedeo.MainToolbar.superclass.initComponent.apply(this, arguments);
        
    },// eo function initComponent
    plugins : Feedeo.plugins.onInit('maintoolbar')
});
 
//register xtype
Ext.reg('maintoolbar', Feedeo.MainToolbar);
 
// end of file
