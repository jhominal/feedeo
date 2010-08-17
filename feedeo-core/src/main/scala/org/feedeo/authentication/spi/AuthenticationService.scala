package org.feedeo.authentication.spi

import scala.collection.Map
import org.feedeo.model.user.User

trait AuthenticationService {
	/**
	 * The login method.
	 * @param params The parameters for the login.
	 * @return The authenticated {@link User}, or null if the authentication is not successful.
	 */
	def login(params: Map[String, Any]): User
	
	/**
	 * A method to register a new User.
	 * @param params The parameters for the registration.
	 * @return The newly created User. If the creation is not successful, this method should throw an exception.
	 */
	def register(params: Map[String, Any]): User
	
}