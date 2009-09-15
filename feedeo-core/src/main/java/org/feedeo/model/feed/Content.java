package org.feedeo.model.feed;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * This is a model for content that is really embedded in the xml feed file.
 * Though it can theoretically correspond to any MIME simple type, non-text content
 * is usually delivered as @see Enclosure.
 * 
 * @author Feedeo Team
 * 
 */
@Embeddable
public class Content {
  
  // Atom RFC: may be either:
  // * html, text, xhtml TODO: check at ROME how they treat these cases
  // * Any non-composite MIME type
  private String type;
  // Specifies the kind of encoding to be expected (Atom only, may be {XML, escaped, base64})
  private String mode;
  // Finally, the content's value.
  private String value;
  
  @SuppressWarnings("unused")
  private Content()
  {
    // Left empty; Hibernate needs it.
  }
  
  /**
   * Builds an instance of Content according to the following parameters.
   * 
   * @param type The MIME type of the content.
   * @param mode The encoding mode (available only in Atom, may be XML, escaped, base64)
   * @param value The value of the text. That should generally not be null.
   */
  public Content(String type, String mode, String value)
  {
    setType(type);
    setMode(mode);
    setValue(value);
  }
  
  /**
   * @return the type ; if null, then it should be assumed to be "text/plain".
   */
  @Column(name = "type", length=255)
  public String getType() {
    return type;
  }
  
  private void setType(String type) {
    this.type = type;
  }
  
  /**
   * @return the mode
   */
  @Column(name = "mode", length = 16)
  public String getMode() {
    return mode;
  }
  
  private void setMode(String mode) {
    this.mode = mode;
  }
  /**
   * @return the value
   */
  @Column(name = "value")
  @org.hibernate.annotations.Type(type = "text")
  public String getValue() {
    return value;
  }
  
  private void setValue(String value) {
    this.value = value;
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((mode == null) ? 0 : mode.hashCode());
    result = prime * result + ((type == null) ? 0 : type.hashCode());
    result = prime * result + ((value == null) ? 0 : value.hashCode());
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
    Content other = (Content) obj;
    if (mode == null) {
      if (other.mode != null) {
        return false;
      }
    } else if (!mode.equals(other.mode)) {
      return false;
    }
    if (type == null) {
      if (other.type != null) {
        return false;
      }
    } else if (!type.equals(other.type)) {
      return false;
    }
    if (value == null) {
      if (other.value != null) {
        return false;
      }
    } else if (!value.equals(other.value)) {
      return false;
    }
    return true;
  }
}
