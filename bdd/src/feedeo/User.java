package feedeo;

import java.util.Collection;



public class User extends HibernateObject {

		private Integer id;
		private String name;
		private String lastname;
		
		private String login;
		//private String password;
		
		//private Collection<Preference> preferences;
		
		public User(){
		}
		public Integer getId() {
		return id;
		}
		public void setId(Integer id) {
		this.id = id;
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
		
		
		/*public Collection<Preference> getPreferences() {
			return preferences;
			}
		
		public void setPreferences(Collection<Preference> preferences) {
			this.preferences = preferences;
			}
		
		*/
		
		
		public void createUser() {
			HibernateObject.create(this);	
		}
		
		
		}


