pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://central.sonatype.com/repository/maven-snapshots/")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://central.sonatype.com/repository/maven-snapshots/")
    }
}

rootProject.name = "pigment-library"

include(":catalog:android")
include(":catalog:desktop")
include(":catalog:shared")
include(":catalog:web")
include(":pigment")
include(":pigment:samples")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
