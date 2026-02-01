import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("multiplatform")
    alias(libs.plugins.android.kotlinMultiplatformLibrary)
    alias(libs.plugins.jetbrains.kotlin.composeCompiler)
    alias(libs.plugins.jetbrains.compose)
    id("dev.sergiobelda.pigment-spotless")
}

group = "dev.sergiobelda.pigment.catalog"

kotlin {
    androidLibrary {
        namespace = "dev.sergiobelda.pigment.catalog"

        compileSdk = 36
        minSdk = 23

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    jvm()
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
        commonMain.dependencies {
            implementation(projects.pigment)
            implementation(projects.pigment.samples)

            implementation(libs.jetbrains.compose.material3)
            implementation(libs.jetbrains.compose.ui)
            implementation(libs.jetbrains.kotlinx.collections.immutable)
        }
        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
}

compose.desktop {
    application {
        mainClass = "dev.sergiobelda.pigment.catalog.Main_jvmKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
        }
    }
}
