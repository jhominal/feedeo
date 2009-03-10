
package serveur;

public class Directory {
	public String title;
	//ID DU DOSSIER PARENT on ne sait pas encore comment ça va fonctionner avec HIBERNATE
	public Directory dirParent;
	//ID DU DOSSIER
	public Integer id;
	//LIEN AVEC L'UTILISATEUR
	public String idUser;
	public Directory(String title){
		this.title=title;
		dirParent=null;
	}
	public Directory(String title,Directory dirParent){
		this.title=title;
		this.dirParent=dirParent;
	}
	public boolean isEmpty()
	{
		//REQUETE HIBERNATE
		return false;
	}
	public boolean deleteDir()
	{
		return false;
	}
	public boolean mooveDir()
	{
		return false;
	}
	
	public boolean createDir()
	{
		return false;
	}
	

}


