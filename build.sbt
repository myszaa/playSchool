name := "playschool"
 
version := "1.0" 
      
lazy val `playschool` = (project in file(".")).enablePlugins(PlayJava,PlayEbean)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
scalaVersion := "2.11.11"

//http://najavie.pl/play-framework-ebean-jako-orm/
libraryDependencies ++= Seq(
  guice,
  "org.postgresql" % "postgresql" % "9.4-1206-jdbc42",
  "javax.mail" % "mail" % "1.4",
  "it.innove" % "play2-pdf" % "1.6.0",
  "be.objectify" %% "deadbolt-java" % "2.6.1",
  "be.objectify" %% "deadbolt-java-gs" % "2.6.0",
  javaJdbc,
  cache,
  javaWs )

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

      