package org.feedeo.test;

import org.feedeo.*;
import org.feedeo.syndication.FeedReader;

/**
 * This class implements various tests, in order
 * to check how the java application is functioning.
 * 
 * @author Feedeo Team
 *
 */
public class TestMain {

	/**
	 * This main method runs the tests.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testSyndication();
		testPersistence();
		
		User u1 = User.getUserByLogin("toto");
		
		u1.getSession().beginTransaction();
		
		Feed f2 = Feed.getFeedByUrl("http://fcargoet.evolix.net/feed/atom/");
		
		FeedReader.update(f2);
		
		User u2 = (User) u1.getSession().merge(u1);
		
		u2.getRootDirectory().subscribeFeed(f2);
		
		System.out.println(u2.getRootDirectory().toMap(true).get("articles"));
		
		u1.getSession().getTransaction().commit();
	}
	
	/**
	 * This method tests if the persistent objects
	 * are mapped to the database correctly.
	 */
	public static void testPersistence() {
		User u1 = new User();
		u1.setLogin("toto");
		u1.setPassword("bidoule");
		u1.setEmail("toto@feedeo.org");
		
		u1.setFirstName("toto");
		u1.setLastName("lateteatoto");
		
		u1.getPreferences().put("pref1","val1");
		
		u1.saveOrUpdate();
		
//		u1.getSession().beginTransaction();
//		
//		Feed f1 = Feed.getFeedByUrl("http://feedproxy.google.com/TechCrunch");
//		
//		Directory d1 = new Directory();
//		d1.setTitle("premier dossier");
//		
//		u1.getRootDirectory().attachDirectory(d1);
//		d1.subscribeFeed(f1);
//		u1.getSession().getTransaction().commit();
//		
//		u1.saveOrUpdate();
	}
	
	/**
	 * This method tests if feeds are imported correctly
	 */
	public static void testSyndication() {
		// Flux RSS 0.92
		Feed f1 = FeedReader.checkout("http://fcargoet.evolix.net/feed/atom/");
		f1.saveOrUpdate();
		// Flux RSS 2.0
		Feed f2 = FeedReader.checkout("http://feedproxy.google.com/TechCrunch");
		f2.saveOrUpdate();
		// Flux Atom 1.0
//		Feed f3 = FeedReader.checkout("http://www.joel.lopes-da-silva.com/index.php?format=feed&type=atom");
//		f3.saveOrUpdate();
	}
}
