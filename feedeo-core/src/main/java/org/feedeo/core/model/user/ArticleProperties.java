package org.feedeo.core.model.user;

import javax.persistence.Embeddable;

/**
 * Models data specific to a user and an article.
 * 
 * This class should be considered a stub and improved in every useful way.
 * 
 * @author Feedeo Team
 * 
 */
@Embeddable
public class ArticleProperties {

  private boolean alreadyRead;
  private boolean important;

  /**
   * Default constructor.
   */
  public ArticleProperties() {
    super();
    alreadyRead = false;
    important = false;
  }

  /**
   * @return the alreadyRead boolean marker
   */
  public boolean isAlreadyRead() {
    return alreadyRead;
  }

  /**
   * @param alreadyRead
   *          the value with which to set the alreadyRead marker
   */
  public void setAlreadyRead(boolean alreadyRead) {
    this.alreadyRead = alreadyRead;
  }

  /**
   * @return the important boolean marker
   */
  public boolean isImportant() {
    return important;
  }

  /**
   * @param important
   *          the value with which to set the important marker
   */
  public void setImportant(boolean important) {
    this.important = important;
  }

}
