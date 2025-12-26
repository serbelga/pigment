import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)
    id("dev.sergiobelda.pigment-spotless")
}

group = "dev.sergiobelda.pigment.catalog"

kotlin {
    androidTarget()
    jvm {
        compilations.all {
            kotlin {
                jvmToolchain(17)
            }
        }
    }
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "catalog"
            isStatic = true
        }
    }
    js {
        browser()
        binaries.executable()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.pigment)
                implementation(projects.pigment.samples)

                implementation(compose.components.resources)
                implementation(compose.material3)
                implementation(compose.ui)

                implementation(libs.kotlin.collections.immutable)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.activity.compose)
                implementation(libs.androidx.appcompat)
                implementation(libs.androidx.coreKtx)
                implementation(libs.androidx.lifecycle.runtimeKtx)
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
        }

        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
}

android {
    namespace = "dev.sergiobelda.pigment.catalog"

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
    implementation(projects.pigment.samples)
}

compose.desktop {
    application {
        mainClass = "dev.sergiobelda.pigment.catalog.Main_jvmKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
        }
    }
}

compose.resources {
    packageOfResClass = "dev.sergiobelda.pigment.catalog"
}
