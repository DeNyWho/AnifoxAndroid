plugins {
    alias(libs.plugins.anifox.android.library.compose)
    alias(libs.plugins.anifox.android.library)
    alias(libs.plugins.anifox.android.library.jacoco)
    alias(libs.plugins.anifox.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "club.anifox.android.domain"
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    api(libs.androidx.compose.runtime)
    implementation(libs.paging.runtime)
    implementation(libs.kotlinx.serialization.json)
}