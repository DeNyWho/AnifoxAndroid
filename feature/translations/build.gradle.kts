plugins {
    alias(libs.plugins.anifox.android.feature)
    alias(libs.plugins.anifox.android.library.compose)
    alias(libs.plugins.anifox.android.library.jacoco)
    alias(libs.plugins.roborazzi)
}

android {
    namespace = "club.anifox.android.feature.translations"
}

dependencies {
    implementation(libs.paging.runtime)
    implementation(libs.paging.compose)

    testImplementation(libs.hilt.android.testing)
    testDemoImplementation(libs.roborazzi)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}