import play.api.libs.json.{Format, JsError, JsSuccess, JsValue, Json}
import play.api.libs.json.Reads._
import scala.util.Try

case class Weather(id:Int,main:String,description:String,icon:String)
case class Clouds(all:Int)
case class Wind(speed:Double,deg:Double)
case class Rain(`3h`:Double)
case class Sys(pod:String)
case class Coord(lat:Double,lon:Double)

case class Forecast(cod:String, message:Double,cnt:Int, weatherList: Seq[WeatherList],city: City)
case class WeatherList(dt:Int,main1: Main1,weather: Seq[Weather], clouds: Clouds, wind: Wind, rain: Option[Rain], sys: Sys,dt_txt:String)
case class City(id:Int,name:String,coord: Coord,country:String,timezone:Int)

//var url = "http://api.openweathermap.org/data/2.5/forecast?id=524901&APPID=f8c2705abbe43a56eaca373e8d86070e"
//val jn = io.Source.fromURL(url).getLines.mkString
case class Main1(temp:Double,temp_min:Double,temp_max:Double,pressure:Double, sea_level:Double,grnd_level:Double,humidity:Int,temp_kf:Double)
object Main1 {
  implicit val jsonFormat: Format[Main1] = new Format[Main1] {
    override def reads(json: JsValue) = Try {
      val temp = (json \ "temp").as[Double]
      val temp_min = (json \ "temp_min").as[Double]
      val temp_max = (json \ "temp_max").as[Double]
      val pressure = (json \ "pressure").as[Double]
      val sea_level =(json \ "sea_level").as[Double]
      val grnd_level =(json \ "grnd_level").as[Double]
      val humidity =(json \ "humidity").as[Int]
      val temp_kf =(json \ "temp_kf").as[Double]
      Main1(temp,temp_min,temp_max,pressure, sea_level, grnd_level,humidity,temp_kf)
    }.fold(err => JsError(err.getMessage), JsSuccess(_))

    override def writes(o: Main1) = Json.obj(
      "temp" -> o.temp,
      "temp_min" -> o.temp_min,
      "temp_max" -> o.temp_max,
      "pressure" -> o.pressure,
      "sea-level" -> o.sea_level,
      "grnd_level" -> o.grnd_level,
      "humidity" -> o.humidity,
      "temp_kf" -> o.temp_kf
    )
  }
}

object Weather{
  implicit val jsonFormat: Format[Weather] = new Format[Weather] {
    override def reads(json: JsValue) = Try {
      val id = (json \ "id").as[Int]
      val main = (json \ "main").as[String]
      val description = (json \ "description").as[String]
      val icon = (json \ "icon").as[String]
      Weather(id,main,description,icon)
    }.fold(err => JsError(err.getMessage), JsSuccess(_))

    override def writes(o: Weather) = Json.obj(
      "id" -> o.id,
      "main" -> o.main,
      "description" -> o.description,
      "icon" -> o.icon
    )
  }
}

object Clouds{
  implicit val jsonFormat: Format[Clouds] = new Format[Clouds] {
    override def reads(json: JsValue) = Try {
      val all = (json \ "all").as[Int]
      Clouds(all)
    }.fold(err => JsError(err.getMessage), JsSuccess(_))

    override def writes(o: Clouds) = Json.obj(
      "all" -> o.all
    )
  }
}

object Wind{
  implicit val jsonFormat: Format[Wind] = new Format[Wind] {
    override def reads(json: JsValue) = Try {
      val speed = (json \ "speed").as[Double]
      val deg = (json \ "deg").as[Double]
      Wind(speed,deg)
    }.fold(err => JsError(err.getMessage), JsSuccess(_))

    override def writes(o: Wind) = Json.obj(
      "speed" -> o.speed,
      "deg" -> o.deg
    )
  }
}

object Rain{
  implicit val jsonFormat: Format[Rain] = new Format[Rain] {
    override def reads(json: JsValue) = Try {
      val`3h` = (json \ "3h").as[Int]
      Rain(`3h`)
    }.fold(err => JsError(err.getMessage), JsSuccess(_))

    override def writes(o: Rain) = Json.obj(
      "3h" -> o.`3h`
    )
  }
}

object Sys{
  implicit val jsonFormat: Format[Sys] = new Format[Sys] {
    override def reads(json: JsValue) = Try {
      val pod = (json \ "pod").as[String]
      Sys(pod)
    }.fold(err => JsError(err.getMessage), JsSuccess(_))

    override def writes(o: Sys) = Json.obj(
      "pod" -> o.pod
    )
  }
}

object Coord{
  implicit val jsonFormat: Format[Coord] = new Format[Coord] {
    override def reads(json: JsValue) = Try {
      val lat = (json \ "lat").as[Double]
      val lon = (json \ "lon").as[Double]
      Coord(lat,lon)
    }.fold(err => JsError(err.getMessage), JsSuccess(_))

    override def writes(o: Coord) = Json.obj(
      "lat" -> o.lat,
      "lon" -> o.lon
    )
  }
}

object City{
  implicit val jsonFormat: Format[City] = new Format[City] {
    override def reads(json: JsValue) = Try {
      val id = (json \ "id").as[Int]
      val name = (json \ "name").as[String]
      val coord = (json \ "coord").as[Coord]
      val country = (json \ "country").as[String]
      val timezone = (json \ "timezone").as[Int]
      City(id,name,coord,country,timezone)
    }.fold(err => JsError(err.getMessage), JsSuccess(_))

    override def writes(o: City) = Json.obj(
      "id" -> o.id,
      "name" -> o.name,
      "coord" -> o.coord,
      "country" -> o.country,
      "timezone" -> o.timezone
    )
  }
}

object WeatherList{
  implicit val jsonFormat: Format[WeatherList] = new Format[WeatherList] {
    override def reads(json: JsValue) = Try {
      val dt = (json \ "dt").as[Int]
      val main1 = (json \ "main").as[Main1]
      val weather = (json \ "weather").as[Seq[Weather]]
      val clouds = (json \ "clouds").as[Clouds]
      val wind = (json \ "wind").as[Wind]
      val rain = (json \ "rain").asOpt[Rain]
      val sys = (json \ "sys").as[Sys]
      val dt_txt = (json \ "dt_txt").as[String]
      WeatherList(dt,main1,weather,clouds,wind,rain,sys,dt_txt)
    }.fold(err => JsError(err.getMessage), JsSuccess(_))

    override def writes(o: WeatherList) = Json.obj(
      "dt" -> o.dt,
      "main" -> o.main1,
      "weather" -> o.weather,
      "clouds" -> o.clouds,
      "wind" -> o.wind,
      "rain" -> o.rain,
      "sys" -> o.sys,
      "dt_txt" -> o.dt_txt
    )
  }
}

object Forecast{
  implicit val jsonFormat: Format[Forecast] = new Format[Forecast] {
    override def reads(json: JsValue) = Try {
      val cod = (json \ "cod").as[String]
      val message = (json \ "message").as[Double]
      val cnt = (json \ "cnt").as[Int]
      val weaterList = (json \ "list").as[Seq[WeatherList]]
      val city = (json \ "city").as[City]
      Forecast(cod,message,cnt,weaterList,city)
    }.fold(err => JsError(err.getMessage), JsSuccess(_))

    override def writes(o: Forecast) = Json.obj(
      "cod" -> o.cod,
      "message" -> o.message,
      "cnt" -> o.cnt,
      "list" -> o.weatherList,
      "city" -> o.city
    )
  }
}

class Convert{

  var url = "http://api.openweathermap.org/data/2.5/forecast?id=524901&APPID=f8c2705abbe43a56eaca373e8d86070e"
  val jn = io.Source.fromURL(url).getLines.mkString
  val userList2 = Json.parse(jn).asOpt[Forecast]

  // membuat list untuk yang akan di-parsing ke csv
  // Forecast
  val f1 = Json.parse(jn).asOpt[Forecast]
  val f_cod = f1.map(_.cod)
  val f_message = f1.map(_.message)
  val f_nct = f1.map(_.cnt)

  // City
  val c_id = f1.map(_.city).map(_.id)
  val c_name = f1.map(_.city).map(_.name)
  val c_coord= f1.map(_.city).map(_.coord)
  val c_country = f1.map(_.city).map(_.country)
  val c_timezone = f1.map(_.city).map(_.timezone)

  // Weather List
  val w_dt = f1.map(_.weatherList).map(t=>(t.seq.map(_.dt)))
  val w_dtx = f1.map(_.weatherList).map(t=>(t.seq.map(_.dt_txt)))
  val w_sys = f1.map(_.weatherList).map(t=>(t.seq.map(_.sys))).map(t=>(t.seq.map(_.pod)))
  val w_cloud= f1.map(_.weatherList).map(t=>(t.seq.map(_.clouds))).map(t=>(t.seq.map(_.all)))

  //val w_rain = f1.map(_.weatherList).map(t=>(t.seq.map(_.rain))).map(t=>(t.seq.map(_."3h")))

  // Weather List - wind
  val w_speed = f1.map(_.weatherList).map(t=>(t.seq.map(_.wind))).map(t=>(t.seq.map(_.speed)))
  val deg = f1.map(_.weatherList).map(t=>(t.seq.map(_.wind))).map(t=>(t.seq.map(_.deg)))

  //Weather List - weather

 // val w_id = f1.map(_.weatherList).map(t=>(t.seq.map(_.weather))).unzip(_.map(t=>(t.seq.map(_.description))))
  val w_id = f1.map(_.weatherList).map(t=>(t.seq.map(_.weather))).unzip(_.map(t=>(t.seq.map(_.description))))

  //Weather List - Main
  val w_temp = f1.map(_.weatherList).map(t=>(t.seq.map(_.main1))).map(t=>(t.seq.map(_.temp)))
  val w_min = f1.map(_.weatherList).map(t=>(t.seq.map(_.main1))).map(t=>(t.seq.map(_.temp_min)))
  val w_max = f1.map(_.weatherList).map(t=>(t.seq.map(_.main1))).map(t=>(t.seq.map(_.temp_max)))
  val w_pressure = f1.map(_.weatherList).map(t=>(t.seq.map(_.main1))).map(t=>(t.seq.map(_.pressure)))
  val w_sea = f1.map(_.weatherList).map(t=>(t.seq.map(_.main1))).map(t=>(t.seq.map(_.sea_level)))
  val w_grnd = f1.map(_.weatherList).map(t=>(t.seq.map(_.main1))).map(t=>(t.seq.map(_.grnd_level)))
  val w_humidity = f1.map(_.weatherList).map(t=>(t.seq.map(_.main1))).map(t=>(t.seq.map(_.humidity)))
  val w_kf = f1.map(_.weatherList).map(t=>(t.seq.map(_.main1))).map(t=>(t.seq.map(_.temp_kf)))


}



object WeatherForecast{

  def main(args: Array[String]) {

    var url = "http://api.openweathermap.org/data/2.5/forecast?id=524901&APPID=f8c2705abbe43a56eaca373e8d86070e"
    val jn = io.Source.fromURL(url).getLines.mkString

    val con = new Convert
    println(con.)



  }
}