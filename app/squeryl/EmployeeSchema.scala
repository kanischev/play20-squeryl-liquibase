package squeryl

import javax.sql.DataSource
import play.api.db.DB
import play.api.Play.current
import org.squeryl.{Session, Schema, KeyedEntity}
import java.sql.Connection
import org.squeryl.internals.DatabaseAdapter
import org.squeryl.adapters.{MySQLAdapter, OracleAdapter, H2Adapter}
import org.squeryl.PrimitiveTypeMode._



/**
 * @date: 05.04.12
 * @author: Kaa
 */

object EmployeeSchema extends Schema {
  val employees = table[Employee]
}

trait SquerylHelper {
  val DbName = "default"

  val NameToAdapter = Map[String, DatabaseAdapter](
    "h2" -> new H2Adapter,
    "oracle" -> new OracleAdapter,
    "mysql" -> new MySQLAdapter
  )

  def dataSource: DataSource

  protected  def createSession = {
    val connection: Connection = dataSource.getConnection
    val driverName = connection.getMetaData.getDriverName
    Session.create(connection, NameToAdapter.find {
      case (name, adapter) => driverName.toLowerCase.contains(name)
    }.get._2)
  }

  def withSession[T](fun : => T): T = {
    if (Session.hasCurrentSession) {
      fun
    } else {
      val session = createSession
      session.bindToCurrentThread
      try {
        fun
      } finally {
        session.unbindFromCurrentThread
        session.close
      }
    }
  }
}

trait PlaySquerylHelper extends SquerylHelper {
  def dataSource = DB.getDataSource(DbName)
}

case class Employee(id: Int, name: String, age: Int, gender: String) extends KeyedEntity[Int]