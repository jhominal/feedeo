package org.feedeo.model.feed;

import static org.feedeo.hibernate.InitSessionFactory.getSession;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.criterion.Restrictions;

/**
 * This class models feeds in our application.
 * 
 * The feed model is heavily borrowed (eg "nearly a carbon copy") from the one
 * used by the ROME library, for two reasons: their model is quite probably
 * better than what we could have come up with, and it is easier to transfer
 * variables from the ROME model to the Feedeo model if they remain similar.
 * 
 * @author Feedeo Team
 * 
 */
@Entity
public class Feed {
  // Rome model fields
  private String         feedType;
  private String         encoding;
  private String         uri;

  private Content        title;

  private String         link;
  private List<Link>     links        = new ArrayList<Link>();

  private Content        description;
  private Date           pubDate;

  private List<Writer>    authors      = new ArrayList<Writer>();
  private Set<Writer>    contributors = new HashSet<Writer>();

  private String         copyright;
  private ImageReference image;

  private Set<Category>  categories   = new HashSet<Category>();

  private Set<Article>   articles     = new LinkedHashSet<Article>();

  private String         language;

  // Variables specific to Feedeo.
  // url used for Feedeo checkout.
  private Long           id;

  private String         url;
  private Date           checkout;
  private Date           lastChange;

  /**
   * Default constructor.
   */
  public Feed() {
    super();
  }

  /**
   * @return the feedType
   */
  public String getFeedType() {
    return feedType;
  }

  /**
   * @param feedType
   *          the feedType to set
   */
  public void setFeedType(String feedType) {
    this.feedType = feedType;
  }

  /**
   * @return the encoding
   */
  public String getEncoding() {
    return encoding;
  }

  /**
   * @param encoding
   *          the encoding to set
   */
  public void setEncoding(String encoding) {
    this.encoding = encoding;
  }

  /**
   * @return the uri
   */
  public String getUri() {
    return uri;
  }

  /**
   * @param uri
   *          the uri to set
   */
  public void setUri(String uri) {
    this.uri = uri;
  }

  /**
   * @return the title
   */
  public Content getTitle() {
    return title;
  }

  /**
   * @param title
   *          the title to set
   */
  public void setTitle(Content title) {
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
   *          the link to set
   */
  public void setLink(String link) {
    this.link = link;
  }

  /**
   * @return the links
   */
  @org.hibernate.annotations.CollectionOfElements
  @org.hibernate.annotations.IndexColumn(name = "linkOrder")
  public List<Link> getLinks() {
    return links;
  }

  /**
   * @param links
   *          the links to set
   */
  public void setLinks(List<Link> links) {
    this.links = links;
  }

  /**
   * @return the description
   */
  public Content getDescription() {
    return description;
  }

  /**
   * @param description
   *          the description to set
   */
  public void setDescription(Content description) {
    this.description = description;
  }

  /**
   * @return the pubDate
   */
  @Temporal(TemporalType.TIMESTAMP)
  public Date getPubDate() {
    return pubDate;
  }

  /**
   * @param pubDate
   *          the pubDate to set
   */
  public void setPubDate(Date pubDate) {
    this.pubDate = new Date(pubDate.getTime());
  }

  /**
   * @return the authors
   */
  @ManyToMany
  @org.hibernate.annotations.IndexColumn(name = "authorOrder")
  public List<Writer> getAuthors() {
    return authors;
  }

  /**
   * @param authors
   *          the authors to set
   */
  public void setAuthors(List<Writer> authors) {
    this.authors = authors;
  }

  /**
   * @return the contributors
   */
  public Set<Writer> getContributors() {
    return contributors;
  }

  /**
   * @param contributors
   *          the contributors to set
   */
  public void setContributors(Set<Writer> contributors) {
    this.contributors = contributors;
  }

  /**
   * @return the copyright
   */
  public String getCopyright() {
    return copyright;
  }

  /**
   * @param copyright
   *          the copyright to set
   */
  public void setCopyright(String copyright) {
    this.copyright = copyright;
  }

  /**
   * @return the image
   */
  public ImageReference getImage() {
    return image;
  }

  /**
   * @param image
   *          the image to set
   */
  public void setImage(ImageReference image) {
    this.image = image;
  }

  /**
   * @return the categories
   */
  @ManyToMany
  public Set<Category> getCategories() {
    return categories;
  }

  /**
   * @param categories
   *          the categories to set
   */
  public void setCategories(Set<Category> categories) {
    this.categories = categories;
  }

  /**
   * @return the articles
   */
  @OneToMany(mappedBy = "source", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  @OrderBy(value = "sortDate desc")
  public Set<Article> getArticles() {
    return articles;
  }

  /**
   * @param articles
   *          the articles to set
   */
  public void setArticles(Set<Article> articles) {
    this.articles = articles;
  }

  /**
   * @return the language
   */
  public String getLanguage() {
    return language;
  }

  /**
   * @param language
   *          the language to set
   */
  public void setLanguage(String language) {
    this.language = language;
  }

  /**
   * @return the object's id in the corresponding table in the database.
   */
  @Id
  public Long getId() {
    return id;
  }

  @SuppressWarnings("unused")
  /**
   * Sets the object's id in the corresponding table in the database. Its
   * visibility, private, means that only Hibernate will access it.
   * 
   * @param id
   */
  private void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the url
   */
  public String getUrl() {
    return url;
  }

  /**
   * @param url
   *          the url to set
   */
  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * @return the checkout
   */
  @Temporal(TemporalType.TIMESTAMP)
  public Date getCheckout() {
    return checkout;
  }

  /**
   * @param checkout
   *          the checkout to set
   */
  public void setCheckout(Date checkout) {
    this.checkout = new Date(checkout.getTime());
  }

  /**
   * @return the lastChange
   */
  @Temporal(TemporalType.TIMESTAMP)
  public Date getLastChange() {
    return lastChange;
  }

  /**
   * @param lastChange
   *          the lastChange to set
   */
  public void setLastChange(Date lastChange) {
    this.lastChange = new Date(lastChange.getTime());
  }

  /**
   * Convenience method to be able to reliably display a valid date when the
   * client asks for it.
   * 
   * @return the publication date displayed by the client.
   */
  @Transient
  public Date getDisplayedPubDate() {
    if (pubDate != null) {
      return new Date(pubDate.getTime());
    } else {
      return new Date(checkout.getTime());
    }
  }

  /**
   * Gets a feed by its url.
   * 
   * @param url
   *          the feed's url.
   * @return a Feed with the corresponding url; if he did not exist in the base,
   *         all fields except login will be empty.
   */
  public static Feed getFeedByUrl(String url) {
    // TODO: eliminate or redo
    Feed refFeed = new Feed();
    refFeed.setUrl(url);
    Feed potentialFeed = (Feed) getSession().createCriteria(Feed.class).add(
        Restrictions.eq("url", url)).uniqueResult();
    if (potentialFeed == null) {
      getSession().saveOrUpdate(refFeed);
      return refFeed;
    } else {
      return potentialFeed;
    }
  }

}
