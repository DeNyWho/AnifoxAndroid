package su.anfiox.core.uikit.components.card.anime.param

import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import su.anfiox.core.uikit.components.card.anime.CardAnimePortraitDefaults
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider
import su.anfiox.core.uikit.param.GlobalParams
import su.anifox.domain.model.anime.common.AnimeLight

internal data class CardAnimePreviewParam(
    val modifier: Modifier = Modifier,
    val thumbnailHeight: Dp,
    val data: AnimeLight = GlobalParams.DataAnimeLightSingle,
    val onClick: () -> Unit = {},
)

internal class CardAnimeProvider : PreviewParameterProvider<CardAnimePreviewParam> {
    override val values: Sequence<CardAnimePreviewParam>
        get() = listOf(
            CardAnimePreviewParam(
                modifier = Modifier.width(CardAnimePortraitDefaults.Width.Default),
                thumbnailHeight = CardAnimePortraitDefaults.Height.Default
            ),
            CardAnimePreviewParam(
                modifier = Modifier.width(CardAnimePortraitDefaults.Width.Small),
                thumbnailHeight = CardAnimePortraitDefaults.Height.GridSmall,
            ),
            CardAnimePreviewParam(
                modifier = Modifier.width(CardAnimePortraitDefaults.Width.Small),
                thumbnailHeight = CardAnimePortraitDefaults.Height.Small,
            ),
        ).asSequence()
}
