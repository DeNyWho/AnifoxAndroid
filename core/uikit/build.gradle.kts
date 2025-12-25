plugins {
    alias(libs.plugins.anifox.kmp.library)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    namespace = "su.anifox.core.uikit"
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
        }
        androidInstrumentedTest.dependencies {
            implementation(libs.androidx.compose.ui.test)
        }
        androidUnitTest.dependencies {
            implementation(libs.androidx.compose.ui.test)
        }
        commonMain.dependencies {
            implementation(projects.domain)

            implementation(compose.components.resources)
            implementation(compose.ui)
            implementation(compose.uiUtil)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.paging.common)
            implementation(libs.paging.compose)
            implementation(libs.shimmer.compose)
            implementation(libs.coil.kt.compose)
        }
    }
}

compose.resources {
    publicResClass = true
    generateResClass = always
    packageOfResClass = "su.anifox.core.uikit.generated.resources"
}