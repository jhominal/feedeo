package org.feedeo;

import org.feedeo.hibernate.HibernateObject;

/**
 * Type a description for ArticleContent here.
 * 
 * @author Feedeo Team
 *
 */
public class ArticleContent extends HibernateObject {
	private String summary;
	private String content;

	/**
	 * Default constructor.
	 */
	public ArticleContent() {
		super();
	}

	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @param summary the summary to set
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
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((summary == null) ? 0 : summary.hashCode());
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
		ArticleContent other = (ArticleContent) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (summary == null) {
			if (other.summary != null)
				return false;
		} else if (!summary.equals(other.summary))
			return false;
		return true;
	}

	/**
	 * Synchronizes this instance to a newer version of the (hopefully same)
	 * article.
	 * 
	 * @param newVersion the ArticleContent to synchronize to.
	 */
	public void sync(ArticleContent newVersion) {
		setSummary(newVersion.getSummary());
		setContent(newVersion.getContent());
	}


}
