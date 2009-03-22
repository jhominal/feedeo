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
                    id : 'articlesgridpanel',
                    region : 'north',
                    url: /*Ext.APPLICATION_URL +'/fake-server/json.php?page=sample2',*/Feedeo.SERVER_URL,
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
    ,onArticleSelect : function(article)
    {
        console.debug('Event articleselect captured, article :',article);
        //give the article to the articlePanel !
        this.articlePanel.setArticle(article);
    },
    setFolder : function(folder_id)
    {
        this.articlesGridPanel.setFolder(folder_id);
    }
});
 
//register xtype
Ext.reg('mainpanel', Feedeo.MainPanel);
 
// end of file