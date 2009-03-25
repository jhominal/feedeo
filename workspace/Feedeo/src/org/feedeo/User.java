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
	
	private Map<Preference, String> prefValues;
	private Map<Article, ArticleProperties> articleProperties;
	private Set<Directory> directories;
	private Set<Feed> feeds;
	
	public User() {
		prefValues = new HashMap<Preference, String>();
		articleProperties = new HashMap<Article,ArticleProperties>();
		directories = new HashSet<Directory>();
		feeds = new HashSet<Feed>();
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

	public Map<Preference, String> getPrefValues() {
		return prefValues;
	}

	public void setPrefValues(Map<Preference, String> prefValues) {
		this.prefValues = prefValues;
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

	public Set<Feed> getFeeds() {
		return feeds;
	}

	public void setFeeds(Set<Feed> feeds) {
		this.feeds = feeds;
	}

}
