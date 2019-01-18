enablePlugins(GatlingPlugin)
enablePlugins(AssemblyPlugin)

scalaVersion := "2.12.8"
// This forbids including Scala related libraries into the dependency
autoScalaLibrary := false

// dependencies for Gatling
libraryDependencies += "io.gatling.highcharts" % "gatling-charts-highcharts" % "3.0.2" % Provided
libraryDependencies += "io.gatling"            % "gatling-test-framework"    % "3.0.2" % Provided

assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)

// make '~' work (again :))
watchSources += baseDirectory.value / "src" / "it"

// configure the assembly
fullClasspath in assembly := (fullClasspath in GatlingIt).value
mainClass in assembly := Some("io.gatling.app.Gatling")
assemblyMergeStrategy in assembly := {
  case path if path.endsWith("io.netty.versions.properties") => MergeStrategy.first
  case path => {
    val currentStrategy = (assemblyMergeStrategy in assembly).value
    currentStrategy(path)
  }
}
test in assembly := {}