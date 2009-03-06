Ext.ns('Feedeo');

 
Feedeo.MainPanel = Ext.extend(Ext.Panel, {
    initComponent:function() {
        // hard coded - cannot be changed from outside
        var config = {
            layout : 'border',
            items:
            [
                {
                    xtype:'articlesgridpanel',
                    region : 'north',
                    url: Ext.APPLICATION_URL+'/fake-server/json.php?page=sample',
                    split:true,
                    height:200
                },
                {
                    xtype:'articlepanel',
                    region : 'center'
                }
            ]
        }; // eo config object
        

        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        
        // call parent
        Feedeo.MainPanel.superclass.initComponent.apply(this, arguments);

        this.articlesGridPanel = this.items.itemAt(0);
        this.articlePanel = this.items.itemAt(1);
         
        this.articlesGridPanel.on({
            scope:this,
            articleselect:this.onArticleSelect   
        });

    } // eo function initComponent
    ,onArticleSelect : function(record)
    {
        console.log('article selected ! change record !');
        //give the record to the articlePanel !
        this.articlePanel.setArticle(record); //arf !
    }
});
 
//register xtype
Ext.reg('mainpanel', Feedeo.MainPanel);
 
// end of file