package org.feedeo.model.user;

import static org.feedeo.hibernate.InitSessionFactory.getSession;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.Writer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.*;

import org.hibernate.criterion.Restrictions;

import org.feedeo.model.feed.Article;

/**
 * This class models the application's users (people who have subscribed to this
 * service).
 * 
 * @author Feedeo Team
 * 
 */
@Entity
public class User {

  private Long                            id;

  private String                          login;
  private String                          pwdHash;
  private String                          email;

  private String                          firstName;
  private String                          lastName;

  private Folder                          rootDirectory;

  private Map<String, Serializable>       preferences;
  private Map<Article, ArticleProperties> articleProperties;

  /**
   * Default constructor.
   */
  public User() {
    super();
    rootDirectory = new Folder();
    rootDirectory.setOwner(this);
    preferences = new HashMap<String, Serializable>();
    articleProperties = new HashMap<Article, ArticleProperties>();
  }

  /**
   * @param id
   *          the id to set
   */
  @SuppressWarnings("unused")
  private void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the id
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Long getId() {
    return id;
  }

  /**
   * @return the login
   */
  public String getLogin() {
    return login;
  }

  /**
   * @param login
   *          the login to set
   */
  public void setLogin(String login) {
    this.login = login;
  }

  private void setPwdHash(String pwdHash) {
    this.pwdHash = pwdHash;
  }

  private String getPwdHash() {
    return pwdHash;
  }

  /**
   * Utility method to compute a Hash String using Java built-in methods.
   * @param stringToHash
   * @return a hash of the string, using the SHA-256 algorithm.
   */
  private String computeHash(String stringToHash) {
    ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
    try {
      Writer out = new OutputStreamWriter(byteArrayOut, "UTF8");
      out.write(stringToHash);
      out.close();
    } catch (IOException ioException) {
      throw new RuntimeException("Exception occurred while converting the string to a UTF-8 byte array", ioException);
    }
    byte[] source = byteArrayOut.toByteArray();

    MessageDigest hashFunction;
    try {
      hashFunction = MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Exception occurred while trying to initialize Digest Algorithm",e);
    }
    byte[] result = hashFunction.digest(source);
    
    StringBuilder stringResultBuilder = new StringBuilder(result.length * 2);
    for (byte resultByte : result) {
      String toAppend = Integer.toHexString(resultByte & 0xff);
      if (toAppend.length() == 1) {
        stringResultBuilder.append("0");
      }
      stringResultBuilder.append(toAppend);
    }
    return stringResultBuilder.toString();
  }

  /**
   * @return whether the password is correct.
   */
  public boolean checkPassword(String givenPassword) {
    String resultingHash = computeHash(givenPassword);
    if (resultingHash == null) {
      return false;
    } else {
      return (resultingHash.equals(getPwdHash()));
    }
  }

  /**
   * @param pwdHash
   *          the pwdHash to set
   */
  public void setPassword(String password) {
    String pwdHash = computeHash(password);
    setPwdHash(pwdHash);
  }

  /**
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param email
   *          the email to set
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
   *          the firstName to set
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
   * @param rootDirectory
   *          the rootDirectory to set
   */
  public void setRootDirectory(Folder rootDirectory) {
    this.rootDirectory = rootDirectory;
  }

  /**
   * @return the lastName
   */
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name="root_dir_fk")
  public Folder getRootDirectory() {
    return rootDirectory;
  }

  /**
   * @param lastName
   *          the lastName to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * @return the preferences
   */
  @org.hibernate.annotations.CollectionOfElements
  public Map<String, Serializable> getPreferences() {
    return preferences;
  }

  /**
   * @param preferences
   *          the preferences to set
   */
  public void setPreferences(Map<String, Serializable> preferences) {
    this.preferences = preferences;
  }

  /**
   * @return the articleProperties
   */
  @org.hibernate.annotations.CollectionOfElements
  public Map<Article, ArticleProperties> getArticleProperties() {
    return articleProperties;
  }

  /**
   * @param articleProperties
   *          the articleProperties to set
   */
  public void setArticleProperties(
      Map<Article, ArticleProperties> articleProperties)
  {
    this.articleProperties = articleProperties;
  }

  /**
   * Gets an article properties for this user.
   * 
   * @param article
   *          the article in question
   * @return the corresponding ArticleProperties object
   */
  @Transient
  public ArticleProperties getArticleProperties(Article article) {
    if (!articleProperties.containsKey(article)) {
      articleProperties.put(article, new ArticleProperties());
    }
    return articleProperties.get(article);
  }

  /**
   * @param article
   *          the article whose map is desired
   * @return an article's map with its parameters.
   */
  public Map<String, Object> articleMap(Article article) {
    Map<String, Object> result = article.toMap(true);
    ArticleProperties currProperties = getArticleProperties(article);
    result.put("read", Boolean.valueOf(currProperties.isAlreadyRead()));
    result.put("important", Boolean.valueOf(currProperties.isImportant()));
    return result;
  }

  /**
   * Gets a User by his login.
   * 
   * @param login
   *          the user's login.
   * @return a User with the corresponding login; if he did not exist in the
   *         base, all fields except login will be empty.
   */
  public static User getUserByLogin(String login) {
    getSession().beginTransaction();
    User potentialUser = (User) getSession().createCriteria(User.class).add(
        Restrictions.eq("login", login)).uniqueResult();
    getSession().getTransaction().commit();
    if (potentialUser == null) {
      return new User();
    } else {
      return potentialUser;
    }
  }
}
