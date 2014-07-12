package io.ekai.sbt.blueprint

import scala.xml.{ NodeSeq, Elem }
import aQute.bnd.osgi.Constants._

/**
 * Created on 06/07/14.
 *
 * @author Matthieu Dartiguenave, matthieu@ekai.io
 */
object Blueprint {

  val \ = (b: String) => (a: NodeSeq) => a \ b
  val \\ = (b: String) => (a: NodeSeq) => a \\ b

  def buildPattern(l: List[NodeSeq => NodeSeq]) =
    (ns: NodeSeq) =>
      {
        val h = l.head
        val t = l.tail

        t.foldLeft(h(ns))((n, op) => n flatMap op)
      }

  val importClassPatterns = Seq(
    List(\\("bean"), \("@class")),
    List(\\("service"), \("@interface")),
    List(\\("service"), \("interfaces"), \("value")),
    List(\\("reference"), \("@interface")),
    List(\\("reference-list"), \("@interface")),
    List(\\("bean"), \("argument"), \("@type")),
    List(\\("list"), \("@value-type")),
    List(\\("set"), \("@value-type")),
    List(\\("array"), \("@value-type")),
    List(\\("map"), \("@key-type")),
    List(\\("map"), \("@value-type"))
  ) map buildPattern

  val exportServicePatterns = Seq(
    List(\\("service"), \("@interface")),
    List(\\("service"), \("interfaces"), \("value"))
  ) map buildPattern

  def classToPackage(c: String) = {
    val i = c lastIndexOf '.'

    if (i < 0) c else c take i
  }

  def importClass(blueprint: Elem) = {
    val imports = importClassPatterns flatMap { p => p(blueprint).map(_.text) } map classToPackage

    imports :+ "org.osgi.service.blueprint;version=\"[1.0.0,2.0.0)\""
  }

  def exportService(blueprint: Elem) = {
    val exports = exportServicePatterns flatMap { p => p(blueprint).map(_.text) }

    exports
  }

  def manifest(blueprint: Elem) = {
    Map(
      IMPORT_PACKAGE -> importClass(blueprint),
      EXPORT_SERVICE -> exportService(blueprint)
    )
  }
}
