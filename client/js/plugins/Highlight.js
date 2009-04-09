var highlightPlugin =
{
    name : 'highlightPlugin',
    description : 'Plugin d\'highlight',
    // methodes pour le onInit de chaque composant 
    onInit :
    {
        articlepanel : function(component)
        {
            console.debug(this.name,' acts at ',component,' initialisation');
            component.topToolbar.push(
                '-',
                'Keyword to Highlight',
                {
                    xtype:'textfield',
                    listeners :
                    {
                        specialkey : function(f,e)
                        {
                            if(e.getKey()==e.ENTER)
                            {
                                this.setKeyword(f.getValue());
                            }
                        },
                        scope : this
                    }
                }
            );
        }
    },
    // methodes appelées par le coeur 
    actions :
    {

    },
    // methodes pour modifier les variables 
    filters :
    {
        articleContent : function(data)
        {
            //console.debug(this.name,' filters : ',data);
            if(!this.regex)
            {
                this.setKeyword('jquery');
            }
            data = data.replace(this.regex,'<span style="background-color:yellow;">$1</span>');
            return data;
        }
    },
    setKeyword : function(keyword)
    {
        this.regex = new RegExp('(?!<[^>]*)\\b('+keyword+')\\b(?![^<]*>)', 'gi');
    }
}
Feedeo.plugins.register(highlightPlugin);