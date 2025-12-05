pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.PREFER_PROJECT
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version("1.0.0")
}

buildCache {
    local {
        isEnabled = true
        directory = File(rootDir, "build-cache")
    }
}

rootProject.name = "Anifox"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":composeApp")
include(":core:common")
include(":core:uikit")

check(JavaVersion.current().isCompatibleWith(JavaVersion.VERSION_17)) {
    """
    This project requires JDK 17+ but it is currently using JDK ${JavaVersion.current()}.
    Java Home: [${System.getProperty("kotlin.home")}]
    https://developer.android.com/build/jdks#jdk-config-in-studio
    """.trimIndent()
}
include(":domain")
include(":data:local")
include(":data:network")
