name := "sbt-blueprint"

version := "0.0.1"

sbtPlugin := true

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-xml" % "1.0.2",
  "biz.aQute.bnd" % "bndlib" % "2.3.0",
  "org.specs2" %% "specs2" % "2.3.13" % "test"
)
    