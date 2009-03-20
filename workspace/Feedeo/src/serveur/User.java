package serveur;

import java.util.HashSet;
import java.util.Set;


public class User extends HibernateObject {

		private long idUser;
		private String name;
		private String lastname;
		
		private String login;
		private String password;
		
		Set<UserPreference> userPreferences=new HashSet<UserPreference>();
		
		//LIEN MANY TO MANY AVEC ARTICLE SET DE PROPRIETES
		private Set<Articles_Properties> article_properties=new HashSet<Articles_Properties>();
		//LIEN ONE TO MANY AVEC DIRECTORY
		private Set<Directory> user_directories=new HashSet<Directory>();

		
		public User(){
		}
		
		public User(String name, String lastname, String login, String password){
			this.setName(name);
			this.setLastname(lastname);
			this.setLogin(login);
			this.setPassword(password);
			
		}
		
		public long getIdUser() {
		return idUser;
		}
		
		public void setIdUser(long idUser) {
		this.idUser = idUser;
		}
		public String getName() {
		return name;
		}
		public void setName(String name) {
		this.name = name;
		}
		public String getLastname() {
		return lastname;
		}
		public void setLastname(String lastname) {
		this.lastname = lastname;
		}
		
		public String getLogin() {
			return login;
			}
		
		public void setLogin(String login) {
			this.login = login;
			}
		
		public String getPassword() {
			return password;
			}
		
		public void setPassword(String password) {
			this.password = password;
			}
		
		
		public Set<UserPreference> getUserPreferences() {
			return userPreferences;
			}
		
		public void setUserPreferences(Set<UserPreference> userPreferences) {
			this.userPreferences = userPreferences;
			}

		public Set<Articles_Properties> getArticles_Properties(){
			return this.article_properties;
		}

		public void setArticles_Properties(Set<Articles_Properties> articles_Properties){
			this.article_properties=articles_Properties;
			
		}
		
		public Set<Directory> getUser_directories(){
			return this.user_directories;
		}

		public void setUser_directories(Set<Directory> directories){
			this.user_directories=directories;
		}
		
		public void createUser() {
			HibernateObject.create(this);	
		}
		
		public void deleteUser() {
			HibernateObject.delete(this);	
		}
		
		public void updateUser() {
			HibernateObject.update(this);	
		}
		
    
}




