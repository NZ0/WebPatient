name := """webpatient"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  evolutions,
  "org.webjars" %% "webjars-play" % "2.4.0",
  "org.webjars" % "angularjs" % "1.3.8",
  "org.webjars" % "bootstrap" % "3.3.2",
  "org.webjars" % "font-awesome" % "4.5.0",
  "org.webjars" % "angular-bootstrap-datetimepicker" % "0.3.8",
  "org.webjars" % "angular-ui-bootstrap" % "0.12.0",
  "org.webjars" % "angular-ui-calendar" % "0.8.1",
  "org.webjars" % "fullcalendar" % "2.4.0",
  "com.google.api-client" % "google-api-client-servlet" % "1.20.0",
  "com.google.apis" % "google-api-services-calendar" % "v3-rev140-1.20.0",
  "it.innove" % "play2-pdf" % "1.3.0"
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
//routesGenerator := InjectedRoutesGenerator