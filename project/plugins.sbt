logLevel := Level.Warn

resolvers += Resolver.typesafeRepo("releases")

addSbtPlugin("com.eed3si9n"      % "sbt-unidoc"     % "0.3.1")
addSbtPlugin("com.jsuereth"      % "sbt-pgp"        % "1.0.0")
addSbtPlugin("org.xerial.sbt"    % "sbt-sonatype"   % "1.1"  )
addSbtPlugin("com.typesafe.sbt"  % "sbt-osgi"       % "0.8.0")
