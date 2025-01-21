package club.anifox.android.core.uikit.component.slider.screenshots.param

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.card.anime.CardAnimePortraitDefaults.HorizontalArrangement
import club.anifox.android.core.uikit.component.card.screenshot.CardScreenshotLandscapeDefaults
import club.anifox.android.core.uikit.component.slider.SliderComponentDefaults
import club.anifox.android.domain.state.StateListWrapper
import kotlinx.collections.immutable.toImmutableList
import java.util.UUID

internal data class SliderScreenshotsComponentPreviewParam(
    val modifier: Modifier = Modifier,
    val headerModifier: Modifier = SliderComponentDefaults.Default,
    val thumbnailHeight: Dp = CardScreenshotLandscapeDefaults.Height.Default,
    val thumbnailWidth: Dp = CardScreenshotLandscapeDefaults.Width.Default,
    val headerTitle: String = "Title",
    val contentState: StateListWrapper<String>,
    val contentPadding: PaddingValues = PaddingValues(horizontal = 12.dp),
    val contentArrangement: Arrangement.Horizontal,
    val onHeaderClick: () -> Unit = { },
    val onMoreClick: () -> Unit = { },
)

private val DataSet = List(10) { UUID.randomUUID().toString() }.toImmutableList()

internal class SliderScreenshotsComponentProvider:
    PreviewParameterProvider<SliderScreenshotsComponentPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<SliderScreenshotsComponentPreviewParam>
        get() = listOf(
            SliderScreenshotsComponentPreviewParam(
                modifier = Modifier,
                headerModifier = SliderComponentDefaults.Default,
                headerTitle = "Scrollable Default",
                contentArrangement = HorizontalArrangement.Default,
                contentState = StateListWrapper.loading()
            ),
            SliderScreenshotsComponentPreviewParam(
                modifier = Modifier,
                headerModifier = SliderComponentDefaults.Default,
                headerTitle = "Scrollable Default",
                contentArrangement = HorizontalArrangement.Default,
                contentState = StateListWrapper(data = DataSet, isLoading = false)
            ),
        ).asSequence()
}