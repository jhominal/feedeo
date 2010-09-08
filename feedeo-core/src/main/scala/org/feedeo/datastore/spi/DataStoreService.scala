package org.feedeo.datastore

import net.liftweb.common.Box
import scala.collection.Map
import org.feedeo.model.{DataEntity, DataCollection, NamespacedKey}

trait DataStoreService[Id] {
	val idType: Class[Id]
	
	def save[A <: DataEntity](entity: A): A
	def delete[A <: DataEntity](entity: A): A
	
	def loadById[A <: DataEntity](id : Id, entityClass: Class[A]) : Box[A]
	def load[A <: DataEntity](params: Map[String, Any], entityClass: Class[A]): Box[A]
	
	def loadCollection[A <: DataEntity, B <: DataEntity](parent : A, key : NamespacedKey) : Box[DataCollection[B]]
}