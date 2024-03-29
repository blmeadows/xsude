package cl._8o.xsude

import org.scalatest.FunSpec
import java.lang.IllegalArgumentException

class SchemaSpecs extends FunSpec {

  describe("Cardinality") {
    it("doesn't allow negative minimum bounds") {
      intercept[IllegalArgumentException] { Cardinality(-10, Some(-5)) }
      intercept[IllegalArgumentException] { Cardinality(-10, Some( 5)) }
      intercept[IllegalArgumentException] { Cardinality(-10, None) }
    }

    it("doesn't allow non-positive maximum bounds") {
      intercept[IllegalArgumentException] { Cardinality(0, Some(-5)) }
      intercept[IllegalArgumentException] { Cardinality(0, Some( 0)) }
    }

    it("doesn't allow maximum bounds less than the minimum bound") {
      intercept[IllegalArgumentException] { Cardinality(4, Some(3)) }
      intercept[IllegalArgumentException] { Cardinality(9, Some(5)) }
    }

    it("knows if a given number satisfies the bounds") {
      assert(0 !∈: Cardinality())
      assert(1  ∈: Cardinality())
      assert(2 !∈: Cardinality())

      assert(0 !∈: Cardinality(3, 5))
      assert(2 !∈: Cardinality(3, 5))
      assert(3  ∈: Cardinality(3, 5))
      assert(4  ∈: Cardinality(3, 5))
      assert(5  ∈: Cardinality(3, 5))
      assert(6 !∈: Cardinality(3, 5))

      for { i ← 0 to 10 } {
        assert(i ∈: Cardinality(0, None))
      }
    }

    it("has an alternative constructor for passing a non-option upper bound") {
      val c = Cardinality(1, 10)
      assert(c === Cardinality(1, Some(10)))
    }

    it("has a cool string representation") {
      assert(Cardinality(2, 2)   .toString === "Cardinality(2)")
      assert(Cardinality(5, 9)   .toString === "Cardinality(5, 9)")
      assert(Cardinality(5, None).toString === "Cardinality(5, ∞)")
    }
  }

}

