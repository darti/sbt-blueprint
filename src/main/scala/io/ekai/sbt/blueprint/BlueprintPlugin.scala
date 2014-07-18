package io.ekai.sbt.blueprint

import sbt._
import Keys._

import scala.xml._

import Blueprint._

import scalaz.Scalaz._

/**
 * Created on 12/07/14.
 *
 * @author Matthieu Dartiguenave, matthieu@ekai.io
 */
object BlueprintPlugin extends AutoPlugin {

  object autoImport {
    lazy val blueprint = taskKey[Unit]("Parse Blueprint file.")
  }

  import autoImport._

  private def mergeHeaders(h1 : Map[String, Seq[String]], h2 : Map[String, Seq[String]]) = {
    h1.unionWith(h2)( _ ++ _)
  }

  override def projectSettings: Seq[Setting[_]] = Seq(
    blueprint := {
      val bp =  (unmanagedResources in Compile).value flatMap { s => (s / "OSGI-INF" / "blueprint"  ** "*.xml").get }
      val headers = bp map { f => blueprintHeaders(XML.loadFile(f))}
      val res = headers reduceLeft { _.unionWith(_) { _ ++ _ }}

      println(res)
    }
  )

}