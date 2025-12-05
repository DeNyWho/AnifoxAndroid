plugins {
    alias(libs.plugins.anifox.kmp.library)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "su.anifox.data.network"
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
            implementation(libs.ktor.serialization.kotlinx.json)

            implementation(libs.kotlinx.serialization.json)
        }
    }
}