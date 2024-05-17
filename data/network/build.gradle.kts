plugins {
    alias(libs.plugins.anifox.android.library)
    alias(libs.plugins.anifox.android.library.jacoco)
    alias(libs.plugins.anifox.android.hilt)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.secrets)
}

android {
    namespace = "club.anifox.android.data.network"
    buildFeatures {
        buildConfig = true
    }
}

secrets {
    defaultPropertiesFileName = "secrets.defaults.properties"
}

dependencies {
    api(projects.domain)
    api(projects.common)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.json)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.logging.jvm)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.apache)
    implementation(libs.ktor.client.okhttp)

    testImplementation(libs.junit)
}