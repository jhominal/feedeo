Ext.ns('Feedeo');

 
Feedeo.ArticlesGridPanel = Ext.extend(Ext.grid.GridPanel, {
    initComponent:function() {
        //create the store to load and store data
        this.jsonStore = new Feedeo.CachedMultiJsonStore({
            url:this.initialConfig.url, //load Json from url
            baseParams : {page:'sample'},
            root:'content.articles',
            autoLoad:true, // auto call the .load() method
            fields:[
                {name: 'author'},
                {name: 'title'},
                {name: 'content'},
                {name: 'url'}
            ]
        });

        // hard coded - cannot be changed from outside
        var config = {
            tbar:
            [{
                icon: Ext.APPLICATION_URL+'/img/icons/page_white.png', // icons can also be specified inline
                cls: 'x-btn-icon',
                tooltip: '<b>Quick Tips</b><br/>Icon only button with tooltip'
            }],
            store: this.jsonStore,
            columns:[
                {header: "Auteur", width: 20, sortable: true, dataIndex: 'author'},
                {header: "Titre", width: 40, sortable: true, dataIndex:'title'}
            ],
            viewConfig:{forceFit:true},
            loadMask:true //add a mask while loading the data
        }; // eo config object
    

        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        
        // call parent
        Feedeo.ArticlesGridPanel.superclass.initComponent.apply(this, arguments);
        
        //register events & listeners
        this.addEvents('articleselect');
        var sm = this.getSelectionModel();
        sm.addListener('rowselect',this.onRowSelect,this); //this = scope
        
        this.on({
           'rowcontextmenu' : this.onRowContextMenu,
           scope : this
        });
    }, // eo function initComponent
    onRowSelect : function(sm,rowindex,record)
    {
        console.debug('Event rowselect captured, fire "articleselect" with :',record);
        this.fireEvent('articleselect',record);
    },
    setFolder : function(folder_id)
    {
        if(this.folder_id !== folder_id)
        {
            //TODO : si on a déjà les données, on ne reload pas !
            // un proxy, un store perso ??
            console.log('store',this.jsonStore);
            this.folder_id = folder_id;
            this.jsonStore.baseParams =
            {
                page: 'sample',
                folder_id:this.folder_id
            };
            this.jsonStore.load();
        }
    },
    onRowContextMenu : function(grid, rowIndex, event)
    {
        //create the menu on first right-click
        if(!this.contextMenu)
        {
            this.contextMenu = new Ext.menu.Menu({
                items: [{
                    id: 'delete-article',
                    text: 'Supprimer'
                }],
                listeners: {
                    itemclick: function(item) {
                        switch (item.id) {
                            case 'delete-article':
                                var article = item.parentMenu.contextArticle;
                                var store = item.parentMenu.contextGrid.store;
                                store.remove(article);
                                break;
                        }
                    }
                }
            })
        }
        //on empeche la propagation de l'event
        event.stopEvent();
        //node.select();
        var article = grid.store.getAt(rowIndex);//find the record
        //attach the article and the grid to the menu
        var ctxMenu = grid.contextMenu;
        ctxMenu.contextArticle = article;
        ctxMenu.contextGrid = grid;
        
        //display the menu
        ctxMenu.showAt(event.getXY());
        console.debug('context article : ',article);
    }
});
 
//register xtype
Ext.reg('articlesgridpanel', Feedeo.ArticlesGridPanel);
 
// end of file