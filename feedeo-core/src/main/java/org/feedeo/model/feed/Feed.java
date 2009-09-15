package org.feedeo.model.feed;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * This class models feeds in our application.
 * 
 * The feed model is heavily borrowed (eg "nearly a carbon copy") from the one
 * used by the ROME library, for two reasons: their model is quite probably
 * better than what we could have come up with, and it is easier to transfer
 * variables from the ROME model to the Feedeo model if they remain similar.
 * 
 * @author Feedeo Team
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

  private List<Writer>   authors      = new ArrayList<Writer>();
  private Set<Writer>    contributors = new HashSet<Writer>();

  private String         copyright;
  private ImageReference image;

  private Set<Category>  categories   = new HashSet<Category>();

  private Set<Article>   articles     = new LinkedHashSet<Article>();

  private String         language;

  // Fields specific to Feedeo.
  // url used for Feedeo checkout.
  private Long           id;

  private String         url;
  private Date           lastChange;

  @SuppressWarnings("unused")
  private Feed() {
    super();
  }

  /**
   * @param url
   */
  public Feed(String url) {
    setUrl(url);
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
  @Embedded
  @AttributeOverrides( {
      @AttributeOverride(name = "type", column = @Column(name = "title_type")),
      @AttributeOverride(name = "mode", column = @Column(name = "title_mode")),
      @AttributeOverride(name = "value", column = @Column(name = "title_value")) })
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
  @Embedded
  @AttributeOverrides( {
      @AttributeOverride(name = "type", column = @Column(name = "desc_type")),
      @AttributeOverride(name = "mode", column = @Column(name = "desc_mode")),
      @AttributeOverride(name = "value", column = @Column(name = "desc_value")) })
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
    if (pubDate != null) {
      this.pubDate = new Date(pubDate.getTime());
    } else {
      this.pubDate = null;
    }
  }

  /**
   * @return the authors
   */
  @ManyToMany
  @JoinTable(name = "Feed_Author")
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
  @ManyToMany
  @JoinTable(name = "Feed_Contributor")
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
  @Embedded
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
  @OneToMany(mappedBy = "source")
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
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Long getId() {
    return id;
  }

  @SuppressWarnings("unused")
  private void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the url
   */
  public String getUrl() {
    return url;
  }

  private void setUrl(String url) {
    this.url = url;
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
    if (lastChange != null) {
      this.lastChange = new Date(lastChange.getTime());
    } else {
      this.lastChange = null;
    }
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
    } else if (lastChange != null) {
      return new Date(lastChange.getTime());
    } else {
      return null;
    }
  }

}
