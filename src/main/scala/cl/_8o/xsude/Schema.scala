package cl._8o.xsude

case class Cardinality(
  min: Int = 1
, max: Option[Int] = Some(1)  // None means unbounded
) {
  require(max.forall(min <= _))
  require(min >= 0)
  require(max.forall(_ >= 1))

  def ∈:(n: Int): Boolean = min <= n && max.forall(_ >= n)
  def !∈:(n: Int): Boolean = !(n ∈: this)
  def isUnbounded: Boolean = max.isEmpty
  def isOptional: Boolean = min == 0
}
object Cardinality {
  def apply(min: Int, max: Int): Cardinality = Cardinality(min, Some(max))
}

sealed trait ElementType

sealed trait SchemaThing
case class Element()     extends SchemaThing
case class ComplexType() extends SchemaThing with ElementType
case class SimpleType()  extends SchemaThing with ElementType

sealed trait ExplicitGroup {
  val children: Seq[SchemaThing]
  val occurs: Cardinality
}
case class Sequence(children: Seq[SchemaThing], occurs: Cardinality) extends ExplicitGroup
case class Choice  (children: Seq[SchemaThing], occurs: Cardinality) extends ExplicitGroup
case class All     (children: Seq[SchemaThing], occurs: Cardinality) extends ExplicitGroup

case class Schema(
  content: Seq[SchemaThing]
, id: String
, version: String
, targetNamespace: String
)


