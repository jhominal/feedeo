package org.feedeo.fetcher.spi

import java.io.InputStream
import java.net.URL
import scala.collection.Map
import org.osgi.framework.BundleContext
import org.osgi.service.event.{Event, EventAdmin}

trait FetcherService {
    /**
     * An implicit conversion function - making it possible to call watch with a String url.
     * @param urlString A String representing the absolute URL.
     * @return The corresponding java.net.URL object.
     */
    implicit def str2url(urlString: String) = new URL(urlString)
    
    /**
     * Tells the FetcherService that it should watch that feed for updates.
     * The parameters are there in order to accommodate push services, such as RSSCloud
     * or PubSubHubbub.
     * 
     * @param url The URL of the target feed. The URL should always point to a location where the feed can be found.
     * @param params
     */
    def watch(url: URL, params: Map[String, Any] = Map.empty): Unit
    
    def getContext(): BundleContext
    
    /**
     * Posts an event signaling that a given feed (identified by its URL) should be parsed by the application - and 
     * provides a reference to the source that should be parsed.
     * @param url The URL of the feed which is considered ready.
     * @param source 
     */
    /*def protected postReady(url: URL, source: InputStream): Unit = {
        eventAdminReference = getContext().getServiceReference("org.osgi.service.event.EventAdmin")
        if (reference != null) {
            eventAdmin = context.getService(eventAdminReference)
            eventAdmin match {
                case eventAdmin: EventAdmin => eventAdmin.postEvent(new Event(feedReadyTopicName, Map("url" -> url, "source" -> source)))
                case _ => 
            }
            
        }
    }*/
    
    
}

object FetcherService {
    val feedReadyTopicName = "org/feedeo/fetcher/FEED_READY"
}
