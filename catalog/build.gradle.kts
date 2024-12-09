plugins {
    id("com.android.application")
    id("kotlin-android")
    id("dev.sergiobelda.pigment-spotless")
    alias(libs.plugins.composeCompiler)
}

android {
    compileSdk = 35

    defaultConfig {
        applicationId = "dev.sergiobelda.pigment.catalog"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

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
    namespace = "dev.sergiobelda.pigment.catalog"
}

dependencies {
    implementation(projects.pigment)
    implementation(projects.pigment.samples)
    
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.coreKtx)
    implementation(libs.androidx.lifecycle.runtimeKtx)

    implementation(platform(libs.androidx.compose.composeBom))
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.uiTooling)

    implementation(libs.kotlin.collections.immutable)
}
