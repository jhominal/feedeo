package org.feedeo.datastore

import net.liftweb.common.Box
import scala.collection.Map
import org.feedeo.model.{ DataEntity, DataCollection, NamespacedKey }

trait DataStoreService[Id] {
  val idType: Class[Id]

  def save[A <: DataEntity[A]](entity: A): A
  def delete[A <: DataEntity[A]](entity: A): A

  def loadById[A <: DataEntity[A]](id: Id, entityClass: Class[A]): Box[A]
  def load[A <: DataEntity[A]](params: Map[String, Any], entityClass: Class[A]): Box[A]

  def loadCollection[A <: DataEntity[A], B <: DataEntity[B]](parent: A, key: NamespacedKey): Box[DataCollection[B]]
}