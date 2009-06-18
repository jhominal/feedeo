package org.feedeo.clientcomm;

import java.util.Map;

/**
 * This interface specifies a way to convert an object into a Map; this class's
 * goal is to let the objects' conversion to JSON be as smooth as possible.
 * 
 * @author Feedeo Team
 */
public interface Mappable {

  /**
   * @param deep
   *          If true, then the direct children's objects maps will be
   *          evaluated.
   * @return a Map representing the object, ready for parsing to JSON.
   */
  public Map<String, Object> toMap(boolean deep);

}
