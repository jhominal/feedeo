package org.feedeo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.feedeo.util.HibernateObject;
import org.feedeo.util.JsonObjectSerializable;

public class Directory extends HibernateObject implements
		JsonObjectSerializable {
	
	private String title;
	
	private Set<Directory> subDirectories;
	
	private Set<Article> articles;
	
	public Directory() {
		subDirectories = new HashSet<Directory>();
		articles = new HashSet<Article>();
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<Directory> getSubDirectories() {
		return subDirectories;
	}

	public void setSubDirectories(Set<Directory> subDirectories) {
		this.subDirectories = subDirectories;
	}

	public Set<Article> getArticles() {
		return articles;
	}

	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}

	@Override
	public Map<String, Object> toMap(boolean shallow) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("id", this.getId());
		result.put("text", this.getTitle());
		result.put("leaf", (!this.getSubDirectories().isEmpty()));
		// TODO Method stub - to be tested...
		if (!shallow && !this.getSubDirectories().isEmpty()) {
			List<Map<String, Object>> subDirsMapList = new ArrayList<Map<String, Object>>(); 
			Iterator<Directory> directorIter = subDirectories.iterator();
			while (directorIter.hasNext()) {
				Directory currSubDir = directorIter.next();
				subDirsMapList.add(currSubDir.toMap(true));
			}
			result.put("children", subDirsMapList);
		}
		return result;
	}
	

}
