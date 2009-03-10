package feedeo;

import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hibernate.InitSessionFactory;

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
		
		createPreference(preference1);
		User user6= new User();
		user6.setLogin("coucou");
		HibernateObject.create(user6);
		
		createUser(user1); 
		createUser(user2);
		createUser(user3);
		createUser(user4);
		createUser(user5);

		listUser();
		System.out.print("_______________");
		deleteUser(user2);
		listUser();
		user3.setName("amal");
		System.out.print("_______________");
		updateUser(user3);
		listUser();
		
	}
	
	public static void createUser(User u) {
		HibernateObject.create(u);	
	}
	
	public static void deleteUser(User u) {
		HibernateObject.delete(u);	
	}
	
	public static void updateUser(User u) {
		HibernateObject.update(u);	
	}

	public static void createPreference(Preference preference) {
		HibernateObject.create(preference);	
	}
	
	
	
	
	private static void listUser() {
		Transaction tx = null;
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		try {
			tx = session.beginTransaction();
			List users = session.createQuery("select name from User as name")
					.list();
			for (Iterator iter = users.iterator(); iter.hasNext();) {
				User element = (User) iter.next();
				System.out.print(element.getName() +"\n");
				logger.debug("{}", element);
			}
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					logger.debug("Error rolling back transaction");
				}
				// throw again the first exception
				throw e;
			}
		}
	}


}
