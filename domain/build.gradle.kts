plugins {
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
    implementation(libs.paging.runtime)
    implementation(libs.kotlinx.serialization.json)
}