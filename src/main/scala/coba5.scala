import JsonTest.jsonValue
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

//https://www.playframework.com/documentation/2.6.x/ScalaJsonCombinators

case class User123(create_date:String,_id:String,name:String,password:String,username:String,role:String)


object Coba5 {

  //var url = "http://api.openweathermap.org/data/2.5/forecast?id=524901&APPID=f8c2705abbe43a56eaca373e8d86070e"
  var url = "http://laundry-microservice-users.herokuapp.com/api/v1/users"
  val jn = io.Source.fromURL(url).getLines.mkString
  //val json = Json.parse(jn)



//  json.validate[Users] match {
//    case s: JsSuccess[Users] => {
//      val place: Users = s.get
//      // do something with place
//    }
//    case e: JsError => {
//      // error handling flow
//      println("Errors: " + JsError.toJson(e).toString())
//    }
//  }

  def printJson ={
    implicit val UserReads: Reads[User123] = (
      (JsPath \ "create_date").read[String] and
        (JsPath \ "_id").read[String] and
        (JsPath \ "name").read[String] and
        (JsPath \ "password").read[String] and
        (JsPath \ "username").read[String] and
        (JsPath \ "role").read[String]
      )(User123.apply _)

    implicit val UserWrites: Writes[User123] = (
      (JsPath \ "create_date").write[String] and
        (JsPath \ "_id").write[String] and
        (JsPath \ "name").write[String] and
        (JsPath \ "password").write[String] and
        (JsPath \ "username").write[String] and
        (JsPath \ "role").write[String]
      )(unlift(User123.unapply))

   // val json: JsValue = Json.parse(jn)
  val userList = Json.parse(jn).asOpt[List[User123]]




    // apa berbedaan .as, .asOpt, dan validate
    // asOpt : If we are unsure about the content of JsValue then we can use asOpt which will return a None if deserializing causes and exception.
    // validate : If we want a boolean then we can use the validate method which returns JsSuccess and JsError Better to use the validate method to check the parsed json

    println(userList)
  }


  def main(args: Array[String]) {

    printJson
  }

}