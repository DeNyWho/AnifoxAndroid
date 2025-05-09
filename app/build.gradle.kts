import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.anifox.android.application)
    alias(libs.plugins.anifox.android.application.compose)
    alias(libs.plugins.anifox.android.application.jacoco)
    alias(libs.plugins.anifox.android.application.firebase)
    alias(libs.plugins.anifox.android.hilt)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.android.application)
    alias(libs.plugins.baselineprofile)
}

android {
    namespace = "club.anifox.android"

    signingConfigs {
        create("release") {
            storeFile = file(gradleLocalProperties(rootDir, providers).getProperty("keystore_path"))
            storePassword =
                gradleLocalProperties(rootDir, providers).getProperty("keystore_password")
            keyAlias = gradleLocalProperties(rootDir, providers).getProperty("key_alias")
            keyPassword = gradleLocalProperties(rootDir, providers).getProperty("key_password")
        }
    }

    defaultConfig {
        applicationId = "club.anifox.android"
        versionCode = 102
        versionName = "1.0.0"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {

        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    buildFeatures {
        buildConfig = true
    }

    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
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
    implementation(projects.feature.characters)
    implementation(projects.feature.character)
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
    implementation(projects.feature.catalog)
    implementation(projects.feature.translations)
    implementation(projects.feature.episodes)
    implementation(projects.feature.settings)

    implementation(projects.domain)

    implementation(projects.core.uikit)
    implementation(projects.core.common)

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3.adaptive)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.tracing.ktx)
    implementation(libs.kotlinx.coroutines.guava)
    implementation(libs.androidx.profileinstaller)
    "baselineProfile"(project(":benchmarks"))

    ksp(libs.hilt.compiler)

    debugImplementation(libs.androidx.compose.ui.testManifest)

    kspTest(libs.hilt.compiler)

    testImplementation(libs.hilt.android.testing)

    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.navigation.testing)
    androidTestImplementation(libs.hilt.android.testing)
}

dependencyGuard {
    configuration("prodReleaseRuntimeClasspath")
}
