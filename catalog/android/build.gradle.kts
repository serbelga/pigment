plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.composeCompiler)
    alias(libs.plugins.jetbrains.compose)
    id("dev.sergiobelda.pigment-spotless")
}

android {
    namespace = "dev.sergiobelda.pigment.catalog.android"

    compileSdk = 36

    defaultConfig {
        applicationId = "dev.sergiobelda.pigment.catalog"
        minSdk = 23
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    kotlin {
        jvmToolchain(17)
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(projects.catalog.shared)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.coreKtx)
    implementation(libs.androidx.lifecycle.runtimeKtx)
}
