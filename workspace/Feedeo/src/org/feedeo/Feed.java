package org.feedeo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.feedeo.hibernate.HibernateObject;

/**
 * This class models feeds in our application.
 * 
 * @author Feedeo Team
 *
 */
public class Feed extends HibernateObject {
	private String title;
	private String url;
	private String link;
	private Date pubDate;
	private String description;
	
	private Set<Article> articles;
	private Set<User> readers;
	
	/**
	 * Default constructor.
	 */
	public Feed() {
		super();
		articles = new HashSet<Article>();
		readers = new HashSet<User>();
	}

	/**
	 * @return the feed's title.
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
	 * @return the feed's url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the reference link (original site)
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * If this data is unspecified/unreadable in the
	 * source feed, it should be set to now at the time
	 * of the feed's checkout by the server.
	 * 
	 * @return the publication date
	 */
	public Date getPubDate() {
		return pubDate;
	}

	/**
	 * @param pubDate
	 */
	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	/**
	 * @return the feed's description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return all articles associated to this feed.
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
	 * @return the feed's subscribers
	 */
	public Set<User> getReaders() {
		return readers;
	}

	/**
	 * @param readers
	 */
	public void setReaders(Set<User> readers) {
		this.readers = readers;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Feed other = (Feed) obj;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	/**
	 * Method to use to add an article to the
	 * list (it sets the "sourceFeed" field in
	 * the article itself).
	 * 
	 * @param article the article to add.
	 */
	public void addArticle(Article article) {
		article.setSourceFeed(this);
		this.getArticles().add(article);
	}
	
	/**
	 * Gets a feed by its url.
	 * 
	 * @param url
	 *            the feed's url.
	 * @return a Feed with the corresponding url; if he did not exist in the
	 *         base, all fields except login will be empty.
	 */
	public static Feed getFeedByUrl(String url) {
		Feed refFeed = new Feed();
		refFeed.setUrl(url);
		Feed potentialFeed = (Feed) refFeed.getSession().createQuery(
				"from Feed as feed where feed.url = ?").setString(0, url)
				.uniqueResult();
		if (potentialFeed == null) {
			refFeed.getSession().saveOrUpdate(refFeed);
			return refFeed;
		} else {
			return potentialFeed;
		}
	}
	
	/**
	 * This method puts all of a feed's articles into a directory.
	 * @param targetDir the directory where the articles should go to.
	 */
	public void putArticles(Directory targetDir) {
		if (targetDir != null) {
			targetDir.getArticles().addAll(getArticles());
		}
	}
}
