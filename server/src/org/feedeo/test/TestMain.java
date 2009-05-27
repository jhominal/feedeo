package org.feedeo.test;

import static org.feedeo.hibernate.InitSessionFactory.getSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.feedeo.*;
import org.feedeo.syndication.FeedReader;
import org.stringtree.json.JSONWriter;

/**
 * This class implements various tests, in order
 * to check how the java application is functioning.
 * 
 * @author Feedeo Team
 *
 */
@SuppressWarnings({ "unused" })
public class TestMain {

	/**
	 * This main method runs the tests.
	 * 
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
//		testSyndication();
		testPersistence();
		
		User u1 = User.getUserByLogin("toto");
		
		getSession().beginTransaction();
		
		String pref1 = (String) u1.getPreferences().get("pref-string");
		Boolean pref2 = (Boolean) u1.getPreferences().get("pref-bool");
		Long pref3 = (Long) u1.getPreferences().get("pref-int");
		HashMap<String, String> pref4 = (HashMap<String, String>) u1.getPreferences().get("pref-map");
		
		System.out.println(pref1);
		System.out.println(pref2);
		System.out.println(pref3);
		System.out.println(pref4.get("1"));
		System.out.println(pref4.get("2"));
		
		getSession().getTransaction().commit();
		
	}
	
	/**
	 * This method tests if the persistent objects
	 * are mapped to the database correctly.
	 */
	public static void testPersistence() {
		getSession().beginTransaction();
		User u1 = new User();
		u1.setLogin("toto");
		u1.setPassword("bidoule");
		u1.setEmail("toto@feedeo.org");
		
		u1.setFirstName("toto");
		u1.setLastName("lateteatoto");
		
		u1.getPreferences().put("pref-string","valString");
		u1.getPreferences().put("pref-string-bool","false");
		u1.getPreferences().put("pref-bool", false);
		u1.getPreferences().put("pref-string-int","15");
		u1.getPreferences().put("pref-int", 15L);
		HashMap<String, String> configObject = new HashMap<String,String>();
		configObject.put("1", "val1");
		configObject.put("2", "val2");
		u1.getPreferences().put("pref-map", configObject);
		
		getSession().saveOrUpdate(u1);
		getSession().getTransaction().commit();
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
		getSession().saveOrUpdate(f1);
		// Flux RSS 2.0
		Feed f2 = FeedReader.checkout("http://feedproxy.google.com/TechCrunch");
		getSession().saveOrUpdate(f2);
		// Flux Atom 1.0
//		Feed f3 = FeedReader.checkout("http://www.joel.lopes-da-silva.com/index.php?format=feed&type=atom");
//		f3.saveOrUpdate();
	}
}
