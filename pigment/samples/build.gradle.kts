plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)
    kotlin("android")
    id("dev.sergiobelda.pigment-spotless")
}

android {
    namespace = "dev.sergiobelda.pigment.samples"

    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }

    kotlin {
        jvmToolchain(17)
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(projects.pigment)

    implementation(compose.components.resources)
    implementation(compose.foundation)
    implementation(compose.preview)
    implementation(compose.ui)
    implementation(compose.uiTooling)
}
