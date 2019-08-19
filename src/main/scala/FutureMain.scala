

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
    // val hdr adalah list yang berisi nama-nama header yang akan ditampilkan pada file CSV

    val future: Future[Unit] = Future {
      // val future mendefinisikan suatu variabel yang berbentuk unit dengan menggunakan metode Future sebagai jenis metode asyncronous

      val url = "http://api.openweathermap.org/data/2.5/forecast?id=524901&APPID=f8c2705abbe43a56eaca373e8d86070e"
      // url mendefinisikan link API

      val jsonStr = io.Source.fromURL(url).getLines.mkString
      // jsonStr berfungsi mengambil data dari API yang sudah didefinisi, dimana data dari API dibaca dengan  io.Source.fromURL,dibaca per line dengan .getLines dan melakukan join tiap line dengan .mkString

      val jsonValue = Json.parse(jsonStr)
      // jsonValue melakukan parsing dari suatu string JSON menjadi suatu scala object

      val forecastOpt = jsonValue.asOpt[Forecast]
      //forecastOpt melakukan konversi dari suatu json value menjadi suatu collection Forecast dengan menggunakan asOpt menjadi suatu Option type, sehingga program akann tetap berjalan meskipun ada kesalahan

      val csvOpt = forecastOpt.map { forecast =>
        //pada csvOpt kita akan melakukan mapping pada collection forecastOpt, collection yang akan di-mapping didalamnya adalah forecast.

        val rowList = forecast.weatherList.map { weatherList =>
          //setelah memasuki collection forecast, kita akan melaukkan mapping, untuk mendapat value dari weatherlist yang berada di dalam collection forecast

          Row(
            // Row -> mengakses sebuat case class didalam Csv yang berisi definisi dari kolom-kolom yang akan ditampilkan pada file CSV

            weatherList.main1.temperature, weatherList.main1.temperatureMin, weatherList.main1.temperatureMax, weatherList.main1.pressure, weatherList.main1.seaLevel, weatherList.main1.groundLevel, weatherList.main1.humidity, weatherList.weather.map(_.description).mkString, weatherList.wind.speed, weatherList.wind.degree, weatherList.dateText, forecast.city.name
            //diatas ini adalah isi dari tiap field yang akan ditampilkan pada CSV
          )
        }
        Csv(rowList.toList, Header(hdr))
        // csv adalah suatu class yang memiliki parameter berupa body dari csv ->rowList.toList(dari collection Row dikonversi menjadi List) dan Header untuk file pada csv-nya->Header(hd) berupa list nama eader

      }
      csvOpt
      // mendefinisikan variabel csvOpt

      //dibawah ini adalah method untuk melakukan validasi (success or failure)
    }.flatMap { csvOpt =>
      //setelah didefinisikan (namanya apa ?) sbg collection, maka kita akan melakuka flatmap()
      // menggabungkan banyak array (nested) menjadi satu kesatuan Array. yang nantinya memudahkan untuk melakukan validasi

      csvOpt match {
        // disini kita akan melakukan match terhadap suatu kondisi yang akan dihadapi oleh collection csvOpt

        case Some(cc) => Future {
          // case  pertama adalah some, berisi aktivitas yang dijalankan bila program berhasil dijalankan
          // penggunaan some, karena diatas kita mengakses suatu nilai dari container Option, maka matching pattern-nya menggunakan some untuk success  result

          val cc = csvOpt.toList.mkString("\n")
          //cc adalah collection csvOpt yang sudah dikonversi menjadi list yang sudah dipisahkan dengan new line("\n")

          val pw = new PrintWriter(new File("weather_future.csv" ))
          // pw menghasilkan new class dari PrintWriter untuk melakukan write pada file system, dengan path dan nama tertentu

          pw.write(cc)
          // setelah itu menuliskan value yang disimpan pada variabel cc ke file csv

          pw.close
          //penulisan ditutup

          println("Csv is already generated")
          // bila file csv sudah di-generate maka akan menampilkan pesan di atas
        }
        case None => println("something bad happened; maybe jsonStr is not a valid json string")
          // case kedua adlah none, termasuk matching pattern dari Option, yang akan menampilkan pesan error
          Future.failed(new RuntimeException())
        // apabila failed, maka Future akan melakukan throw exception
      }
    }


    val result: Unit = Await.result(future,1.minute)
    // val result digunakan untuk memanggil hasil dari eksekusi Future dimana pemanggilan hasil Future dengan Await, dengan 2 parameter input, yaitu variabel future dan durasi




  }

}

// as[Foo] will simply try to convert a JsValue into a Foo, and if it fails, it will throw an exception. This is not a safe thing to do
// https://stackoverflow.com/questions/42727949/whats-the-difference-between-json-fromjson-as-asopt-and-validate
// asOpt[Foo] will try to convert a JsValue into a Foo, and if it fails, it will simply return None. If it's successful, it will return the de-serialized value wrapped in Some. This is much better than as, since you are forced to confront the failure case (unless you do something silly like call .get). The downside is, all of the errors are swallowed when converting the failure to None, so you have no idea why it failed. If you don't care, using  asOpt is perfectly fine.
// validate[Foo] will try to convert a JsValue into a Foo, and will always return a JsResult[Foo]. If the conversion is successful, it will be a JsSuccess[Foo] that contains the de-serialized value. If it fails, it will be a  JsError that contains all of the error information such as what paths are missing and what types do not match what they are expected to be.
