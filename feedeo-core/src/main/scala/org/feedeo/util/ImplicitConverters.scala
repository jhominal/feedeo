package org.feedeo.util

import java.util.{Dictionary, Hashtable, Enumeration => JEnumeration}
import java.net.URL
import scala.collection.Map

object ImplicitConverters {
	/**
	 * An implicit conversion function from a {@link Map} to a {@link Dictionary}
	 * @param map The Scala Map that you wish to convert.
	 * @return A Dictionary view containing the same entries.
	 */
	implicit def map2dico[A,B >: Null <: AnyRef](map:Map[A,B]): Dictionary[A,B] = new MapDictWrapper[A,B](map)
	
    /**
     * An implicit conversion function - from String to URL. May throw {@link java.util.MalformedURLException}
     * if URL is malformed (if it does not correspond to a known protocol)
     * @param urlString A String representing the absolute URL.
     * @return The corresponding java.net.URL object.
     */
    implicit def str2url(urlString: String) : URL = new URL(urlString)

    implicit def iterator2jenumeration[A](iterator : Iterator[A]) : JEnumeration[A] = new IteratorJEnumerationWrapper(iterator)


}