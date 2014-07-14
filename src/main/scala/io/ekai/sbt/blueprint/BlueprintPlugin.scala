package io.ekai.sbt.blueprint

import sbt._
import Keys._
import com.typesafe.sbt.osgi.SbtOsgi


/**
 * Created on 12/07/14.
 *
 * @author Matthieu Dartiguenave, matthieu@ekai.io
 */
object BlueprintPlugin extends AutoPlugin {

  override lazy val projectSettings = Seq(commands += blueprint)

  lazy val blueprint =
    Command.command("blueprint") { (state: State) =>
      println("Hi!")
      state
    }

}
