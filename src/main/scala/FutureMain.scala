import csv.{Csv, Header, Row}
import play.api.libs.json.{JsError, JsSuccess, Json}
import weatherForecast.Forecast
import scala.util.{Try, Success, Failure}

import scala.util.Success
import scala.util.Failure
import java.io._

import scala.concurrent.{Await, Future, future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.Random

object FutureMain {

  def main(args: Array[String]): Unit = {

    //load from source
    //change source to weather forecast
    val hdr = List("Temperature","Temperature Min","Temperature Max","Pressure","Sea Level","Ground Level","Humidity","Description","Wind Speed","Wind Degree","Date","City")
    val url = "http://api.openweathermap.org/data/2.5/forecast?id=524901&APPID=f8c2705abbe43a56eaca373e8d86070e"
    val jsonStr = io.Source.fromURL(url).getLines.mkString
    val jsonValue = Json.parse(jsonStr)
    val forecastOpt = jsonValue.asOpt[Forecast]

    def csvOpt = Future{
      forecastOpt.map { forecast =>
        val rowList = forecast.weatherList.map { weatherList =>
          Row(
            weatherList.main1.temperature,weatherList.main1.temperatureMin,weatherList.main1.temperatureMax, weatherList.main1.pressure, weatherList.main1.seaLevel,weatherList.main1.groundLevel,weatherList.main1.humidity, weatherList.weather.map(_.description).mkString,weatherList.wind.speed, weatherList.wind.degree, weatherList.dateText, forecast.city.name
          )
        }
        Csv(rowList.toList,Header(hdr))
      }
    }

    val conversion = Await.result(csvOpt, 1 seconds)
    val convPrint = conversion.toList.mkString("\n")



    val t1 = Future{
      jsonValue.validate[Forecast]
    }

    val t2 = Await.result(t1,1 seconds) match {
      case JsSuccess(t1,_) => {
        val pw = new PrintWriter(new File("weather_Future.csv" ))
        pw.write(convPrint)
        pw.close
        println("Csv is already generated")
             }
      case JsError(errors)=> println("Failed, Can not generate csv"+"\n"+t1)
    }



  }

}

