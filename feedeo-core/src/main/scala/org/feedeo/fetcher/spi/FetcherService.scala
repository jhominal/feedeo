package org.feedeo.fetcher.spi

import java.io.InputStream
import java.net.URL
import scala.collection.Map
import org.osgi.framework.BundleContext

trait FetcherService {
    
    /**
     * Tells the FetcherService that it should watch that feed for updates.
     * The parameters are there in order to accommodate push services, such as RSSCloud
     * or PubSubHubbub.
     * 
     * @param url The {@link URL} of the target feed. The URL should always point to a location where the feed can be found.
     * @param params
     */
    def watch(url: URL, params: Map[String, Any] = Map.empty): Unit;
    
    /**
     * Returns a reference to {@link BundleContext} appropriate for this Service.
     * @return The BundleContext associated to this service.
     */
    protected def getContext(): BundleContext
    
    /**
     * Posts an event signaling that a given feed (identified by its URL) should be parsed by the application - and 
     * provides a reference to the source that should be parsed.
     * Will fail silently if the EventAdmin service is not found.
     * @param url The URL of the feed which is considered ready.
     * @param source The InputStream that should be used by the Feedeo to get the feed information.
     */
    protected def postReady(url: URL, source: InputStream = null): Unit = {
        import org.osgi.service.event.{Event, EventAdmin}
        val eventAdminReference = getContext().getServiceReference("org.osgi.service.event.EventAdmin")
        if (eventAdminReference != null) {
            val eventAdmin = getContext().getService(eventAdminReference)
            eventAdmin match {
                case eventAdmin: EventAdmin => 
                    import org.feedeo.util.ImplicitConverters.map2dico
                    eventAdmin.postEvent(new Event(FetcherService.feedReadyTopicName, Map("url" -> url, "source" -> source)))
                case _ => 
            }
        }
    }
    
}

object FetcherService {
    val feedReadyTopicName = "org/feedeo/fetcher/FEED_READY"
}
