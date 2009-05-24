Ext.ns('Feedeo');

 
Feedeo.ArticlesGridPanel = Ext.extend(Ext.grid.GridPanel, {
    initComponent:function() {
        //create the store to load and store data
        this.jsonStore = new Feedeo.CachedMultiJsonStore({
            url:this.initialConfig.url, //load Json from url
            baseRequestParams :
            {
                type:'simple',
                request : {
                    action : 'getArticles',
                    object : 'folder'
                }
            },
            root:'articles',
            //autoLoad:true, // auto call the .load() method
            fields:[
                {name : 'id'},
                {name: 'author'},
                {name: 'title'},
                {name: 'content'},
                {name: 'url'},
                {name : 'date', type :'date', dateFormat:'timestamp'},
                {name : 'categories'}, //Array
                {name :'summary'},
                {name : 'read'},
                {name : 'important'}
            ],
            sortInfo :{field: "date", direction: "DESC"}
        });


        var titleStateSummaryRenderer = function(val,metadata,record /*,...*/ )
        {
            var sum = record.get('summary');
            /*
            console.log(sum);
            //sum = sum.replace(/<.+>/, '');
            // marche pas là, bizarre (marche à la main dans la console)
            var sumShort = sum.replace(/^(.{40}).*$/,'$1');
            console.log(sumShort);
            */
            var html='';
            html+='<b>'+val+'</b>';
            html+='<br/>'+sum.stripScripts().stripTags()+'...';
            return html;
        }
        var tagsRenderer = function(tagsArray)
        {
            var tagsString='';
            for(var i = 0;i!= tagsArray.length;i++)
            {
                tagsString+=tagsArray[i]+' ';
            }
            return tagsString;
        }

        // hard coded - cannot be changed from outside
        var config = {
            tbar:
            [{
                icon: Ext.APPLICATION_URL+'/img/icons/page_white.png', // icons can also be specified inline
                cls: 'x-btn-icon',
                tooltip: '<b>Quick Tips</b><br/>Icon only button with tooltip',
                handler : function(){console.log(this.jsonStore.commitChanges());},
                scope:this
            }],
            store: this.jsonStore,
            columns:[
                {header: "Titre", width: 40, sortable: true, dataIndex:'title',renderer:titleStateSummaryRenderer},
                {header: "Auteur", width: 20, sortable: true, dataIndex: 'author'},

                {header: "Publié le", width : 20,sortable:true,dataIndex :'date',renderer:Ext.util.Format.dateRenderer('d/m/Y')},
                {header: "Tags", width:30,sortable:false,dataIndex:'categories',renderer:tagsRenderer}
                
            ],
            viewConfig:{forceFit:true},
            loadMask:true //add a mask while loading the data
        }; // eo config object
    

        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        
        // call parent
        Feedeo.ArticlesGridPanel.superclass.initComponent.apply(this, arguments);
        
        //set the row class function (bold if unread)
        this.getView().getRowClass = function(article, index)
        {
            //css class read & unread... are defined in feedeo.css
            var rowClass='';
            rowClass+=(article.data.read ? '' : 'unread');
            rowClass+=' '+(article.data.important ? 'important':'');
            return rowClass;
            
        }; 
        
        
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
        //set article as read
        record.set('read',true);
        //un peu bourrin de commiter à chaque fois...
        //this.commitState();
        console.debug('Event rowselect captured, fire "articleselect" with :',record);
        this.fireEvent('articleselect',record);
    },
    commitState : function()
    {
        //vague souvenir (donc à tester) :
        //les champs (dans record.data) complexes, ne sont pas gérés par ExtJS
        //pas marqué dirty (c'est pour ca que read et important ne sont pas rangés dans data.state)
        var modifiedRecords = this.store.getModifiedRecords();
        //un peu bourrin de faire une requete pour chaque
        for(var i = 0;i!= modifiedRecords.length;i++)
        {
            currentRecord = modifiedRecords[i]
            console.debug('changes',currentRecord,currentRecord.getChanges());
            Feedeo.request(
                {
                    params :
                    {
                        type:'simple',
                        request :
                        {
                            action : 'update',
                            object : 'article',
                            articleId : currentRecord.data.id,
                            changes : currentRecord.getChanges()
                        }
                    },
                    success:function(){currentRecord.commit();},
                    failure:function(){currentRecord.reject();}
                }
            )
        }
    },
    setFolder : function(folderId, forceReload)
    {
    	console.log('setFolder(',folderId,',',forceReload,')');    
        if(forceReload || this.folderId !== folderId)
        {
            //TODO : si on a déjà les données, on ne reload pas !
            // un proxy, un store perso ??
            console.log('store',this.jsonStore);
            this.folderId = folderId;
            //restore baseParams
            
            this.jsonStore.baseRequestParams.request.folderId = this.folderId;
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
