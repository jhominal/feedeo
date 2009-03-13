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
    html : '<h2>A propos de Feedeo</h2>'
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

Ext.onReady(function() {
    Ext.QuickTips.init();
    
    //si pas de firebug, faire pointer les console.* vers des fonctions vides
    // pour ne pas faire d'erreurs...
    if (!window.console || !console.firebug)
    {
        var names = ["log", "debug", "info", "warn", "error", "assert", "dir", "dirxml",
        "group", "groupEnd", "time", "timeEnd", "count", "trace", "profile", "profileEnd"];
    
        window.console = {};
        for (var i = 0; i < names.length; ++i)
            window.console[names[i]] = function() {};
    }

    var vp = new Ext.Viewport({
        layout:'border',
        items:[
            {
                region : 'north',
                xtype : 'maintoolbar'
            },
            {
                region : 'west',
                xtype:'feedsandarchivespanel',
                width: 200,
                split :true,
                collapsible : true,
                collapseMode : 'mini',
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
});