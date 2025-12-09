import com.codingfeline.buildkonfig.compiler.FieldSpec
import java.util.Properties

plugins {
    alias(libs.plugins.anifox.kmp.library)
    alias(libs.plugins.apollo.gradlePlugin)
    alias(libs.plugins.buildkonfig)
}

android {
    namespace = "su.anifox.data.network"
}

buildkonfig {
    packageName = "su.anifox.data.network"

    val localProps = Properties().apply {
        rootProject.file("local.properties").takeIf { it.exists() }?.inputStream()?.use { load(it) }
    }

    defaultConfigs {
        buildConfigField(
            FieldSpec.Type.STRING, "BASE_URL", "\"${localProps.getProperty("api.baseUrl", "")}\""
        )
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.domain)

            implementation(libs.apollo.graphql)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)
        }
    }
}

apollo {
    service("anifox") {
        packageName.set("su.anifox.data.network.graphql")

        packageName.set("su.anifox.graphql")
        srcDir("src/commonMain/graphql/")
        schemaFile.set(file("src/commonMain/graphql/schema.graphqls"))

        generateKotlinModels.set(true)
        codegenModels.set("operationBased")
        generateFragmentImplementations.set(true)
        useSemanticNaming.set(true)
        generateOptionalOperationVariables.set(false)

        mapScalar("DateTime", "kotlinx.datetime.Instant")
        mapScalar("JSON", "kotlinx.serialization.json.JsonElement")
    }
}