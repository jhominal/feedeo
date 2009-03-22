package serveur;

import java.util.HashSet;
import java.util.Set;


public class User extends HibernateObject {

		private long idUser;
		private String name;
		private String lastname;
		
		private String login;
		private String password;
		private String email;
		
		Set<UserPreference> userPreferences=new HashSet<UserPreference>();
		
		//LIEN MANY TO MANY AVEC ARTICLE au travers d'un SET DE ARTICLES_PROPRIETES
		private Set<Articles_Properties> article_properties=new HashSet<Articles_Properties>();
		//LIEN ONE TO MANY AVEC DIRECTORY
		private Set<Directory> user_directories=new HashSet<Directory>();
		//LIEN ONE TO MANY AVEC FLUX
		private Set<Feed> user_feeds=new HashSet<Feed>();
		
		public User(){
		}
		
		public User(String name, String lastname, String login, String password,String email){
			this.setName(name);
			this.setLastname(lastname);
			this.setLogin(login);
			this.setPassword(password);
			this.setEmail(email);
			
		}
		public User(String login, String password){
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
		public String getEmail() {
			return email;
			}
		
		public void setEmail(String email) {
			this.email = email;
			}
		
		public Set<UserPreference> getUserPreferences() {
			return userPreferences;
			}
		
		public void setUserPreferences(Set<UserPreference> userPreferences) {
			this.userPreferences = userPreferences;
			}

		public Set<Articles_Properties> getArticle_properties(){
			return this.article_properties;
		}

		public void setArticle_properties(Set<Articles_Properties> articles_Properties){
			this.article_properties=articles_Properties;
			
		}
		
		public Set<Directory> getUser_directories(){
			return this.user_directories;
		}

		public void setUser_directories(Set<Directory> directories){
			this.user_directories=directories;
		}
		public void addDierctory(Directory dir){
			this.user_directories.add(dir);
		}
		public Set<Feed> getUser_feeds(){
			return this.user_feeds;
		}

		public void setUser_feeds(Set<Feed> feeds){
			this.user_feeds=feeds;
		}
		
		public void addFeed(Feed feed){
			this.user_feeds.add(feed);
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




