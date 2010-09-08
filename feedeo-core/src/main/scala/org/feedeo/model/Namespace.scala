package org.feedeo.model

/**
 * This class represents Feedeo attributes namespaces.
 * 
 * The namespaces URIs should mostly coincide with the URIs used in the 
 * XML feeds.
 * 
 * @author jean
 *
 */
case class Namespace(uri: String) {
	def ##(key : String) = NamespacedKey(this, key)
}

case class NamespacedKey(namespace: Namespace, key: String) {
}

object Namespaces {
	val Feedeo = Namespace("http://www.feedeo.org/")
	val Rss1 = Namespace("http://purl.org/rss/1.0/")
	val Rss1_Rdf = Namespace("http://www.w3.org/1999/02/22-rdf-syntax-ns#")
	val Rss2 = Namespace("http://purl.org/rss/2.0/")
	val Atom = Namespace("http://www.w3.org/2005/Atom")
	val Html = Namespace("http://www.w3.org/1999/xhtml")
}