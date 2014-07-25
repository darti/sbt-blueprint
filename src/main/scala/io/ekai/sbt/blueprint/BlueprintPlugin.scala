package io.ekai.sbt.blueprint

import io.ekai.sbt.blueprint.Blueprint._
import sbt.Keys._
import sbt._

import scala.xml._

/**
 * Created on 12/07/14.
 *
 * @author Matthieu Dartiguenave, matthieu@ekai.io
 */
object BlueprintPlugin extends AutoPlugin {

  object autoImport {
    lazy val blueprintResources = taskKey[Seq[File]]("Blueprint resources")

    lazy val blueprintNodes = taskKey[Seq[Elem]]("Blueprint xml")

    lazy val blueprintImportPackage = taskKey[Seq[String]]("Import-Package")
    lazy val blueprintImportService = taskKey[Seq[String]]("Import-Service")
    lazy val blueprintExportService = taskKey[Seq[String]]("Export-Service")

    lazy val bpImports = taskKey[Unit]("Print Import-Package")
    lazy val bpImportServices = taskKey[Unit]("Print Import-Service")
    lazy val bpExportServices = taskKey[Unit]("Print Export-Service")
  }

  import io.ekai.sbt.blueprint.BlueprintPlugin.autoImport._

  override def projectSettings: Seq[Setting[_]] = Seq(
    blueprintResources := (unmanagedResources in Compile).value flatMap { s => (s / "OSGI-INF" / "blueprint"  ** "*.xml").get },
    blueprintNodes := blueprintResources.value map XML.loadFile,

    blueprintImportPackage := blueprintNodes.value flatMap importPackage,
    blueprintImportService := blueprintNodes.value flatMap importService,
    blueprintExportService := blueprintNodes.value flatMap exportService,


    bpImports := println(blueprintImportPackage.value mkString ", "),
    bpImportServices := println(blueprintImportPackage.value mkString ", "),
    bpExportServices := println(blueprintImportPackage.value mkString ", ")
  )
}