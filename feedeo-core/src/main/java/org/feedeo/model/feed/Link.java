package org.feedeo.model.feed;

import javax.persistence.Embeddable;

/**
 * Type a description for Link here.
 * 
 * @author Feedeo Team
 * 
 */
@Embeddable
public class Link {
  //TODO Understand Links.
  private String rel;
  private String href;
  private String title;
  private String type;
  private String hreflang;
  private long   length;
  
  @SuppressWarnings("unused")
  private Link()
  {
    //Needed by Hibernate.
  }
  
  /**
   * Default constructor. Parameters are thos of ROME, I must
   * get to understand their meaning.
   * @param rel
   * @param href
   * @param title
   * @param type
   * @param hreflang
   * @param length
   */
  public Link(String rel, String href, String title, String type,
      String hreflang, long length)
  {
    setRel(rel);
    setHref(href);
    setTitle(title);
    setType(type);
    setHreflang(hreflang);
    setLength(length);
  }



  /**
   * @return the rel
   */
  public String getRel() {
    return rel;
  }
  
  private void setRel(String rel) {
    this.rel = rel;
  }
  /**
   * @return the href
   */
  public String getHref() {
    return href;
  }
  
  private void setHref(String href) {
    this.href = href;
  }
  /**
   * @return the title
   */
  public String getTitle() {
    return title;
  }
  
  private void setTitle(String title) {
    this.title = title;
  }
  /**
   * @return the type
   */
  public String getType() {
    return type;
  }
  
  private void setType(String type) {
    this.type = type;
  }
  /**
   * @return the hrefLang
   */
  public String getHreflang() {
    return hreflang;
  }
  
  private void setHreflang(String hreflang) {
    this.hreflang = hreflang;
  }
  /**
   * @return the length
   */
  public long getLength() {
    return length;
  }
  
  private void setLength(long length) {
    this.length = length;
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((href == null) ? 0 : href.hashCode());
    result = prime * result + ((hreflang == null) ? 0 : hreflang.hashCode());
    result = prime * result + (int) (length ^ (length >>> 32));
    result = prime * result + ((rel == null) ? 0 : rel.hashCode());
    result = prime * result + ((title == null) ? 0 : title.hashCode());
    result = prime * result + ((type == null) ? 0 : type.hashCode());
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
    Link other = (Link) obj;
    if (href == null) {
      if (other.href != null) {
        return false;
      }
    } else if (!href.equals(other.href)) {
      return false;
    }
    if (hreflang == null) {
      if (other.hreflang != null) {
        return false;
      }
    } else if (!hreflang.equals(other.hreflang)) {
      return false;
    }
    if (length != other.length) {
      return false;
    }
    if (rel == null) {
      if (other.rel != null) {
        return false;
      }
    } else if (!rel.equals(other.rel)) {
      return false;
    }
    if (title == null) {
      if (other.title != null) {
        return false;
      }
    } else if (!title.equals(other.title)) {
      return false;
    }
    if (type == null) {
      if (other.type != null) {
        return false;
      }
    } else if (!type.equals(other.type)) {
      return false;
    }
    return true;
  }
}
