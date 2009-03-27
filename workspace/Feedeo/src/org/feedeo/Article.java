package org.feedeo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.feedeo.util.HibernateObject;
import org.feedeo.util.JsonObjectSerializable;

public class Article extends HibernateObject implements JsonObjectSerializable {
	
	private String title;
	
	private String link;
	
	private Date date;
	
	private String content;
	
	private Feed sourceFeed;
	
	private String summary;
	
	private String author;
//	Les catégories d'un articles sont définies dans 
//	le flux ou par l'utilisateur ?
//	private Set<String> categories;
	
	public Article() {
		
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Feed getSourceFeed() {
		return sourceFeed;
	}

	public void setSourceFeed(Feed sourceFeed) {
		this.sourceFeed = sourceFeed;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	//TODO Methode a revoir
	public Map<String, Object> toMap(boolean shallow) {
		/*
		 * 	
		 *	this.link=url;
		 *	this.date=date;
		 *	this.summary=summary;
		 *	this.author=author;
		 *	this.owner=owner;
		 */
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("id", ""+this.getId());
		result.put("title",this.getTitle());
		result.put("author",this.getAuthor());
		result.put("summary", this.getSummary());
		result.put("content", this.getSummary()); //pour au moins avoir qqch pour la d�mo
		result.put("date", this.getDate().getTime());
//		ArrayList<String> categories = new ArrayList<String>();
		//add categories
//		result.put("categories", categories);
		
		// TODO Method stub - Ajouter catégories
		return result;
	}

}
