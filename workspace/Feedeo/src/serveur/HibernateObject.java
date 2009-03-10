package serveur;

import hibernate.InitSessionFactory;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class HibernateObject {
  
	final static Logger logger = LoggerFactory.getLogger(TestClient.class);
	
	Integer id;
	
	public HibernateObject(){}
	
	
	
	static void delete(HibernateObject object) {
		Transaction tx = null;
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		try {
			tx = session.beginTransaction();
			session.delete(object);
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
  
	
	static void update(HibernateObject object) {
		Transaction tx = null;
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		try {
			tx = session.beginTransaction();
			session.update(object);
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
	
	
	static void create(HibernateObject object) {
		Transaction tx = null;
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		try {
			tx = session.beginTransaction();
			session.save(object);
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
