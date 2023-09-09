package quarkustemplate.resource

import anorm._

case class Client(
    shortName: String,
    longName: String,
    address: String,
    address2: Option[String],
    address3: Option[String],
    postalCode: String,
    city: String,
    country: String,
    email: Option[String],
    phoneNum: Option[String],
    phoneNum2: Option[String],
    phoneNum3: Option[String]
)

object Client {
  val parser: RowParser[Client] = Macro.namedParser[Client](Macro.ColumnNaming.SnakeCase)
}
