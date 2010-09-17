package org.feedeo.model.feed

import org.feedeo.model._

class FeedFragment extends DynamicObject[NamespacedKey, FeedFragment] {
  def statics = FeedFragment.statics

}

object FeedFragment {
  val statics = new StaticBindings[NamespacedKey, FeedFragment](Nil, Map.empty)
}