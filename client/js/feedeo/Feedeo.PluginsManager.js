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
    },
    
    /* appelée par Ext
     * Feedeo.plugins.action('actionName')
     * 
     * doit retourner un array de plugins (au sens Extjs)
     */
    action : function(action)
    {
        /* l'array à retourner */
        var returnedPlugins = [];
        
        /* on parcourt les plugins */
        for(var i=0;i!=this.plugins.length;i++)
        {
            var plugin = this.plugins[i];
            var pluginAction = plugin.actions[action];
            /* si le plugin à une fonction correspondant à l'action */
            if(typeof(pluginAction) == 'function')
            {
                /* on lui définit la methode init (qui pointe vers la methode
                 * du plugin correspondant à l'action) et on l'ajoute à l'array
                 */
                plugin.init = pluginAction;
                returnedPlugins.push(plugin);
            }
        }
        /* on retourne l'array de plugins répondant à l'action à ExtJS */
        return returnedPlugins;
    }
}

//plugin sample

var mySamplePlugin =
{
    name : 'samplePlugin',
    actions :
    {
        maintoolbar : function(component)
        {
            console.log(this.name,' : ',component);
        }
    }
}
Feedeo.plugins.register(mySamplePlugin);
