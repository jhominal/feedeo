package org.feedeo.model.feed

import org.feedeo.model.StaticProperty
import org.feedeo.model.Namespaces
import org.feedeo.model.NamespacedKey
import org.feedeo.model.StaticBindings
import org.feedeo.model.DataEntity

class Person(
    var name: String, 
    var uri: Option[String] = None,
    var email: Option[String] = None)
extends DataEntity[Person] {

  protected def statics() = Person.statics

}

object Person {
  val statics = new StaticBindings[NamespacedKey, Person](
    List(DataEntity.statics),
    Map(Namespaces.Feedeo ## "name" -> StaticProperty[Person, String](_.name _, Some(_.name_= _)),
        Namespaces.Feedeo ## "uri" -> StaticProperty[Person, Option[String]](_.uri _, Some(_.uri_= _)),
        Namespaces.Feedeo ## "email" -> StaticProperty[Person, Option[String]](_.email _, Some(_.email_= _))
        )
    )
}