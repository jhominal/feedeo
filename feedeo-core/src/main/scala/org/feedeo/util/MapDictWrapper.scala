package org.feedeo.util

import java.util.{ Dictionary, Enumeration => JEnumeration }
import scala.collection.Map
import scala.collection.mutable.{ Map => MMap }

class MapDictWrapper[K <: AnyRef, V >: Null <: AnyRef](private val delegate: Map[K, V]) extends Dictionary[K, V] {

  def size(): Int = { delegate.size }

  def isEmpty(): Boolean = { delegate.isEmpty }

  def keys(): JEnumeration[K] = { delegate.keysIterator }

  def elements(): JEnumeration[V] = { delegate.valuesIterator }

  def get(key: AnyRef): V = {
    key match {
      case null => throw new NullPointerException("The key of a Dictionary entry cannot be null.")
      case key: AnyRef =>
        delegate.get(key.asInstanceOf[K]) orNull
      case _ => null
    }
  }

  def put(key: K, value: V): V = {
    if (key == null) throw new NullPointerException("The key of a Dictionary entry cannot be null.")
    if (value == null) throw new NullPointerException("The value of a Dictionary entry cannot be null.")
    delegate match {
      case delegate: MMap[_, _] =>
        delegate.asInstanceOf[MMap[K, V]].put(key, value) orNull
      case _ =>
        throw new Exception("Invalid operation - Underlying map is not mutable.")
    }
  }

  def remove(key: AnyRef): V = {
    key match {
      case null => throw new NullPointerException("The key of a Dictionary entry cannot be null.")
      case key: AnyRef =>
        delegate match {
          case delegate: MMap[_, _] =>
            delegate.asInstanceOf[MMap[K, V]].remove(key.asInstanceOf[K]) orNull
          case _ =>
            throw new Exception("Invalid operation - Underlying map is not mutable.")
        }
      case _ => null
    }
  }

  private implicit def iterator2jenumeration[A](iterator: Iterator[A]): JEnumeration[A] = new IteratorJEnumerationWrapper(iterator)
}

private[this] class IteratorJEnumerationWrapper[A](private val delegate: Iterator[A]) extends JEnumeration[A] {

  def hasMoreElements(): Boolean = {
    delegate.hasNext
  }

  def nextElement(): A = {
    delegate.next()
  }
}