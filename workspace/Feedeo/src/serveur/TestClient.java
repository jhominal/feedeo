package serveur;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TestClient {
	final static Logger logger = LoggerFactory.getLogger(TestClient.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		User user1 = new User("meriam","sekkat","mimi","tintin20","merian@yahoo.fr");
		User user2 = new User("ghita","sekkat","tita","tite200","merian@yahoo.fr");
		
		/** In a first time, two preferences: color and police size*/

		 Preference color= new Preference("color");
	 	 Preference police=new Preference ("police");
	 	 
	 	 UserPreferencePK pk=new UserPreferencePK(user1, color);
	 	 UserPreference up= new UserPreference(pk,"vert");
	 	 
	 	 
	 	 color.createPreference();
	 	 police.createPreference();
		 user1.createUser();
		 user2.createUser();
		 up.createUserPreference();
		 
         
		 
       	
	     
		
		
		
	HibernateObject.listObject("select name from User as name");
		

/*		HibernateObject.listUser("select name from User as name");
		//System.out.print("_______________");
		user2.deleteUser();
		//listUser("select name from User as name");
		user3.setName("amal");
		System.out.print("_______________");
		user3.updateUser();
		//listUser("select name from User as name");
		*/
	}
	
	
	
	
	
	
}
