package club.anifox.android.core.uikit.component.card.screenshot.param

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import club.anifox.android.core.uikit.component.card.screenshot.CardScreenshotLandscapeDefaults

internal data class CardScreenshotLandscapePreviewParam(
    val modifier: Modifier = Modifier,
    val image: String = "",
    val thumbnailHeight: Dp = CardScreenshotLandscapeDefaults.Height.Default,
    val thumbnailWidth: Dp = CardScreenshotLandscapeDefaults.Width.Default,
    val onClick: () -> Unit = { },
)

internal class CardScreenshotLandscapeProvider: PreviewParameterProvider<CardScreenshotLandscapePreviewParam> {
    override val values: Sequence<CardScreenshotLandscapePreviewParam>
        get() = listOf(
            CardScreenshotLandscapePreviewParam(),
        ).asSequence()
}
