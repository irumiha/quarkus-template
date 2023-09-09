package quarkustemplate.resource

import io.smallrye.common.annotation.Blocking
import jakarta.annotation.security.{PermitAll, RolesAllowed}
import quarkustemplate.util.Jdbc
import scalatags.Text.all._
import jakarta.ws.rs._
import jakarta.ws.rs.core.{Context, MediaType, SecurityContext}
import org.eclipse.microprofile.jwt.JsonWebToken

case class HelloResponse(message: String)
case class HelloRequest(name: String, sort: String)

@Path("/hello")
class GreetingsResource(
    clientRepository: ClientRepository,
    jdbc: Jdbc,
    jwt: JsonWebToken
) {

  @POST
  @Path("/named")
  @Consumes(Array(MediaType.APPLICATION_JSON))
  @Produces(Array(MediaType.APPLICATION_JSON))
  def hello(req: HelloRequest): HelloResponse =
    HelloResponse(s"Hello ${req.name}, from Scala 2.13.10, sorted as ${req.sort}")

  @GET
  @Path("/named/{name}")
  @PermitAll
  @Produces(Array(MediaType.APPLICATION_JSON))
  def hello(
      @PathParam("name") name: String,
      @Context ctx: SecurityContext
  ): HelloResponse =
    HelloResponse(
      s"Hello $name, from Scala 2.13.10, security context: ${getResponseString(ctx)}"
    )

  @GET
  @Path("html")
  @Produces(Array(MediaType.TEXT_HTML))
  @RolesAllowed(Array("User", "Admin"))
  @Blocking
  def helloHtml(@QueryParam("name") name: String, @Context context: SecurityContext): String = jdbc.withConnection {
    implicit connection =>
      val cl = clientRepository.findByName(name)

      "<!DOCTYPE html>" + html(
        head(title := "This is a title"),
        body(
          div(
            h1(id := "title", "This is a title"),
            p("This is a big paragraph of text"),
            p(s"Client name: ${cl.headOption.map(_.shortName).getOrElse("Not found")}"),
            p("Authorization details: ", getResponseString(context))
          )
        )
      )
  }

  private def getResponseString(ctx: SecurityContext): String = {
    val name = if (ctx.getUserPrincipal == null) {
      "anonymous"
    } else if (!ctx.getUserPrincipal.getName.equals(jwt.getName)) {
      throw new InternalServerErrorException(
        "Principal and JsonWebToken names do not match"
      )
    } else {
      ctx.getUserPrincipal.getName
    }

    s"hello $name, isHttps: ${ctx.isSecure}, authScheme: ${ctx.getAuthenticationScheme}, hasJWT: $hasJwt"
  }

  private def hasJwt = jwt.getClaimNames != null
}
