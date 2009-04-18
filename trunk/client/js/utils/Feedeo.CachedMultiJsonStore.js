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
    
        function clone(obj){
            if(obj == null || typeof(obj) != 'object')
                return obj;
            var temp = new obj.constructor(); // changed (twice)
            for(var key in obj)
                temp[key] = clone(obj[key]);
            return temp;
        }
        //verifier que pas tout cassé pendant le rush démo
        this.baseParams = clone(this.baseRequestParams);
        if(this.baseParams.request)
        {
            if(typeof(this.baseParams.request) == "object")
            {
                
                this.baseParams.request = Ext.util.JSON.encode(this.baseParams.request);
            }
        }
        console.debug('formated baseParams',this.baseParams,this.baseRequestParams);
        
        Feedeo.CachedMultiJsonStore.superclass.load.apply(this,arguments);
    }
});