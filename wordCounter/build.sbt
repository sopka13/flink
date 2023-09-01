ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.17"

lazy val root = (project in file("."))
  .settings(
    name := "wordCounter"
  )

libraryDependencies ++= Seq(
  "org.apache.kafka"  %   "kafka-clients"         % "3.5.0",
  "org.apache.flink"  %%  "flink-scala"           % "1.17.1",
  "org.apache.flink"  %   "flink-connector-kafka" % "1.17.1",
  "org.apache.flink"  %   "flink-core"            % "1.17.1",
  "org.apache.flink"  %%  "flink-streaming-scala" % "1.17.1",
  "com.typesafe"      %   "config"                % "1.4.2"
)

Compile/mainClass :=  Some("WordCounter")

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", _*) => MergeStrategy.discard
  case _                        => MergeStrategy.first
}