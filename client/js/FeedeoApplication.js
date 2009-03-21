Ext.ns('Feedeo');
/* Feedeo functions */
Feedeo.addFeed = function(o)
{
    //o.feedUrl
    //o.folder_id
};
Feedeo.deleteFeed = function(o)
{
    //o.feed_id ?
};
Feedeo.addFolder = function(o)
{
    //o.name
    //o.parent_id
};
Feedeo.removeFolder = function(o)
{
    //o.fodler_id
};
Feedeo.moveFolder = function(o)
{
    //o.folder_id
    //o.new_parent_id
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
Feedeo.windows.loginForm =
{
    title : 'Connectez-vous',
    width : 600,
    height : 400,
    modal : true,
    items :
    [
        {
            xtype : "form",
            url : Feedeo.SERVER_URL,
            listeners :
            {
                beforeaction : { fn :function(form,action)
                {
                    
                    var params = {type : 'simple'};
                    var values = form.getValues();
                    values.action = 'login';
                    //json encode the request
                    params.request = Ext.util.JSON.encode(values);
                    form.baseParams = params;
                    //login & password are sent to the server... tant pis
                
                }}
            },
            defaults :
            {
                listeners :
                    {
                        specialkey : function(f,e)
                        {
                            console.log('toto');
                            if(e.getKey()==e.ENTER)
                            {
                                console.log('enter');
                                this.ownerCt.getForm().submit
                                (
                                    {
                                        success:function(data){console.log('success:',data);}
                                    }
                                );
                            }
                        }
                    }
            },
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
        url: Feedeo.SERVER_URL,
        method: 'POST',
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
