package org.feedeo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.feedeo.util.HibernateObject;

public class User extends HibernateObject {
	
	private String login;
	private String password;
	private String email;
	
	private String firstName;
	private String lastName;
	
	private Map<String, String> preferences;
	private Map<Article, ArticleProperties> articleProperties;
	private Set<Directory> directories;
	private Map<Feed, Directory> feeds;
	
	public User() {
		preferences = new HashMap<String, String>();
		articleProperties = new HashMap<Article,ArticleProperties>();
		directories = new HashSet<Directory>();
		feeds = new HashMap<Feed, Directory>();
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Map<String, String> getPreferences() {
		return preferences;
	}

	public void setPreferences(Map<String, String> prefValues) {
		this.preferences = prefValues;
	}

	public Map<Article, ArticleProperties> getArticleProperties() {
		return articleProperties;
	}

	public void setArticleProperties(
			Map<Article, ArticleProperties> articleProperties) {
		this.articleProperties = articleProperties;
	}

	public Set<Directory> getDirectories() {
		return directories;
	}

	public void setDirectories(Set<Directory> directories) {
		this.directories = directories;
	}

	public Map<Feed, Directory> getFeeds() {
		return feeds;
	}

	public void setFeeds(Map<Feed, Directory> feeds) {
		this.feeds = feeds;
	}

}
