import csv.{Csv, Row,Header}
import play.api.libs.json.Json
import weatherForecast.Forecast

import java.io._

object Main {

  def main(args: Array[String]): Unit = {

    //load from source
    //change source to weather forecast
    val hdr = List("Temperature","Temperature Min","Temperature Max","Pressure","Sea Level","Ground Level","Humidity","Description","Wind Speed","Wind Degree","Date","City")
    val url = "http://api.openweathermap.org/data/2.5/forecast?id=524901&APPID=f8c2705abbe43a56eaca373e8d86070e"
    val jsonStr = io.Source.fromURL(url).getLines.mkString
    val jsonValue = Json.parse(jsonStr)
    //val forecastOpt = jsonValue.asOpt[Forecast]
    val forecastOpt = jsonValue.asOpt[Forecast]
    val csvOpt = forecastOpt.map { forecast =>
      val rowList = forecast.weatherList.map { weatherList =>
        Row(
          weatherList.main1.temperature,weatherList.main1.temperatureMin,weatherList.main1.temperatureMax, weatherList.main1.pressure, weatherList.main1.seaLevel,weatherList.main1.groundLevel,weatherList.main1.humidity, weatherList.weather.map(_.description).mkString,weatherList.wind.speed, weatherList.wind.degree, weatherList.dateText, forecast.city.name
        )
      }
      //Csv(rowList.toList,Header(hdr))
      Csv(rowList.toList,Header(hdr))
    }
    //csvOpt.foreach(_.toString)
    //weatherList.weather.map(_.description).toString



   // bikin error handle, jabarin bingung dmn, kpn pake optioon, either, try
    //

    val cc = csvOpt.toList.mkString("\n")

    println(cc)



//    val pw = new PrintWriter(new File("weather_3.csv" ))
//    pw.write(cc)
//    pw.close

// buat header dr class csv

  }

}
