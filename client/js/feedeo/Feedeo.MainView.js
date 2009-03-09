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
        this.articlePanel.on(
            {
                openintabclick : this.onOpenInTab,
                scope : this //comme ça, le "this" dans openInTab sera mainView
            }
        );
        this.on(
            {
                remove:this.onTabRemove,
                scope : this
            },
            {
                add:this.onTabAdd,
                scope : this
            }
        );
    }, // eo function initComponent
    onOpenInTab : function(article){
        console.debug('Event openintabclick captured, article:',article);
        //add a fake panel for debug purpose
        if(article!==null)
        {
            var panel = this.add({xtype:'panel',title:'15 first cha...',article:article,html:'<object style="width:100%;height:100%;" data="'+article.data.url+'"></object>',closable:true});
            //this.setActiveTab(panel);
            console.log(panel,this);
            this.doLayout(); //force le calcul du layout
                             //et décaenche le préchargement de l'onglet grâce à deferredRender:false;
        }
    },
    onTabRemove : function ()
    {
        //vérifier le nombre d'onglets et masquer si un seul onglet
        //this. hideTabStripItem(0);
        //this.getCount(); retourne le nombre de tab
        if(this.getCount()==1)
        {
            this.hideTabStripItem(0);
        }
        
    },
    onTabAdd : function()
    {
        //Affiche la barre d'onglets si masquée
    }
});
 
//register xtype
Ext.reg('mainview', Feedeo.MainView);
 
// end of file