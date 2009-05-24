Ext.ns('Feedeo');

 
Feedeo.ArticlePanel = Ext.extend(Ext.Panel, {
    record : null,
    initComponent:function() {
        // hard coded - cannot be changed from outside
    
        var config = {
	    viewInSite:false,
            tbar:
            [{
                text:'Ouvrir dans un onglet',
                tooltip: 'Ouvre l\'article dans un onglet',
                handler: this.openInTabClick,
                scope:this //comme ça, le "this" dans le handler sera "articlePanel" au lieu de "button"
            },{
		text:'Afficher le site original',
		enableToggle:true,
		tooltip:'Affiche l\'article dans son contexte original',
		handler: function(){
			this.viewInSite = !this.viewInSite;
			this.refresh();
		},
		scope:this
	    }],
            autoScroll:true,
            //bodyStyle:'padding:15px',
            html:'Ce flux ne dispose pas d\'informations'
        }; // eo config object
        
    
        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        
        // call parent
        Feedeo.ArticlePanel.superclass.initComponent.apply(this, arguments);
        
        //define events
        this.addEvents('openintabclick');
    }, // eo function initComponent
    openInTabClick : function()
    {
        console.debug('Event buttonclick captured : fire openintabclick with :',this.record);
        this.fireEvent('openintabclick',this.record);
    },
    setArticle : function(record)
    {
        this.record = record;
        this.refresh();
    },
    refresh : function()
    {
	if(this.record){
	        Feedeo.plugins.actions('beforeArticleRefresh',this);
	        if(this.viewInSite === true)
		{
			var url = this.record.data.url;
			this.body.dom.innerHTML = '<object style="width:100%;height:100%;" data="'+url+'"></object>';
		}
		else
		{
			var articleContent = this.record !== null ? this.record.data.content : 'Pas d\'article sélectionné.';
	        	articleContent = Feedeo.plugins.filters('articleContent',articleContent);
	        	this.body.dom.innerHTML = articleContent;
		}
		this.body.scrollTo('top',0);
		this.body.scrollTo('left',0);
	        Feedeo.plugins.actions('afterArticleRefresh',this);
	}
    }
});
 
//register xtype
Ext.reg('articlepanel', Feedeo.ArticlePanel);
 
// end of file
