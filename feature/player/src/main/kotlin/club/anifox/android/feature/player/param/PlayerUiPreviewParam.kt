package club.anifox.android.feature.player.param

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

internal data class PlayerUiPreviewParam(
    val onBackPressed: () -> Unit = { },
)

internal class PlayerUiProvider:
    PreviewParameterProvider<PlayerUiPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<PlayerUiPreviewParam>
        get() = listOf(
            PlayerUiPreviewParam(
            ),
        ).asSequence()
}