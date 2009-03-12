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
		User user1 = new User();
		user1.setName("meriam");
		user1.setLastname("sekkat");
		user1.setLogin("mimi");
		User user2 = new User();
		user2.setName("zak");
		user2.setLastname("salim");
		User user3 = new User();
		user3.setName("abla");
		user3.setLastname("ben");
		User user4 = new User();
		user4.setName("salwa");
		user4.setLastname("bousfiha");
		user4.setLogin("sliwa");
		User user5 = new User();
		user5.setName("florian");
		user5.setLastname("cargoet");
		user5.setLogin("flori");
		
		Preference preference1= new Preference();
		preference1.setName("Couleur");
		
		
		
		preference1.createPreference();
		
		//Preference preference2= new Preference();
		//preference2.setName("mode");
		
		Set<Preference> preferences1= new HashSet<Preference>();
		preferences1.add(preference1);
		//preferences1.add(preference2);
    	
		
		User user6= new User();
		user6.setLogin("coucou");
		user6.setName("salut");
		
		user6.setPreferences(preferences1);
		
		/*user2.createUser();
		user3.createUser();
		user4.createUser();	
		user5.createUser();
		*/
		user6.createUser();
		
	HibernateObject.listUser("select name from User as name");
		/*

		HibernateObject.listUser("select name from User as name");
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
