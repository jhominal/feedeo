Ext.ns('Feedeo');


Feedeo.TreeLoader = function(config) {
    Feedeo.TreeLoader.superclass.constructor.call(this, config);
}
Ext.extend(Feedeo.TreeLoader, Ext.tree.TreeLoader, {
    processResponse : function(response, node, callback){
        var json = response.responseText;
        try {
            var o = eval("("+json+")");
            o=o.children;     //<<======= change this line
            node.beginUpdate();
            for(var i = 0, len = o.length; i < len; i++){
                var n = this.createNode(o[i]);
                if(n){
                    node.appendChild(n);
                }
            }
            node.endUpdate();
            if(typeof callback == "function"){
                callback(this, node);
            }
        }catch(e){
            this.handleFailure(response);
        }
    }        
});