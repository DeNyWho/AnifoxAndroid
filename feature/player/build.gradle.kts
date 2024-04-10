plugins {
    alias(libs.plugins.anifox.android.feature)
    alias(libs.plugins.anifox.android.library.compose)
    alias(libs.plugins.anifox.android.library.jacoco)
    alias(libs.plugins.roborazzi)
}

android {
    namespace = "club.anifox.android.feature.player"
}

dependencies {
//    implementation(projects.core.data)
//    implementation(projects.core.domain)

    testImplementation(libs.hilt.android.testing)
    testDemoImplementation(libs.roborazzi)
}
