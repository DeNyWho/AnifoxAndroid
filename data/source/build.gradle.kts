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
    api(projects.domain)
    api(projects.common)
    api(projects.data.network)
    api(projects.data.datastore)
    api(projects.data.local)

    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit)
}