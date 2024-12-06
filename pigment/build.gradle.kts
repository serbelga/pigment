import com.vanniktech.maven.publish.SonatypeHost

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("dev.sergiobelda.pigment-spotless")
    alias(libs.plugins.vanniktechMavenPublish)
    alias(libs.plugins.composeCompiler)
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
        jvmToolchain(11)
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.composeBom))
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.materialIconsCore)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.uiTooling)

    implementation(libs.kotlin.collections.immutable)
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.S01, true)

    signAllPublications()
}
