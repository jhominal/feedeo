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
            window.console[names[i]] = function() {}
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
                autoScroll:true //? comment ca marche ?
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