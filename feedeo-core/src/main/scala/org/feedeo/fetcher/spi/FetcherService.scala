package org.feedeo.fetcher.spi

import java.net.URL
import scala.collection.Map

trait FetcherService {
    implicit def str2url(urlString: String) = new URL(urlString)
    
    /**
     * Tells the FetcherService that it should 
     * 
     * @param url The URL of the target feed. The URL should always point to the feed itself.
     * @param params
     */
    def watch(url: URL, params: Map[String, Any] = Map.empty): Unit
}

