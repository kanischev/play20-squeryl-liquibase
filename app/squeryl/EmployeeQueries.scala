package squeryl

/**
 * @date: 08.04.12
 * @author: Kaa
 */
import EmployeeSchema._
import org.squeryl.PrimitiveTypeMode._

object EmployeeQueries extends PlaySquerylHelper {
  def allEmployees() = withSession {
    employees.toList
  }
}