package io.ekai.sbt.blueprint

import org.specs2.mutable.Specification

import scala.xml.XML

import aQute.bnd.osgi.Constants._

/**
 * Created on 06/07/14.
 *
 * @author Matthieu Dartiguenave, matthieu@ekai.io
 */
class BlueprintSpec extends Specification {

  import io.ekai.sbt.blueprint.Blueprint._

  "The blueprint file bp.xml" should {
    val path = getClass.getResource("/OSGI-INF/blueprint/bp.xml")
    val bp = XML.load(path)

    val bpManifest = manifest(bp)

    "exports package p1 to p13" in {

      val importedPackages = bpManifest(IMPORT_PACKAGE)
      val expectedPackages = (1 to 13 map { "p" + _ }).toSeq

      importedPackages must containAllOf(expectedPackages)
    }

    "contains exports services" in {
      val exportedServices = bpManifest(EXPORT_SERVICE)

      exportedServices must not be empty
    }

    "be true" in {
      Console.println(bpManifest)

      true
    }
  }

}
