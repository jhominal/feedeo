package org.feedeo.platform;

import java.util.Map;

import org.apache.felix.framework.Felix;
import org.apache.felix.framework.util.FelixConstants;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

public class FeedeoApplication {
  private static Logger logger = LoggerFactory.getLogger(FeedeoApplication.class);
  
  private OsgiActivator activator;
  private Felix felix;
  
  public FeedeoApplication() {
    Map<String, Object> configMap = Maps.newHashMap();
    activator = new OsgiActivator();
    configMap.put(FelixConstants.SYSTEMBUNDLE_ACTIVATORS_PROP, ImmutableList.of(activator));
    
    felix = new Felix(configMap);
    try {
      felix.start();
      Runtime.getRuntime().addShutdownHook(new Thread() {
        @SuppressWarnings("synthetic-access")
        @Override
        public void run() {
          try {
            felix.stop();
          } catch (BundleException e) {
            logger.error("Error while stopping Felix framework instance.", e);
          }
        }
      });
    } catch (BundleException e) {
      logger.error("Error while launching Felix framework instance.", e);
    }
  }
  
  public Bundle[] getInstalledBundles() {
    return activator.getBundles();
  }
  
  public void shutdown() {
    try {
      felix.stop();
    } catch (BundleException e) {
      logger.error("Error while stopping Felix framework instance.", e);
    }
    try {
      felix.waitForStop(0);
    } catch (InterruptedException e) {
      logger.warn("Shutdown of Felix has been forced to complete - Errors may be present.", e);
    }
  }
  
}
