package cl._8o.xsude
package xsd

import org.scalatest.FunSuite
import scala.xml.XML

class Test extends FunSuite {

  def repr(thing: Any): String = {
    thing match {
      case e: xsd.TopLevelElement     ⇒
        val name = e.name getOrElse "ø"
        val type_ = e.typeValue.fold("") { " :" + _ }
        s"Element$type_\t$name"
      case c: xsd.TopLevelComplexType ⇒
        val name = c.name getOrElse "ø"
        s"ComplexType\t$name"
      case s: xsd.TopLevelSimpleType  ⇒
        val name = s.name getOrElse "ø"
        s"SimpleType\t$name"
      case _ ⇒ thing.getClass.toString
    }
  }

  test("basic") {
    val schemaXml = XML.loadFile("schema129.xsd")
    val schema = scalaxb.fromXML[Schema](schemaXml)

    println("------- Attributes ----------")
    for { (k, v) ← schema.attributes if v.value.toString.nonEmpty } {
      println(s"$k:\n\t${v.value}")
    }
    println()

    println("------- Schema sequence -----")
    for { (s, i) ← schema.schemasequence1.zipWithIndex } {
      val dr = s.schemaTopOption1
      println(s"[$i]\t${repr(dr.value)}")
    }


  }

}
