package serveur;

public class Articles_Properties extends HibernateObject{
	
	//TOUTES LES PROPRIETES D'UN ARTICLE
	private boolean lu;
	private boolean important;
	public Article_PropertiesPK  idUserArticle;
	
	public Articles_Properties (){}
	public Articles_Properties (Article_PropertiesPK  idUserArticle, boolean read, boolean important){
		this.idUserArticle=idUserArticle;
		this.lu=read;
		this.important=important;
	}
	
	public boolean getLu(){
		return lu;
	}
	public void setLu(boolean read){
		this.lu=read;
	}
	public boolean getImportant(){
		return this.important;
	}
	
	public void setImportant(boolean important){
		this.important=important;
	}
	public Article_PropertiesPK getIdUserArticle(){
		return this.idUserArticle;
	}
	
	public void setIdUserArticle(Article_PropertiesPK id){
		this.idUserArticle=id;
	}
// METHODES PR LA PERSISTANCE DES DONNEES DANS HIBERNATE
	public void createArticles_Properties() {
		HibernateObject.create(this);	
	}

	public void deleteArticles_Properties() {
		HibernateObject.delete(this);	
	}

	public void updateArticles_Properties() {
		HibernateObject.update(this);	
	}
	//FIN METHODES PR LA PERSISTANCE DES DONNEES DANS HIBERNATE

}
