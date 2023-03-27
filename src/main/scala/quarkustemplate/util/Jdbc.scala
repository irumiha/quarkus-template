// Heavily inspired by play.api.db.Databases from Play Framework

package quarkustemplate.util

import io.agroal.api.AgroalDataSource

import java.sql.Connection
import javax.enterprise.context.ApplicationScoped
import scala.language.implicitConversions
import scala.util.control.ControlThrowable

@ApplicationScoped
class Jdbc(dataSource: AgroalDataSource) {

  private def getConnection(autocommit: Boolean): Connection = {
    val connection = dataSource.getConnection
    try
      connection.setAutoCommit(autocommit)
    catch {
      case e: Throwable =>
        connection.close()
        throw e
    }
    connection
  }

  def withConnection[A](block: Connection => A): A =
    withConnection(autocommit = true)(block)

  def withConnection[A](autocommit: Boolean)(block: Connection => A): A = {
    val connection = getConnection(autocommit)
    try
      block(connection)
    finally
      connection.close()
  }

  def withTransaction[A](block: Connection => A): A =
    withConnection(autocommit = false) { connection =>
      try {
        val r = block(connection)
        connection.commit()
        r
      } catch {
        case e: ControlThrowable =>
          connection.commit()
          throw e
        case e: Throwable =>
          connection.rollback()
          throw e
      }
    }

  def withTransaction[A](isolationLevel: Int)(block: Connection => A): A =
    withConnection(autocommit = false) { connection =>
      val oldIsolationLevel = connection.getTransactionIsolation
      try {
        connection.setTransactionIsolation(isolationLevel)
        val r = block(connection)
        connection.commit()
        r
      } catch {
        case e: ControlThrowable =>
          connection.commit()
          throw e
        case e: Throwable =>
          connection.rollback()
          throw e
      } finally
        connection.setTransactionIsolation(oldIsolationLevel)
    }

}
