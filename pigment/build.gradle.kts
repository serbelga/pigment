import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.kotlinMultiplatformLibrary)
    alias(libs.plugins.jetbrains.kotlin.composeCompiler)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.dokka)
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.vanniktechMavenPublish)
    id("dev.sergiobelda.pigment-spotless")
    // alias(libs.plugins.paparazzi)
}

group = "dev.sergiobelda.pigment"
version = libs.versions.pigment.get()

kotlin {
    androidLibrary {
        namespace = "dev.sergiobelda.pigment"
        compileSdk = 36
        minSdk = 23

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }

        androidResources.enable = true

        withHostTest {}
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
            implementation(libs.jetbrains.compose.components.resources)
            implementation(libs.jetbrains.compose.material3)
            implementation(libs.jetbrains.compose.ui)
        }
        androidMain.dependencies {
            implementation(libs.androidx.appcompat)
        }
        getByName("androidHostTest").dependencies {
            implementation(libs.google.testParameterInjector)
        }
    }
}

mavenPublishing {
    publishToMavenCentral()

    signAllPublications()
}

tasks.withType<Jar> {
    from(file("$rootDir/${projects.pigment.name}/samples/src/main/kotlin"))
}

compose.resources {
    packageOfResClass = "dev.sergiobelda.pigment.resources"
}

dokka {
    dokkaSourceSets.configureEach {
        // https://github.com/Kotlin/dokka/issues/3701
        // samples.from( "$rootDir/${projects.pigment.name}/samples/src/commonMain/kotlin")
    }
}
