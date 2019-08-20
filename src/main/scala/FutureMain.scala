

import csv.{Csv, Header, Row}
import play.api.libs.json.{JsError, JsSuccess, Json}
import weatherForecast.Forecast

import scala.util.Success
import scala.util.Failure
import java.io._



import org.joda.time
import org.joda.time.Seconds

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration._

object Main {

  def main(args: Array[String]): Unit = {
    val hdr = List("Temperature","Temperature Min","Temperature Max","Pressure","Sea Level","Ground Level","Humidity","Description","Wind Speed","Wind Degree","Date","City")
        /**
      * hdr adalah variabel dengan tipe List[A]
      * berisi list header yang akan digunakana pada CSV
      * */

    val future: Future[Unit] = Future {
            /**
        * future adalah variabel dengan tipe Future[Unit]
        * didalam Future{} terdapat aktivitas yang akan dieksekusi secara asyncronous
        * */

      /**
        *
        * */
      val url = "http://api.openweathermap.org/data/2.5/forecast?id=524901&APPID=f8c2705abbe43a56eaca373e8d86070e"
      // url adalah variabel mendefinisikan link API

      val jsonStr = io.Source.fromURL(url).getLines.mkString
      /**
        * jsonStr adalah variabel dimana ketika dijalankan, io.Source.fromURL akan membaca data dari link variabel url menjadi BufferedSource
        * kemudian getLines akan mengubah buffered source menjadi non empty iterator
        * setelah itu, .mkString akan menjadikan non-empty-iterator menjadi string
        * */

      val jsonValue = Json.parse(jsonStr)
      /**
        * jsonValue adalah variabel yang ketika dijalankan, Json.parse() akan menjadikan jsonStr yang merupakan JSON String menjadi jsonvalue
        * */

      val forecastOpt = jsonValue.asOpt[Forecast]
      /**
        * forecastOpt adalah variabel yang bertipe Option[Forecast]
        * */

      val csvOpt = forecastOpt.map { forecast =>
        /**
          * @correction
          * forecastOpt adalah variabel dengan tipe Option[Forecast]
          * ketika forecastOpt.map() dijalankan, map() akan mengubah concrete value yang ada di dalam forecastOpt
          * berdasarkan anonymous function yang jadi argumen map() jika forecastOpt adalah Some[Forecast].
          * Jika forecastOpt adalah None, map() tidak akan bisa mengubah None menjadi None lain
          * */

        val rowList = forecast.weatherList.map { weatherList =>
          /**
            * @correction
            * forecast adalah concrete value bertipe Forecast yang dikandung oleh forecastOpt jika dan hanya jika forecastOpt bertipe Some[Forecast]
            * forecast.weatherList adalah field dari Forecast yang bertipe data List[WeatherList]
            * oleh karena itu ketika forecast.weatherList.map() dijalankan, map() akan mengubah List[WeatherList] menjadi List[A],
            * sesuai dengan tipe kembalian dari anonymous function yang menjadi argumen dari map
            * untuk kasus di bawah, map() memiliki tipe WeatherList => Row, sehingga map() di bawah ini
            * akan mengubah List[WeatherList] menjadi List[Row]
            *
            * */

          Row(
            /**
              * Row adalah sebuah case class yang berisi beberapa field yang akan di-write ke CSV
              * */

            weatherList.main1.temperature, weatherList.main1.temperatureMin, weatherList.main1.temperatureMax, weatherList.main1.pressure, weatherList.main1.seaLevel, weatherList.main1.groundLevel, weatherList.main1.humidity, weatherList.weather.map(_.description).mkString, weatherList.wind.speed, weatherList.wind.degree, weatherList.dateText, forecast.city.name
            //diatas ini adalah isi dari tiap field yang akan ditampilkan pada CSV
            /**
              * didalam Row() terdapat beberapa parameter
              * parameter tersebut diakses dari  weatherList.main1 bertipe List[main1],
              * dimana didalamnya terdapat beberapa value seperti temperature, pressure dll
              * */

          )
        }
        Csv(rowList.toList, Header(hdr))
        // csv adalah suatu class yang memiliki parameter berupa body dari csv ->rowList.toList(dari collection Row dikonversi menjadi List) dan Header untuk file pada csv-nya->Header(hd) berupa list nama header
        /**
          * Csv adalah case class yang memiliki 2 parameter input, yaitu body dan header
          * rowList adalah variable bertipe seq[Row] hasil dari mapping dari forecast.weatherList.map
          * kemudian untuk menjadikannya list makan rowList dikonversi .toList
          * Header() adalah case class yang berisi hdr, yang merupakan List[] dari header yang akan di-write ke CSV
          * * */

      }
      csvOpt
      /**
        *csvOpt adalah sebuah variabel bertipe Option[Csv]
        * */

    }.flatMap { csvOpt =>
      /**
        * flatmap() akan mengubah Option[Csv] menjadi Future[S]
        * */

      csvOpt match {
        // disini kita akan melakukan match terhadap suatu kondisi yang akan dihadapi oleh collection csvOpt
        /**
          *match {} mengubah match statement menjadi pattern matching
          * */

        case Some(cc) => Future {
          /**
            * case Some(A), berisi aktivitas yang dijalankan bila program berhasil dieksekusi
            * Future {} berisi multiple implementation
            * */

          val cc = csvOpt.toList.mkString("\n")
/**
            * cc adalah variabel yang didalmnya terdapat csvOpt yang bertipe Option[Csv]
            * dimana csvOpt akan dijadikan List dengan .toList
            * setelah itu tiap nilai dari List akan diprint dengan new line("\n") dengan .mkString
            * */

          val pw = new PrintWriter(new File("weather_future.csv" ))

          /**
            *variabel pw mengakses class yang disediakan oleh java io
            * pertama variabel pw akan mengakses new class PrintWriter(File file), dimana parameternya adalah File
            * kedua, new PrintWriter mengakses new class File(String Path Name), dimana parameternya adalah path dimana file akan disimpan
            * */

          pw.write(cc)

          /**
            *write(cc) akan menuliskan file yang disimpan dalam variabel cc ke dalam file csv
            * */

          pw.close
          /**
            *close akan menutup stream penulisan ke file csv
            * */

          println("Csv is already generated")
          /**
            *bila file csv sudah di-generate maka akan menampilkan pesan di atas
            * */
        }
        case None => println("something bad happened; maybe jsonStr is not a valid json string")
                   /**
            *None bertipe Option[Nothing], berisi pesan exception
            * */
          Future.failed(new RuntimeException())
        /**
          *apabila failed, maka Future akan melakukan throw exception
          * */
      }
    }


    val result: Unit = Await.result(future,1.minute)

    /**
      * val result bertipe Unit yang akan menampung hasil balikan Future dengan fungsi Await
      * .result(Awaitable[T],Duration) memiliki 2 parameter yaitu Awaitable yang berupa variabel yang telah mengimplementasi Future dan Duration
      * */



  }

}

// as[Foo] will simply try to convert a JsValue into a Foo, and if it fails, it will throw an exception. This is not a safe thing to do
// https://stackoverflow.com/questions/42727949/whats-the-difference-between-json-fromjson-as-asopt-and-validate
// asOpt[Foo] will try to convert a JsValue into a Foo, and if it fails, it will simply return None. If it's successful, it will return the de-serialized value wrapped in Some. This is much better than as, since you are forced to confront the failure case (unless you do something silly like call .get). The downside is, all of the errors are swallowed when converting the failure to None, so you have no idea why it failed. If you don't care, using  asOpt is perfectly fine.
// validate[Foo] will try to convert a JsValue into a Foo, and will always return a JsResult[Foo]. If the conversion is successful, it will be a JsSuccess[Foo] that contains the de-serialized value. If it fails, it will be a  JsError that contains all of the error information such as what paths are missing and what types do not match what they are expected to be.
