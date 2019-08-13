//import play.api.libs.json.{JsValue, Json}
//
//import scala.io.Source
//
//
////  val source: String = Source.fromFile("app/assets/jsons/countriesToCities.json").getLines.mkString
//
////  val source: String = Source.fromFile("weather.json").getLines.mkString
////
////  val source22: String = Source.fromFile("weather.json").getLines.mkString.toString
//
//
////  val json: JsValue = Json.parse(source)
//
//// https://stackoverflow.com/questions/34334462/how-to-load-json-file-using-play-with-scala
//
//
//
////case class Bucket(key:String, subBuckets: List[Bucket] )
//case class List1(dt:String,main:Main1,weather: Weather,clouds: Clouds, wind: Wind,rain: Rain, sys: Sys, dx_txt:String )
//case class Main1(temp: String,temp_min:String,temp_max:String,pressure:String,sea_level:String,grnd_level:String,humidity:String,temp_kf:String )
//case class Weather(id:String, main:String, description:String,icon:String)
//case class Clouds(all:String)
//case class Wind(speed:String,deg:String)
//case class Rain(`3h`:String)
//case class Sys(pod:String)
////case class Raw(cod:String,message,cnt,)
//case class Users(create_date:String,_id:String,name:String,password:String,username:String,role:String)
////case class Business(name:String,preferredUrl:String,businessPhone:String,retailer:Retailer)
//
////
//
//object CountWord222 {
//
//  var url = "http://api.openweathermap.org/data/2.5/forecast?id=524901&APPID=f8c2705abbe43a56eaca373e8d86070e"
////var url = "http://laundry-microservice-users.herokuapp.com/api/v1/users"
//
//
//  val jn = io.Source.fromURL(url).getLines.mkString
//  val json = Json.parse(jn)
//
//
//
//
//
//  def main(args: Array[String]) {
//
//println(jn)
//  }
//
//}