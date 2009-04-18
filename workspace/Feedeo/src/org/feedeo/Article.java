package org.feedeo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.feedeo.clientcomm.JsonObjectSerializable;
import org.feedeo.hibernate.HibernateObject;
import org.feedeo.hibernate.ObjSession;

/**
 * This class models an article, such as found in an RSS/Atom syndication file.
 * 
 * @author Feedeo Team
 */
public class Article extends HibernateObject implements JsonObjectSerializable {

	private String title;
	private String link;
	private boolean reliablePubDate;
	private Date pubDate;
	private Feed sourceFeed;
	private String author;

	private String summary;
	private String content;

	// private Set<String> categories;

	/**
	 * Default constructor.
	 */
	public Article() {
		super();
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link
	 *            the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * This boolean tells whether the pubDate for this article was found in the
	 * feed, or if the pubDate has been filled by Feedeo.
	 * 
	 * Should the latter be true, the pubDate property should not be used for
	 * comparisons.
	 * 
	 * @return the reliablePubDate
	 */
	public boolean isReliablePubDate() {
		return reliablePubDate;
	}

	/**
	 * @param reliablePubDate
	 *            the reliablePubDate to set
	 */
	public void setReliablePubDate(boolean reliablePubDate) {
		this.reliablePubDate = reliablePubDate;
	}

	/**
	 * @return the pubDate
	 */
	public Date getPubDate() {
		return pubDate;
	}

	/**
	 * @param pubDate
	 *            the pubDate to set
	 */
	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	/**
	 * @return the sourceFeed
	 */
	public Feed getSourceFeed() {
		return sourceFeed;
	}

	/**
	 * @param sourceFeed
	 *            the sourceFeed to set
	 */
	public void setSourceFeed(Feed sourceFeed) {
		this.sourceFeed = sourceFeed;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author
	 *            the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @param summary
	 *            the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		if (isReliablePubDate())
			result = prime * result
					+ ((pubDate == null) ? 0 : pubDate.hashCode());
		result = prime * result
				+ ((sourceFeed == null) ? 0 : sourceFeed.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		Article other = (Article) obj;

		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;

		if (link == null) {
			if (other.link != null)
				return false;
		} else if (!link.equals(other.link))
			return false;

		// Use the pubDate in the hashCode if and only if both entries have
		// reliable publication dates.
		if (isReliablePubDate() && other.isReliablePubDate()) {
			if (pubDate == null) {
				if (other.pubDate != null)
					return false;
			} else if (!pubDate.equals(other.pubDate))
				return false;
		}

		if (sourceFeed == null) {
			if (other.sourceFeed != null)
				return false;
		} else if (!sourceFeed.equals(other.sourceFeed))
			return false;

		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;

		return true;
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
		result.put("title", this.getTitle());
		result.put("author", this.getAuthor());
		result.put("summary", this.getSummary());
		result.put("content", this.getContent());
		// Nécessaire de passer du timestamp java en millisecondes à un
		// timestamp en secondes pour extJS.
		result.put("date", (this.getPubDate().getTime() / 1000L));
		result.put("url", this.getLink());
		// ArrayList<String> categories = new ArrayList<String>();
		// add categories
		// result.put("categories", categories);

		// TODO Method stub - Ajouter categories
		return result;
	}

	/* (non-Javadoc)
	 * @see org.feedeo.hibernate.HibernateObject#getReference()
	 */
	@Override
	protected ObjSession getReference() {
		return sourceFeed;
	}

}
