Ext.ns('Feedeo');

 
Feedeo.ArticlePanel = Ext.extend(Ext.Panel, {
    record : null,
    initComponent:function() {
    // hard coded - cannot be changed from outside

    var config = {
        tbar:
        [{
            text:'Ouvrir dans un onglet',
            tooltip: 'Ouvre l\'article dans un onglet',
            handler: this.openInTabClick,
            scope:this //comme ça, le "this" dans le handler sera "articlePanel" au lieu de "button"
        }],
        autoScroll:true,
        bodyStyle:'padding:15px',
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
        this.body.dom.innerHTML = this.record !== null ? this.record.data.content : 'pas d\'article sélectionné.';
    }
});
 
//register xtype
Ext.reg('articlepanel', Feedeo.ArticlePanel);
 
// end of file