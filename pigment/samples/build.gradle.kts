plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.kotlinMultiplatform)
    id("dev.sergiobelda.pigment-spotless")
}

kotlin {
    androidTarget()
    jvm()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    js {
        browser()
        binaries.executable()
    }

    sourceSets {

        val commonMain by getting {
            dependencies {
                implementation(projects.pigment)

                implementation(compose.foundation)
                implementation(compose.ui)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(compose.preview)
                implementation(compose.uiTooling)
            }
        }
    }
}

android {
    namespace = "dev.sergiobelda.pigment.samples"

    compileSdk = 36

    defaultConfig {
        minSdk = 21
    }

    kotlin {
        jvmToolchain(17)
    }
}
