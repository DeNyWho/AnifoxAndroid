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

internal data class GridComponentPreviewParam(
    val modifier: Modifier = Modifier,
    val itemModifier: Modifier = Modifier.width(CardAnimePortraitDefaults.Width.GridSmall),
    val thumbnailHeight: Dp = CardAnimePortraitDefaults.Height.GridSmall,
    val thumbnailWidth: Dp = CardAnimePortraitDefaults.Width.GridSmall,
    val contentState: StateListWrapper<AnimeLight>,
    val contentArrangement: Arrangement.Horizontal = CardAnimePortraitDefaults.HorizontalArrangement.Grid,
    val onItemClick: (String) -> Unit = { },
)

internal class GridComponentProvider:
    PreviewParameterProvider<GridComponentPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<GridComponentPreviewParam>
        get() = listOf(
            GridComponentPreviewParam(
                contentState = StateListWrapper.loading(),
            ),
            GridComponentPreviewParam(
                contentState = StateListWrapper(data = GlobalParams.DataSetAnimeLight, isLoading = false),
            ),
        ).asSequence()
}


