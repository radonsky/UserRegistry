package models

import play.api.libs.json._
import play.api.libs.functional.syntax._


case class Address(
    street: Option[String],
    city: Option[String],
    state: Option[String],
    zip: Option[String])
    
    
object Address {
  
//  implicit val reads: Reads[Address] = (
//      (__ \ "street").readNullable[String] and
//      (__ \ "city").readNullable[String] and
//      (__ \ "state").readNullable[String] and
//      (__ \ "zip").readNullable[String]
//  ) (Address.apply _)
//  
//  implicit val writes: Writes[Address] = (
//      (__ \ "street").writeNullable[String] and
//      (__ \ "city").writeNullable[String] and
//      (__ \ "state").writeNullable[String] and
//      (__ \ "zip").writeNullable[String]
//  ) (unlift(Address.unapply))

  implicit val format: Format[Address] = (
      (__ \ "street").formatNullable[String] and
      (__ \ "city").formatNullable[String] and
      (__ \ "state").formatNullable[String] and
      (__ \ "zip").formatNullable[String]
  ) (Address.apply _, unlift(Address.unapply))
}