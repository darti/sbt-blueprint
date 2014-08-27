package io.ekai.sbt.blueprint

import java.io.File
import java.util.Properties

import aQute.bnd.osgi._

/**
 * Created on 17/08/14.
 *
 * @author Matthieu Dartiguenave, matthieu@ekai.io
 */
object Osgi {

  def bnd(classesDir : String, fullClasspath: Seq[String], headers : Map[String, Seq[String]]) = {
    val analyzer = new Analyzer

    val classes = new Jar(new File(classesDir))
    analyzer.setJar(classes)

    fullClasspath foreach {f => analyzer.addClasspath(new File(f))}

    val properties = headers2properties(headers)
    analyzer.setProperties(properties)

    analyzer.calcManifest()
  }

  /**
   *
   * based on
   * @see http://felix.apache.org/site/apache-felix-maven-bundle-plugin-bnd.html
   * @see http://svn.apache.org/repos/asf/maven/shared/trunk/maven-osgi/src/main/java/org/apache/maven/shared/osgi/DefaultMaven2OsgiConverter.java
   *
   */
  def computeBundleSymbolicName(organization : String, name : String) = {
    val organizationParts = organization split "[-.]"
    val nameParts = name split "[-.]"

    if(organizationParts.size <= 1) {
      name
    }
    else if(organizationParts.last == nameParts.head) {
      organization + "." + (nameParts drop 1 mkString ".")
    }
    else {
      organization + "." + name
    }
  }

  def computeBundleVersion(version : String) = {
    version replace ("-", ".")
  }


  def headers2properties(headers : Map[String, Seq[String]]) = {
    val properties = new Properties

    headers foreach {
      e => properties.setProperty(e._1, e._2 mkString ",")
    }

    properties
  }

}
