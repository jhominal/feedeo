package serveur;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
//import java.net.URL;
//import java.util.Collection;
//import java.util.List;
import java.util.HashSet;
import java.util.Set;
//import java.util.Vector;
import com.sun.syndication.io.XmlReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import java.util.Date;
public class Feed extends HibernateObject {
/*Feed's title*/
private long idFeed;
private String title;
private String url;
private String link;
private Date pubDate;
public SyndFeed feedOrig;
//Lien Unidirectionnel vers la liste des articles de ce flux
private Set<Article> articles=new HashSet<Article>();

//LIEN MANY TO ONE AVEC USER
private User user;
//public Vector<Article> articles;


/** Nvelle instance de flux
 * 
 */
public Feed(){}
public Feed (String url,Directory dir,User user){
	SyndFeed feed;
	this.url=url;
	try {
			URL new_url=new URL(url);
			SyndFeedInput input = new SyndFeedInput();
			feed=input.build(new XmlReader(new_url));
			this.feedOrig=feed;
			this.title=this.feedOrig.getTitle();
			this.link=this.feedOrig.getLink();
			this.pubDate=this.feedOrig.getPublishedDate();
			this.user=user;
			//CREER ICI LES ARTICLES
		}
	catch (Exception e){ 
		try {
				// création d'une fabrique de documents
				DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
               
			// création d'un constructeur de documents
				DocumentBuilder constructeur = fabrique.newDocumentBuilder();
               
			// lecture du contenu d'un fichier XML avec DOM
				String chaine="";
				InputStream ips=new FileInputStream(url); 
				InputStreamReader ipsr=new InputStreamReader(ips);
				BufferedReader br=new BufferedReader(ipsr);
				String ligne;
				while ((ligne=br.readLine())!=null){
						System.out.println(ligne);
						chaine+=ligne+"\n";
					}
				br.close();
				File xml = new File(chaine);
				//File xml = new File(url);
				org.w3c.dom.Document doc = constructeur.parse(xml);        
				//URL feedUrl = new URL(url);
				SyndFeedInput input = new SyndFeedInput();
				feed = input.build(doc);
				this.title=feed.getTitle();
				this.link=feed.getLink();
				this.pubDate=feed.getPublishedDate();
				this.user=user;
		}
		catch (Exception ex){
	
				System.out.println("Erreur");
			//System.out.println(Langue.URL_ERROR);
		}
	}
	
}

// DEBUT SET GET
public long getIdFeed(){
	return this.idFeed;
}
public void setIdFeed(long id){
	this.idFeed=id;
}
public String getTitle(){
        return this.title;
}
public void setTitle(String title){
	this.title=title;
}
public String geturl(){
	return this.url;
}
public void seturl(String url){
	this.url=url;
}
;
public String getlink(){
	return link;
}
public void setlink(String link){
	this.link=link;
}
public Date getPubDate(){
	return pubDate;
}
public void setpubDate(Date pubDate){
	this.pubDate=pubDate;
}
public User getUser(){
	return user;
}
public void setUser(User user){
	this.user=user;
}
//FIN SET GET
public void setArticles(Directory dir){
	SyndFeed feed = this.feedOrig;
    int nbarticles = feed.getEntries().size();
    Set<Article>articles=new HashSet<Article>();
    int i;
            for (i = 0; i <nbarticles ; i++) 
            {   
            	//verifier que c'est un article déjà dans la base par l'url
            	//si oui on verifie sa date de publication passe à un autre article
            	//sinon on l'ajoute et on fait les lien avec le dossier et le flux
            	Article article=new Article((SyndEntry)(feed.getEntries().get(i)),this,dir);
            	articles.add(article);
            	article.createArticle();
            	Article_PropertiesPK pk =new Article_PropertiesPK(this.user,article);
            	boolean read=false;
            	boolean important=false;
            	Articles_Properties properties=new Articles_Properties(pk,read,important);
            	properties.createArticles_Properties();
            	/*
            	 * User user2 = new User("ghita","sekkat","tita","tite200","merian@yahoo.fr");
				
				//In a first time, two preferences: color and police size
		
				 Preference color= new Preference("color");
			 	 Preference police=new Preference ("police");
			 	 
			 	 UserPreferencePK pk=new UserPreferencePK(user1, color);
			 	 UserPreference up= new UserPreference(pk,"vert");
			 	 color.createPreference();
			 	 police.createPreference();
				 user1.createUser();
				 user2.createUser();
				 up.createUserPreference();
				 
            	 */
            //updater les anciens articles 
            //si lu REMETTRE à NON LU si la date de mise à jour est récente
            //s'il n'est pas encore dans la liste d'article de l'utilisateur alors on l'insère et on l'ajoute au dossier par défaut
            	
            }
            this.setArticles(articles);
            dir.setlistArticle(articles);
}

public void setArticles(Set<Article> articles){
	this.articles=articles;
}
public Set<Article> getArticles(){
	return articles;
}

//METHODES PR LA PERSISTANCE DES DONNEES DANS HIBERNATE
public void createFeed() {
	HibernateObject.create(this);	
}

public void deleteFeed() {
	HibernateObject.delete(this);	
}

public void updateFeed() {
	HibernateObject.update(this);	
}
//FIN METHODES PR LA PERSISTANCE DES DONNEES DANS HIBERNATE

}
