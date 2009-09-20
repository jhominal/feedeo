package org.feedeo.core.model.feed;

import javax.persistence.Embeddable;

/**
 * Type a description for Enclosure here.
 * 
 * @author Feedeo Team
 *
 */
@Embeddable
public class Enclosure {
  //TODO: Understand Enclosures.
  private String url;
  private long length;
  private String type;
  
  @SuppressWarnings("unused")
  private Enclosure()
  {
    //Needed by Hibernate.
  }
  
  /**
   * Builds an instance of Enclosure according to the following parameters:
   * 
   * @param url The URL contained in the enclosure.
   * @param length The enclosure's "length".
   * @param type The enclosure's "type".
   */
  public Enclosure(String url, long length, String type)
  {
    setUrl(url);
    setLength(length);
    setType(type);
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
   * @return the type
   */
  public String getType() {
    return type;
  }
  
  private void setType(String type) {
    this.type = type;
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
