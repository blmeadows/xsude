package cl._8o.xsude

case class Cardinality(
  min: Int = 1
, max: Option[Int] = Some(1)  // None means unbounded
) {
  require(min          >= 0 )
  require(max.forall(_ >= 1))
  require(max.forall(_ >= min))

  def ∈:(n: Int): Boolean = min <= n && max.forall(_ >= n)
  def !∈:(n: Int): Boolean = !(n ∈: this)
  def isUnbounded: Boolean = max.isEmpty
  def isOptional: Boolean = min == 0
  override def toString =
    if (max.exists(_ == min)) s"Cardinality($min)"
    else                      s"Cardinality($min, ${max.getOrElse("∞")})"
}
object Cardinality {
  def apply(min: Int, max: Int): Cardinality = Cardinality(min, Some(max))
}

sealed trait Annotation
case class Documentation(content: String) extends Annotation
case class AppInfo      (content: String) extends Annotation

sealed trait ElementType
case class ComplexType(group: Group)   extends ElementType
case class SimpleType ()               extends ElementType
case class NamedType(typeName: String) extends ElementType

sealed trait GroupType
case object Sequence extends GroupType
case object Choice   extends GroupType
case object All      extends GroupType

sealed trait Grouped

case class Group(
  groupType: GroupType
, children: Seq[Grouped]
, occurs: Cardinality
) extends Grouped

sealed trait SchemaThing
case class Element(_type: ElementType, occurs: Cardinality) extends SchemaThing with Grouped
case class TopLevelComplexType(name: String, _type: ComplexType) extends SchemaThing
case class TopLevelSimpleType (name: String, _type: SimpleType ) extends SchemaThing

case class Schema(
  content: Seq[SchemaThing]
, annotations: Seq[Annotation]
, id: String
, version: String
, targetNamespace: String
)

