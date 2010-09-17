package org.feedeo.model.feed

import org.feedeo.model._

class Entry(
  var title: String,
  var description: String) extends DataEntity[Entry] {

  def statics: StaticBindings[NamespacedKey, Entry] = Entry.statics
}

object Entry {
  val statics = new StaticBindings[NamespacedKey, Entry](
    List(DataEntity.statics),
    Map(Namespaces.Feedeo ## "title" -> StaticProperty[Entry, String](_.title _, Some(_.title_= _)),
      Namespaces.Feedeo ## "description" -> StaticProperty[Entry, String](_.description _, Some(_.description_= _)))
    )
}