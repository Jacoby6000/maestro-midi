
resolvers += Resolver.sonatypeRepo("releases")

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.3")

lazy val root =
  (project in file(".")).aggregate(core)

lazy val core =
  (project in file("./core"))
    .configs(IntegrationTest)
    .settings(Defaults.itSettings)
    .settings(commonSettings)
    .settings(
      name := "maestro-midi",
      libraryDependencies ++= Seq(
        "org.scodec" %% "scodec-core" % "1.10.3",
        "org.specs2" %% "specs2-core" % "3.8.9" % "test,it", // for testing
        "org.scalacheck" %% "scalacheck" % "1.13.4" % "test,it" // for doing tests with arbitrary data
      )
  )


lazy val commonSettings = Seq(
  version := "0.1.0",
  scalaVersion := "2.12.3",
  organization := "com.github.jacoby6000",
  licenses += ("MIT", url("http://opensource.org/licenses/MIT")),
  crossScalaVersions := Seq("2.11.11", scalaVersion.value),
  autoAPIMappings := true,
  scalacOptions ++= Seq(
    "-deprecation",                      // Emit warning and location for usages of deprecated APIs.
    "-encoding", "utf-8",                // Specify character encoding used by source files.
    "-explaintypes",                     // Explain type errors in more detail.
    "-feature",                          // Emit warning and location for usages of features that should be imported explicitly.
    "-language:existentials",            // Existential types (besides wildcard types) can be written and inferred
    "-language:experimental.macros",     // Allow macro definition (besides implementation and application)
    "-language:higherKinds",             // Allow higher-kinded types
    "-language:implicitConversions",     // Allow definition of implicit functions called views
    "-unchecked",                        // Enable additional warnings where generated code depends on assumptions.
    "-Xcheckinit",                       // Wrap field accessors to throw an exception on uninitialized access.
    "-Xfatal-warnings",                  // Fail the compilation if there are any warnings.
    "-Xfuture",                          // Turn on future language features.
    "-Xlint:adapted-args",               // Warn if an argument list is modified to match the receiver.
    "-Xlint:by-name-right-associative",  // By-name parameter of right associative operator.
    "-Xlint:constant",                   // Evaluation of a constant arithmetic expression results in an error.
    "-Xlint:delayedinit-select",         // Selecting member of DelayedInit.
    "-Xlint:doc-detached",               // A Scaladoc comment appears to be detached from its element.
    "-Xlint:inaccessible",               // Warn about inaccessible types in method signatures.
    "-Xlint:infer-any",                  // Warn when a type argument is inferred to be `Any`.
    "-Xlint:missing-interpolator",       // A string literal appears to be missing an interpolator id.
    "-Xlint:nullary-override",           // Warn when non-nullary `def f()' overrides nullary `def f'.
    "-Xlint:nullary-unit",               // Warn when nullary methods return Unit.
    "-Xlint:option-implicit",            // Option.apply used implicit view.
    "-Xlint:package-object-classes",     // Class or object defined in package object.
    "-Xlint:poly-implicit-overload",     // Parameterized overloaded implicit methods are not visible as view bounds.
    "-Xlint:private-shadow",             // A private field (or class parameter) shadows a superclass field.
    "-Xlint:stars-align",                // Pattern sequence wildcard must align with sequence component.
    "-Xlint:type-parameter-shadow",      // A local type parameter shadows a type already in scope.
    "-Xlint:unsound-match",              // Pattern match may not be typesafe.
    "-Yno-adapted-args",                 // Do not adapt an argument list (either by inserting () or creating a tuple) to match the receiver.
    "-Ypartial-unification",             // Enable partial unification in type constructor inference
    "-Ywarn-dead-code",                  // Warn when dead code is identified.
    "-Ywarn-extra-implicit",             // Warn when more than one implicit parameter section is defined.
    "-Ywarn-inaccessible",               // Warn about inaccessible types in method signatures.
    "-Ywarn-infer-any",                  // Warn when a type argument is inferred to be `Any`.
    "-Ywarn-nullary-override",           // Warn when non-nullary `def f()' overrides nullary `def f'.
    "-Ywarn-nullary-unit",               // Warn when nullary methods return Unit.
    "-Ywarn-numeric-widen",              // Warn when numerics are widened.
    "-Ywarn-unused:imports",             // Warn if an import selector is not referenced.
    "-Ywarn-unused:patvars",             // Warn if a variable bound in a pattern is unused.
    "-Ywarn-value-discard",              // Warn when non-Unit expression results are unused.
    "-Ypatmat-exhaust-depth", "off"
  ),
  scalacOptions in (Compile, console) ~= (_.filterNot(Set(
    "-Ywarn-unused:imports",
    "-Xfatal-warnings"
  )))
)

def publishSettings(packageName: String) = osgiSettings ++ Seq(
  //buildInfoKeys := Seq(name, version, scalaVersion, sbtVersion),
  //buildInfoPackage := packageName + ".build",
  //buildInfoKeys ++= Seq[BuildInfoKey](
  //resolvers,
  //libraryDependencies in Test,
  //BuildInfoKey.map(name) { case (k, v) => "project" + k.capitalize -> v.capitalize },
  //BuildInfoKey.action("buildTime") { System.currentTimeMillis }
  //),
  sonatypeProfileName := "com.github.jacoby6000",
  publishMavenStyle := true,
  publishTo := Some {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Opts.resolver.sonatypeSnapshots
    else
      Opts.resolver.sonatypeStaging
  },
  publishArtifact in Test := false,
  homepage := Some(url("https://github.com/jacoby6000/scoobie")),
  apiURL := Some(url("https://github.com/jacoby6000/scoobie/tree/master")),
  pomIncludeRepository := Function.const(false),
  pomExtra :=
    <scm>
      <url>git@github.com:Jacoby6000/maestro-midi.git</url>
      <connection>scm:git:git@github.com:Jacoby6000/maestro-midi.git</connection>
    </scm>
      <developers>
        <developer>
          <id>Jacoby6000</id>
          <name>Jacob Barber</name>
          <url>http://jacoby6000.github.com/</url>
          <email>Jacoby6000@gmail.com</email>
        </developer>
      </developers>
)

