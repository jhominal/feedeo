package serveur;

public class Articles_Properties extends HibernateObject{
	
	//TOUTES LES PROPRIETES D'UN ARTICLE
	private boolean read;
	private boolean important;
	public Article_PropertiesPK  idUserArticle;
	
	public Articles_Properties (){}
	public Articles_Properties (Article_PropertiesPK  idUserArticle, boolean read, boolean important){
		this.idUserArticle=idUserArticle;
		this.read=read;
		this.important=important;
	}
	
	public boolean getread(){
		return read;
	}
	public void setread(boolean read){
		this.read=read;
	}
	public boolean getImportant(){
		return this.important;
	}
	
	public void setmportant(boolean important){
		this.important=important;
	}
	public Article_PropertiesPK getIdUserArticle(){
		return this.idUserArticle;
	}
	
	public void setIdUserArticle(Article_PropertiesPK id){
		this.idUserArticle=id;
	}

}
