package serveur;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@SuppressWarnings("serial")
public class UserPreferencePK implements Serializable{
	
	public User idUser;
	public Preference idPreference;
	
	
	
	public UserPreferencePK(){}
	
	public UserPreferencePK(User idUser, Preference idPreference){
		
		this.idUser=idUser;
		this.idPreference=idPreference;
	
	}
	
	public User getIdUser() {
		return idUser;
		}
		public void setIdUser(User idUser) {
		this.idUser = idUser;
		}
	
		
		public Preference getIdPreference() {
			return idPreference;
			}
			public void setIdPreference(Preference idPreference) {
			this.idPreference = idPreference;
			}
			
 
       public boolean equals(Object other) {
           if ( !(other instanceof UserPreferencePK) ) return false;
           UserPreferencePK castOther = (UserPreferencePK) other;
           return new EqualsBuilder()
               .append(this.getIdUser(), castOther.getIdUser())
               .append(this.getIdPreference(), castOther.getIdPreference())
               .isEquals();
       }

       public int hashCode() {
           return new HashCodeBuilder()
               .append(getIdUser())
               .append(getIdPreference())
               .toHashCode();
       }
       
}
