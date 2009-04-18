var navHandler = function(direction){
    console.debug('navH : ',direction);
};

Feedeo.PNPanel = Ext.extend(Ext.Panel, {

    layout:'card',
    activeItem: 0, // make sure the active item is set on the container config!
    bodyStyle: 'padding:15px',
    defaults: {
        // applied to each contained panel
        border:false
    },
    // just an example of one possible navigation scheme, using buttons
    bbar: [
        {
            id: 'move-prev',
            text: 'Back',
            handler: navHandler.createDelegate(this, [-1]),
            disabled: true
        },
        '->', // greedy spacer so that the buttons are aligned to each side
        {
            id: 'move-next',
            text: 'Next',
            handler: navHandler.createDelegate(this, [1])
        }
    ],
    // the panels (or "cards") within the layout
    items: [{
        id: 'card-0',
        html: '<h1>Welcome to the Wizard!</h1><p>Step 1 of 3</p>'
    },{
        id: 'card-1',
        html: '<p>Step 2 of 3</p>'
    },{
        id: 'card-2',
        html: '<h1>Congratulations!</h1><p>Step 3 of 3 - Complete</p>'
    }]
});


Ext.reg('prevnextpanel', Feedeo.PNPanel);


/* le plugin ! */


var fullScreenSites =
{
    name : 'Full Screen Sites',
    onInit :
    {
        maintoolbar : function(component)
        {
            console.debug(this.name,' acts on ',component);
            //la toolbar n'est pas encore créé, on ne peut pas accéder aux items
            //on peut le faire après le rendering
            component.afterRender =
                component.afterRender.createSequence(function(config){
                    console.debug('afterrender',config);
                    
                    //find the article menu
                    var folderMenu=null;
                    for(var i =0;i!=component.items.length;i++)
                    {
                        if(component.items.itemAt(i).id == 'folderMenu')
                        {
                            folderMenu = component.items.itemAt(i).menu;
                            break;
                        }
                    }

                    //if found, add our menu
                    console.log(folderMenu,'found !');
                    if(folderMenu)
                    {
                        folderMenu.add(
                            '-',
                            {
                                text: 'Démarrer la visualisation plein écran',
                                icon: Ext.APPLICATION_URL+'/img/icons/arrow_out.png',
                                handler : function()
                                {
                                    var pvpItems = [];
                                    var store = Ext.ComponentMgr.get('articlesgridpanel').store;
                                    if(store)
                                    {
                                        store.each(function(article)
                                        {
                                            pvpItems.push(
                                                {
                                                    html:'<object style="width:100%;height:100%;" data="'+article.data.url+'"></object>'
                                                }
                                            );
                                            console.debug('added preview article : ',article);
                                        });
                                    }
                                    var win = new Ext.Window({
                                        title : 'Visualisation plein écran',
                                        width : 600,
                                        height : 400,
                                        maximized : true,
                                        modal : true,
                                        items :
                                        [
                                            {   //Previous/NextPanel
                                                //xtype : 'prevnextpanel',
                                                xtype : 'tabpanel', //pour la démo
                                                deferredRender : false,
                                                defaultType : 'panel',
                                                activeItem:0, //?
                                                activeTab:0,  //?
                                                items : pvpItems
                                            }
                                        ]
                                    });
                                    win.show();
                                }
                            }
                        );
                    }
                });
        }
    }
}
Feedeo.plugins.register(fullScreenSites);

/*tbar : 
    [
        {
            icon: Ext.APPLICATION_URL+'/img/icons/arrow_left.png',
            cls: 'x-btn-icon',
            handler : function()
            {
                prevNextManager(-1);
            },
            tooltip: '<b>Précedent</b><br/>Revenir sur l\'article précédent'
        },
        {
            icon: Ext.APPLICATION_URL+'/img/icons/arrow_right.png',
            cls: 'x-btn-icon',
            handler : function()
            {
                prevNextManager(+1);
            },
            tooltip: '<b>Suivant</b><br/>Passer à l\'article suivant'
        }
    ],
                                   */