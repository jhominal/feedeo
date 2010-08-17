package org.feedeo.datastore

import scala.collection.Map
import org.feedeo.model.DataEntity

trait DataStoreService {
	def save(entity: DataEntity): Long
	def update(entity: DataEntity): Unit
	def delete(entity: DataEntity): Unit
	def load[A <: DataEntity](params: Map[String, Any], entityClass: Class[A]): A
}