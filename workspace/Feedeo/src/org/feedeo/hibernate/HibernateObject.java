package org.feedeo.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

/**
 * This class specifies a few things that are common to all of our Hibernate
 * entities. It also gives a few methods for easier saving/updating/deletion.
 * 
 * @author Feedeo Team
 */
// TODO classe a revoir integralement.
public abstract class HibernateObject {

	// final static Logger logger = LoggerFactory.getLogger(TestClient.class);

	/**
	 * Default constructor.
	 */
	public HibernateObject() {
		super();
	}

	private Long id;

	/**
	 * Sets the object's id in the corresponding table in the database. Its
	 * visibility, protected, means that only Hibernate will access it.
	 * 
	 * @param id
	 */
	protected void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the object's id in the corresponding table in the database.
	 */
	protected Long getId() {
		return id;
	}

	/**
	 * gets the session appropriate for the given object for now it refers to a
	 * singleton, but it should not stay this way.
	 * 
	 * Probably, it would be wise to have a mechanism where every user and every
	 * feed has his/her/its own session
	 * 
	 * @return the session associated to a User or to a Feed.
	 */
	public Session getSession() {
		return InitSessionFactory.getInstance().getCurrentSession();
	}

	/**
	 * Makes a detached object transient. Should not be used with persistent instances.
	 */
	public void delete() {
		// TODO to be redone for multi-session use.
		Transaction tx = null;
		Session session = getSession();
		try {
			tx = session.beginTransaction();
			session.delete(this);
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					// logger.debug("Error rolling back transaction");
				}
				// throw again the first exception
				throw e;
			}
		}
	}

	/**
	 * 
	 */
	public void saveOrUpdate() {
		// TODO to be redone for multi-session use.
		Transaction tx = null;
		Session session = getSession();
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(this);
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null && tx.isActive()) {
				try {
					// Second try catch as the rollback could fail as well
					tx.rollback();
				} catch (HibernateException e1) {
					// logger.debug("Error rolling back transaction");
				}
				// throw again the first exception
				throw e;
			}
		}
	}
}
