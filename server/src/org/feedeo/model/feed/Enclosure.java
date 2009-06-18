package org.feedeo.model.feed;

import javax.persistence.Embeddable;

/**
 * Type a description for Enclosure here.
 * 
 * @author Feedeo Team
 *
 */
@Embeddable
public class Enclosure {
  private String url;
  private long length;
  private String type;
  
  /**
   * @return the url
   */
  public String getUrl() {
    return url;
  }
  /**
   * @param url the url to set
   */
  public void setUrl(String url) {
    this.url = url;
  }
  /**
   * @return the type
   */
  public String getType() {
    return type;
  }
  /**
   * @param type the type to set
   */
  public void setType(String type) {
    this.type = type;
  }
  /**
   * @return the length
   */
  public long getLength() {
    return length;
  }
  /**
   * @param length the length to set
   */
  public void setLength(long length) {
    this.length = length;
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (int) (length ^ (length >>> 32));
    result = prime * result + ((type == null) ? 0 : type.hashCode());
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
    Enclosure other = (Enclosure) obj;
    if (length != other.length) {
      return false;
    }
    if (type == null) {
      if (other.type != null) {
        return false;
      }
    } else if (!type.equals(other.type)) {
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
