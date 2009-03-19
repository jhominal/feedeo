package serveur;


import java.util.HashSet;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TestClient {
	final static Logger logger = LoggerFactory.getLogger(TestClient.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		User user1 = new User("meriam","sekkat","mimi","tintin20");
		User user2 = new User("ghita","sekkat","tita","tite200");
		
		
		Preference preference1= new Preference();
		preference1.setName("Couleur");
		
		
		
		preference1.createPreference();
		
		Preference preference2= new Preference();
		preference2.setName("Police");
		preference2.createPreference();
		
		Set<Preference> preferences1= new HashSet<Preference>();
		preferences1.add(preference1);
		//preferences1.add(preference2);
    	
		
		user1.setPreferences(preferences1);
		
	   user2.createUser();
		/*user3.createUser();
		user4.createUser();	
		user5.createUser();
		*/
		user1.createUser();
		
	HibernateObject.listUser("select name from User as name");
		

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
