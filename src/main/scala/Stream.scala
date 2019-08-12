class Stream {

}

object CountWord {

  // accessing the data from api, in xml format
  val url = "http://api.openweathermap.org/data/2.5/forecast?id=524901&APPID=f8c2705abbe43a56eaca373e8d86070e"

  // getline = to get words in any lines
  // after that, we use flatmap then split with this regex "\W+" to cleanse space, and other redundant expression (character)
  // then, foldLeft = folds over each element, adding each item to the list
  // after the file is cleansed, we count  the words
  val xml_count = io.Source.fromURL(url).getLines.flatMap(_.split("\\W+")).foldLeft(Map.empty[String, Int]){
    (count, word) => count + (word -> (count.getOrElse(word, 0) + 1))
  }


  def main(args: Array[String]) {
    println(xml_count)


  }

}