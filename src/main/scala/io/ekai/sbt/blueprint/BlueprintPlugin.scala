package io.ekai.sbt.blueprint

import io.ekai.sbt.blueprint.Blueprint._
import io.ekai.sbt.blueprint.Bnd._

import sbt.Keys._
import sbt._

import scala.xml._

import scala.collection.JavaConversions._

/**
 * Created on 12/07/14.
 *
 * @author Matthieu Dartiguenave, matthieu@ekai.io
 */
object BlueprintPlugin extends AutoPlugin {

  object autoImport {
    lazy val blueprintResources = taskKey[Seq[File]]("Blueprint resources")

    lazy val blueprintNodes = taskKey[Seq[Elem]]("Blueprint xml")

    lazy val blueprintImportPackage = taskKey[Set[String]]("Import-Package")
    lazy val blueprintImportService = taskKey[Set[String]]("Import-Service")
    lazy val blueprintExportService = taskKey[Set[String]]("Export-Service")

    lazy val bpImports = taskKey[Unit]("Print Import-Package")
    lazy val bpImportServices = taskKey[Unit]("Print Import-Service")
    lazy val bpExportServices = taskKey[Unit]("Print Export-Service")

    lazy val osgiBnd = taskKey[Unit]("Run Bnd")
  }

  import io.ekai.sbt.blueprint.BlueprintPlugin.autoImport._

  override def projectSettings: Seq[Setting[_]] = Seq(
    blueprintResources := (unmanagedResources in Compile).value flatMap { s => (s / "OSGI-INF" / "blueprint"  ** "*.xml").get },
    blueprintNodes := blueprintResources.value map XML.loadFile,

    blueprintImportPackage := (blueprintNodes.value flatMap importPackage).toSet,
    blueprintImportService := (blueprintNodes.value flatMap importService).toSet,
    blueprintExportService := (blueprintNodes.value flatMap exportService).toSet,


    bpImports := println(blueprintImportPackage.value mkString ", "),
    bpImportServices := println(blueprintImportService.value mkString ", "),
    bpExportServices := println(blueprintExportService.value mkString ", "),

    osgiBnd := println("narf") //bnd((fullClasspath in Compile).value).getEntries foreach println
  )
}