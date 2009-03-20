package serveur;


	import java.io.Serializable;

	import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

	@SuppressWarnings("serial")
	public class Article_PropertiesPK implements Serializable{
		
		public User idUser;
		public Article idArticle;
		
		
		
		public Article_PropertiesPK(){}
		
		public Article_PropertiesPK(User idUser, Article idArticle){
			
			this.idUser=idUser;
			this.idArticle=idArticle;
		
		}
		
		public User getIdUser() {
			return idUser;
			}
			public void setIdUser(User idUser) {
			this.idUser = idUser;
			}
		
			
			public Article getIdArticle() {
				return idArticle;
				}
				public void setIdArticle(Article idArticle) {
				this.idArticle= idArticle;
				}
				
	 
	       public boolean equals(Object other) {
	           if ( !(other instanceof Article_PropertiesPK) ) return false;
	           Article_PropertiesPK castOther = (Article_PropertiesPK) other;
	           return new EqualsBuilder()
	               .append(this.getIdUser(), castOther.getIdUser())
	               .append(this.getIdArticle(), castOther.getIdArticle())
	               .isEquals();
	       }

	       public int hashCode() {
	           return new HashCodeBuilder()
	               .append(getIdUser())
	               .append(getIdArticle())
	               .toHashCode();
	       }
	       
	}

