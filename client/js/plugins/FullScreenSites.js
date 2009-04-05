
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
                                    
                                    var win = new Ext.Window({
                                        title : 'Visualisation plein écran',
                                        width : 600,
                                        height : 400,
                                        store : Ext.ComponentMgr.get('articlesgridpanel').store,
                                        maximized : true,
                                        modal : true,
                                        layout : 'card',
                                        defaultType : 'panel',
                                        tbar : 
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
                                        initFSF : function()
                                        {
                                            this.store.each(function(article)
                                            {
                                                //win.addToolbar
                                                win.add(
                                                    {
                                                        html:'<object style="width:100%;height:100%;" data="'+article.data.url+'"></object>'
                                                    }
                                                );
                                                console.debug('added preview article : ',article);
                                                win.activeItem = 0;
                                            });
                                        }
                                    });//.show();
                                    //un peu moche tout ça...
                                    var prevNextManager = function(direction)
                                    {
                                        console.debug('prevNextManager called ',direction);
                                        if(direction == -1)
                                        {
                                            if(win.activeItem > 0)
                                            {
                                                console.debug('-1 : ',win.activeItem);
                                                win.layout.setActiveItem(win.activeItem--);
                                            }
                                        }
                                        else if(direction == 1)
                                        {
                                            if(win.activeItem < win.items.length)
                                            {
                                                console.debug('+1 : ',win.activeItem);
                                                win.layout.setActiveItem(win.activeItem++);
                                            }
                                        }
                                    }
                                    win.initFSF();
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