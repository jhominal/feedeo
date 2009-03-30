package org.feedeo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.feedeo.clientcomm.JsonObjectSerializable;
import org.feedeo.hibernate.HibernateObject;

/**
 * Directory reprensents the "folders" in which
 * feeds can be stored.
 * 
 * @author Feedeo Team
 *
 */
public class Directory extends HibernateObject implements
		JsonObjectSerializable {
	
	private String title;
	
	private Set<Directory> subDirectories;
	private Set<Article> articles;
	
	private User owner;
	
	/**
	 * Default constructor.
	 */
	public Directory() {
		super();
		articles = new HashSet<Article>();
		subDirectories = new HashSet<Directory>();
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
	 * This function makes a given directory a child of
	 * this directory.
	 * 
	 * @param directory
	 */
	public void attachDirectory(Directory directory) {
		this.getSubDirectories().add(directory);
		directory.setOwner(this.getOwner());
	}


	/* (non-Javadoc)
	 * @see org.feedeo.clientcomm.JsonObjectSerializable#toMap(boolean)
	 */
	@Override
	public Map<String, Object> toMap(boolean deep) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("id", ((Long) this.getId()).toString());
		result.put("text", this.getTitle());
		result.put("leaf", false);
		// TODO Method stub - to be tested...
		if (deep && !this.getSubDirectories().isEmpty()) {
			List<Map<String, Object>> dirsMapList = new ArrayList<Map<String, Object>>(); 
			Iterator<Directory> directorIter = this.getSubDirectories().iterator();
			while (directorIter.hasNext()) {
				Directory currDir = directorIter.next();
				dirsMapList.add(currDir.toMap(false));
			}
			result.put("children", dirsMapList);
		}
		if (deep && !this.getArticles().isEmpty()) {
			List<Map<String, Object>> articlesMapList = new ArrayList<Map<String, Object>>(); 
			Iterator<Article> artIter = this.getArticles().iterator();
			while (artIter.hasNext()) {
				Article currArticle = artIter.next();
				Map<String, Object> currMap = currArticle.toMap(false);
				ArticleProperties currProperties = getOwner().getArticleProperties(currArticle);
				currMap.put("read", currProperties.isAlreadyRead());
				currMap.put("important", currProperties.isImportant());
				articlesMapList.add(currMap);
			}
			result.put("articles", articlesMapList);
		}
		return result;
	}
	

}
