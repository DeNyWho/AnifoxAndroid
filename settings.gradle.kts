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
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "AnifoxAndroid"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")

include(":feature:detail")
include(":feature:home")
include(":feature:player")
include(":feature:schedule")
include(":feature:search")

include(":core:testing")

include(":data:datastore")
include(":data:network")
include(":data:local")
include(":data:source")

include(":lint")
include(":domain")
include(":common-ui")
include(":common")
