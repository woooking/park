
lazy val root = (project in file(".")).enablePlugins(SbtTwirl)

name := "park"

version := "1.0"

scalaVersion := "2.12.2"

libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.0.6"
libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.6"
libraryDependencies += "com.typesafe.slick" %% "slick" % "3.2.0"
libraryDependencies += "com.typesafe.slick" %% "slick-hikaricp" % "3.2.0"
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.1"
libraryDependencies += "com.typesafe.akka" %% "akka-typed" % "2.5.1"
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.1"
libraryDependencies += "org.xerial" % "sqlite-jdbc" % "3.16.1"
libraryDependencies += "org.slf4j" % "slf4j-nop" % "1.6.4"

