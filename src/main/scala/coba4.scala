import play.api.libs.json._
import play.api.libs.functional.syntax._
//import play.api.data.validation.ValidationError
import play.api.libs.json.Reads._

case class Retailer(firstName:String,lastName:String,email:String,mobileNo:String,password:String)
case class Business(name:String,preferredUrl:String,businessPhone:String,retailer:Retailer)

object JsonTest {
  val jsonValue = """
  {
    "business":
    {
      "name":"Some Business Name",
      "preferredUrl":"someurl",
      "businessPhone":"somenumber",
      "retailer":
      {
        "firstName":"Some",
        "lastName":"One",
        "email":"someone@somewhere.com",
        "mobileNo":"someothernumber",
        "password":"$^^HFKH*"
      }
    }

  }
  """
  def printJson ={

    implicit val rltRds = (
      (__ \ "firstName").read[String] ~
        (__ \ "lastName").read[String] ~
        (__ \ "email").read[String] ~
        (__ \ "mobileNo").read[String] ~
        (__ \ "password").read[String]
      )(Retailer)

//    implicit val bsnsRds = (
//      (__ \ "name").read[String] ~
//        (__ \ "preferredUrl").read[String] ~
//        (__ \ "businessPhone").read[String] ~
//        (__ \ "retailer").read[Retailer](rltRds)
//      )(Business)

    implicit val bsnsRds = (
      (__ \ "business" \ "name").read[String] ~
        (__ \ "business" \ "preferredUrl").read[String] ~
        (__ \ "business" \ "businessPhone").read[String] ~
        (__ \ "business" \ "retailer").read[Retailer](rltRds)
      )(Business)


    val buisness = Json.parse(jsonValue).validate[Business](bsnsRds)
    val bus = new Business("Some Business","somebusinessurl","somenumber", new Retailer("Some","One","someone@somewhere.com","someothernumber","$^^HFKH*"))
    //val json = Json.toJson(bus)

    println(buisness)
  }




  def main(args: Array[String]): Unit = {
    printJson
  }

}
