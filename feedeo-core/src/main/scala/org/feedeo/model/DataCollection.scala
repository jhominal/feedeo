package org.feedeo.model

trait DataCollection[A <: DataEntity] {
    def getView(offset : Int = 0, maxSize : Int = 20, order : Seq[OrderSpec] = Nil) : Iterable[A]
}

final case class OrderSpec(attribute : NamespacedKey, descending : Boolean = false) {
    
}
