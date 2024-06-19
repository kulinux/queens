val scala3Version = "3.4.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "Queens",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core" % "2.12.0",
      "org.scalactic" %% "scalactic" % "3.2.18",
      "org.scalatest" %% "scalatest" % "3.2.18" % Test,
      "org.scalamock" %% "scalamock" % "6.0.0" % Test
    )
  )
