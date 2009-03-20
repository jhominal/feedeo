package serveur;

import java.util.HashSet;
import java.util.Set;



public class Preference extends HibernateObject {
	
	 private long idPreference;
	 private String name;
	
	 
	 Set<UserPreference> userPreferences=new HashSet<UserPreference>();

	 
	 public Preference(){}
	 
	 public Preference(String name){
			
		 this.name=name;
		 
	 }
	 
	 
	 public void setIdPreference(long idPreference){
		 this.idPreference=idPreference;
	 }
	 
	 public long getIdPreference(){
		 return idPreference;
	 }
	 public void setName(String name){
		 this.name=name;
	 }
	 
	 public String getName(){
		 return name;
	 }
	 
/*
	 
	 public void setUsers(Set<User> users){
		 this.users=users;
	 }
	 
	 public Set<User> getUsers(){
		 return users;
	 }
	 */
	 

		public Set<UserPreference> getUserPreferences() {
			return userPreferences;
			}
		
		public void setUserPreferences(Set<UserPreference> userPreferences) {
			this.userPreferences = userPreferences;
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
