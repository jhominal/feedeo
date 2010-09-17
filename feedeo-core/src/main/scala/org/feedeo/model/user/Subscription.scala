package org.feedeo.model.user

import org.feedeo.model.feed._
import java.util.Date

case class Subscription(feed: Feed, folder: Folder) {

  /**
   * The last date at which the Feed's entries were pulled for the last time.
   */
  var lastPull: Option[Date] = None

}