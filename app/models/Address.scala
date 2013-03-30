package models

import play.api.libs.json._
import play.api.libs.functional.syntax._


case class Address(
    street: Option[String],
    city: Option[String],
    state: Option[String],
    zip: Option[String])
    
    

object AddressFormat {
    implicit val addressFormat = Json.format[Address]
}