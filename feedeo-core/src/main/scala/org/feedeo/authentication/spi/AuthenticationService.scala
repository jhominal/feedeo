package org.feedeo.authentication.spi

import net.liftweb.common.Box
import scala.collection.Map
import org.feedeo.model.user.User

trait AuthenticationService {
    /**
     * The login method.
     * @param params The parameters for the login.
     * @return A Box containing the authenticated {@link User}, or a Failure if authentication is not successful.
     */
    def login(params: Map[String, Any]): Box[User]
    
    /**
     * A method to register a new User. Will return a Failure if registration is unsuccessful
     * @param params The parameters for the registration.
     * @return A Box containing the newly created User, or a Failure if the registration is not successful.
     */
    def register(params: Map[String, Any]): Box[User]
    
}