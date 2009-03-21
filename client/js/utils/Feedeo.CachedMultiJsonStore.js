Ext.ns('Feedeo');

Feedeo.CachedMultiJsonStore = function(c){

    Feedeo.CachedMultiJsonStore.superclass.constructor.call(this, Ext.apply(c, {
        proxy: c.proxy || (!c.data ? new Ext.data.HttpProxy({url: c.url}) : undefined),
        reader: new Ext.data.JsonReader(c, c.fields)
    }));
};
Ext.extend(Feedeo.CachedMultiJsonStore, Ext.data.Store,{
    load : function()
    {
        console.debug('checking/formating baseParams',this.baseParams);
        if(this.baseParams && this.baseParams.request)
        {
            if(typeof(this.baseParams.request) == "object")
            {
                this.baseParams.request = Ext.util.JSON.encode(this.baseParams.request);
            }
        }
        console.debug('formated baseParams',this.baseParams);
        
        Feedeo.CachedMultiJsonStore.superclass.load.apply(this,arguments);
    }
});