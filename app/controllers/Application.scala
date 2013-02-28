package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import models.User

object Application extends Controller {
  
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }
  
  def addUser() = Action(parse.json) { implicit request =>
    implicit val reads = User.format
    val user = request.body.as[User]
    User.save(user).map { user => 
      Created
    }.getOrElse{
      BadRequest
    }
  }
  
  def getUser(id: Long) = Action { implicit request =>
    User.findById(id).map { user =>
      implicit val format = User.format
      Ok(Json.toJson(user))
    }.getOrElse {
      NotFound("User with id " + id + " wasn't found")
    }
  }
  
}