package org.feedeo.model

import scala.collection.mutable.{Map => MMap}

trait DataEntity {
	var id : Option[Any] = None
}

object DataEntity {
	val statics = new StaticBindings[NamespacedKey, DataEntity](
			Nil, 
			Map(Namespaces.Feedeo##"id" -> StaticProperty[DataEntity, Option[Any]](x => x.id _, Some(x => x.id_= _)))
			)
	
}
