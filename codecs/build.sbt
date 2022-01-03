course := "progfun2"
assignment := "codecs"

scalaVersion := "3.0.0"

scalacOptions ++= Seq("-deprecation")

libraryDependencies += "io.dylemma" %% "scala-frp" % "1.2"

libraryDependencies ++= Seq(
  "org.typelevel" %% "jawn-parser" % "1.1.2",
  "org.scalacheck" %% "scalacheck" % "1.15.4" % Test,
  "org.scalameta" %% "munit" % "0.7.26" % Test
)
