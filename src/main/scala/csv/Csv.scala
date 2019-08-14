package csv

case class Csv(rows: List[Row]) {
  override def toString: String = {
    val str = rows.map(_.toString).mkString("\n")
    str
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
