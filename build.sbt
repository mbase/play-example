name := "warehouse"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "com.google.guava" % "guava" % "14.0",
  filters,
  "securesocial" %% "securesocial" % "master-SNAPSHOT"
)     

resolvers += Resolver.url("sbt-plugin-snapshots",
     new URL("http://repo.scala-sbt.org/scalasbt/sbt-plugin-snapshots/")
   )(Resolver.ivyStylePatterns)

play.Project.playJavaSettings
