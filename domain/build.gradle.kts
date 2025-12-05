plugins {
    alias(libs.plugins.anifox.kmp.library)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    namespace = "su.anifox.domain"
}

kotlin {
    sourceSets {
        androidInstrumentedTest.dependencies {
            implementation(libs.androidx.compose.ui.test)
        }
        androidUnitTest.dependencies {
            implementation(libs.androidx.compose.ui.test)
        }
        commonMain.dependencies {
            api(compose.runtime)
            implementation(libs.kotlinx.serialization.json)
        }
    }
}