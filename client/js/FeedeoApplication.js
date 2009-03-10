


Ext.onReady(function() {
    Ext.QuickTips.init();
    


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