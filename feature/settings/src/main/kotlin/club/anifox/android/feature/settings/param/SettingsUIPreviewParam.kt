package club.anifox.android.feature.settings.param

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

internal data class SettingsUIPreviewParam(
    val onBackPressed: () -> Boolean = { false },
)

internal class SettingsUIProvider:
    PreviewParameterProvider<SettingsUIPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<SettingsUIPreviewParam>
        get() = listOf(
            SettingsUIPreviewParam(),
        ).asSequence()
}