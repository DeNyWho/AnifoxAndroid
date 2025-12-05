import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "org.convention.buildlogic"

// Configure the build-logic plugins to target JDK 19
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.androidx.room.gradle.plugin)
    compileOnly(libs.firebase.crashlytics.gradlePlugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }

    // Configure JUnit 5 for testing keystore management functionality
    test {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }
}

gradlePlugin {
    plugins {
        // Android Plugins
        register("anifoxAndroidApplicationCompose") {
            id = "su.anifox.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }

        register("anifoxAndroidApplication") {
            id = "su.anifox.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("anifoxFirebase") {
            id = "su.anifox.firebase"
            implementationClass = "AndroidApplicationFirebaseConventionPlugin"
        }

        register("anifoxCmpFeature") {
            id = "su.anifox.cmp.feature"
            implementationClass = "CMPFeatureConventionPlugin"
        }

        register("anifoxKmpKoin") {
            id = "su.anifox.kmp.koin"
            implementationClass = "KMPKoinConventionPlugin"
        }

        register("anifoxKmpLibrary") {
            id = "su.anifox.kmp.library"
            implementationClass = "KMPLibraryConventionPlugin"
        }

        register("anifoxKMPRoom"){
            id = "su.anifox.kmp.room"
            implementationClass = "KMPRoomConventionPlugin"
        }
    }
}