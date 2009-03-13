
Ext.ns('Feedeo');
var icons = Ext.APPLICATION_URL+'/img/icons';
 
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
                            {text: 'Ajouter', handler: testHandler, icon: icons+'/feed_add.png'},
                            {text: 'Importer', handler: testHandler, icon: icons+'/feed_go.png'},
                            {text: 'Exporter', handler: testHandler, icon: icons+'/feed_go.png'}
                        ]
                    }
                },
                {
                    text: 'Dossier',
                    menu: {
                        ignoreParentClicks : true,
                        items: [
                            {text: 'Créer', handler: testHandler, icon: icons+'/folder_add.png'},
                            {text: 'Supprimer', handler: testHandler, icon: icons+'/folder_delete.png'},
                            {text: 'Renommer', handler: testHandler, icon: icons+'/folder_edit.png'},
                            {text: 'Marquer tout comme lu', handler: testHandler},
                            '-',
                            {text: 'Démarrer la visualisation plein écran', handler: testHandler, icon: icons+'/arrow_out.png'}
                        ]
                    }
                },
                {
                    text: 'Article',
                    menu: {
                        ignoreParentClicks : true,
                        items: [
                            {text: 'Ouvrir l\'article dans un onglet', handler: testHandler, icon: icons+'/tab_go.png'},
                            {text: 'Ouvrir la sélection dans des onglets', handler: testHandler, icon: icons+'/tab_go.png'},
                            {text: 'Ouvrir l\'article dans le navigateur', handler: testHandler, icon: icons+'/world_go.png'},
                            {text: 'Ouvrir la sélection dans le navigateur', handler: testHandler, icon: icons+'/world_go.png'},
                            
                            '-',
                            {text: 'Marquer l\'article comme lu', handler: testHandler},
                            {text: 'Marquer la sélection comme lue', handler: testHandler},
                            '-',
                            {text: 'Marquer l\'article comme important', handler: testHandler, icon: icons+'/flag_red.png'},
                            {text: 'Marquer la sélection comme importante', handler: testHandler, icon: icons+'/flag_red.png'},
                            '-',
                            {text: 'Supprimer l\'article', handler: testHandler, icon: icons+'/page_white_delete.png'},
                            {text: 'Supprimer la sélection', handler: testHandler, icon: icons+'/delete.png'},
                            '-',
                            {
                                text: 'Revue de presse',
                                icon: icons+'/newspaper.png',
                                menu : {
                                     ignoreParentClicks : true,
                                    items : [
                                            {text: 'Exporter la sélection en HTML', handler: testHandler, icon: icons+'/html.png'},
                                            {text: 'Exporter la sélection en PDF', handler: testHandler, icon: icons+'/page_white_acrobat.png'}
                                    ]
                                }
                            
                            }
                            
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
                                icon: icons+'/layout_edit.png'
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
                                icon: icons+'/email_edit.png'
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
                                icon: icons+'/help.png'
                            },
                            '-',
                            {
                                text: 'Rapporter un bug',
                                handler: function()
                                {
                                    new Ext.Window(Feedeo.windows.help.bugReport).show();
                                },
                                icon: icons+'/bug.png'
                            },
                            '-',
                            {
                                text: 'A propos de Feedeo',
                                icon: icons+'/vcard.png',
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
                    icon : icons+'/door_out.png',
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
    plugins : Feedeo.plugins.action('maintoolbar')
});
 
//register xtype
Ext.reg('maintoolbar', Feedeo.MainToolbar);
 
// end of file