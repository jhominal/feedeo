package org.feedeo;

import java.util.Date;
import java.util.Set;

import org.feedeo.util.HibernateObject;

import com.sun.syndication.feed.synd.SyndFeed;

public class Feed extends HibernateObject {
	private String title;
	private String url;
	private String link;
	private Date pubDate;
	public SyndFeed feedOrig;
//	private long idDirOrig // C'est quoi ce truc ?
	
	private Set<Article> articles;
	private Set<User> readers;
	
	public Feed() {
		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public Set<Article> getArticles() {
		return articles;
	}

	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}

	public Set<User> getReaders() {
		return readers;
	}

	public void setReaders(Set<User> readers) {
		this.readers = readers;
	}
	
	
}
