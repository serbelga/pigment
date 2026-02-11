import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.jetbrains.kotlin.composeCompiler)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.kotlin.jvm)
    id("dev.sergiobelda.pigment-spotless")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(libs.versions.jdk.get().toInt())
    }
}

dependencies {
    implementation(projects.catalog.shared)

    implementation(compose.desktop.currentOs)
}

compose.desktop {
    application {
        mainClass = "dev.sergiobelda.pigment.catalog.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
        }
    }
}
