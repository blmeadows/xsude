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



