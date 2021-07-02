package logdb

import scala.io.Source

object Main extends App {

  def indexOfTransactionEndChar(transactionId: String) : Int = {
    var index = transactionId.indexOf("-")
    if (index == -1) {
      index = transactionId.indexOf(":")
    }
    index
  }

  var db: Map[String, List[String]] = Map()
  val logfile = "newrelic_agent_log"
  val bufferedSource = Source.fromFile(logfile)
  for (line <- bufferedSource.getLines()) {
    val strings = line.split(" ")
    for (s <- strings) {
      if (s.startsWith("com.newrelic.agent.Transaction@")) {
        var transactionId = s.substring(31)
        var index = indexOfTransactionEndChar(transactionId)
        if (index != -1) {
          transactionId = transactionId.substring(0, index)
        }
        val listOption = db.get(transactionId)
        if (listOption.isEmpty) {
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