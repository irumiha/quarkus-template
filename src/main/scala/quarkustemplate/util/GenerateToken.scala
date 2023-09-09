package quarkustemplate.util

import io.smallrye.jwt.build.Jwt
import org.eclipse.microprofile.jwt.Claims

import scala.collection.mutable
import scala.jdk.CollectionConverters._

object GenerateToken {
  def main(args: Array[String]): Unit = {
    val token = Jwt
      .issuer("https://example.com/issuer")
      .upn("jdoe@quarkus.io")
      .groups(mutable.HashSet("User", "Admin").asJava)
      .claim(Claims.birthdate.name, "2001-07-13")
      .sign

    System.out.println(token)
  }
}
