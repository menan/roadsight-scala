name := "roadsight"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  cache,
  "com.novus" %% "salat" % "1.9.7",
  "org.webjars" % "bootstrap" % "3.1.1"
)     

play.Project.playScalaSettings

lessEntryPoints <<= baseDirectory(_ / "app" / "assets" / "stylesheets" ** "main.less")
