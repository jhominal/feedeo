package serveur;

import java.util.Set;

public class Preference extends HibernateObject {
	
	 private long idPref;
	 private String name;
	 private String value;
	 private Set<User> users;

	 
	 public Preference(){}
	 
	 public void setIdPref(long idPref){
		 this.idPref=idPref;
	 }
	 
	 public long getIdPref(){
		 return idPref;
	 }
	 public void setName(String name){
		 this.name=name;
	 }
	 
	 public String getName(){
		 return name;
	 }
	 
	 public void setValue(String value){
		 this.value=value;
	 }
	 
	 public String getValue(){
		 return value;
	 }
	 
	 public void setUsers(Set<User> users){
		 this.users=users;
	 }
	 
	 public Set<User> getUsers(){
		 return users;
	 }
	 
	
	 public void createPreference() {
			HibernateObject.create(this);	
		}
	 public void deletePreference() {
			HibernateObject.delete(this);	
		}
	 
	 public void updatePreference() {
			HibernateObject.update(this);	
		}
	

}
