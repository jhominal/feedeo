package org.feedeo.model

trait DataCollection[A <: DataEntity[A]] {
  def getStream(skip: Int = 0, maxSize: Int = Int.MaxValue,
                order: Seq[OrderBy] = Nil, filter: (A => Boolean) = (_ => true)): Stream[A]
}

final case class OrderBy(attribute: NamespacedKey, var descending: Boolean = false) {
  def desc = {
    descending = true
  }
  def asc = {
    descending = false
  }
  
}

object OrderBy {
  implicit def fromKey(key: NamespacedKey) : OrderBy = {
    OrderBy(key)
  }
}