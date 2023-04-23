package quarkustemplate.resource

import anorm._

import java.sql.Connection
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class ClientRepository {
  def findByName(name: String)(implicit connection: Connection): List[Client] =
    SQL("SELECT * FROM client WHERE short_name = {name}")
      .on("name" -> name)
      .as(Client.parser.*)

}
