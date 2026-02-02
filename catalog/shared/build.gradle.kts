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

        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

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
        useEsModules()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.pigment)
            implementation(projects.pigment.samples)

            api(libs.jetbrains.compose.components.resources)
            api(libs.jetbrains.compose.material3)
            api(libs.jetbrains.compose.ui)
            implementation(libs.jetbrains.kotlinx.collections.immutable)
        }
        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
}
