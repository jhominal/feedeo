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
	var jsonResponse = Ext.util.JSON.decode(response.responseText);
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

Feedeo.clone = function(obj){
console.log('cloning ',obj);
   var seenObjects = [];
   var mappingArray = [];
   var	f = function(simpleObject) {
      var indexOf = seenObjects.indexOf(simpleObject);
      if (indexOf == -1) {		
	 if(simpleObject instanceof Date){
		return new Date(simpleObject);
	 }	
         switch (Ext.type(simpleObject)) {
            case 'object':
               seenObjects.push(simpleObject);
               var newObject = {};
               mappingArray.push(newObject);
               for (var p in simpleObject) 
                  newObject[p] = f(simpleObject[p]);
               newObject.constructor = simpleObject.constructor;				
            return newObject;
 
            case 'array':
               seenObjects.push(simpleObject);
               var newArray = [];
               mappingArray.push(newArray);
               for(var i=0,len=simpleObject.length; i<len; i++)
                  newArray.push(f(simpleObject[i]));
            return newArray;
 
            default:	
            return simpleObject;
         }
      } else {
         return mappingArray[indexOf];
      }
   };
   return f(obj);		
}


