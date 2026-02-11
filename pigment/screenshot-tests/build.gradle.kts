import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.composeCompiler)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.paparazzi)
    id("dev.sergiobelda.pigment-spotless")
}

android {
    namespace = "dev.sergiobelda.pigment.screenshottests"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    kotlin {
        jvmToolchain(21)
    }
}

dependencies {
    implementation(projects.pigment)

    testImplementation(libs.google.testParameterInjector)
    testImplementation(libs.jetbrains.compose.ui)
}
