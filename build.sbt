sbtPlugin := true

name := "sbt-blueprint"

organization := "io.ekai.sbt"

version := "0.0.1-SNAPSHOT"


scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "biz.aQute.bnd" % "bndlib" % "2.3.0",
  "org.specs2" %% "specs2" % "2.3.13" % "test",
  "org.scalaz" %% "scalaz-core" % "7.0.6"
)

addSbtPlugin("com.typesafe.sbt" % "sbt-osgi" % "0.7.0")


initialCommands in console := "import io.ekai.sbt.blueprint._"
