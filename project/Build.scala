import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "play20-squeryl-liquibase"
    val appVersion      = "1.0"

  val appDependencies = Seq("org.squeryl" %% "squeryl" % "0.9.5",
        "play" %% "play20-liquibase" % "1.0",
        "org.liquibase" % "liquibase-core" % "2.0.3"
  )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      // Add your own project settings here      
    )

}
