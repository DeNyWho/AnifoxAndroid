package club.anifox.lint

import club.anifox.lint.designsystem.DesignSystemDetector
import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue

class AnifoxIssueRegistry : IssueRegistry() {

    override val issues = listOf(
        DesignSystemDetector.ISSUE,
        TestMethodNameDetector.FORMAT,
        TestMethodNameDetector.PREFIX,
    )

    override val api: Int = CURRENT_API

    override val minApi: Int = 12

    override val vendor: Vendor = Vendor(
        vendorName = "Anifox",
        feedbackUrl = "https://github.com/DeNyWho/AnifoxAndroid/issues",
        contact = "https://github.com/DeNyWho/AnifoxAndroid",
    )
}