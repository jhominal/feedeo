
package serveur;

import java.util.HashSet;
import java.util.Set;

public class Directory extends HibernateObject{
	private Long idDirectory;
	private String title;
	//ID DU DOSSIER PARENT on ne sait pas encore comment ça va fonctionner avec HIBERNATE
	private Long idParent;
	private boolean leaf;
	//Lien dossier aux articles many to many
	private Set<Article> listArticle=new HashSet<Article>();
	private User user;
	
	
	
	public Directory(String title,User user){
		this.title=title;
		this.idParent=null;
		this.user=user;
		this.leaf=false;
	}
	public Directory(String title,Long dirParent,User user){
		this.title=title;
		this.idParent=dirParent;
		this.user=user;
		this.leaf=false;
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
	public boolean getLeaf(){
		return this.leaf;
	}
	
	public void setILeaf(boolean leaf){
		this.leaf=leaf;
	}
	public User getUser(){
		return this.user;
	}
	
	public void setUser(User user){
		this.user=user;
	}
	public Set<Article> getlistArticle(){
		return this.listArticle;
	}
	
	public void setlistArticle(Set<Article> articles){
		this.listArticle=articles;
	}
	/*public void addArticle(Article a){
		this.listArticle.add(a);
	}*/
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
	
	//METHODES PR LA PERSISTANCE DES DONNEES DANS HIBERNATE
	public void createDirectory() {
		HibernateObject.create(this);	
	}

	public void deleteDirectory() {
		HibernateObject.delete(this);	
	}

	public void updateDirectory() {
		HibernateObject.update(this);	
	}
	//FIN METHODES PR LA PERSISTANCE DES DONNEES DANS HIBERNATE	

}


