package serveur;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import java.util.HashSet;
//import java.util.List;
import java.util.Set;


import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;

public class Article extends HibernateObject implements JsonObjectSerializable{
	//ID de l'article
	private long idArticle;
	//Tittre de l'article
	private String title;
	//Lien de l'article
	private String link;
	//Date de l'article
	private Date date;
	private String content;
	//Catégories de l'article /tags
	//private Set<String> categories;
	private Feed feed;
	
	//Rï¿½sumï¿½ de l'article()
	private String summary;
	//LIEN AVEC LES PROPRIETES D'UN ARTICLES lien one to many avec la classe Articl_Properties
	private Set<Articles_Properties> article_properties=new HashSet<Articles_Properties>();
	
	
	//PROPRIETE DE L'ARTICLE 
	//private Set<Articles_User>
	
	
	//Auteur de l'article
	private String author;
	//Lien article au dossier many to many
	private Set<Directory> listDir=new HashSet<Directory>();;
	//proprio de l'article
	private String owner;
	private SyndContent debug;
	
	public Article(){}
	
	public Article(String title,String url,Date date,String summary,String author,Set<String> categories, boolean read,String owner){
		this.title=title;
		this.link=url;
		this.date=date;
		this.summary=summary;
		this.author=author;
		this.owner=owner;
		//this.read=read;
		//this.categories=categories;
	}
	
	
	public Article(SyndEntry se,Feed feed,Directory dir) {
		this.title=se.getTitle();
        this.link=se.getLink();
        try{
        	this.date=se.getPublishedDate();
        	if (this.date==null) {
        		this.date=new Date(0);
        	}
    	}
        catch(Exception e){
    		this.date=new Date(0);
    		e.printStackTrace();
    	}
        try{
        //	this.categories=(Set<String>) se.getCategories();
    	}
        catch(Exception e){
    		e.printStackTrace();
    	}
        try{
        	this.summary=se.getDescription().getValue();
        }
        catch(Exception e){
        	
        	try{
        	this.summary=((SyndContent)se.getContents().get(0)).getValue();
        	}catch(Exception ex){
        		ex.printStackTrace();
        	}
        	
        }
        try{
        	//this.content=((SyndContent)se.getContents().get(0)).getValue();
        	ArrayList debug = (ArrayList)se.getContents();
        	this.content = debug.toString();
        }
        catch(Exception e){
        	
        	this.content="";
        }
        	
       // this.read=(boolean)false;
        try{
        	this.author=se.getAuthor();
    	}
        catch(Exception e){
    		e.printStackTrace();
    	}
       //VERIFIER QUE L'ARTICLE N'EST PASDEJA DANS LE DOSSIER
        Set<Directory>dirs=new HashSet<Directory>();
        dirs.add(dir);
       this.setlistDir(dirs);
       this.feed= feed;
    }
	
// DEBUT SET GET
	public void setIdArticle(long id){
		idArticle=id;
	}
	public long getIdArticle(){
		return idArticle;
	}
	public void setTitle(String title){
		this.title=title;
	}
	public String getTitle(){
		return title;
	}
	public void setLink(String link){
		this.link= link;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLink(){
		return link;
	}
	public void setDate(Date date){
		this.date= date;
	}
	
	public Date getDate(){
		return date;
	}
	
	/*public void setCategories(Set<String> cat){
		categories=cat;
	}
	public Set<String> getCategories(){
		return categories;
		
		
	}
	
	public void addCategory(String newCat){
		categories.add(newCat);	
	}
	*/
	public void setSummary(String sum){
		summary=sum;
	}
	public String getSummary(){
		return summary;
	}
public String getSummaryLight() {
		
		String summary=this.getSummary();
		summary=summary.replaceAll("<img.*?/.{0,4}?>", "");
		summary=summary.replaceAll("<embed.*?>", "");
		return summary;
}

public void setFeed(Feed feed){
	this.feed=feed;
}

public Feed getFeed(){
	return this.feed;
}
/*
 * public boolean getread(){
	return read;
}
public void setread(boolean read){
	this.read=read;
}
*/

public String getOwner() {
	return owner;
}

public void setOwner(String owner) {
	this.owner = owner;
}

public void setAuthor(String author){
	this.author=author;
}
	
public String getAuthor(){
	return author;
}

public void setArticle_properties(Set<Articles_Properties> articles_properties){
	this.article_properties=articles_properties;
}
public Set<Articles_Properties> getArticle_properties(){
	return article_properties;

}


public Set<Directory> getlistDir(){
	return this.listDir;
}

public void setlistDir(Set<Directory> listDir){
	this.listDir=listDir;
}
public void addDir(Directory dir){
	this.listDir.add(dir);
}

public Set<Articles_Properties> getArticle_user(){
	return this.article_properties;
}

public void setArticle_user(Set<Articles_Properties> article_properties){
	this.article_properties=article_properties;
}
//FIN SET GET

//GESTION DES PIECES JOINTES ENCLOSURES

//FIN GESTION DES PIECES JOINTES ENCLOSURES

//GESTION DES FONCTION ISREAD

//FIN GESTION DES FONCTION ISREAD ET SETREAD VIA L'APPLICATION EXTEND


// METHODES PR LA PERSISTANCE DES DONNEES DANS HIBERNATE
public void createArticle() {
	HibernateObject.create(this);	
}

public void deleteArticle() {
	HibernateObject.delete(this);	
}

public void updateArticle() {
	HibernateObject.update(this);	
}
//FIN METHODES PR LA PERSISTANCE DES DONNEES DANS HIBERNATE

@Override
public HashMap<String, Object> toHashMap() {
	/*
	 * 	
	 *	this.link=url;
	 *	this.date=date;
	 *	this.summary=summary;
	 *	this.author=author;
	 *	this.owner=owner;
	 */
	HashMap<String, Object> article = new HashMap<String, Object>();
	article.put("id", ""+this.idArticle);
	article.put("title",this.title);
	article.put("author",this.author);
	article.put("summary", this.summary);
	article.put("content", this.summary); //pour au moins avoir qqch pour la démo
	article.put("date", this.date.getTime());
	ArrayList<String> categories = new ArrayList<String>();
	//add categories
	article.put("categories", categories);
	
	return article;
}

}
