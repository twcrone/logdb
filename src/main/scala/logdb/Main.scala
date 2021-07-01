package logdb

import scala.io.Source

object Main extends App {
  val logfile = "/Users/tcrone/lib/newrelic/logs/newrelic_agent.log"
  val bufferedSource = Source.fromFile(logfile)
  for(line <- bufferedSource.getLines()) {
    val strings = line.split(" ")
    for(s <- strings) {
      if(s.startsWith("com.newrelic.agent.Transaction@")) {
        val transactionId = s.substring(31)
        println(transactionId)
        println(line)
      }
    }
  }
  bufferedSource.close()
}