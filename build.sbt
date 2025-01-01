ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.6"

lazy val root = (project in file("."))
  .settings(
    name := "music-is-a-closed-loop"
  )

libraryDependencies += "de.sciss" %% "scalacollider" % "2.7.3"
