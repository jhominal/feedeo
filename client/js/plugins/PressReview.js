(function(){//no footprint function
var pressReview =
{
    name : 'Revue de Presse',
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
                    var article_menu=null;
                    for(var i =0;i!=component.items.length;i++)
                    {
                        if(component.items.itemAt(i).id == 'article')
                        {
                            article_menu = component.items.itemAt(i).menu;
                            break;
                        }
                    }

                    //if found, add our menu
                    console.log(article_menu,'found !');
                    if(article_menu)
                    {
                        article_menu.add(
                            '-',
                            {
                                text: 'Revue de presse',
                                icon: Feedeo.ICONS_URL+'/newspaper.png',
                                menu : {
                                     ignoreParentClicks : true,
                                    items : [
                                            {text: 'Exporter la sélection en HTML', icon: Feedeo.ICONS_URL+'/html.png'},
                                            {text: 'Exporter la sélection en PDF', icon: Feedeo.ICONS_URL+'/page_white_acrobat.png'}
                                    ]
                                }                            
                            }
                        );
                    }
                });
        }
    }
}
Feedeo.plugins.register(pressReview);
})();//eo no footprint function
