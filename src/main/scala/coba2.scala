//import java.io.{File, FileOutputStream}
//
//import com.github.agourlay.json2Csv.Json2Csv
//
//object Boot {
//  def main(args: Array[String]): Unit = {
//    if (args.isEmpty) println("E:\\Project_A\\resources\\task-2")
//    else {
//      val output = new FileOutputStream("result-json.csv")
//      Json2Csv.convert(new File(args(0)), output) match {
//        case Right(nb) => println(s"$nb CSV lines written to 'result-json.csv'")
//        case Left(e)  => println(s"Something bad happened $e")
//      }
//    }
//  }
//
//
//
//}
