
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
                            {text: 'Ajouter', handler: testHandler},
                            {text: 'Importer', handler: testHandler},
                            {text: 'Exporter', handler: testHandler}
                        ]
                    }
                },
                {
                    text: 'Dossier',
                    menu: {
                        ignoreParentClicks : true,
                        items: [
                            {text: 'Créer', handler: testHandler,icon: Ext.APPLICATION_URL+'folder.png'},
                            {text: 'Supprimer', handler: testHandler},
                            {text: 'Marquer tout comme lu', handler: testHandler},
                            '-',
                            {text: 'Démarrer la visualisation plein écran', handler: testHandler}
                        ]
                    }
                },
                {
                    text: 'Article',
                    menu: {
                        ignoreParentClicks : true,
                        items: [
                            {text: 'Ouvrir dans un onglet', handler: testHandler},
                            {text: 'Ouvrir la sélection dans des onglets', handler: testHandler},
                            {text: 'Ouvrir dans le navigateur', handler: testHandler},
                            {text: 'Ouvrir la sélection dans le navigateur', handler: testHandler},
                            
                            '-',
                            {text: 'Marquer comme lu', handler: testHandler},
                            {text: 'Marquer la sélection comme lue', handler: testHandler},
                            '-',
                            {text: 'Marquer comme important', handler: testHandler},
                            {text: 'Marquer la sélection comme importante', handler: testHandler},
                            '-',
                            {text: 'Supprimer', handler: testHandler},
                            {text: 'Supprimer la sélection', handler: testHandler},
                            '-',
                            {
                                text: 'Exporter la sélection',
                                menu : {
                                     ignoreParentClicks : true,
                                    items : [
                                            {text: 'en HTML', handler: testHandler},
                                            {text: 'en PDF', handler: testHandler},
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
                            {text: 'Configurer l\'apparence du lecteur', handler: testHandler},
                            {text: 'Configurer les raccourcis clavier', handler: testHandler},
                            {text: 'Configurer les notifications', handler: testHandler}
                        ]
                    }
                },
                { //it may be implemented as a plugin, doesn't it ?
                    text: 'Aide',
                    menu: {
                        ignoreParentClicks : true,
                        items: [
                            {text: 'Aide', handler: testHandler},
                            '-',
                            {text: 'Rapporter un bug', handler: testHandler},
                            '-',
                            {text: 'A propos de Feedeo', handler: testHandler}
                        ]
                    }
                },
            ] //eo items
        }; // eo config object
    

        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        
        // call parent
        Feedeo.MainToolbar.superclass.initComponent.apply(this, arguments);
        
    }// eo function initComponent

});
 
//register xtype
Ext.reg('maintoolbar', Feedeo.MainToolbar);
 
// end of file