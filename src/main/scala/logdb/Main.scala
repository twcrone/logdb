package logdb

import scala.:+
import scala.io.Source

object Main extends App {
  var db:Map[String, List[String]] = Map()
  val logfile = "/Users/tcrone/lib/newrelic/logs/newrelic_agent.log"
  val bufferedSource = Source.fromFile(logfile)
  for(line <- bufferedSource.getLines()) {
    val strings = line.split(" ")
    for(s <- strings) {
      if(s.startsWith("com.newrelic.agent.Transaction@")) {
        var transactionId = s.substring(31)
        var index = transactionId.indexOf("-")
        if(index != -1) {
          transactionId = transactionId.substring(0, index)
        }
        index = transactionId.indexOf(":")
        if(index != -1) {
          transactionId = transactionId.substring(0, index)
        }
        val listOption = db.get(transactionId)
        if(listOption.isEmpty) {
          db += (transactionId -> List(line))
        }
        else {
          db += (transactionId -> listOption.get.:+(line))
        }
      }
    }
  }
  println(db.keys)
  bufferedSource.close()
}