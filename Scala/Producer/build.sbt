ThisBuild / organization := "ru.example"
ThisBuild / version := "1.0"
ThisBuild / scalaVersion := "2.13.10"

ThisBuild / libraryDependencies ++= Seq(
  "org.apache.kafka" % "kafka-clients"   % "3.9.0",
  "ch.qos.logback"   % "logback-classic" % "1.5.18"
)

lazy val root = (project in file("."))
  .settings(name := "Producer")
  .settings(assembly / mainClass := Some("ru.example.kafka.producer.Producer"))
  .settings(assembly / assemblyJarName := "producer.jar")
  .settings(assembly / assemblyMergeStrategy := {
    case m if m.toLowerCase.endsWith("manifest.mf")       => MergeStrategy.discard
    case m if m.toLowerCase.matches("meta-inf.*\\.sf$")   => MergeStrategy.discard
    case "module-info.class"                              => MergeStrategy.first
    case "version.conf"                                   => MergeStrategy.discard
    case "reference.conf"                                 => MergeStrategy.concat
    case x: String if x.contains("UnusedStubClass.class") => MergeStrategy.first
    case _                                                => MergeStrategy.first
  })
