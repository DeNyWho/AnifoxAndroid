plugins {
    alias(libs.plugins.anifox.android.feature)
    alias(libs.plugins.anifox.android.library.compose)
    alias(libs.plugins.anifox.android.library.jacoco)
    alias(libs.plugins.roborazzi)
}

android {
    namespace = "club.anifox.android.feature.schedule"
}

dependencies {
    testImplementation(libs.hilt.android.testing)
    testDemoImplementation(libs.roborazzi)
}
