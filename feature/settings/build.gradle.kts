plugins {
    alias(libs.plugins.anifox.android.feature)
    alias(libs.plugins.anifox.android.library.compose)
    alias(libs.plugins.anifox.android.library.jacoco)
    alias(libs.plugins.secrets)
}


android {
    namespace = "club.anifox.android.feature.settings"
    buildFeatures {
        buildConfig = true
    }
}

secrets {
    defaultPropertiesFileName = "secrets.defaults.properties"
}

dependencies {
    testImplementation(libs.hilt.android.testing)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}
