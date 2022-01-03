import java.util.concurrent.TimeUnit
import scala.concurrent.duration.TimeUnit

sealed trait Json
case class JNumber(value: BigDecimal) extends Json
case class JString(value: String) extends Json
case class JBoolean(value: Boolean) extends Json
case class JArray(elems: List[Json]) extends Json
case class JObject(fields: (String, Json)*) extends Json

JObject("name" -> JString("Paul"), "age" -> JNumber(42))


def obj(fields: (String, Json)*): Json = JObject(fields: _*)

object Json {
  import scala.language.implicitConversions
  implicit def stringToJson(s: String): Json = JString(s)
  implicit def intToJson(n: Int): Json = JNumber(n)
}
import Json.*


obj("name" -> "Paul", "age" -> 42)


case class Duration(value: Int, unit: TimeUnit)

object Duration {

  object Syntax {
    import scala.language.implicitConversions
    implicit class HasSeconds(n: Int) {
      def seconds: Duration = Duration(n, TimeUnit.SECONDS)
    }
  }

}
import Duration.Syntax._

val delay =15.seconds


