plugins {
    alias(libs.plugins.anifox.kmp.library)
    alias(libs.plugins.apollo.gradlePlugin)
}

android {
    namespace = "su.anifox.data.network"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
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