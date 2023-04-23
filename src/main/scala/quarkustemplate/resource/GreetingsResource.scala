package quarkustemplate.resource

import io.smallrye.common.annotation.Blocking
import quarkustemplate.util.Jdbc
import scalatags.Text.all._

import javax.ws.rs._
import javax.ws.rs.core.MediaType

case class HelloResponse(message: String)
case class HelloRequest(name: String, sort: String)

@Path("/hello")
class GreetingsResource(clientRepository: ClientRepository, jdbc: Jdbc) {

  @POST
  @Path("/named")
  def hello(req: HelloRequest): HelloResponse =
    HelloResponse(s"Hello ${req.name}, from Scala 2.13.10, sorted as ${req.sort}")

  @GET
  @Path("html")
  @Produces(Array(MediaType.TEXT_HTML))
  @Blocking
  def helloHtml(@QueryParam("name") name: String): String = jdbc.withConnection {
    implicit connection =>
      val cl = clientRepository.findByName(name)

      "<!DOCTYPE html>" + html(
        head(title := "This is a title"),
        body(
          div(
            h1(id := "title", "This is a title"),
            p("This is a big paragraph of text"),
            p(s"Client name: ${cl.headOption.map(_.shortName).getOrElse("Not found")}")
          )
        )
      )
  }
}
