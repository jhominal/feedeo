package org.feedeo.core.model.user;

import java.util.*;

import javax.persistence.*;

import org.feedeo.core.model.feed.Article;
import org.feedeo.core.model.feed.Feed;

/**
 * Folder reprensents the "folders" in which feeds can be stored.
 * 
 * @author Feedeo Team
 * 
 */
@Entity
public class Folder {
  private Long         id;

  private String       title;

  private Date         lastUpdate;

  private List<Folder>  subFolders;

  // Each folder lists the feeds to which it has subscribed. This information is
  // only important when updating articles.
  private Set<Feed>    feeds;
  private Set<Article> articles;

  private User         owner;

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
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Long getId() {
    return id;
  }

  /**
   * Default constructor.
   */
  public Folder() {
    super();
    lastUpdate = Calendar.getInstance().getTime();
    subFolders = new ArrayList<Folder>();
    feeds = new HashSet<Feed>();
    articles = new HashSet<Article>();
  }

  /**
   * @return the directory's title.
   */
  public String getTitle() {
    return title;
  }

  /**
   * @param title
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * @return the lastUpdate
   */
  @Temporal(TemporalType.TIMESTAMP)
  public Date getLastUpdate() {
    return lastUpdate;
  }

  /**
   * @param lastUpdate
   *          the lastUpdate to set
   */
  public void setLastUpdate(Date lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

  /**
   * Returns the feeds for which this folder is the default incoming folder.
   * This view is unmodifiable; you can only change it by using one of the
   * subscribeFeed or unsubscribe Feed methods.
   * 
   * @return the feeds
   */
  @ManyToMany
  @JoinTable(name = "Folder_Feeds")
  public Set<Feed> getFeeds() {
    return feeds;
  }

  /**
   * @param feeds
   *          the feeds to set
   */
  public void setFeeds(Set<Feed> feeds) {
    this.feeds = feeds;
  }

  /**
   * @return a unmodifiable view to the folder's articles
   */
  @ManyToMany
  @JoinTable(name = "Folder_Articles")
  @OrderBy(value = "sortDate desc")
  public Set<Article> getArticles() {
    return articles;
  }

  /**
   * @param articles
   */
  public void setArticles(Set<Article> articles) {
    this.articles = articles;
  }

  /**
   * @return the directory's subFolders.
   */
  @ManyToMany
  @JoinTable(name = "Folder_subFolders")
  @org.hibernate.annotations.IndexColumn(name = "subFolderOrder")
  public List<Folder> getSubFolders() {
    return subFolders;
  }

  /**
   * @param subFolders
   */
  public void setSubFolders(List<Folder> subFolders) {
    this.subFolders = subFolders;
  }

  /**
   * @return the folder's owner
   */
  @ManyToOne
  public User getOwner() {
    return owner;
  }

  /**
   * @param owner
   */
  public void setOwner(User owner) {
    this.owner = owner;
  }

  /**
   * This function makes a given folder a child of this folder.
   * 
   * @param folder
   */
  public void attachFolder(Folder folder) {
    subFolders.add(folder);
    folder.setOwner(getOwner());
  }
  
  /**
   * This method removes a specified folder.
   * 
   * @param folder
   */
  public boolean removeFolder(Folder folder) {
    return subFolders.remove(folder);
  }

  /**
   * Lets the owner subscribe to a Feed and makes this folder a destination for
   * this feed.
   * 
   * @param feed
   *          the feed to subscribe to
   */
  public void subscribeFeed(Feed feed) {
    feeds.add(feed);
    articles.addAll(feed.getArticles());
  }

  /**
   * Lets the User cancel his subscription to a Feed.
   * 
   * @param feed
   *          the feed to cancel.
   */
  public void unsubscribeFeed(Feed feed) {
    feeds.remove(feed);
  }
  
  public void addArticles(Collection<? extends Article> articlesToAdd)
  {
    articles.addAll(articlesToAdd);
  }
}
