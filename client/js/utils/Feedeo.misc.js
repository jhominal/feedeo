Ext.ns('Feedeo');

Feedeo.request = function(options)
{
    Ext.applyIf(options,
        {
            url: Feedeo.SERVER_URL,
            method: 'POST'
        }
    );
    options.handlers = {};
    if(options.success != null)
    {
        options.handlers.success = options.success;
    }
    if(options.failure != null)
    {
        options.handlers.failure = options.failure;
    }
    options.success = function(response)
    {
        jsonResponse = Ext.util.JSON.decode(response.responseText);
        if(jsonResponse.success!= null && jsonResponse.success)
        {
            if(options.handlers.success != null)
            {
                options.handlers.success(jsonResponse);
            }
        }
        else
        {
            if(options.handlers.failure != null)
            {
                options.handlers.failure(jsonResponse);
            }    
        }
    }
    options.failure = function(response)
    {
        Ext.Msg.alert('Error','Ajax request failed.');
    }
    
    if(options.params && options.params.request && typeof(options.params.request)=="object")
    {
        options.params.request = Ext.util.JSON.encode(options.params.request);
    }
    var connection = new Ext.data.Connection();
    connection.request(options);
}

/* To copy object in depth
   Usage :
   var a = Feedeo.clone(b);
*/

Feedeo.clone = function()
{
    var clone;
    if(this instanceof Array)
    {
        clone = [];
        for(var i=0;i!= this.length;i++)
        {
            if(typeof this[i] == 'object')
            {
                clone.push(this[i].clone());
            }
            else
            {
                clone.push(this[i]);
            }
        }
        
    }
    else //object
    {
        clone = {};
        for(var i in this)
        {
            if(typeof this[i] == 'object')
            {
                clone[i] = this[i].clone();
            }
            else
            {
                clone[i] = this[i];
            }
        }
    }
    return clone;
};

