package club.anifox.android.core.uikit.component.slider.screenshots.content.param

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.card.anime.CardAnimePortraitDefaults.HorizontalArrangement
import club.anifox.android.core.uikit.component.slider.SliderContentDefaults
import club.anifox.android.core.uikit.component.card.screenshot.CardScreenshotLandscapeDefaults
import club.anifox.android.domain.state.StateListWrapper
import java.util.UUID

data class SliderScreenshotsContentPreviewParam(
    val modifier: Modifier = Modifier,
    val headerModifier: Modifier = SliderContentDefaults.Default,
    val itemModifier: Modifier = Modifier.width(CardScreenshotLandscapeDefaults.Width.Default),
    val thumbnailHeight: Dp = CardScreenshotLandscapeDefaults.Height.Default,
    val thumbnailWidth: Dp = CardScreenshotLandscapeDefaults.Width.Default,
    val headerTitle: String = "Title",
    val contentState: StateListWrapper<String>,
    val contentPadding: PaddingValues = PaddingValues(horizontal = 12.dp),
    val contentArrangement: Arrangement.Horizontal,
    val onHeaderClick: () -> Unit = { },
    val onItemClick: (String) -> Unit = { },
)

private val DataSet = List(10) { UUID.randomUUID().toString() }

class SliderScreenshotsContentProvider:
    PreviewParameterProvider<SliderScreenshotsContentPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<SliderScreenshotsContentPreviewParam>
        get() = listOf(
            SliderScreenshotsContentPreviewParam(
                modifier = Modifier,
                headerModifier = SliderContentDefaults.Default,
                headerTitle = "Scrollable Default",
                contentArrangement = HorizontalArrangement.Default,
                contentState = StateListWrapper.loading()
            ),
            SliderScreenshotsContentPreviewParam(
                modifier = Modifier,
                headerModifier = SliderContentDefaults.Default,
                headerTitle = "Scrollable Default",
                contentArrangement = HorizontalArrangement.Default,
                contentState = StateListWrapper(data = DataSet, isLoading = false)
            ),
        ).asSequence()
}