import Dependencies._
import sbt.Keys.libraryDependencies

ThisBuild / scalaVersion     := "2.13.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "sandbox-scala",
    // https://mvnrepository.com/artifact/com.twitter/util-core
    libraryDependencies += "com.twitter" %% "util-core" % "22.4.0"

)

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
