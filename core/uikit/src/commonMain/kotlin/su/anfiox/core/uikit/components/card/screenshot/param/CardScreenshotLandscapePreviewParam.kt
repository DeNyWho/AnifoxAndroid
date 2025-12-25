package su.anfiox.core.uikit.components.card.screenshot.param

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import su.anfiox.core.uikit.components.card.screenshot.CardScreenshotLandscapeDefaults
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

internal data class CardScreenshotLandscapePreviewParam(
    val modifier: Modifier = Modifier,
    val image: String = "",
    val thumbnailHeight: Dp = CardScreenshotLandscapeDefaults.Height.Default,
    val thumbnailWidth: Dp = CardScreenshotLandscapeDefaults.Width.Default,
    val onClick: () -> Unit = { },
)

internal class CardScreenshotLandscapeProvider :
    PreviewParameterProvider<CardScreenshotLandscapePreviewParam> {
    override val values: Sequence<CardScreenshotLandscapePreviewParam>
        get() = listOf(
            CardScreenshotLandscapePreviewParam(),
        ).asSequence()
}
