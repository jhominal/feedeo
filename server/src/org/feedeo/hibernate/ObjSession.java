package org.feedeo.hibernate;

import org.hibernate.Session;

/**
 * Type a description for ObjSession here.
 * 
 * @author Feedeo Team
 *
 */
public interface ObjSession {
	/**
	 * gets the session appropriate for the given object; for now it refers to a
	 * singleton, but it should not stay this way.
	 * 
	 * Probably, it would be wise to have a mechanism where every user and every
	 * feed has his/her/its own session
	 * 
	 * @return a Session that will be unique to this object.
	 */
	public Session getObjectSession();
}
