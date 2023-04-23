package quarkustemplate.resource

import anorm._
import anorm.SqlParser._

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
  val parser: RowParser[Client] =
    get[String]("short_name") ~
      get[String]("long_name") ~
      get[String]("address") ~
      get[Option[String]]("address2") ~
      get[Option[String]]("address3") ~
      get[String]("postal_code") ~
      get[String]("city") ~
      get[String]("country") ~
      get[Option[String]]("email") ~
      get[Option[String]]("phone_num") ~
      get[Option[String]]("phone_num2") ~
      get[Option[String]]("phone_num3") map {
        case shortName ~ longName ~ address ~ address2 ~ address3 ~ postalCode ~ city ~ country ~ email ~ phoneNum ~ phoneNum2 ~ phoneNum3 =>
          Client(
            shortName,
            longName,
            address,
            address2,
            address3,
            postalCode,
            city,
            country,
            email,
            phoneNum,
            phoneNum2,
            phoneNum3
          )
      }
}
