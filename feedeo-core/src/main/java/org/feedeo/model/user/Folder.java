package org.feedeo.model.user;

import static org.feedeo.hibernate.InitSessionFactory.getSession;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import org.feedeo.clientcomm.Mappable;
import org.feedeo.model.feed.Article;
import org.feedeo.model.feed.Feed;

/**
 * Folder reprensents the "folders" in which feeds can be stored.
 * 
 * @author Feedeo Team
 * 
 */
@Entity
public class Folder implements Mappable {
  private Long         id;

  private String       title;

  private Date         lastUpdate;

  private Set<Folder>  subFolders;

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
    subFolders = new HashSet<Folder>();
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
  public Set<Feed> getFeeds() {
    return Collections.unmodifiableSet(feeds);
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
  public Set<Article> getArticles() {
    return Collections.unmodifiableSet(articles);
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
  public Set<Folder> getSubFolders() {
    return Collections.unmodifiableSet(subFolders);
  }

  /**
   * @param subFolders
   */
  public void setSubFolders(Set<Folder> subFolders) {
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

  /**
   * This functions updates all of a folder's default feeds, and puts the
   * articles published after the lastUpdate date into this folder.
   */
  public void updateArticles() {
    Date attemptUpdate = Calendar.getInstance().getTime();

    if (!feeds.isEmpty()) {
      Criteria criteria = getSession().createCriteria(Article.class, "article");
      criteria.add(Restrictions.between("downloadDate", getLastUpdate(),
          attemptUpdate));
      criteria.add(Restrictions.in("sourceFeed", feeds));
      // Safe suppress warning: The criteria instance has been configured to
      // only search for instances of the Article class.
      @SuppressWarnings("unchecked")
      List<Article> resultList = criteria.list();
      articles.addAll(resultList);
    }
    setLastUpdate(attemptUpdate);
  }

  /**
   * @return The list of the children's maps.
   */
  @Transient
  public List<Map<String, Object>> getChildrenMap() {
    List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
    for (Folder directory : subFolders) {
      result.add(directory.toMap(false));
    }
    return result;
  }

  /**
   * @param articlesList
   * @return a list of maps qualified with the owner's properties corresponding
   *         to the articles referenced by the list.
   */
  public List<Map<String, Object>> getArticlesMap(Set<Article> articlesList) {
    List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
    for (Article article : articlesList) {
      result.add(getOwner().articleMap(article));
    }
    return result;
  }

  /**
   * {@inheritDoc}
   * 
   * @see org.feedeo.clientcomm.Mappable#toMap(boolean)
   */
  @Override
  public Map<String, Object> toMap(boolean deep) {
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("id", getId().toString());
    result.put("text", getTitle());
    result.put("leaf", Boolean.FALSE);
    if (deep) {
      result.put("children", getChildrenMap());
    }
    if (deep) {
      result.put("articles", getArticlesMap(getArticles()));
    }
    return result;
  }
}
