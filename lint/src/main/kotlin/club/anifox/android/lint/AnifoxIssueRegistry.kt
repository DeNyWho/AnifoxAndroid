package club.anifox.android.lint

import club.anifox.android.lint.designsystem.DesignSystemDetector
import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API

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