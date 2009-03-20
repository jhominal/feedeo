
package serveur;

import java.util.Set;

public class Directory extends HibernateObject{
	public String title;
	//ID DU DOSSIER PARENT on ne sait pas encore comment ça va fonctionner avec HIBERNATE
	public Long idParent;
	//Lien dossier aux articles many to many
	private Set<Article> listArticle;
	
	
	
	public Directory(String title){
		this.title=title;
		this.idParent=null;
	}
	public Directory(String title,Long dirParent){
		this.title=title;
		this.idParent=dirParent;
	}
	
	public Set<Article> getlistArticle(){
		return this.listArticle;
	}
	
	public void setlistArticle(Set<Article> articles){
		this.listArticle=articles;
	}
	
	public boolean isEmpty()
	{
		//REQUETE HIBERNATE
		return false;
	}
	public boolean deleteDir()
	{
		if (isEmpty())
			//REQUETE HIBERNATE ET UPDATE
			return true;
		else
			return false;
	}
	public boolean mooveDir(Long IdDest, Long IdOrig)
	{
		//REQUETE HIBERNATE ET UPDATE
		return false;
	}
	
	public boolean createDir(Long IdParent)
	{
		//CREATE 
		return false;
	}
	

}


