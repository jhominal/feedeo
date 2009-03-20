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
private Long idFeed;
private String title;
private String url;
private String link;
private Date pubDate;
//Lien Unidirectionnel vers la liste des articles de ce flux
private Set<Article> articles=new HashSet<Article>();
//public Vector<Article> articles;


/** Nvelle instance de flux
 * 
 */
public Feed(){}
public Feed (String url){
	SyndFeed feed;
	this.url=url;
	try {
			URL new_url=new URL(url);
			SyndFeedInput input = new SyndFeedInput();
			feed =  input.build(new XmlReader(new_url));
			this.title=feed.getTitle();
			this.link=feed.getLink();
			this.pubDate=feed.getPublishedDate();
			//CREER ICI LES ARTICLES
			this.setArticles(feed);
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
				//CREER ICI LES ARTICLES
				this.setArticles(feed);
		}
		catch (Exception ex){
	
				System.out.println("Erreur");
			//System.out.println(Langue.URL_ERROR);
		}
	}
	
}


// DEBUT SET GET
public Long getId(){
	return this.idFeed;
}
public void setId(Long id){
	this.idFeed=id;
}
public String getTitle(){
        return this.title;
}
public void setitle(String title){
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
public Date getpubDate(){
	return pubDate;
}
public void getpubDate(Date pubDate){
	this.pubDate=pubDate;
}

//FIN SET GET
public void setArticles(SyndFeed feed){
    int nbarticles = feed.getEntries().size();
    //Vector<Article> Larticles=new Vector<Article>(nbarticles);
    int i;
            for (i = 0; i <nbarticles ; i++) 
            {   Article article=new Article((SyndEntry)(feed.getEntries().get(i)),this);
            //verifier que c'est un article déjà dans la base
            //faire un select sur les articles de ce flux pour rajouter les nouveaux articles et les lier à un dossier
            //updater les anciens articles 
            //si lu REMETTRE à NON LU si la date de mise à jour est récente
            //s'il n'est pas encore dans la liste d'article de l'utilisateur alors on l'insère et on l'ajoute au dossier par défaut
            	articles.add(article);
            }
	
}

public void setArticles(Set<Article> articles){
	this.articles=articles;
}
public Set<Article> getArticles(){
	return articles;
}

}
