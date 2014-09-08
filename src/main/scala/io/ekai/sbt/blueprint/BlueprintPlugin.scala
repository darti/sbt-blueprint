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

    lazy val osgiBnd = taskKey[Seq[(String, String)]]("Run Bnd")
  }

  import io.ekai.sbt.blueprint.BlueprintPlugin.autoImport._
  import Osgi._


  lazy val blueprintResourcesImpl = Def.task {
    (unmanagedResources in Compile).value flatMap { s => (s / "OSGI-INF" / "blueprint"  ** "*.xml").get }
  }

  lazy val osgiHeadersImpl = Def.task {
    import scala.xml._
    import aQute.bnd.osgi.Constants._

    val blueprintNodes = blueprintResources.value map XML.loadFile

    val importedPackage = (blueprintNodes flatMap Blueprint.importPackage).toSeq ++ Seq("org.osgi.service.blueprint;version=\"[1.0.0,2.0.0)\"", "*")
    val importedService = (blueprintNodes flatMap Blueprint.importService).toSeq
    val exportedService = (blueprintNodes flatMap Blueprint.exportService).toSeq
    val bundleSymbolicName = computeBundleSymbolicName(organization.value, name.value)
    val bundleVersion = computeBundleVersion(version.value)

    Map(
      BUNDLE_SYMBOLICNAME -> Seq(bundleSymbolicName),
      BUNDLE_VERSION -> Seq(bundleVersion),
      IMPORT_PACKAGE -> importedPackage.distinct,
      IMPORT_SERVICE -> importedService.distinct,
      EXPORT_SERVICE ->exportedService.distinct
    )
  }

  lazy val osgiBndImpl = Def.task {
    val classes = (classDirectory in Compile).value
    val classpath = (dependencyClasspath in Compile).value map {_.data.absolutePath}
    val inputHeaders = osgiHeaders.value

    val bndManifest = bnd(classes.absolutePath, classpath, inputHeaders)

    import scala.collection.JavaConversions._
    // temporary filter Require-Capability until upgrade to karaf 3.0
    bndManifest.getMainAttributes.entrySet().withFilter(_.getKey.toString !=  "Require-Capability" ).map {e => e.getKey.toString ->  e.getValue.toString}.toSeq
  }

  override def projectSettings: Seq[Setting[_]] = Seq(
    blueprintResources := blueprintResourcesImpl.value,
    osgiHeaders := osgiHeadersImpl.value,
    osgiBnd := osgiBndImpl.value,
    packageOptions in (Compile, packageBin) += Package.ManifestAttributes { osgiBnd.value : _*}
  )
}