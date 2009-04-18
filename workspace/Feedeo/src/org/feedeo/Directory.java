package org.feedeo;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.feedeo.clientcomm.JsonObjectSerializable;
import org.feedeo.hibernate.HibernateObject;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 * Directory reprensents the "folders" in which feeds can be stored.
 * 
 * @author Feedeo Team
 * 
 */
public class Directory extends HibernateObject implements
		JsonObjectSerializable {

	private String title;

	private Date lastUpdate;

	private Set<Directory> subDirectories;
	private Set<Feed> feeds;
	private Set<Article> articles;

	private User owner;

	/**
	 * Default constructor.
	 */
	public Directory() {
		super();
		lastUpdate = GregorianCalendar.getInstance().getTime();
		subDirectories = new HashSet<Directory>();
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
	public Date getLastUpdate() {
		return lastUpdate;
	}

	/**
	 * @param lastUpdate
	 *            the lastUpdate to set
	 */
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	/**
	 * Returns the feeds for which this folder is the default incoming folder.
	 * 
	 * @return the feeds
	 */
	public Set<Feed> getFeeds() {
		return feeds;
	}

	/**
	 * @param feeds
	 *            the feeds to set
	 */
	public void setFeeds(Set<Feed> feeds) {
		this.feeds = feeds;
	}

	/**
	 * @return the directory's articles
	 */
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
	 * @return the directory's subDirectories.
	 */
	public Set<Directory> getSubDirectories() {
		return subDirectories;
	}

	/**
	 * @param subDirectories
	 */
	public void setSubDirectories(Set<Directory> subDirectories) {
		this.subDirectories = subDirectories;
	}

	/**
	 * @return the directory's owner
	 */
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
	 * This function makes a given directory a child of this directory.
	 * 
	 * @param directory
	 */
	public void attachDirectory(Directory directory) {
		this.getSubDirectories().add(directory);
		directory.setOwner(this.getOwner());
	}

	/**
	 * Lets the owner subscribe to a Feed and makes this folder a destination
	 * for this feed.
	 * 
	 * @param feed
	 *            the feed to subscribe to
	 */
	public void subscribeFeed(Feed feed) {
		this.getFeeds().add(feed);
		this.getArticles().addAll(feed.getArticles());
		feed.getFolders().add(this);
	}

	/**
	 * Lets the User cancel his subscription to a Feed.
	 * 
	 * @param feed
	 *            the feed to cancel.
	 */
	public void unsubscribeFeed(Feed feed) {
		this.getFeeds().remove(feed);
		feed.getFolders().remove(this);
	}

	/**
	 * This functions updates all of a folder's default feeds, and puts the
	 * articles published after the lastUpdate date into this folder.
	 */
	@SuppressWarnings("unchecked")
	public void updateArticles() {
		Date attemptUpdate = GregorianCalendar.getInstance().getTime();

		if (!getFeeds().isEmpty()) {
			Criteria criteria = getSession().createCriteria(Article.class,
					"article");
			criteria.add(Restrictions.between("pubDate", getLastUpdate(),
					attemptUpdate));
			criteria.add(Restrictions.in("sourceFeed", getFeeds()));
			List<Article> resultList = criteria.list();
			getArticles().addAll(resultList);
		}
		setLastUpdate(attemptUpdate);
	}

	/**
	 * @return The list of the children's maps.
	 */
	public List<Map<String, Object>> getChildrenMap() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (Directory directory : getSubDirectories())
			result.add(directory.toMap(false));
		return result;
	}

	/**
	 * @return a list of the maps of all the articles
	 */
	public List<Map<String, Object>> getAllArticlesMap() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (Article article : getArticles())
			result.add(getOwner().articleMap(article));
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.feedeo.clientcomm.JsonObjectSerializable#toMap(boolean)
	 */
	@Override
	public Map<String, Object> toMap(boolean deep) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("id", this.getId().toString());
		result.put("text", this.getTitle());
		result.put("leaf", false);
		if (deep) {
			result.put("children", getChildrenMap());
		}
		if (deep) {
			result.put("articles", getAllArticlesMap());
		}
		return result;
	}

}
