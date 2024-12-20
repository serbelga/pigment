import com.android.build.gradle.tasks.SourceJarTask
import com.vanniktech.maven.publish.SonatypeHost

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("dev.sergiobelda.pigment-spotless")
    alias(libs.plugins.vanniktechMavenPublish)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.paparazzi)
}

group = "dev.sergiobelda.pigment"
version = libs.versions.pigment.get()

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
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(platform(libs.androidx.compose.composeBom))
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.uiTooling)

    testImplementation(libs.google.testParameterInjector)
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.S01, true)

    signAllPublications()
}

tasks.withType<SourceJarTask> {
    from(file("$rootDir/${projects.pigment.name}/samples/src/main/kotlin"))
}
