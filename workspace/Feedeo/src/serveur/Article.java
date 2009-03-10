package serveur;


import java.util.Date;
import java.util.List;


import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;

public class Article{
	//Tittre de l'article
	public String title;
	//Lien de l'article
	public String link;
	//Date de l'article
	public Date date;
	//Catégories de l'article /tags
	public List<String> categories;
	
	//Résumé de l'article
	public String summary;
	
	//booléen lu ou pas PAS ICI DANS LA TABLE KI LIE ARTICLE ET USER
	public boolean read;
	
	//Auteur de l'article
	public String author;
	//Lien article au flux d'origine
	public Feed origFeed;
	//Lien article au dossier PAS ICI DANS LA TABLE KI LIE ARTICLE ET DOSSIER
	public Directory directory;
	//proprio de l'article
	public String owner;
	
	//Lien vers le flux d'origine
	public Feed feedOrig;
	//RECUPERER UN ARTICLE AVEC UN SYNDENTRY
	
	//public Article(String tittle){
		//super(tittle);
	//}
	public Article(String title,String url,Date date,String summary,String author,List<String> categories, boolean read,String owner){
		this.title=title;
		this.link=url;
		this.date=date;
		this.summary=summary;
		this.author=author;
		this.owner=owner;
		this.read=read;
		this.categories=categories;
	}
	
	@SuppressWarnings("unchecked")
	public Article(SyndEntry se,Feed feedOrig) {
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
        	this.categories=se.getCategories();
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
        this.read=(boolean)false;
        try{
        	this.author=se.getAuthor();
    	}
        catch(Exception e){
    		e.printStackTrace();
    	}
        this.feedOrig=feedOrig;
        
    }
	
	
	public void setURL(String link){
		this.link= link;
	}
	
	public String getURL(){
		return link;
	}
	
	public void setDate(Date date){
		this.date= date;
	}
	
	public Date getDate(){
		return date;
	}
	
	public void setCategories(List<String> cat){
		categories=cat;
	}
	public List<String> getCategories(){
		return categories;
		
		
	}
	public void addCategory(String newCat){
		categories.add(newCat);	
	}
	public void setSummary(String sum){
		summary=sum;
	}
	public String getSummary(){
		return summary;
	}
	
	// GESTION DES PIECES JOINTES ENCLOSURES
	
	//FIN GESTION DES PIECES JOINTES ENCLOSURES
public String getSummaryLight() {
		
		String summary=this.getSummary();
		summary=summary.replaceAll("<img.*?/.{0,4}?>", "");
		summary=summary.replaceAll("<embed.*?>", "");
		return summary;
}

public boolean getread(){
	return read;
}
public void setread(boolean read){
	this.read=read;
}
// GESTION DES FONCTION ISREAD

// FIN GESTION DES FONCTION ISREAD ET SETREAD VIA L'APPLICATION EXTEND

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

}
