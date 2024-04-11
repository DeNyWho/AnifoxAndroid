plugins {
    alias(libs.plugins.anifox.android.library)
    alias(libs.plugins.anifox.android.library.jacoco)
    alias(libs.plugins.anifox.android.hilt)
    id("kotlinx-serialization")
}

android {
    namespace = "club.anifox.android.core.data"
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
}

dependencies {
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotlinx.serialization.json)
}
