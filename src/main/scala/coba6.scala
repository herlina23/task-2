
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

//https://www.playframework.com/documentation/2.6.x/ScalaJsonCombinators

object Coba6 {

  val json: JsValue = Json.parse(
  """
  {
    "fixed": "abc",
    "issues": [{
    "issue": "issue0",
    "fields": {
      "value": "value1"
    }
  }, {
    "issue": "issue1",
    "fields": {
      "value": "value2",
      "nonexistingcolumn": "blabla"
    }
  }]
  }""")



  case class Issue(issue: String, fields: Map[String, String])
  case class Model(fixed: String, issues: List[Issue])

  def printJson ={

    implicit val issueFormat = Json.format[Issue]
    implicit val modelFormat = Json.format[Model]

    val model = json.as[Model]

    val fieldNames = (for {
      issue <- model.issues
      field <- issue.fields
    } yield field._1) distinct

    val table = model.issues map { issue =>
      model.fixed :: issue.issue :: (fieldNames map { fieldName =>
        issue.fields.getOrElse(fieldName, "null")
      })
    }

    val headers = (List("fixed", "issue") ++ fieldNames)
    val tableWithHeaders =  headers :: table

    tableWithHeaders foreach { row =>
      println(row map (" %-20s ".format(_)) mkString("|"))
    }
  }


  def main(args: Array[String]) {
    printJson

  }

}