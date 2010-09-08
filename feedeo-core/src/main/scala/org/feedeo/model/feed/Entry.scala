package org.feedeo.model.feed

import org.feedeo.model._

class Entry(
    var title: String,
    var description: String) extends DynamicObject[NamespacedKey, Entry] with DataEntity {
	
	def statics = Entry.statics
}

object Entry {
	val statics = new StaticBindings[NamespacedKey, Entry](
			List(DataEntity.statics),
			Map(Namespaces.Feedeo##"title" -> StaticProperty[Entry, String](x => x.title _, Some(x => x.title_= _)),
				Namespaces.Feedeo##"description" -> StaticProperty[Entry, String](x => x.description _, Some(x => x.description_= _)))
			)
}