package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current
import play.api.libs.json._
import play.api.libs.functional.syntax._

    

case class User(
    id: Pk[Long],
    name: String,
    email: Option[String],
    phone: Option[String],
    address: Address) {

}


object User {
  
  val rowParser = {
    get[Pk[Long]]("id") ~
    get[String]("name") ~
    get[Option[String]]("email") ~
    get[Option[String]]("phone") ~
    get[Option[String]]("street") ~
    get[Option[String]]("city") ~
    get[Option[String]]("state") ~
    get[Option[String]]("zip") map {
      case id~name~email~phone~street~city~state~zip =>
        User(id, name, email, phone, 
            Address(street, city, state, zip))
    }
  }
  
  def findById(id: Long): Option[User] = {
    DB.withConnection { implicit connection =>
      SQL("select * from \"User\" where id = {id}")
        .on('id -> id).as(User.rowParser *).headOption
    }    
  }
  
  
  def save(user: User): Option[Long] = {
    DB.withConnection { implicit connection =>
      SQL("insert into \"User\"(name, email, phone, street, city, state, zip) " +
          "values ({name}, {email}, {phone}, {street}, {city}, {state}, {zip})")
      .on(
        'name -> user.name,
        'email -> user.email,
        'phone -> user.phone,
        'street -> user.address.street,
        'city -> user.address.city,
        'state -> user.address.state,
        'zip -> user.address.zip
      ).executeInsert()
    }
  }
  
//  implicit val reads: Reads[User] = (
//    (__ \ "id").readNullable[Long] and  
//    (__ \ "name").read[String] and
//    (__ \ "email").readNullable[String] and
//    (__ \ "phone").readNullable[String] and
//    (__ \ "address").read[Address]
//  ) (
//    (id, name, email, phone, address) => User(id.map( Id(_) ).getOrElse( NotAssigned ), 
//            name, email, phone, address)
//  )  
//
//  implicit val writes: Writes[User] = (
//    (__ \ "id").writeNullable[Long] and  
//    (__ \ "name").write[String] and
//    (__ \ "email").writeNullable[String] and
//    (__ \ "phone").writeNullable[String] and
//    (__ \ "address").write[Address]
//  ) (
//    (u: User) => (u.id.toOption, u.name, u.email, u.phone, u.address)
//  )
  
  implicit val format: Format[User] = (
    (__ \ "id").formatNullable[Long] and  
    (__ \ "name").format[String] and
    (__ \ "email").formatNullable[String] and
    (__ \ "phone").formatNullable[String] and
    (__ \ "address").format[Address]
  ) (
    (id, name, email, phone, address) => User(id.map( Id(_) ).getOrElse( NotAssigned ), 
            name, email, phone, address),
    (u: User) => (u.id.toOption, u.name, u.email, u.phone, u.address)
  )  
    
}
  

