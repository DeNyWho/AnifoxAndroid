
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import su.anifox.kmp.buildlogic.convention.libs

/**
 * Plugin that applies the Koin plugin and configures it.
 */
class KMPKoinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.devtools.ksp")
            }

            dependencies {
                val bom = libs.findLibrary("koin-bom").get()
                add("commonMainImplementation", platform(bom))
                add("commonMainImplementation", libs.findLibrary("koin.core").get())
                add("commonMainImplementation", libs.findLibrary("koin.annotations").get())

                add("commonTestImplementation", libs.findLibrary("koin.test").get())
            }
        }
    }
}