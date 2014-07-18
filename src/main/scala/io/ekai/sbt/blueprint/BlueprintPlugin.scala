package io.ekai.sbt.blueprint

import org.apache.ivy.util.ChecksumHelper
import sbt._
import Keys._
import com.typesafe.sbt.osgi.SbtOsgi


/**
 * Created on 12/07/14.
 *
 * @author Matthieu Dartiguenave, matthieu@ekai.io
 */
object BlueprintPlugin extends AutoPlugin {

  object autoImport {
    lazy val blueprint = taskKey[String]("Parse Blueprint file.")
  }

  import autoImport._

  override def projectSettings: Seq[Setting[_]] = Seq(
    includeFilter in blueprint := AllPassFilter,
    excludeFilter in blueprint := HiddenFileFilter,
    blueprint := {
      unmanagedResourceDirectories map {
        _ map { f =>
          println(f)
        }
      }

      "coincoin"
    }
  )

}