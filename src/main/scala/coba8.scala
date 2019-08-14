//import play.api.libs.json.Json // coba pake validation "either"
// https://stackoverflow.com/questions/18318380/how-to-turn-a-list-of-objects-into-a-map-of-two-fields-in-scala
//
//    val userList1 = Json.parse(jn).validate[Forecast]
//    println(userList1)
//search mapping nested list/object
//
//val userList2 = Json.parse(jn).asOpt[Forecast] // js domain object
//println(userList2.toList)
////
//val f1 = userList2.map(_.weatherList)
//println(f1)
//val f2 = f1.map(t=>(t.seq.map(_.clouds))).map(t=>(t.seq.map(_.all)))
//println(f2)
//
//val f3 = f1.map(t=>(t.seq.map(_.dt_txt)))
//println(f3)
//f3.foreach{println}
////println(f3.foreach(t=>t.seq))
//for(t1<-f3) println(t1)
//
//
//    val userList4 = userList2.map(f=>(f.cod,f.cnt,f.message,f.weatherList,f.city)).toBuffer
//    println(userList4)
