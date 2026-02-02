plugins {
    `kotlin-dsl`
}

group = "dev.sergiobelda.pigment.buildlogic.convention"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.spotless.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("spotless") {
            id = "dev.sergiobelda.pigment-spotless"
            implementationClass =
                "dev.sergiobelda.pigment.buildlogic.convention.SpotlessConventionPlugin"
        }
    }
}
