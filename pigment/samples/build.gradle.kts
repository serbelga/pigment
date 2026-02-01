import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.kotlinMultiplatformLibrary)
    alias(libs.plugins.jetbrains.kotlin.composeCompiler)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    id("dev.sergiobelda.pigment-spotless")
}

kotlin {
    androidLibrary {
        namespace = "dev.sergiobelda.pigment.samples"
        compileSdk = 36
        minSdk = 23

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    jvm()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    js {
        browser()
        binaries.executable()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.pigment)

            implementation(libs.jetbrains.compose.foundation)
            implementation(libs.jetbrains.compose.ui)

            implementation(libs.jetbrains.compose.uiToolingPreview)
        }
    }
}

dependencies {
    androidRuntimeClasspath(libs.jetbrains.compose.uiTooling)
}
