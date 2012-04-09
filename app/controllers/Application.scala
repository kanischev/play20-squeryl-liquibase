package controllers

import play.api.mvc._
import squeryl.EmployeeQueries

object Application extends Controller {
  
  def index = Action {
      Ok(views.html.index(EmployeeQueries.allEmployees()))
  }
  
}