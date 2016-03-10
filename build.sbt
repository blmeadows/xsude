import ScalaxbKeys._

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.scalatest"  % "scalatest_2.11" % "2.2.6" % "test"
, "org.scala-lang"         %  "scala-xml"                % "2.11.0-M4"
, "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4"
)

scalaxbSettings

packageName in scalaxb in Compile := "xsude"

sourceGenerators in Compile <+= scalaxb in Compile

dispatchVersion in scalaxb in Compile := "0.11.1"

