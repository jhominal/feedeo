Ext.ns('Feedeo');

 
Feedeo.ArticlePanel = Ext.extend(Ext.Panel, {
    initComponent:function() {
    // hard coded - cannot be changed from outside

    var config = {
    tbar:
    [{
        icon: Ext.APPLICATION_URL+'/img/icons/page_white.png', // icons can also be specified inline
        cls: 'x-btn-icon',
        tooltip: '<b>Quick Tips</b><br/>Icon only button with tooltip'
    }],
    autoScroll:true,
    bodyStyle:'padding:15px',
    html:'Ce flux ne dispose pas d\'informations'
    }; // eo config object
    

    // apply config
    Ext.apply(this, Ext.apply(this.initialConfig, config));
    
    // call parent
    Feedeo.ArticlePanel.superclass.initComponent.apply(this, arguments);
    } // eo function initComponent
    
});
 
//register xtype
Ext.reg('articlepanel', Feedeo.ArticlePanel);
 
// end of file