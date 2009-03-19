package serveur;

public class Articles_Properties {
	
	//TOUTES LES PROPRIETES D'UN ARTICLE
	private boolean read;
	private boolean important;
	private Long idUser;
	
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
	public Long getIdUser(){
		return this.idUser;
	}
	
	public void setIdUser(Long id){
		this.idUser=id;
	}

}
