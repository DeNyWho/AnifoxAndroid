package club.anifox.android.core.uikit.component.slider.simple.content.param

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.card.anime.CardAnimePortraitDefaults
import club.anifox.android.core.uikit.component.card.anime.CardAnimePortraitDefaults.HorizontalArrangement
import club.anifox.android.core.uikit.component.slider.SliderContentDefaults
import club.anifox.android.core.uikit.param.GlobalParams
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.state.StateListWrapper

internal data class SliderContentPreviewParam(
    val modifier: Modifier = Modifier,
    val headerModifier: Modifier = SliderContentDefaults.Default,
    val itemModifier: Modifier = Modifier.width(CardAnimePortraitDefaults.Width.Default),
    val thumbnailHeight: Dp = CardAnimePortraitDefaults.Height.Default,
    val thumbnailWidth: Dp = CardAnimePortraitDefaults.Width.Default,
    val headerTitle: String = "Title",
    val contentState: StateListWrapper<AnimeLight>,
    val contentPadding: PaddingValues = PaddingValues(horizontal = 12.dp),
    val contentArrangement: Arrangement.Horizontal,
    val textAlign: TextAlign = TextAlign.Start,
    val onHeaderClick: () -> Unit = { },
    val onItemClick: (String) -> Unit = { },
)

internal class SliderContentProvider:
    PreviewParameterProvider<SliderContentPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<SliderContentPreviewParam>
        get() = listOf(
            SliderContentPreviewParam(
                modifier = Modifier,
                headerModifier = SliderContentDefaults.Default,
                headerTitle = "Scrollable Default",
                contentArrangement = HorizontalArrangement.Default,
                contentState = StateListWrapper.loading()
            ),
            SliderContentPreviewParam(
                modifier = Modifier,
                headerModifier = SliderContentDefaults.Default,
                headerTitle = "Scrollable Default",
                contentArrangement = HorizontalArrangement.Default,
                contentState = StateListWrapper(data = GlobalParams.DataSetAnimeLight, isLoading = false)
            ),
        ).asSequence()
}


