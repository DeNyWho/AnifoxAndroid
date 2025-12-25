
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import su.anifox.kmp.buildlogic.convention.libs

class CMPFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("su.anifox.kmp.library")
                apply("su.anifox.kmp.koin")
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("org.jetbrains.compose")
            }

            dependencies {
//                add("commonMainImplementation", project(":core:ui"))
//                add("commonMainImplementation", project(":core-base:ui"))
//                add("commonMainImplementation", project(":core:designsystem"))
//                add("commonMainImplementation", project(":core-base:designsystem"))
//                add("commonMainImplementation", project(":core:data"))
//                add("commonMainImplementation", project(":core-base:designsystem"))
//                add("commonMainImplementation", project(":core:analytics"))

                add("commonMainImplementation", libs.findLibrary("decompose").get())
                add("commonMainImplementation", libs.findLibrary("decompose.compose").get())

                add("commonMainImplementation", libs.findLibrary("koin.compose").get())
                add("commonMainImplementation", libs.findLibrary("koin.compose.viewmodel").get())

                add("commonMainImplementation", libs.findLibrary("jetbrains.composeRuntime").get())
                add("commonMainImplementation", libs.findLibrary("jetbrains.composeViewmodel").get())
                add("commonMainImplementation", libs.findLibrary("jetbrains.lifecycleViewmodel").get())
                add("commonMainImplementation", libs.findLibrary("jetbrains.lifecycle.compose").get())

                add(
                    "commonMainImplementation",
                    libs.findLibrary("jetbrains.lifecycleViewmodelSavedState").get(),
                )
                add("commonMainImplementation", libs.findLibrary("jetbrains.savedstate").get())
                add("commonMainImplementation", libs.findLibrary("jetbrains.bundle").get())
                add("commonMainImplementation", libs.findLibrary("jetbrains.composeNavigation").get())
                add(
                    "commonMainImplementation",
                    libs.findLibrary("kotlinx.collections.immutable").get(),
                )

                add("androidMainImplementation", platform(libs.findLibrary("koin-bom").get()))
                add("androidMainImplementation", libs.findLibrary("koin-android").get())
                add("androidMainImplementation", libs.findLibrary("koin.androidx.compose").get())

                add("androidMainImplementation", libs.findLibrary("koin.android").get())
                add("androidMainImplementation", libs.findLibrary("koin.androidx.navigation").get())
                add("androidMainImplementation", libs.findLibrary("koin.androidx.compose").get())
                add("androidMainImplementation", libs.findLibrary("koin.core.viewmodel").get())

            }
        }
    }
}