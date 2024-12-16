import com.android.build.gradle.tasks.SourceJarTask
import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.paparazzi)
    alias(libs.plugins.vanniktechMavenPublish)
    id("dev.sergiobelda.pigment-spotless")
}

group = "dev.sergiobelda.pigment"
version = libs.versions.pigment.get()

kotlin {
    androidTarget()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.components.resources)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.uiTooling)
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

tasks.withType<SourceJarTask> {
    from(file("$rootDir/${projects.pigment.name}/samples/src/main/kotlin"))
}
