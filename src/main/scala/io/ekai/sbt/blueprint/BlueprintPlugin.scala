package io.ekai.sbt.blueprint

import sbt._
import sbt.Keys._


/**
 * Created on 12/07/14.
 *
 * @author Matthieu Dartiguenave, matthieu@ekai.io
 */
object BlueprintPlugin extends AutoPlugin {

  object autoImport {
    lazy val blueprintResources = taskKey[Seq[File]]("Blueprint resources")

    lazy val osgiHeaders = taskKey[Map[String, Seq[String]]]("Osgi Headers")

    lazy val osgiBnd = taskKey[Unit]("Run Bnd")
  }

  import io.ekai.sbt.blueprint.BlueprintPlugin.autoImport._


  lazy val blueprintResourcesImpl = Def.task {
    (unmanagedResources in Compile).value flatMap { s => (s / "OSGI-INF" / "blueprint"  ** "*.xml").get }
  }

  lazy val osgiHeadersImpl = Def.task {
    import scala.xml._
    import aQute.bnd.osgi.Constants._

    val blueprintNodes = blueprintResources.value map XML.loadFile

    val importedPackage = (blueprintNodes flatMap Blueprint.importPackage).toSeq :+ "*"
    val importedService = (blueprintNodes flatMap Blueprint.importService).toSeq
    val exportedService = (blueprintNodes flatMap Blueprint.exportService).toSeq

    Map(
      IMPORT_PACKAGE -> importedPackage,
      IMPORT_SERVICE -> importedService,
      EXPORT_SERVICE ->exportedService
    )
  }

  lazy val osgiBndImpl = Def.task {
    import Bnd._

    val classpath = (fullClasspath in Compile).value map {_.data.absolutePath}
    val inputHeaders = osgiHeaders.value

    val bndManifest = bnd(classpath, inputHeaders)

    import scala.collection.JavaConversions._
    bndManifest.getEntries foreach println

  }



  override def projectSettings: Seq[Setting[_]] = Seq(
    blueprintResources := blueprintResourcesImpl.value,
    osgiHeaders := osgiHeadersImpl.value,
    osgiBnd := osgiBndImpl.value

  )
}