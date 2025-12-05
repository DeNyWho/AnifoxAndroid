@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

package su.anifox.kmp.buildlogic.convention

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinHierarchyBuilder
import org.jetbrains.kotlin.gradle.plugin.KotlinHierarchyTemplate
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

/**
 * Defines the hierarchical structure for source set organization.
 *
 * This template establishes the relationships between different platform targets,
 * creating logical groupings based on platform similarities to facilitate code sharing.
 */
private val hierarchyTemplate = KotlinHierarchyTemplate {
    withSourceSetTree(
        KotlinSourceSetTree.main,
        KotlinSourceSetTree.test,
    )

    common {
        withCompilations { true }

        groupAndroid()
        groupIos()
        groupDesktop()
        groupMacos()
    }
}

private fun KotlinHierarchyBuilder.groupAndroid() {
    group("android") {
        withAndroidTarget()
    }
}

private fun KotlinHierarchyBuilder.groupIos() {
    group("ios") {
        withIos()
    }
}

private fun KotlinHierarchyBuilder.groupDesktop() {
    group("desktop") {
        withJvm()
    }
}

private fun KotlinHierarchyBuilder.groupMacos() {
    group("macos") {
        withMacos()
    }
}

/**
 * Applies the predefined hierarchy template to a Kotlin Multiplatform project.
 *
 * This extension function should be called within the `kotlin` block of a Multiplatform
 * project's build script to establish the source set hierarchy defined in this file.
 *
 * Example usage:
 * ```
 * kotlin {
 *     applyProjectHierarchyTemplate()
 *     // Configure targets...
 * }
 * ```
 */
fun KotlinMultiplatformExtension.applyProjectHierarchyTemplate() {
    applyHierarchyTemplate(hierarchyTemplate)
}