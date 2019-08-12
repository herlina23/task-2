//class nyoba {
//
//}
//
//// PrintWriter
//
////import java.io._
////val pw = new PrintWriter(new File("hello.txt" ))
////pw.write("Hello, world")
////pw.close
//
//// accessing the data from api, in xml format
//val url = "http://api.openweathermap.org/data/2.5/forecast?mode=xml&lat=55&lon=0&APPID=f8c2705abbe43a56eaca373e8d86070e"
//
//var json = "http://api.openweathermap.org/data/2.5/forecast?id=524901&APPID=f8c2705abbe43a56eaca373e8d86070e"
//
////make the xml parse to list of string
//val l: List[String] = io.Source.fromURL(url).getLines.toList
//
//val br = io.Source.fromURL(url).getLines.mkString
//
//val jn = io.Source.fromURL(json).getLines.mkString
//
//
//
//// mapping and trim
////make parsing easier, trim that white space from each line
//val k = l map (_.trim)
//
//
//// new version