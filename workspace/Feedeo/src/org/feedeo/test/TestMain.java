package org.feedeo.test;

import org.feedeo.*;

public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testPersistence();
		
	}
	public static void testPersistence() {
		User u1 = new User();
		u1.setLogin("toto");
		u1.setPassword("bidoule");
		u1.setEmail("toto@feedeo.org");
		
		u1.setFirstName("toto");
		u1.setLastName("lateteatoto");
		
		u1.create();
		
		Feed f1 = new Feed();
	}
}
