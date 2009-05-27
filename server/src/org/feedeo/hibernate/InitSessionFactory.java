package org.feedeo.hibernate;

import java.io.IOException;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author hennebrueder
 * 
 *         This class garanties that only one single SessionFactory is
 *         instanciated and that the configuration is done thread safe as
 *         singleton. Actually it only wraps the Hibernate SessionFactory. You
 *         are free to use any kind of JTA or Thread transactionFactories.
 * 
 *         jhominal: This class has been refactored to use an enum rather than a
 *         class with a static instance and a private constructor to enforce
 *         singleton, as recommended in "Effective Java 2nd edition"
 */
public enum InitSessionFactory {
	/**
	 * This object contains a sessionFactory to which the operations will be
	 * forwarded.
	 */
	instance;

	private org.hibernate.SessionFactory sessionFactory;

	static {
		// Annotation and XML
		// sessionFactory = new
		// AnnotationConfiguration().configure().buildSessionFactory();
		// XML only
		// sessionFactory = new
		// Configuration().configure().buildSessionFactory();

		Configuration config = new Configuration();
		// ClassLoader loader = InitSessionFactory.class.getClassLoader();
		// URL xmlConfFile = loader.getResource("hibernate.cfg.xml");
		// URL propertiesFile = loader.getResource("hibernate.properties");
		// config.configure(xmlConfFile);

		config.configure("hibernate.cfg.xml");
		Properties extraProperties = new Properties();
		try {
			extraProperties.load(InitSessionFactory.class.getClassLoader()
					.getResourceAsStream("hibernate.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("File 'hibernate.properties' not found at the "
					+ "classpath root. If you haven't already, please "
					+ "add such a file to the src folder, using "
					+ "hibernate.properties.sample as an example.");
			// Thread.currentThread().interrupt();
		}
		config.mergeProperties(extraProperties);

		System.out.println(config.getProperties());

		instance.sessionFactory = config.buildSessionFactory();
	}

	/**
	 * @return the unique session instance
	 */
	public static SessionFactory getInstance() {
		return instance.sessionFactory;
	}

	/**
	 * Opens a session and will not bind it to a session context
	 * 
	 * @return the session
	 */
	public Session openSession() {
		return sessionFactory.openSession();
	}

	/**
	 * Returns a session from the session context. If there is no session in the
	 * context it opens a session, stores it in the context and returns it. This
	 * factory is intended to be used with a hibernate.cfg.xml including the
	 * following property <property
	 * name="current_session_context_class">thread</property> This would return
	 * the current open session or if this does not exist, will create a new
	 * session
	 * 
	 * @return the session
	 */
	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * Does the same as getCurrentSession(), only in a static method.
	 * 
	 * @return the session
	 */
	public static Session getSession() {
		return instance.getCurrentSession();
	}

	/**
	 * closes the session factory
	 */
	public static void close() {
		if (instance.sessionFactory != null)
			instance.sessionFactory.close();
		instance.sessionFactory = null;
	}
}