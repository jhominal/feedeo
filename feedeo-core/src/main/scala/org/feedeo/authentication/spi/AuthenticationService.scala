package org.feedeo.authentication

import scala.collection.Map
import org.feedeo.model.user.User

trait AuthenticationService {
	def login(params: Map[String, Any]): User
}