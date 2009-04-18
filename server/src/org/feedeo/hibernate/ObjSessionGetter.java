package org.feedeo.hibernate;

import org.hibernate.Session;

/**
 * Type a description for ObjSessionGetter here.
 * 
 * @author Feedeo Team
 *
 */
public class ObjSessionGetter {
	
	/**
	 * @param object
	 * @return the Hibernate Session associated to object
	 */
	public static Session get(ObjSession object) {
		return InitSessionFactory.getInstance().getCurrentSession();
	}

}
