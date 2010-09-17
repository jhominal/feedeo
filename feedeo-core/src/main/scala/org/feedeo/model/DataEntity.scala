package org.feedeo.model

import scala.collection.mutable.{ Map => MMap }

trait DataEntity[T <: DataEntity[T]] extends DynamicObject[NamespacedKey, T] {
  // def statics = DataEntity.statics
  var id: Option[Any] = None
}

object DataEntity {
  val statics = new StaticBindings[NamespacedKey, DataEntity[_]](
    Nil,
    Map(Namespaces.Feedeo ## "id" -> StaticProperty[DataEntity[_], Option[Any]](_.id _, Some(_.id_= _)))
    )

}
