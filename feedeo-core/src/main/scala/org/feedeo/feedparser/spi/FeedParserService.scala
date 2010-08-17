package org.feedeo.feedparser

import java.io.InputStream
import java.net.URL
import org.feedeo.model.feed.FeedFragment

trait FeedParserService {
	/**
	 * Requests for the feed to be parsed into a FeedFragment.
	 * @param url 
	 * @param source
	 * @param encodings
	 * @return
	 */
	def parse(url: URL, source:InputStream = null, encodings: List[String] = List.empty): FeedFragment
}