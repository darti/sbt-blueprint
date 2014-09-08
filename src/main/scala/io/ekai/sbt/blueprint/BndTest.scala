package io.ekai.sbt.blueprint

import scala.collection.JavaConversions._

object BndTest extends App {


  val classpath = Seq(
        "/home/matthieu/.sbt/boot/scala-2.10.4/lib/scala-library.jar",
    "/home/matthieu/.ivy2/cache/org.apache.camel/camel-core/jars/camel-core-2.13.2.jar",
    "/home/matthieu/.ivy2/cache/org.slf4j/slf4j-api/jars/slf4j-api-1.6.6.jar",
    "/home/matthieu/.ivy2/cache/com.sun.xml.bind/jaxb-impl/jars/jaxb-impl-2.2.6.jar",
    "/home/matthieu/.ivy2/cache/org.apache.camel/camel-scala/bundles/camel-scala-2.13.2.jar",
    "/home/matthieu/.ivy2/cache/org.apache.camel/camel-restlet/bundles/camel-restlet-2.13.2.jar",
    "/home/matthieu/.ivy2/cache/org.restlet.osgi/org.restlet/jars/org.restlet-2.1.7.jar",
    "/home/matthieu/.ivy2/cache/org.osgi/org.osgi.core/jars/org.osgi.core-4.3.1.jar",
    "/home/matthieu/.ivy2/cache/org.restlet.osgi/org.restlet.ext.httpclient/jars/org.restlet.ext.httpclient-2.1.7.jar",
    "/home/matthieu/.ivy2/cache/commons-codec/commons-codec/jars/commons-codec-1.5.jar",
    "/home/matthieu/.ivy2/cache/org.apache.httpcomponents/httpclient/jars/httpclient-4.1.1.jar",
    "/home/matthieu/.ivy2/cache/org.apache.httpcomponents/httpcore/jars/httpcore-4.3.2.jar",
    "/home/matthieu/.ivy2/cache/commons-logging/commons-logging/jars/commons-logging-1.1.3.jar",
    "/home/matthieu/.ivy2/cache/org.apache.httpcomponents/httpmime/jars/httpmime-4.1.1.jar",
    "/home/matthieu/.ivy2/cache/net.jcip/jcip-annotations/jars/jcip-annotations-1.0.jar",
    "/home/matthieu/.ivy2/cache/org.apache.james/apache-mime4j/jars/apache-mime4j-0.6.jar",
    "/home/matthieu/.ivy2/cache/org.restlet.osgi/org.restlet.ext.ssl/jars/org.restlet.ext.ssl-2.1.7.jar",
    "/home/matthieu/.ivy2/cache/org.jsslutils/jsslutils/bundles/jsslutils-1.0.5.jar"
  )

  import Osgi._
  import aQute.bnd.osgi.Constants._

  val properties = Map(
   // BUNDLE_SYMBOLICNAME -> Seq("routes"),
    BUNDLE_VERSION -> Seq("0.0.1-SNAPSHOT"),
    IMPORT_PACKAGE -> Seq("*")
  )

  val classes ="/home/matthieu/Projects/afirm/routes/target/scala-2.10/classes"
  val manifest = bnd(classes, classpath, properties)


  val headers = manifest.getMainAttributes.entrySet().withFilter(_.getKey.toString !=  "Require-Capability" ).map {e => e.getKey.toString ->  e.getValue.toString}.toSeq

  headers foreach println
}
