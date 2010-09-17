package org.feedeo.feedparser

import net.liftweb.common.Box
import java.io.InputStream
import java.net.URL
import org.feedeo.model.feed.FeedFragment

trait FeedParserService {
  /**
   * Requests for the feed to be parsed into a FeedFragment. This method is blocking.
   * @param url The feed's URL.
   * @param source The InputStream that will be parsed.
   * @param encodings A list of encodings that should be tried.
   * @return
   */
  def parse(url: URL, source: InputStream, encodings: List[String] = Nil): Box[FeedFragment]
}