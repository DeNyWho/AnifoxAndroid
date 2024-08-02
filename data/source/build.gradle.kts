plugins {
    alias(libs.plugins.anifox.android.library)
    alias(libs.plugins.anifox.android.library.jacoco)
    alias(libs.plugins.anifox.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "club.anifox.android.data.source"
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.domain)
    implementation(projects.core.common)
    implementation(projects.data.network)
    implementation(projects.data.datastore)
    implementation(projects.data.local)

    implementation(libs.paging.runtime)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit)
}