plugins {
    alias(libs.plugins.anifox.android.library)
    alias(libs.plugins.anifox.android.library.jacoco)
    alias(libs.plugins.anifox.android.hilt)
    alias(libs.plugins.anifox.android.room)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "club.anifox.android.data.local"
}

dependencies {

    implementation(projects.domain)
    implementation(libs.paging.runtime)
    implementation(libs.kotlinx.datetime)

    testImplementation(libs.junit)
}