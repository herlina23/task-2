package csv

case class Csv(rows: List[Row],header: Header) {
  override def toString: String = {
    val body = rows.map(_.toString).mkString("\n")
    val str = header.head+"\n"+body
    str
  }
}

//case class Csv(rows: List[Row],header: Header)

//class Header{
//  val abc = List("Temperature","Temperature Min","Temperature Max","Pressure","Sea Level","Ground Level","Humidity","Description","Wind Speed","Wind Degree","Date","City")
//}

case class Header(head: List[String]) {
  override def toString: String = {
    val head1 = head.mkString(",")
    head1
  }
}

case class Row
(
  temperature: Double,
  temperatureMin: Double,
  temperatureMax: Double,
  pressure: Double,
  seaLevel: Double,
  groundLevel: Double,
  humidity: Int,

  weatherDesc: String,
  windSpeed: Double,
  windDegree: Double,
  dateText: String,
  cityName: String
) {
  override def toString = List(
    temperature, temperatureMin,temperatureMax,pressure,seaLevel,groundLevel,humidity, weatherDesc, windDegree, windSpeed,dateText,cityName
  ).mkString(",")
}
