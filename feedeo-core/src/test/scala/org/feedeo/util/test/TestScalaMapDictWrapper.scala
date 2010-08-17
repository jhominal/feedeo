package org.feedeo.util.test

import scala.collection.mutable.{Map => MMap}
import org.feedeo.util.ImplicitConverters.map2dico

object TestScalaMapDictWrapper {
	val immutableMap = Map("key1" -> "value1", "key2" -> "value2")
	val mutableMap = MMap.empty[String, String]
	mutableMap.put("key1", "value1")
	mutableMap.put("key2", "value2")
	
	val wrappedImmutable = map2dico(immutableMap)
	val wrappedMutable = map2dico(mutableMap)
	
	def testWrappedMutable = {
		
	}
	
	def testWrappedImmutable = {
		
	}
}