import club.anifox.buildlogic.convention.AnifoxBuildType

plugins {
    alias(libs.plugins.anifox.android.application)
    alias(libs.plugins.anifox.android.application.compose)
    alias(libs.plugins.anifox.android.application.flavors)
    alias(libs.plugins.anifox.android.application.jacoco)
    alias(libs.plugins.anifox.android.hilt)
    alias(libs.plugins.roborazzi)
}

android {
    namespace = "club.anifox.android"

    defaultConfig {
        applicationId = "club.anifox.android"
        versionCode = 1
        versionName = "0.1.1"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = AnifoxBuildType.DEBUG.applicationIdSuffix
        }
        release {
            isMinifyEnabled = true
            applicationIdSuffix = AnifoxBuildType.RELEASE.applicationIdSuffix
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

            // To publish on the Play store a private signing key is required, but to allow anyone
            // who clones the code to sign and run the release variant, use the debug signing key.
            // TODO: Abstract the signing configuration to a separate file to avoid hardcoding this.
            signingConfig = signingConfigs.named("debug").get()
        }
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(projects.feature.home)
    implementation(projects.feature.detail)
    implementation(projects.feature.player)
    implementation(projects.feature.search)
    implementation(projects.feature.schedule)
    implementation(projects.feature.video)
    implementation(projects.feature.screenshots)
    implementation(projects.feature.favourite)
    implementation(projects.feature.browse)
    implementation(projects.feature.profile)
    implementation(projects.feature.login)
    implementation(projects.feature.registration)
    implementation(projects.feature.onboarding)
    implementation(projects.feature.genres)

    implementation(projects.domain)

    implementation(projects.core.uikit)
    implementation(projects.core.common)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3.adaptive)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.tracing.ktx)
    implementation(libs.kotlinx.coroutines.guava)

    ksp(libs.hilt.compiler)

    debugImplementation(libs.androidx.compose.ui.testManifest)

    kspTest(libs.hilt.compiler)

    testImplementation(libs.hilt.android.testing)

    testDemoImplementation(libs.roborazzi)

    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.navigation.testing)
    androidTestImplementation(libs.hilt.android.testing)
}