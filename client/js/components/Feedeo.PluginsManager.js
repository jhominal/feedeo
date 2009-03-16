//les namespaces pour ranger ça proprement
Ext.ns('Feedeo','Feedeo.plugins');


Feedeo.plugins =
{
    /* le tableau de plugins */
    plugins : [],
    
    /* pour enregistrer un nouveau plugin */
    register : function(plugin)
    {
        /* on ajoute le nouveau plugin à la liste */
        this.plugins.push(plugin);
        console.debug('plugin registered :',plugin);
    },
    
    /* appelée par Ext
     * Feedeo.plugins.onInit('componentName')
     * 
     * doit retourner un array de plugins (au sens Extjs)
     */
    
    onInit : function(component)
    {
        /* l'array à retourner */
        var returnedPlugins = [];
        
        /* on parcourt les plugins */
        for(var i=0;i!=this.plugins.length;i++)
        {
            var plugin = this.plugins[i];
            
            /* si le plugin définit des methodes onInit */
            if(plugin.onInit)
            {
                var pluginOnInit = plugin.onInit[component];
                /* si le plugin à une fonction correspondant à l'action */
                if(typeof(pluginOnInit) == 'function')
                {
                    /* on lui définit la methode init (qui pointe vers la methode
                     * du plugin correspondant à l'action) et on l'ajoute à l'array
                     */
                    plugin.init = pluginOnInit;
                    returnedPlugins.push(plugin);
                }
            }
        }
        /* on retourne l'array de plugins répondant à l'action à ExtJS */
        console.debug('plugins called for ',component,':',returnedPlugins);
        return returnedPlugins;
    },
    /* appelée par le coeur
     * Feedeo.plugins.actions('action',object)
     * 
     * execute des actions sur un objet
     * similaire au onInit mais plus souple
     */
    actions : function(actionName,object)
    {

        /* on parcourt les plugins */
        for(var i=0;i!=this.plugins.length;i++)
        {
            var plugin = this.plugins[i];
            
            /* si le plugin définit des actions */
            if(plugin.actions)
            {
                var pluginAction = plugin.actions[actionName];
                /* si le plugin à un filtre correspondant à la propriété */
                if(typeof(pluginAction) == 'function')
                {
                    /* on l'applique */
                    pluginAction.call(plugin,object); //scope = plugin
                }
            }
        }
    },
    /* appelée par le coeur
     * Feedeo.plugins.filters('filteredProperty',data)
     * 
     * execute les filtres définit pour une propriété
     */
    filters : function(filteredProperty,value)
    {
        var result = value;
        /* on parcourt les plugins */
        for(var i=0;i!=this.plugins.length;i++)
        {
            var plugin = this.plugins[i];
            
            /* si le plugin définit des methodes filters */
            if(plugin.filters)
            {
                var pluginFilter = plugin.filters[filteredProperty];
                /* si le plugin à un filtre correspondant à la propriété */
                if(typeof(pluginFilter) == 'function')
                {
                    /* on l'applique */
                    result = pluginFilter.call(plugin,result); //scope = plugin
                }
            }
        }
        return result;
    }
}

//plugin sample

var mySamplePlugin =
{
    name : 'samplePlugin',
    description : 'Plugin d\'exemple',
    /* methodes pour le onInit de chaque composant */
    onInit :
    {
        maintoolbar : function(component)
        {
            console.debug(this.name,' acts at ',component,' initialisation');
        }
    },
    /* methodes appelées par le coeur */
    actions :
    {
        beforeArticleRefresh : function(object)
        {
            console.debug(this.name,' acts before ',object,' refreshes the article');
        },
        afterArticleRefresh : function(object)
        {
            console.debug(this.name,' acts after ',object,' refreshes the article');
        }
    },
    /* methodes pour modifier les variables */
    filters :
    {
        articleContent : function(data)
        {
            console.debug(this.name,' filters : ',data);
            data +="/toto";
            return data;
        }
    }
}
Feedeo.plugins.register(mySamplePlugin);
