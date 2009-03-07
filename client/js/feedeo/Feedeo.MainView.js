Ext.ns('Feedeo');

 
Feedeo.MainView = Ext.extend(Ext.TabPanel, {
    initComponent:function() {
        // hard coded - cannot be changed from outside
    
        var config = {
            activeTab: 0,
            deferredRender:false,//permet de pre-loader les onglets
            defaults : {xtype:'articlefullpanel'},
            items:
            [
                {
                    xtype:'mainpanel',
                    title:'Articles'
                }
            ]
        }; // eo config object
        
    
        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        
        // call parent
        Feedeo.MainView.superclass.initComponent.apply(this, arguments);
        
        this.mainPanel = this.items.itemAt(0);
        this.articlePanel = this.mainPanel.items.itemAt(1);
        

        //handle openInTab event
        this.articlePanel.on({
            openintabclick : this.onOpenInTab,
            scope : this //comme ça, le "this" dans openInTab sera mainView
        });
    }, // eo function initComponent
    onOpenInTab : function(article){
        console.debug('Event openintabclick captured, article:',article);
        //add a fake panel for debug purpose
        if(article!=null)
        {
            this.add({xtype:'panel',title:'15 first cha...',article:article,html:'<object style="width:100%;height:100%;" data="'+article.data.url+'"></object>',closable:true});
            this.doLayout(); //force le calcul du layout
                             //et déclenche le préchargement de l'onglet grâce à deferredRender:false;
        }
    }
});
 
//register xtype
Ext.reg('mainview', Feedeo.MainView);
 
// end of file