package su.anfiox.core.uikit.components.slider.video.param

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import su.anfiox.core.uikit.components.card.anime.CardAnimePortraitDefaults.HorizontalArrangement
import su.anfiox.core.uikit.components.card.screenshot.CardScreenshotLandscapeDefaults
import su.anfiox.core.uikit.components.slider.SliderComponentDefaults
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider
import su.anfiox.core.uikit.param.GlobalParams.DataAnimeVideos
import su.anifox.domain.model.anime.media.AnimeVideo
import su.anifox.domain.state.StateListWrapper

internal data class SliderVideoComponentPreviewParam(
    val modifier: Modifier = Modifier,
    val headerModifier: Modifier = SliderComponentDefaults.Default,
    val itemModifier: Modifier = Modifier.width(CardScreenshotLandscapeDefaults.Width.Default),
    val thumbnailHeight: Dp = CardScreenshotLandscapeDefaults.Height.Default,
    val thumbnailWidth: Dp = CardScreenshotLandscapeDefaults.Width.Default,
    val headerTitle: String = "Title",
    val contentState: StateListWrapper<AnimeVideo>,
    val contentPadding: PaddingValues = PaddingValues(horizontal = 12.dp),
    val contentArrangement: Arrangement.Horizontal,
    val onHeaderClick: () -> Unit = { },
    val onItemClick: (String) -> Unit = { },
    val onMoreClick: () -> Unit = { },
)

internal class SliderVideoComponentProvider :
    PreviewParameterProvider<SliderVideoComponentPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<SliderVideoComponentPreviewParam>
        get() = listOf(
            SliderVideoComponentPreviewParam(
                modifier = Modifier,
                headerModifier = SliderComponentDefaults.Default,
                headerTitle = "Scrollable Default",
                contentArrangement = HorizontalArrangement.Default,
                contentState = StateListWrapper.loading()
            ),
            SliderVideoComponentPreviewParam(
                modifier = Modifier,
                headerModifier = SliderComponentDefaults.Default,
                headerTitle = "Scrollable Default",
                contentArrangement = HorizontalArrangement.Default,
                contentState = StateListWrapper(data = DataAnimeVideos, isLoading = false)
            ),
        ).asSequence()
}