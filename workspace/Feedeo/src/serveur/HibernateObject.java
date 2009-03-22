package serveur;

import java.util.Iterator;
import java.util.List;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class HibernateObject {
  
	final static Logger logger = LoggerFactory.getLogger(TestClient.class);
	
	
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
	@SuppressWarnings("unchecked")
	static List<HibernateObject> listObject(String requete) {
	//static void listUser(String requete) {
		List<HibernateObject> response=null;
		Transaction tx = null;
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		try {
			tx = session.beginTransaction();
			response=session.createQuery(requete).list();
			//List<User> users = session.createQuery(requete).list();
			//for (Iterator<User> iter = users.iterator(); iter.hasNext();) {
			//	User element = iter.next();
				//System.out.print(element.getName() +"\n");
				//logger.debug("{}", element);
			//}
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
	return response;
	}


/*
	
	public boolean assocUserPreferences(Integer _idUser, Long _idRole)
	   throws HibernateException    {
 	boolean retour=true;
 	Role rol=new Role();
 	Utilisateur2 util=new Utilisateur2();
 	Session session = sessFactory.openSession();
     Transaction tx = null;
     try {
	tx = session.beginTransaction();
 	Query q1=session.createQuery("from Utilisateur2 where idUser = :idUser");
 	Query q2 = session.createQuery("from Role where idRole = :idRole");
 	q1.setParameter("idUser", _idUser);
 	q2.setParameter("idRole", _idRole);
	util=(Utilisateur2) q1.list().get(0);
 	System.out.println("Login = "+util.getLogin());
	rol=(Role) q2.list().get(0);
 	System.out.println ("Nom du Role ="+rol.getNom());
	((Set)util.getRoles()).add((Role) rol);
	((Set)rol.getUtilisateur2s()).add((Utilisateur2) util);
	session.save(rol);
	session.save(util);
         tx.commit();
     }
     catch (HibernateException he) {
		retour=false;
         if (tx!=null) tx.rollback();
         throw he;
	
     }
     finally {
         session.close();
     }
     return retour;
	}
 }
*/
}
