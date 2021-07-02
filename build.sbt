ThisBuild / scalaVersion := "2.13.6"
ThisBuild / organization := "com.twcrone"

lazy val logdb = (project in file("."))
  .settings(
    name := "logdb",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.7" % Test,
  )