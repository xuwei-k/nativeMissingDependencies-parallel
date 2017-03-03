import scalanative.sbtplugin.ScalaNativePluginInternal.nativeMissingDependencies

lazy val common = Seq(
  scalaVersion := "2.11.8"
)

lazy val x = project.settings(
  common
).enablePlugins(ScalaNativePlugin)

lazy val y = project.settings(
  common
).dependsOn(x).enablePlugins(ScalaNativePlugin)

lazy val z = project.settings(
  common
).dependsOn(x).enablePlugins(ScalaNativePlugin)

TaskKey[Unit]("checkNativeMissingDependencies") := {
  Seq(
    (nativeMissingDependencies in Compile in x),
    (nativeMissingDependencies in Compile in y),
    (nativeMissingDependencies in Compile in z)
  ).join.value.foreach{ missingDeps =>
    assert(missingDeps.isEmpty, missingDeps)
  }
}
