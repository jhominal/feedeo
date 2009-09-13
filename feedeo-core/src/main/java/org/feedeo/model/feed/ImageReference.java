package org.feedeo.model.feed;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Type a description for ImageReference here.
 * 
 * @author Feedeo Team
 *
 */
@Embeddable
public class ImageReference {
  private String title;
  private String url;
  private String link;
  private String description;
  
  @SuppressWarnings("unused")
  private ImageReference()
  {
    //Necessary for Hibernate.
  }
  
  /**
   * @param title The image's title.
   * @param url The image's url (where the image *is*)
   * @param link The image's link (where you go after a click)
   * @param description A description of whatever.
   */
  public ImageReference(String title, String url, String link,
      String description)
  {
    setTitle(title);
    setUrl(url);
    setLink(link);
    setDescription(description);
  }



  /**
   * @return the image's title.
   */
  @Column(name="image_title",length=255)
  public String getTitle() {
    return title;
  }
  
  private void setTitle(String title) {
    this.title = title;
  }
  /**
   * @return the url The image's url
   */
  @Column(name="image_url",length=255)
  public String getUrl() {
    return url;
  }
  
  private void setUrl(String url) {
    this.url = url;
  }
  /**
   * @return the link
   */
  @Column(name="image_link",length=255)
  public String getLink() {
    return link;
  }
  
  private void setLink(String link) {
    this.link = link;
  }
  /**
   * @return the description
   */
  @Column(name="image_description",length=255)
  public String getDescription() {
    return description;
  }
  
  private void setDescription(String description) {
    this.description = description;
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((description == null) ? 0 : description.hashCode());
    result = prime * result + ((link == null) ? 0 : link.hashCode());
    result = prime * result + ((title == null) ? 0 : title.hashCode());
    result = prime * result + ((url == null) ? 0 : url.hashCode());
    return result;
  }
  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    ImageReference other = (ImageReference) obj;
    if (description == null) {
      if (other.description != null) {
        return false;
      }
    } else if (!description.equals(other.description)) {
      return false;
    }
    if (link == null) {
      if (other.link != null) {
        return false;
      }
    } else if (!link.equals(other.link)) {
      return false;
    }
    if (title == null) {
      if (other.title != null) {
        return false;
      }
    } else if (!title.equals(other.title)) {
      return false;
    }
    if (url == null) {
      if (other.url != null) {
        return false;
      }
    } else if (!url.equals(other.url)) {
      return false;
    }
    return true;
  }
}
