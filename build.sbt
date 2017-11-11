name := "sentiment-service"

version := "1.0"

scalaVersion := "2.12.4"

logLevel := Level.Info

libraryDependencies ++= Seq(
  "edu.stanford.nlp" % "stanford-corenlp" % "3.7.0",
  "edu.stanford.nlp" % "stanford-corenlp" % "3.7.0" classifier "models",
  "org.twitter4j" % "twitter4j-core" % "3.0.6",
  "org.twitter4j" % "twitter4j-stream" % "3.0.6",
  "io.grpc" % "grpc-netty" % com.trueaccord.scalapb.compiler.Version.grpcJavaVersion,
  "com.trueaccord.scalapb" %% "scalapb-runtime-grpc" % com.trueaccord.scalapb.compiler.Version.scalapbVersion
)

PB.targets in Compile := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value
)