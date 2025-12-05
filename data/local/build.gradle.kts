plugins {
    alias(libs.plugins.anifox.kmp.library)
    alias(libs.plugins.anifox.kmp.room)
    alias(libs.plugins.kotlin.serialization)
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    namespace = "su.anifox.data.local"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.domain)
            implementation(projects.core.common)
            implementation(libs.kotlinx.coroutines.core)

            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.sqlite.bundled)

            implementation(libs.koin.core)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)
        }
    }
}