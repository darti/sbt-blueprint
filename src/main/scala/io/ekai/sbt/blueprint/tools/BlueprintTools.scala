package io.ekai.sbt.blueprint.tools

import scala.xml.NodeSeq

/**
 * Created on 24/07/14.
 *
 * @author Matthieu Dartiguenave, matthieu@ekai.io
 */
trait BlueprintTools {

  type Rules = Seq[List[NodeSeq => NodeSeq]]

  val \ = (b: String) => (a: NodeSeq) => a \ b
  val \\ = (b: String) => (a: NodeSeq) => a \\ b

  def buildPattern(l: List[NodeSeq => NodeSeq]) =
    (ns: NodeSeq) => {
      val h = l.head
      val t = l.tail

      t.foldLeft(h(ns)) {(n, op) => n flatMap op}
  }



  def classToPackage(c: String) = {
    val i = c lastIndexOf '.'

    if (i < 0) c else c take i
  }
}
