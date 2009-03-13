
var pressReview =
{
    name : 'Revue de Presse',
    actions :
    {
        maintoolbar : function(component)
        {
            console.debug(this.name,' acts on ',component);
            //la toolbar n'est pas encore créé, on ne peut pas accéder aux items
            //on peut le faire après le rendering
            
            component.on('render',function(){
                component.add({text:'toto'});
                //toto est ajouté au début, le rendering des autres éléments est fait après...
                console.debug(component.items);
            })
            
            
            
        }
    }
}
Feedeo.plugins.register(pressReview);