import csv.{Csv, Row}
import play.api.libs.json.Json
import weatherForecast.Forecast

object Main {

  def main(args: Array[String]): Unit = {

    //load from source
    //change source to weather forecast
    val url = "http://api.openweathermap.org/data/2.5/forecast?id=524901&APPID=f8c2705abbe43a56eaca373e8d86070e"
    val jsonStr = io.Source.fromURL(url).getLines.mkString
    val jsonValue = Json.parse(jsonStr)
    val forecastOpt = jsonValue.asOpt[Forecast]
    val csvOpt = forecastOpt.map { forecast =>
      val rowList = forecast.weatherList.map { weatherList =>
        Row(
          weatherList.main1.temperature,

        )
      }
      Csv(rowList.toList)
    }
    csvOpt.foreach(_.toString)
  }

}
