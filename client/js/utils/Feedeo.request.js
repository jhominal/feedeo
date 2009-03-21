Ext.ns('Feedeo');

Feedeo.request = function(options)
{
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