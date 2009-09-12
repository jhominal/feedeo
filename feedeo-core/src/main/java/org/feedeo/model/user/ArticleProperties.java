package org.feedeo.model.user;

/**
 * Models data specific to a user and an article.
 * 
 * This class should be considered a stub and improved in every useful way.
 * 
 * @author Feedeo Team
 * 
 */
public class ArticleProperties {

  private Long    id;

  private boolean alreadyRead;
  private boolean important;

  private User    owner;

  /**
   * Default constructor.
   */
  public ArticleProperties() {
    super();
    alreadyRead = false;
    important = false;
  }

  /**
   * @param id
   *          the id to set
   */
  @SuppressWarnings("unused")
  private void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the id
   */
  public Long getId() {
    return id;
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

  /**
   * @return the owner
   */
  public User getOwner() {
    return owner;
  }

  /**
   * @param owner
   *          the owner to set
   */
  public void setOwner(User owner) {
    this.owner = owner;
  }

}
