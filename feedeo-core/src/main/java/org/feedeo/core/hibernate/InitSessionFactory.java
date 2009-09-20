package org.feedeo.core.hibernate;

import java.io.IOException;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import org.feedeo.core.model.feed.*;
import org.feedeo.core.model.user.ArticleProperties;
import org.feedeo.core.model.user.Folder;
import org.feedeo.core.model.user.User;

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
  
  INSTANCE;
  
  private org.hibernate.SessionFactory sessionFactory;
  private boolean initialized = false;

  static {
    init();
  }
  
  public static void init()
  {
    if (!INSTANCE.initialized)
    {
      AnnotationConfiguration config = new AnnotationConfiguration();

      config.configure("hibernate.cfg.xml");
      
      config.addAnnotatedClass(Content.class);
      config.addAnnotatedClass(Enclosure.class);
      config.addAnnotatedClass(ImageReference.class);
      config.addAnnotatedClass(Link.class);
      
      config.addAnnotatedClass(Article.class);
      config.addAnnotatedClass(Category.class);
      config.addAnnotatedClass(Feed.class);
      config.addAnnotatedClass(Writer.class);
      
      config.addAnnotatedClass(User.class);
      config.addAnnotatedClass(Folder.class);
      config.addAnnotatedClass(ArticleProperties.class);
      
      INSTANCE.sessionFactory = config.buildSessionFactory();
      INSTANCE.initialized = true;
    }
    
  }

  /**
   * @return the unique session instance
   */
  public static SessionFactory getInstance() {
    return INSTANCE.sessionFactory;
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
  public static Session getSession() {
    return INSTANCE.sessionFactory.getCurrentSession();
  }

  /**
   * closes the session factory
   */
  public static void close() {
    if (INSTANCE.sessionFactory != null) {
      INSTANCE.sessionFactory.close();
    }
    INSTANCE.sessionFactory = null;
  }
}