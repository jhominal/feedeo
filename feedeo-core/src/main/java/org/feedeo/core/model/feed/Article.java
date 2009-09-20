package org.feedeo.core.model.feed;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * This class models an article, such as found in an RSS/Atom syndication file.
 * The model used for it follows quite closely the
 * 
 * @author Feedeo Team
 */
@Entity
public class Article {
  // This uri may contain the link (RSS), a guid (RSS 0.92 and RSS 2.0), or
  // a unique ID (Atom). This should be null only if no entry link is provided.*
  // For more details, look at <a
  // href="http://wiki.java.net/bin/view/Javawsxml/Rome04URIMapping">Feed and
  // entry URI mapping</a>
  private String          uri;

  private Content         title;

  // This field should have a valid link, except if null.
  private String          link;
  private List<Link>      links        = new ArrayList<Link>();

  private Content         description;
  private List<Content>   contents     = new ArrayList<Content>();
  private List<Enclosure> enclosures   = new ArrayList<Enclosure>();

  private Date            entryPubDate;
  // The entryUpdate does not map to much, except eventually in Atom feeds.
  private Date            entryUpdate;

  private List<Writer>    authors      = new ArrayList<Writer>();
  private Set<Writer>     contributors = new HashSet<Writer>();

  private Set<Category>   categories   = new HashSet<Category>();

  private Feed            source;

  /**
   * This field should be used to represent the date at which this article
   * appeared on the server.
   */
  private Long            id;

  private Date            checkout;
  private Date            lastChange;

  // That is a utility field that should be set only by Hibernate to order
  // articles, with something like "order = sortDate, desc"
  // It is set by calls to setCheckout and setEntryPubDate
  private Date            sortDate;

  @SuppressWarnings("unused")
  private Article() {
    // Empty for use by Hibernate.
  }

  /**
   * Constructor for a new Article. Automatically sets checkout and sortDate
   * parameters.
   * 
   * @param source
   * @param uri
   * @param title
   * @param pubDate
   */
  public Article(Feed source, String uri, Content title, Date pubDate) {
    setSource(source);
    setUri(uri);
    setTitle(title);
    setEntryPubDate(pubDate);
    setCheckout(new Date(System.currentTimeMillis()));
    setLastChange(getCheckout());
  }

  /**
   * @return the uri
   */
  public String getUri() {
    return uri;
  }

  private void setUri(String uri) {
    this.uri = uri;
  }

  /**
   * @return the title
   */
  @Embedded
  @AttributeOverrides( {
      @AttributeOverride(name = "type", column = @Column(name = "title_type", length = 255)),
      @AttributeOverride(name = "mode", column = @Column(name = "title_mode", length = 16)),
      @AttributeOverride(name = "value", column = @Column(name = "title_value")) })
  public Content getTitle() {
    return title;
  }

  private void setTitle(Content title) {
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
      @AttributeOverride(name = "type", column = @Column(name = "desc_type", length = 255)),
      @AttributeOverride(name = "mode", column = @Column(name = "desc_mode", length = 16)),
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
   * @return the contents
   */
  @org.hibernate.annotations.CollectionOfElements
  @org.hibernate.annotations.IndexColumn(name = "contentOrder")
  public List<Content> getContents() {
    return contents;
  }

  /**
   * @param contents
   *          the contents to set
   */
  public void setContents(List<Content> contents) {
    this.contents = contents;
  }

  /**
   * @return the enclosures
   */
  @org.hibernate.annotations.CollectionOfElements
  @org.hibernate.annotations.IndexColumn(name = "enclosureOrder")
  public List<Enclosure> getEnclosures() {
    return enclosures;
  }

  /**
   * @param enclosures
   *          the enclosures to set
   */
  public void setEnclosures(List<Enclosure> enclosures) {
    this.enclosures = enclosures;
  }

  /**
   * @return the entryPubDate
   */
  @Temporal(TemporalType.TIMESTAMP)
  public Date getEntryPubDate() {
    return entryPubDate;
  }

  private void setEntryPubDate(Date entryPubDate) {
    if (entryPubDate != null) {
      this.entryPubDate = new Date(entryPubDate.getTime());
      this.setSortDate(this.entryPubDate);
    } else {
      this.entryPubDate = null;
    }
  }

  /**
   * @return the entryUpdate
   */
  @Temporal(TemporalType.TIMESTAMP)
  public Date getEntryUpdate() {
    return entryUpdate;
  }

  /**
   * @param entryUpdate
   *          the entryUpdate to set
   */
  public void setEntryUpdate(Date entryUpdate) {
    if (entryUpdate != null) {
      this.entryUpdate = new Date(entryUpdate.getTime());
    } else {
      this.entryUpdate = null;
    }
  }

  /**
   * @return the authors
   */
  @ManyToMany
  @JoinTable(name = "Article_Author")
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
  @JoinTable(name = "Article_Contributor")
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
   * @return the source
   */
  @ManyToOne
  @JoinColumn(name = "feed_id")
  public Feed getSource() {
    return source;
  }

  private void setSource(Feed source) {
    this.source = source;
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
   * @return the checkout date
   */
  @Temporal(TemporalType.TIMESTAMP)
  public Date getCheckout() {
    return checkout;
  }

  private void setCheckout(Date checkout) {
    if (checkout != null) {
      this.checkout = new Date(checkout.getTime());
      if (getEntryPubDate() == null) {
        setSortDate(this.checkout);
      }
    } else {
      this.checkout = null;
    }
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

  @Temporal(TemporalType.TIMESTAMP)
  private void setSortDate(Date sortDate) {
    this.sortDate = new Date(sortDate.getTime());
  }

  private Date getSortDate() {
    return sortDate;
  }

  /**
   * Convenience method to be able to reliably display a valid date when the
   * client asks for it.
   * 
   * @return the publication date displayed by the client.
   */
  @Transient
  public Date getDisplayedPubDate() {
    return new Date(getSortDate().getTime());
  }

  /**
   * @return the lastChange date displayed by the client.
   */
  @Transient
  public Date getDisplayedUpDate() {
    if (entryUpdate != null) {
      return new Date(entryUpdate.getTime());
    } else {
      return new Date(lastChange.getTime());
    }
  }
}
