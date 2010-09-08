package org.feedeo.model

/**
 * This class represents a static property, of type T, that returns 
 * @author jean
 * 
 */
case class StaticProperty[-T, P](
		val getter: T => (() => P), 
		val setter: Option[T => (P => Unit)])
		{
	@throws(classOf[IllegalArgumentException])
	@throws(classOf[ClassCastException])
	def uncheckedSet(member: T)(value: Any) {
		setter match {
			case None => throw new IllegalArgumentException("Attempted to write to a read-only StaticProperty.")
			case Some(eSetter) => eSetter(member)(value.asInstanceOf[P])
		}
	}
}

class StaticBindings[K, T](
		// Parents are transitive.
		private val parents: List[StaticBindings[K, _ >: T]], 
		// These bindings are there mainly for purposes of serialization.
		private val bindings: Map[K, StaticProperty[T, _]])
		{

	def lookup(key: K) : Option[StaticProperty[T , _]] = {
		// First lookup in my own bindings:
		if (bindings.contains(key)) {
			return bindings.get(key)
		}
		for {parent <- parents
			found  <- parent.lookup(key)} {
			return Some(found)
		}
		return None
	}
	
	import scala.collection.immutable.{Set => ISet}
	val keys : ISet[K] = {
		val b = ISet.newBuilder[K]
		b ++= bindings.keySet
		for {parent <- parents} {
			b++= parent.keys
		}
		b.result
	}
}


import scala.collection.mutable.{Map => MMap}

trait DynamicObject[K, T <: DynamicObject[K,T]] {
	private val dynamics : MMap[K, Any] = MMap.empty()
	protected def statics : StaticBindings[K, T]
	
	private val self = this.asInstanceOf[T]
	
	def get(key: K): Option[_] = {
		statics.lookup(key) match {
			case None => dynamics.get(key)
			case Some(prop) => Some(prop.getter(self)())
		}
	}
	
	def canSet(key: K): Boolean = {
		statics.lookup(key) match {
			case None => true
			case Some(prop) => prop.setter match {
				case None => false
				case _ => true
			}
		}
	}
	
	def set(key: K)(value: Any): Unit = {
		statics.lookup(key) match {
			case None => dynamics.put(key, value)
			case Some(prop) =>
			prop.setter match {
				case None => throw new IllegalArgumentException("Property %s is readonly.".format(key))
				case Some(setter) => prop.uncheckedSet(self)(value)
			}
		}
	}
	
	/**
	 * Create a new Set that contains all current keys.
	 */
	import scala.collection.Set
	def keys : Set[K] = {
		statics.keys union dynamics.keySet
	}
	
	/**
	 * Create a new, immutable Map, that contains all keyed properties
	 * of the DynamicObject instance, with its values.
	 * This map should only be used 
	 * @return
	 */
	def getMap() : Map[K, Any] = {
		val b = Map.newBuilder[K, Any]
		for { key <- statics.keys
			value <- this.get(key) } {
				b += (key -> value)
		}
		b ++= dynamics
		b.result
	}
}