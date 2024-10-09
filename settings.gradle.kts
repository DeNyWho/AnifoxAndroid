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

gradle.startParameter.excludedTaskNames.addAll(listOf(":build-logic:convention:testClasses"))

rootProject.name = "AnifoxAndroid"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")

include(":feature:detail")
include(":feature:home")
include(":feature:player")
include(":feature:schedule")
include(":feature:search")
include(":feature:video")
include(":feature:screenshots")
include(":feature:browse")
include(":feature:profile")
include(":feature:favourite")
include(":feature:login")
include(":feature:registration")
include(":feature:onboarding")
include(":feature:genres")
include(":feature:catalog")
include(":feature:translations")

include(":core:testing")
include(":core:uikit")
include(":core:common")

include(":data:datastore")
include(":data:network")
include(":data:local")
include(":data:source")

include(":lint")
include(":domain")
