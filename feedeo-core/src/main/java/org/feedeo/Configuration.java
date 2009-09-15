package org.feedeo;

import java.util.EnumSet;
import java.util.Locale;
import java.util.Set;

/**
 * Type a description for Configuration here.
 * 
 * @author Feedeo Team
 *
 */
public class Configuration {
  
  private enum Parameters {
    //All possible parameters must written in upper case.
    DEBUG;
    
    /**
     * @param name The parameter's name - case is ignored.
     * @return the corresponding Parameter.
     * @throws IllegalArgumentException if specified name does not correspond to a parameter.
     * @throws NullPointerException if name is null.
     */
    public static Parameters get(String name) {
      String capitalized = name.toUpperCase(Locale.ENGLISH);
      try {
        return Parameters.valueOf(capitalized);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException(name+" is not a valid parameter name.");
      }
    }
  }
  
  private static Configuration config = new Configuration();

  
  private Set<Parameters> params = EnumSet.noneOf(Parameters.class);
  
  /**
   * Default Constructor.
   */
  public Configuration() {
    //This Constructor does not do anything… yet.
  }
  
  /**
   * @return the current configuration for Feedeo.
   */
  public static synchronized Configuration getConfig() {
    return config;
  }
  
  /**
   * @param name the parameter's name
   * @return The parameter's value
   * @throws IllegalArgumentException if specified name does not correspond to a parameter.
   * @throws NullPointerException if name is null.
   */
  public synchronized boolean getParameter(String name) {
    return (params.contains(Parameters.get(name)));
  }
  
  /**
   * @param name the parameter's name
   * @param value the parameter's value to set
   * @throws IllegalArgumentException if specified name does not correspond to a parameter.
   * @throws NullPointerException if name is null.
   */
  public synchronized void setParameter(String name, boolean value) {
    Parameters param = Parameters.get(name);
    if (value) {
      params.add(param);
    } else {
      params.remove(param);
    }
  }
}
