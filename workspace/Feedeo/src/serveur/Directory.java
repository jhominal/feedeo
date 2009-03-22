
package serveur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Directory extends HibernateObject implements JsonObjectSerializable{
	private long idDirectory;
	private String title;
	//ID DU DOSSIER PARENT on ne sait pas encore comment ça va fonctionner avec HIBERNATE
	private long idParent;
	private boolean hasChildren;
	//Lien dossier aux articles many to many
	private Set<Article> listArticle=new HashSet<Article>();
	private User user;
	
	
	public Directory(){}
	public Directory(String title,User user){
		this.title=title;
		this.idParent=0;
		this.user=user;
		this.hasChildren=false;
	}
	public Directory(String title,long dirParent,User user){
		this.title=title;
		this.idParent=dirParent;
		this.user=user;
		this.hasChildren=false;
	}
	
	
	
	
	//ID DU DOSSIER PARENT on ne sait pas encore comment ça va fonctionner avec HIBERNATE
	
	
	
	
	public long getIdDirectory(){
		return this.idDirectory;
	}
	
	public void setIdDirectory(long id){
		this.idDirectory=id;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public void setTitle(String title){
		this.title=title;
	}
	public long getIdParent(){
		return this.idParent;
	}
	
	public void setIdParent(long id){
		this.idParent=id;
	}
	public boolean getHasChildren(){
		return this.hasChildren;
	}
	
	public void setHasChildren(boolean hasChildren){
		this.hasChildren=hasChildren;
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
	/*
	 * public void addArticle(Article a){
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
	public boolean mooveDir(long IdDest, long IdOrig)
	{
		//REQUETE HIBERNATE ET UPDATE
		return false;
	}
	public HashMap<String, Object> toHashMap(){
		
		HashMap<String, Object> dir = new HashMap<String, Object>();
		dir.put("id", ""+this.idDirectory);
		dir.put("text",this.title);
		dir.put("leaf", false);
		if(!this.hasChildren)
		{
			dir.put("children", new ArrayList<HashMap<String,Object>>());
		}
		return dir;
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


