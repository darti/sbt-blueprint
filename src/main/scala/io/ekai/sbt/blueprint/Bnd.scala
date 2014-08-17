package io.ekai.sbt.blueprint

import java.io.File

import aQute.bnd.osgi._

/**
 * Created on 17/08/14.
 *
 * @author Matthieu Dartiguenave, matthieu@ekai.io
 */
object Bnd {

  def bnd(fullClasspath: Seq[String]) = {
    val analyzer = new Analyzer
    // val bin = new Jar( new File("bin") );  // where our data is
     // analyzer.setJar( bin );                // give bnd the contents

    // You can provide additional class path entries to allow
    // bnd to pickup export version from the packageinfo file,
    // Version annotation, or their manifests.
    //analyzer.addClasspath( new File("jar/spring.jar") );

    val classes = new Jar( new File("/home/matthieu/Projects/afirm/routes/target/scala-2.10/classes"))
    analyzer.setJar(classes)
    fullClasspath foreach {f => analyzer.addClasspath(new File(f))}

    analyzer.setProperty("Bundle-SymbolicName","org.osgi.core")
    analyzer.setProperty("Export-Package",
      "org.osgi.framework,org.osgi.service.event,*")
    analyzer.setProperty("Bundle-Version","1.0")

    // There are no good defaults so make sure you set the
    // Import-Package
    analyzer.setProperty("Import-Package","*")

    // Calculate the manifest
    analyzer.calcManifest()
  }
}
