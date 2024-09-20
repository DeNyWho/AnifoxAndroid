package club.anifox.android.core.uikit.component.card.anime.param

import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import club.anifox.android.core.uikit.component.card.anime.CardAnimePortraitDefaults
import club.anifox.android.core.uikit.param.GlobalParams
import club.anifox.android.domain.model.anime.AnimeLight

internal data class CardAnimePreviewParam(
    val modifier: Modifier = Modifier,
    val thumbnailHeight: Dp,
    val data: AnimeLight = GlobalParams.DataAnimeLightSingle,
    val onClick: () -> Unit = {},
)

internal class CardAnimeProvider: PreviewParameterProvider<CardAnimePreviewParam> {
    override val values: Sequence<CardAnimePreviewParam>
        get() = listOf(
            CardAnimePreviewParam(
                modifier = Modifier.width(CardAnimePortraitDefaults.Width.Default),
                thumbnailHeight = CardAnimePortraitDefaults.Height.Default
            ),
            CardAnimePreviewParam(
                modifier = Modifier.width(CardAnimePortraitDefaults.Width.Small),
                thumbnailHeight = CardAnimePortraitDefaults.Height.Grid,
            ),
            CardAnimePreviewParam(
                modifier = Modifier.width(CardAnimePortraitDefaults.Width.Small),
                thumbnailHeight = CardAnimePortraitDefaults.Height.Small,
            ),
        ).asSequence()
}
