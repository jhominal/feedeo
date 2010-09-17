package org.feedeo.model.feed

import org.feedeo.model._

class Feed(
  var title: String) extends DataEntity[Feed] {
  def statics = Feed.statics

}

object Feed {
  val statics = new StaticBindings[NamespacedKey, Feed](
    List(DataEntity.statics),
    Map(Namespaces.Feedeo ## "title" -> StaticProperty[Feed, String](_.title _, Some(_.title_= _)))
    )
}