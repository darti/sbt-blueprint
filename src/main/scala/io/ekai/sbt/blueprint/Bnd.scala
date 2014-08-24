package io.ekai.sbt.blueprint

import java.io.File
import java.util.Properties

import aQute.bnd.osgi._

/**
 * Created on 17/08/14.
 *
 * @author Matthieu Dartiguenave, matthieu@ekai.io
 */
object Bnd {

  def bnd(fullClasspath: Seq[String], headers : Map[String, Seq[String]]) = {
    val analyzer = new Analyzer

    val classes = new Jar( new File("/home/matthieu/Projects/afirm/routes/target/scala-2.10/classes"))
    analyzer.setJar(classes)

    fullClasspath foreach {f => analyzer.addClasspath(new File(f))}

    val properties = headers2properties(headers)
    analyzer.setProperties(properties)

    analyzer.calcManifest()
  }

  def headers2properties(headers : Map[String, Seq[String]]) = {
    val properties = new Properties

    headers foreach {
      e => properties.setProperty(e._1, e._2 mkString ",")
    }

    properties
  }

}
