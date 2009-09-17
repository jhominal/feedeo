package org.feedeo.test;

import static org.feedeo.hibernate.InitSessionFactory.getSession;

import java.util.HashMap;

import org.junit.Test;

import org.feedeo.hibernate.Queries;
import org.feedeo.model.user.User;
import org.feedeo.syndication.FeedFetcher;

/**
 * This class implements various tests, in order to check how the java
 * application is functioning.
 * 
 * @author Feedeo Team
 * 
 */
public class TestMain {

  /**
   * This main method runs the tests.
   * 
   * @param args
   * @throws Exception 
   */
  @Test
  public void run() throws Exception {
    getSession();
    testSyndication();
    testPersistence();

    getSession().beginTransaction();
    
    User u1 = Queries.getUserByLogin("toto");


    String pref1 = (String) u1.getPreferences().get("pref-string");
    Boolean pref2 = (Boolean) u1.getPreferences().get("pref-bool");
    Long pref3 = (Long) u1.getPreferences().get("pref-int");
    @SuppressWarnings("unchecked")
    HashMap<String, String> pref4 = (HashMap<String, String>) u1
        .getPreferences().get("pref-map");

    System.out.println(pref1);
    System.out.println(pref2);
    System.out.println(pref3);
    System.out.println(pref4.get("1"));
    System.out.println(pref4.get("2"));

    getSession().getTransaction().commit();

  }

  /**
   * This method tests if the persistent objects are mapped to the database
   * correctly.
   */
  public static void testPersistence() {
    getSession().beginTransaction();
    User u1 = new User();
    u1.setLogin("toto");
    u1.setPassword("bidoule");
    u1.setEmail("toto@feedeo.org");

    u1.setFirstName("toto");
    u1.setLastName("lateteatoto");

    u1.getPreferences().put("pref-string", "valString");
    u1.getPreferences().put("pref-string-bool", "false");
    u1.getPreferences().put("pref-bool", Boolean.FALSE);
    u1.getPreferences().put("pref-string-int", "15");
    u1.getPreferences().put("pref-int", Long.valueOf(15L));
    HashMap<String, String> configObject = new HashMap<String, String>();
    configObject.put("1", "val1");
    configObject.put("2", "val2");
    u1.getPreferences().put("pref-map", configObject);

    getSession().saveOrUpdate(u1);
    getSession().getTransaction().commit();
  }

  /**
   * This method tests if feeds are imported correctly
   * @throws Exception 
   */
  public static void testSyndication() throws Exception {
    getSession().beginTransaction();
    // Flux RSS 1.0
    FeedFetcher.getFetcher("http://rss.slashdot.org/Slashdot/slashdot").update();
    // Flux RSS 2.0
    FeedFetcher.getFetcher("http://feeds.arstechnica.com/arstechnica/everything").update();
    
    // Flux Atom 1.0
    
    getSession().getTransaction().commit();
  }
}
