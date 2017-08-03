logLevel := Level.Warn

resolvers += Resolver.typesafeRepo("releases")

addSbtPlugin("org.tpolecat"      % "tut-plugin"     % "0.5.2")
addSbtPlugin("com.47deg"         % "sbt-microsites" % "0.6.1")
addSbtPlugin("org.scoverage"     % "sbt-scoverage"  % "1.5.0")
addSbtPlugin("com.jsuereth"      % "sbt-pgp"        % "1.0.0")
addSbtPlugin("org.xerial.sbt"    % "sbt-sonatype"   % "2.0"  )
addSbtPlugin("com.eed3si9n"      % "sbt-buildinfo"  % "0.7.0")