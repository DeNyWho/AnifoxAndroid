import org.gradle.kotlin.dsl.plugins.precompiled.kotlinDslPluginOptions
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "club.anifox.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

//gradlePlugin {
//    plugins {
//        register("androidApplicationCompose") {
//            id = "nowinandroid.android.application.compose"
//            implementationClass = "AndroidApplicationComposeConventionPlugin"
//        }
//        register("androidApplication") {
//            id = "nowinandroid.android.application"
//            implementationClass = "AndroidApplicationConventionPlugin"
//        }
//        register("androidApplicationJacoco") {
//            id = "nowinandroid.android.application.jacoco"
//            implementationClass = "AndroidApplicationJacocoConventionPlugin"
//        }
//        register("androidLibraryCompose") {
//            id = "nowinandroid.android.library.compose"
//            implementationClass = "AndroidLibraryComposeConventionPlugin"
//        }
//        register("androidLibrary") {
//            id = "nowinandroid.android.library"
//            implementationClass = "AndroidLibraryConventionPlugin"
//        }
//        register("androidFeature") {
//            id = "nowinandroid.android.feature"
//            implementationClass = "AndroidFeatureConventionPlugin"
//        }
//        register("androidLibraryJacoco") {
//            id = "nowinandroid.android.library.jacoco"
//            implementationClass = "AndroidLibraryJacocoConventionPlugin"
//        }
//        register("androidTest") {
//            id = "nowinandroid.android.test"
//            implementationClass = "AndroidTestConventionPlugin"
//        }
//        register("androidHilt") {
//            id = "nowinandroid.android.hilt"
//            implementationClass = "AndroidHiltConventionPlugin"
//        }
//        register("androidRoom") {
//            id = "nowinandroid.android.room"
//            implementationClass = "AndroidRoomConventionPlugin"
//        }
//        register("androidFirebase") {
//            id = "nowinandroid.android.application.firebase"
//            implementationClass = "AndroidApplicationFirebaseConventionPlugin"
//        }
//        register("androidFlavors") {
//            id = "nowinandroid.android.application.flavors"
//            implementationClass = "AndroidApplicationFlavorsConventionPlugin"
//        }
//        register("androidLint") {
//            id = "nowinandroid.android.lint"
//            implementationClass = "AndroidLintConventionPlugin"
//        }
//        register("jvmLibrary") {
//            id = "nowinandroid.jvm.library"
//            implementationClass = "JvmLibraryConventionPlugin"
//        }
//    }
//}
