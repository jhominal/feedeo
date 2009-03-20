
package serveur;

import java.util.Set;

public class Directory extends HibernateObject{
	private Long idDirectory;
	private String title;
	//ID DU DOSSIER PARENT on ne sait pas encore comment ça va fonctionner avec HIBERNATE
	private Long idParent;
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
	
	
	
	
	//ID DU DOSSIER PARENT on ne sait pas encore comment ça va fonctionner avec HIBERNATE
	
	
	
	
	public Long getIdDirectory(){
		return this.idDirectory;
	}
	
	public void setIdDirectory(Long id){
		this.idDirectory=id;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public void setTitle(String title){
		this.title=title;
	}
	public Long getIdParent(){
		return this.idParent;
	}
	
	public void setIdParent(Long id){
		this.idParent=id;
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


