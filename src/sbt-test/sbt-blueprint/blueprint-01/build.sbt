name := "blueprint-01"

organization := "io.ekai.sbt"

scalaVersion := "2.10.4"


val root = (project in file(".")) enablePlugins(BlueprintPlugin)
