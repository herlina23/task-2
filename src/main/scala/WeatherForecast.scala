//
//import play.api.libs.json._
//import play.api.libs.json.Reads._
//import play.api.libs.functional.syntax._
//
////https://www.playframework.com/documentation/2.6.x/ScalaJsonCombinators
//
//
//
//object WeatherForecast {
//
//  var url = "http://api.openweathermap.org/data/2.5/forecast?id=524901&APPID=f8c2705abbe43a56eaca373e8d86070e"
//  val jn = io.Source.fromURL(url).getLines.mkString
//
//  case class Forecast(cod:Int, message:Double,cnt:Int, weatherList: Seq[WeatherList])
//  case class WeatherList(dt:Int,main1: Main1,weather: Weather, clouds: Clouds, wind: Wind, rain: Option[Rain], sys: Sys,dt_txt:String)
//  case class City(id:Int,name:String,coord: Coord,country:String,timezone:Int)
//
//  case class Main1(temp:Double,temp_min:Double,temp_max:Double,pressure:Double, sea_level:Double,grnd_level:Double,humidity:Int,temp_kf:Double)
//  case class Weather(id:Int,main:String,description:String,icon:String)
//  case class Clouds(all:Int)
//  case class Wind(speed:Double,deg:Double)
//  case class Rain(`3h`:Double)
//  case class Sys(pod:String)
//  case class Coord(lat:String,lon:String)
//
//
//  def printJson ={
//
//    implicit val main1Reads: Reads[Main1] = (
//      (JsPath \ "temp").read[Double] and
//        (JsPath \ "temp_min").read[Double] and
//        (JsPath \ "temp_max").read[Double] and
//        (JsPath \ "pressure").read[Double] and
//        (JsPath \ "sea_level").read[Double] and
//        (JsPath \ "grnd_level").read[Double] and
//        (JsPath \ "humidity").read[Int] and
//        (JsPath \ "temp_kf").read[Double]
//      )(Main1.apply _)
//
//    implicit val weatherReads: Reads[Weather] = (
//      (JsPath \ "id").read[Int] and
//        (JsPath \ "main").read[String] and
//        (JsPath \ "description").read[String] and
//        (JsPath \ "icon").read[String]
//      )(Weather.apply _)
//
//      implicit val cloudsReads: Reads[Int] = (JsPath \ "all").read[Int]
//
//    implicit val windReads: Reads[Wind] = (
//      (JsPath \ "speed").read[Double] and
//        (JsPath \ "deg").read[Double]
//      )(Wind.apply _)
//
//    implicit val rainReads: Reads[Double] = (JsPath \ "3h").read[Double]
//
//
//  }
//
//
//  def main(args: Array[String]) {
//    printJson
//
//  }
//
//}