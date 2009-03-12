package serveur;



import java.util.Set;




public class User extends HibernateObject {

		private long idUser;
		private String name;
		private String lastname;
		
		private String login;
		//private String password;
		
		private Set<Preference> preferences;
		
		public User(){
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
		
		
		public Set<Preference> getPreferences() {
			return preferences;
			}
		
		public void setPreferences(Set<Preference> preferences) {
			this.preferences = preferences;
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


