package club.anifox.android.lint.designsystem

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UQualifiedReferenceExpression

class DesignSystemDetector : Detector(), Detector.UastScanner {

    override fun getApplicableUastTypes(): List<Class<out UElement>> = listOf(
        UCallExpression::class.java,
        UQualifiedReferenceExpression::class.java,
    )

    override fun createUastHandler(context: JavaContext): UElementHandler =
        object : UElementHandler() {
            override fun visitCallExpression(node: UCallExpression) {
                val name = node.methodName ?: return
                val preferredName = METHOD_NAMES[name] ?: return
                reportIssue(context, node, name, preferredName)
            }

            override fun visitQualifiedReferenceExpression(node: UQualifiedReferenceExpression) {
                val name = node.receiver.asRenderString()
                val preferredName = RECEIVER_NAMES[name] ?: return
                reportIssue(context, node, name, preferredName)
            }
        }

    companion object {
        @JvmField
        val ISSUE: Issue = Issue.create(
            id = "DesignSystem",
            briefDescription = "Design system",
            explanation = "This check highlights calls in code that use Compose Material " +
                "composables instead of equivalents from the Anifox design system " +
                "module.",
            category = Category.CUSTOM_LINT_CHECKS,
            priority = 7,
            severity = Severity.ERROR,
            implementation = Implementation(
                DesignSystemDetector::class.java,
                Scope.JAVA_FILE_SCOPE,
            ),
        )

        val METHOD_NAMES = mapOf(
            "MaterialTheme" to "AnifoxTheme",
            "Button" to "AnifoxButton",
            "OutlinedButton" to "AnifoxOutlinedButton",
            "TextButton" to "AnifoxTextButton",
            "FilterChip" to "AnifoxFilterChip",
            "ElevatedFilterChip" to "AnifoxFilterChip",
            "Icon" to "AnifoxIcon",
            "NavigationBar" to "AnifoxNavigationBar",
            "NavigationBarItem" to "AnifoxNavigationBarItem",
            "NavigationRail" to "AnifoxNavigationRail",
            "NavigationRailItem" to "AnifoxNavigationRailItem",
            "TabRow" to "AnifoxTabRow",
            "Tab" to "AnifoxTab",
            "IconToggleButton" to "AnifoxIconToggleButton",
            "FilledIconToggleButton" to "AnifoxIconToggleButton",
            "FilledTonalIconToggleButton" to "AnifoxIconToggleButton",
            "OutlinedIconToggleButton" to "AnifoxIconToggleButton",
            "CenterAlignedTopAppBar" to "AnifoxTopAppBar",
            "SmallTopAppBar" to "AnifoxTopAppBar",
            "MediumTopAppBar" to "AnifoxTopAppBar",
            "LargeTopAppBar" to "AnifoxTopAppBar",
        )
        val RECEIVER_NAMES = mapOf(
            "Icons" to "AnifoxIcons",
        )

        fun reportIssue(
            context: JavaContext,
            node: UElement,
            name: String,
            preferredName: String,
        ) {
            context.report(
                ISSUE,
                node,
                context.getLocation(node),
                "Using $name instead of $preferredName",
            )
        }
    }
}