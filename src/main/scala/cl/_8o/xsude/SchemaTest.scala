package cl._8o.xsude

import scala.xml.XML

// sealed trait SchemaTop
// case class Schema(
//   // TODO: support includes, imports, redefines and annotations
//   targetNamespace: String
// , version: String
// , id: String
// , content: Seq[SchemaTop]
// )

object SchemaTest {

  def repr(thing: Any): String = thing match {
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

  def printAttributes(schema: xsd.Schema) = {
    for { (k, v) ← schema.attributes if v.value.toString.nonEmpty } {
      println(s"$k:\n\t${v.value}")
    }
  }

  def printSchemaSeq(schema: xsd.Schema) = {
    for { (s, i) ← schema.schemasequence1.zipWithIndex } {
      val dr = s.schemaTopOption1
      println(s"[$i]\t${repr(dr.value)}")
    }
  }

  def loadSchema(fileName: String) = {
    val schemaXml = XML.loadFile(fileName)
    scalaxb.fromXML[xsd.Schema](schemaXml)
  }

  def main(args: Array[String]) = {
    val s = loadSchema(args.headOption getOrElse "schema129.xsd")
    println("------- Attributes ----------")
    printAttributes(s)
    println()
    println("------- Schema sequence -----")
    printSchemaSeq(s)
  }

}
