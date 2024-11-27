plugins {
    alias(libs.plugins.anifox.android.library.compose)
    alias(libs.plugins.anifox.android.library)
    alias(libs.plugins.anifox.android.library.jacoco)
    alias(libs.plugins.anifox.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "club.anifox.android.core.uikit"
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
}

dependencies {
    lintPublish(projects.lint)
    implementation(projects.domain)
    
    implementation(libs.youtube.player)

    api(libs.airbnb.lottie)
    api(libs.paging.runtime)
    api(libs.paging.compose)
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.material.iconsExtended)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.ui.util)

    api(libs.shimmer.compose)

    api(libs.coil.kt.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}