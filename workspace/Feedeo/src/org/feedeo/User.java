package org.feedeo;

import java.util.HashMap;
import java.util.Map;

/**
 * This class models the application's users (people who have subscribed to this
 * service).
 * 
 * @author Feedeo Team
 * 
 */
public class User extends DirContainer {

	private String login;
	private String password;
	private String email;

	private String firstName;
	private String lastName;

	private Map<String, String> preferences;
	private Map<Article, ArticleProperties> articleProperties;
	private Map<Feed, Directory> feeds;

	/**
	 * Default constructor.
	 */
	public User() {
		super();
		preferences = new HashMap<String, String>();
		articleProperties = new HashMap<Article, ArticleProperties>();
		feeds = new HashMap<Feed, Directory>();
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login
	 *            the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the preferences
	 */
	public Map<String, String> getPreferences() {
		return preferences;
	}

	/**
	 * @param preferences
	 *            the preferences to set
	 */
	public void setPreferences(Map<String, String> preferences) {
		this.preferences = preferences;
	}

	/**
	 * @return the articleProperties
	 */
	public Map<Article, ArticleProperties> getArticleProperties() {
		return articleProperties;
	}

	/**
	 * @param articleProperties
	 *            the articleProperties to set
	 */
	public void setArticleProperties(
			Map<Article, ArticleProperties> articleProperties) {
		this.articleProperties = articleProperties;
	}

	/**
	 * @return the feeds
	 */
	public Map<Feed, Directory> getFeeds() {
		return feeds;
	}

	/**
	 * @param feeds
	 *            the feeds to set
	 */
	public void setFeeds(Map<Feed, Directory> feeds) {
		this.feeds = feeds;
	}

	protected User getDirUser() {
		return this;
	}

	/**
	 * Lets the User subscribe to a Feed.
	 * 
	 * @param feed
	 *            the feed to subscribe to
	 * @param targetDirectory
	 *            the directory where this feed should be put
	 */
	public void subscribeFeed(Feed feed, Directory targetDirectory) {
		this.getFeeds().put(feed, targetDirectory);
		feed.getReaders().add(this);
	}

	/**
	 * Lets the User cancel his subscription to a Feed.
	 * 
	 * @param feed
	 *            the feed to cancel.
	 */
	public void unsubscribeFeed(Feed feed) {
		this.getFeeds().remove(feed);
		feed.getReaders().remove(this);
	}
	
	/**
	 * Gets an article properties for this user.
	 * 
	 * @param article the article in question
	 * @return the corresponding ArticleProperties object
	 */
	public ArticleProperties getArticleProperties(Article article) {
		if (!articleProperties.containsKey(article)) {
			articleProperties.put(article, new ArticleProperties());
		}
		return articleProperties.get(article);
	}

	/**
	 * Gets a User by his login.
	 * 
	 * @param login
	 *            the user's login.
	 * @return a User with the corresponding login; if he did not exist in the
	 *         base, all fields except login will be empty.
	 */
	public static User getUserByLogin(String login) {
		User refUser = new User();
		refUser.setLogin(login);
		refUser.getSession().beginTransaction();
		User potentialUser = (User) refUser.getSession().createQuery(
				"from User as user where user.login = ?").setString(0, login)
				.uniqueResult();
		refUser.getSession().getTransaction().commit();
		if (potentialUser == null) {
			return refUser;
		} else {
			return potentialUser;
		}
	}

}
