

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
                autoScroll:true

            },
            {
                region : 'center',
                xtype : 'mainview'
            }
        ]
    });
});