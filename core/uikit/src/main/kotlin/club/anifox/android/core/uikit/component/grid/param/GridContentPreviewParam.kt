package club.anifox.android.core.uikit.component.grid.param

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import club.anifox.android.core.uikit.component.card.anime.CardAnimePortraitDefaults
import club.anifox.android.core.uikit.param.GlobalParams
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.state.StateListWrapper

internal data class GridContentPreviewParam(
    val modifier: Modifier = Modifier,
    val itemModifier: Modifier = Modifier.width(CardAnimePortraitDefaults.Width.Grid),
    val thumbnailHeight: Dp = CardAnimePortraitDefaults.Height.Grid,
    val thumbnailWidth: Dp = CardAnimePortraitDefaults.Width.Grid,
    val contentState: StateListWrapper<AnimeLight>,
    val contentArrangement: Arrangement.Horizontal = CardAnimePortraitDefaults.HorizontalArrangement.Grid,
    val onItemClick: (String) -> Unit = { },
)

internal class GridContentProvider:
    PreviewParameterProvider<GridContentPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<GridContentPreviewParam>
        get() = listOf(
            GridContentPreviewParam(
                contentState = StateListWrapper.loading(),
            ),
            GridContentPreviewParam(
                contentState = StateListWrapper(data = GlobalParams.DataSetAnimeLight, isLoading = false),
            ),
        ).asSequence()
}


