Ext.ns('Feedeo');
/* Feedeo functions */
Feedeo.addFeed = function(o)
{
    //o.feedUrl
    //o.folderId
    Feedeo.request(
    {
        params:
        {
            type : 'simple',
            request :
            {
                action:'addFeed',
                object:'folder',
                feedUrl:o.feedUrl,
                folderId:o.folderId
            }
        },
        success: function(data)
        {
            //si folderId = currentFolder, on reload
            var folderTree = Ext.ComponentMgr.get('folderstree');
            var currentId = folderTree.getSelectionModel().getSelectedNode().id;
            //if(currentId==data.)
        },
        failure:function(data)
        {
            if(data.error)
            {
                Ext.Msg.alert('Error',data.error);
            }
        }
    });
};
Feedeo.getFeeds = function(o)
{
    Feedeo.request(
    {
        params:
        {
            type : 'simple',
            request :
            {
                action:'get',
                object:'feed'
            }
        },
        success: o.success,
        failure:function(data)
        {
            if(data.error)
            {
                Ext.Msg.alert('Error',data.error);
            }
        }
    });
};
Feedeo.deleteFeed = function(o)
{
    //o.feed_id ?
    Feedeo.request(
    {
        params:
        {
            type : 'simple',
            request :
            {
                action:'delete',
                object:'feed',
                feedId:o.feedId
            }
        },
        success: function(data)
        {
            //si folderId = currentFolder, on reload
        },
        failure:function(data)
        {
            if(data.error)
            {
                Ext.Msg.alert('Error',data.error);
            }
        }
    });
};
Feedeo.addFolder = function(o)
{
    //o.name
    //o.parent_id
    Feedeo.request(
    {
        params:
        {
            type : 'simple',
            request :
            {
                action:'add',
                object:'folder',
                parentId:o.parentId,
                name : o.name
            }
        },
        success: function(data)
        {
            //ajouter le dossier à l'arbre
            
        },
        failure:function(data)
        {
            if(data.error)
            {
                Ext.Msg.alert('Error',data.error);
            }
        }
    });
    
};
Feedeo.deleteFolder = function(o)
{
    //o.folder_id
    Feedeo.request(
    {
        params:
        {
            type : 'simple',
            request :
            {
                action:'delete',
                object:'folder',
                folderId:o.folderId
            }
        },
        success: function(data)
        {
            //supprimer le dossier de l'arbre
            
        },
        failure:function(data)
        {
            if(data.error)
            {
                Ext.Msg.alert('Error',data.error);
            }
        }
    });
};
Feedeo.moveFolder = function(o)
{
    //o.folder_id
    //o.new_parent_id
    Feedeo.request(
    {
        params:
        {
            type : 'simple',
            request :
            {
                action:'move',
                object:'folder',
                folderId:o.folderId,
                newParentId:o.newParentId
            }
        },
        success: function(data)
        {
            //déplacer le dossier dans l'arbre
            
        },
        failure:function(data)
        {
            if(data.error)
            {
                Ext.Msg.alert('Error',data.error);
            }
        }
    });
};
Ext.ns('Feedeo.windows','Feedeo.windows.config','Feedeo.windows.help');

Feedeo.windows.help.about =
{
    title : 'A propos de Feedeo',
    width : 600,
    height : 400,
    modal : true,
    bodyStyle : 'padding:10px',
    autoLoad : Ext.APPLICATION_URL+'/html/about.html',
    autoScroll : true
};
Feedeo.windows.help.bugReport =
{
    title : 'Reporter un bug',
    width : 600,
    height : 400,
    modal : true
};
Feedeo.windows.help.manual =
{
    title : 'Manuel d\'utilisation',
    width : 600,
    height : 400,
    modal : true
};

Feedeo.windows.config.appearence =
{
    title : 'Apparence de Feedeo',
    width : 600,
    height : 400,
    modal : true
};
Feedeo.windows.config.keyboard =
{
    title : 'Configurer les raccourcis clavier',
    width : 600,
    height : 400,
    modal : true
};
Feedeo.windows.config.notifications =
{
    title : 'Configurer les notifications',
    width : 600,
    height : 400,
    modal : true
};
//TODO:reformater tout ça !
Feedeo.windows.loginForm =
{
    title : 'Connectez-vous',
    width : 600,
    height : 400,
    modal : true,
    items :
    [
        {
            xtype : "tabpanel",
            defaults :
            {
                url : Feedeo.SERVER_URL,
                listeners : //Default listeners for tabs
                {
                    beforeaction : function(form,action)
                    {
                        
                        var params = {type : 'simple'};
                        var values = form.getValues();
                        values.action = this.action;
                        //json encode the request
                        params.request = Ext.util.JSON.encode(values);
                        form.baseParams = params;
                        //login & password are sent to the server... tant pis
                    
                    },
                    activate : function()
                    {
                        //to fix the "not rendering" bug. see ticket#18
                        this.doLayout();
                    }
                },
                defaults : //Default defaults ^^
                {
                    listeners :
                        {
                            specialkey : function(f,e)
                            {
                                if(e.getKey()==e.ENTER)
                                {
                                    this.ownerCt.getForm().submit
                                    (
                                        {
                                            success:function(form,data)
                                            {
                                                //console.log('login success:',data);
                                                Feedeo.init(data.preferences||{});
                                            }
                                        }
                                    );
                                }
                            }
                        }
                }
            },
            items :
            [
                //Connexion tab
                {
                    xtype : "form",
                    title : "Connexion",      
                    action : 'login', //request param
                    items :
                    [
                        {
                            xtype : "textfield",
                            name : "login",
                            fieldLabel : "Identifiant"
                        },
                        {
                            xtype : "textfield",
                            fieldLabel : "Mot de passe",
                            name : "password"
                        }
                    ]
                },
                //tab enregistrement
                {
                  xtype : "form",
                  title : "Enregistrement",
                  action : 'create', //request param
                  items :
                  [
                      {
                          xtype : "textfield",
                          fieldLabel : "Identifiant",
                          name : "login"
                      },
                      {
                          xtype : "textfield",
                          fieldLabel : "Prénom",
                          name : "name"
                      },
                      {
                          xtype : "textfield",
                          fieldLabel : "Nom",
                          name : "lastname"
                      },
                      {
                          xtype : "textfield",
                          fieldLabel : "Mot de passe",
                          name : "password"
                      },
                      {
                          xtype : "textfield",
                          fieldLabel : "E-mail",
                          name : "mail"
                      }
                  ]
              }
            ],
            activeTab : 0
        }
    ]
}

Feedeo.init = function(options)
{
    //use options to custom the display
    //print hello login, use user prefs...
    var vp = new Ext.Viewport({
        layout:'border',
        items:[
            {
                region : 'north',
                xtype : 'maintoolbar'
            },
            {
                region : 'west',
                title : 'Vos dossiers',
                xtype:'feedsandarchivespanel',
                width: 200,
                split :true,
                collapsible : true,
                //collapseMode : 'mini',
                autoScroll:true//? comment ca marche ?
    
            },
            {
                region : 'center',
                xtype : 'mainview'
    
            }
        ]
    });
    vp.items.itemAt(1).on( //events on treepanels
        {
            'folderselect' : function(folder_id)
            {
                this.items.itemAt(2).mainPanel.setFolder(folder_id);
            },
            scope:vp
        }
    );
}

Ext.onReady(function() {
    Ext.QuickTips.init();

    Feedeo.request({
        params:
        {
            type : 'simple',
            request:
            {
                action:"get",
                object:"preferences"
            }
        },
        success: function(data) {
            console.debug('getPrefs',data);
            Feedeo.init(data);
        },
         failure: function(data) {
            console.debug('getPrefs failure',data)
            //handle failure
            new Ext.Window(Feedeo.windows.loginForm).show();
         }
    });


});
