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
		// testPersistence();
		System.out.print(new Long(1L).toString());
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
		
		Feed f1 = new Feed();
		f1.setTitle("flux bousique");
		f1.saveOrUpdate();
		
		Directory d1 = new Directory();
		d1.setTitle("premier dossier");
		d1.saveOrUpdate();
		
		u1.getRootDirectory().attachDirectory(d1);
		u1.subscribeFeed(f1, d1);
		
		u1.saveOrUpdate();
	}
	
	/**
	 * This method tests if feeds are imported correctly
	 */
	public static void testSyndication() {
		// Flux RSS 0.92
		Feed f1 = FeedReader.checkout("http://fcargoet.evolix.net/feed/rss/");
		f1.saveOrUpdate();
		// Flux RSS 2.0
		Feed f2 = FeedReader.checkout("http://feedproxy.google.com/TechCrunch");
		f2.saveOrUpdate();
		// Flux Atom 1.0
//		Feed f3 = FeedReader.checkout("http://www.joel.lopes-da-silva.com/index.php?format=feed&type=atom");
//		f3.saveOrUpdate();
	}
}
