package io.ekai.sbt.blueprint

import io.ekai.sbt.blueprint.tools.BlueprintTools

import scala.xml.{ NodeSeq, Elem }
import aQute.bnd.osgi.Constants._

/**
 * Created on 06/07/14.
 *
 * @author Matthieu Dartiguenave, matthieu@ekai.io
 */
object Blueprint extends BlueprintTools {


  val importClassPatterns : Rules = Seq(
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
  )

  val exportServicePatterns : Rules = Seq(
    List(\\("service"), \("@interface")),
    List(\\("service"), \("interfaces"), \("value"))
  )

  val importServicePatterns : Rules = Seq(
    List(\\("reference"), \("@interface")),
    List(\\("reference-list"), \("@interfaces"))
  )



  private def extractor(rules : Rules) = (bp : Elem) => rules map buildPattern flatMap { p => p(bp) map (_.text) }

  def importPackage(blueprint : Elem) =
    (extractor(importClassPatterns)(blueprint) map classToPackage) :+
      "org.osgi.service.blueprint;version=\"[1.0.0,2.0.0)\""

  def exportService(blueprint : Elem) = extractor(exportServicePatterns)(blueprint)

  def importService(blueprint : Elem) = extractor(importServicePatterns)(blueprint)

}
