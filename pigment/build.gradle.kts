import com.vanniktech.maven.publish.SonatypeHost
import org.gradle.jvm.tasks.Jar

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.dokka)
    alias(libs.plugins.paparazzi)
    alias(libs.plugins.vanniktechMavenPublish)
    id("dev.sergiobelda.pigment-spotless")
}

group = "dev.sergiobelda.pigment"
version = libs.versions.pigment.get()

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
                implementation(compose.components.resources)
                implementation(compose.material3)
                implementation(compose.ui)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.appcompat)
            }
        }
        val androidUnitTest by getting {
            dependencies {
                implementation(libs.google.testParameterInjector)
            }
        }
        val jsMain by getting
        val jsTest by getting
        val jvmMain by getting
        val jvmTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating
        val iosTest by creating
    }
}

android {
    namespace = "dev.sergiobelda.pigment"

    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    kotlin {
        jvmToolchain(17)
    }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.S01, true)

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
        samples.from( "$rootDir/${projects.pigment.name}/samples/src/commonMain/kotlin")
    }
}
