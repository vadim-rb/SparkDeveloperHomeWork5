ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.17"
//val sparkVersion = "2.4.1"
val sparkVersion = "3.1.0"
val scalaTestVersion = "3.2.1"
lazy val root = (project in file("."))
  .settings(
    name := "HomeWork2_12",
    /*
    libraryDependencies += "org.apache.spark" %% "spark-core" % sparkVersion,
    libraryDependencies += "org.apache.spark" %% "spark-sql" % sparkVersion,
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.1" % Test,
    libraryDependencies ++= Seq(
      "org.scalactic" %% "scalactic" % "3.2.1",
      "org.apache.spark" %% "spark-core" % sparkVersion % Test,
      "org.apache.spark" %% "spark-core" % sparkVersion % Test classifier "tests",
      "org.apache.spark" %% "spark-sql" % sparkVersion % Test,
      "org.apache.spark" %% "spark-sql" % sparkVersion % Test classifier "tests",
      "org.apache.spark" %% "spark-catalyst" % sparkVersion % Test,
      "org.apache.spark" %% "spark-catalyst" % sparkVersion % Test classifier "tests",
      "org.apache.spark" %% "spark-hive" % sparkVersion % Test,
      "org.apache.spark" %% "spark-hive" % sparkVersion % Test classifier "tests",
      )
     */
    libraryDependencies ++= Seq(
      "org.apache.spark" %% "spark-sql" % sparkVersion,
      "org.apache.spark" %% "spark-sql" % sparkVersion % Test,
      "org.apache.spark" %% "spark-sql" % sparkVersion % Test classifier "tests",
      "org.apache.spark" %% "spark-catalyst" % sparkVersion % Test,
      "org.apache.spark" %% "spark-catalyst" % sparkVersion % Test classifier "tests",
      "org.apache.spark" %% "spark-hive" % sparkVersion % Test,
      "org.apache.spark" %% "spark-hive" % sparkVersion % Test classifier "tests",
      "org.apache.spark" %% "spark-core" % sparkVersion,
      "org.apache.spark" %% "spark-core" % sparkVersion % Test classifier "tests",
    ),
    libraryDependencies += "org.scalactic" %% "scalactic" % scalaTestVersion,
    libraryDependencies +="org.scalatest" %% "scalatest" % scalaTestVersion % "test"


  )
