package club.anifox.android.core.uikit.component.grid.param

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import club.anifox.android.core.uikit.component.card.anime.CardAnimePortraitDefaults

internal data class GridComponentPreviewParam(
    val modifier: Modifier = Modifier,
    val itemModifier: Modifier = Modifier.width(CardAnimePortraitDefaults.Width.GridSmall),
    val thumbnailHeight: Dp = CardAnimePortraitDefaults.Height.GridSmall,
    val thumbnailWidth: Dp = CardAnimePortraitDefaults.Width.GridSmall,
    val contentArrangement: Arrangement.Horizontal = CardAnimePortraitDefaults.HorizontalArrangement.Grid,
    val onItemClick: (String) -> Unit = { },
    val minColumnSize: Dp = CardAnimePortraitDefaults.Width.GridSmall,
)

internal class GridComponentProvider : PreviewParameterProvider<GridComponentPreviewParam> {
    override val count: Int
        get() = super.count

    override val values: Sequence<GridComponentPreviewParam>
        get() = sequenceOf(
            GridComponentPreviewParam(
            ),
        )
}


