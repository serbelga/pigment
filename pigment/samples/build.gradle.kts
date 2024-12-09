plugins {
    id("com.android.library")
    id("kotlin-android")
    id("dev.sergiobelda.pigment-spotless")
    alias(libs.plugins.composeCompiler)
}

android {
    namespace = "dev.sergiobelda.pigment.samples"

    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }

    kotlin {
        jvmToolchain(17)
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(projects.pigment)

    implementation(platform(libs.androidx.compose.composeBom))
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.uiTooling)
    implementation(libs.androidx.compose.uiToolingPreview)
}
