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
});
