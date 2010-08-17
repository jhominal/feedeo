package org.feedeo.util

import java.util.{Dictionary, Enumeration => JEnumeration}
import scala.collection.Map
import scala.collection.mutable.{Map => MMap}
import ImplicitConverters.iterator2jenumeration

class MapDictWrapper[K, V >: Null <: AnyRef](private val delegate : Map[K, V]) extends Dictionary[K, V] {
	
  def size(): Int = { delegate.size }

  def isEmpty(): Boolean = { delegate.isEmpty }

  def keys(): JEnumeration[K] = { delegate.keysIterator }

  def elements(): JEnumeration[V] = { delegate.valuesIterator }

  def get(key: AnyRef): V = {
	  key match {
	 	  case key : K => 
	     	  delegate.get(key) orNull
	 	  case _ => null
      }
  }

  def put(key: K, value: V): V = { 
	  delegate match {
	 	  case delegate : MMap[K, V] =>
	 	      delegate.put(key, value) orNull
	 	  case _ =>
	 	       throw new Exception("Invalid operation - Underlying map is not mutable.")
	  }
  }

  def remove(key: AnyRef): V = {
	  key match {
	 	  case key : K =>
	 	  	delegate match {
	 	        case delegate : MMap[K,V] =>
	          	  delegate.remove(key) orNull
	 	        case _ =>
                  throw new Exception("Invalid operation - Underlying map is not mutable.")
	      }
	 	  case _ => null
	  }
  }

}

class IteratorJEnumerationWrapper[A](private val delegate : Iterator[A]) extends JEnumeration[A] {
	
	def hasMoreElements() : Boolean = {
		delegate.hasNext
	}
	
	def nextElement() : A = {
		delegate.next
	}
}