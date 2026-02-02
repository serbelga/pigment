plugins {
    kotlin("multiplatform")
    alias(libs.plugins.jetbrains.kotlin.composeCompiler)
    alias(libs.plugins.jetbrains.compose)
    id("dev.sergiobelda.pigment-spotless")
}

kotlin {
    js {
        outputModuleName = "catalog"
        browser {
            commonWebpackConfig {
                outputFileName = "catalog.js"
            }
        }
        binaries.executable()
        useEsModules()
    }

    sourceSets {
        jsMain.dependencies {
            implementation(projects.catalog.shared)
        }
    }
}
