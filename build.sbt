lazy val pimpathon = (project in file(".")
  settings(
    organization              := "com.github.stacycurl",
    scalaVersion              := "2.12.0",
    crossScalaVersions        := Seq("2.12.0", "2.11.7"),
    scalacOptions             := Seq("-feature", "-Xfatal-warnings", "-deprecation", "-unchecked", "-target:jvm-1.8"),
    javacOptions              := Seq("-source", "1.8", "-target", "1.8", "-Xlint"),
    maxErrors                 := 1,
    parallelExecution in Test := true,
    resolvers += "Stacy Curl's repo" at "http://dl.bintray.com/stacycurl/repo/",
    resolvers += "jcenter" at "http://jcenter.bintray.com",
    libraryDependencies ++= (CrossVersion.partialVersion(scalaVersion.value) match {
      case Some((2, n)) if n >= 12 => Seq(
        scalaOrganization.value      % "scala-compiler"    % scalaVersion.value
          exclude("org.scala-lang.modules", s"scala-xml_${scalaBinaryVersion.value}"),
        scalaOrganization.value      % "scala-library"     % scalaVersion.value % "test",
        "com.github.julien-truffaut" %% "monocle-core"     % "1.3.2"     % "provided",
        "io.argonaut"                %% "argonaut"         % "6.2-RC1"   % "provided",
        "io.argonaut"                %% "argonaut-monocle" % "6.2-RC1"   % "provided",
        "org.scalaz"                 %% "scalaz-core"      % "7.3.0-M6"  % "provided",
        "io.gatling"                 %% "jsonpath"         % "0.6.8"     % "provided",
        "com.novocode"               % "junit-interface"   % "0.11"      % "test",
        "com.github.stacycurl"       %% "delta-matchers"   % "1.1.0"     % "test"
      )
      case Some((2, 11)) => Seq(
        "com.github.julien-truffaut" %% "monocle-core"     % "1.2.2"  % "provided",
        "io.argonaut"                %% "argonaut"         % "6.2-M2" % "provided",
        "io.argonaut"                %% "argonaut-monocle" % "6.2-M2" % "provided",
        "org.scalaz"                 %% "scalaz-core"      % "7.2.2"  % "provided",
        "io.gatling"                 %% "jsonpath"         % "0.6.7"  % "provided",
        "com.novocode"               %  "junit-interface"  % "0.11"   % "test",
        "com.github.stacycurl"       %% "delta-matchers"   % "1.0.19" % "test"
      )}),
    doc := version.apply(Documentation.generate).value,
    initialize := {
      val _ = initialize.value
      require(sys.props("java.specification.version") == "1.8", "Java 8 is required for this project.")
    }
    //    coverageEnabled := true,
    //    coverageMinimum := 100,
    //    coverageHighlighting := true,
    //    coverageFailOnMinimum := true
  )
  settings(Publishing.settings: _*)
  settings addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.3")
)
