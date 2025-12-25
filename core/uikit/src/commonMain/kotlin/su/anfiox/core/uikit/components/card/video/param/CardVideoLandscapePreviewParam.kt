package su.anfiox.core.uikit.components.card.video.param

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import su.anfiox.core.uikit.components.card.video.CardVideoLandscapeDefaults
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider
import su.anfiox.core.uikit.param.GlobalParams
import su.anifox.domain.model.anime.media.AnimeVideo

internal data class CardVideoLandscapePreviewParam(
    val modifier: Modifier = Modifier,
    val data: AnimeVideo,
    val thumbnailHeight: Dp = CardVideoLandscapeDefaults.Height.Default,
    val thumbnailWidth: Dp = CardVideoLandscapeDefaults.Width.Default,
    val onClick: () -> Unit = { },
    val isTypeVisible: Boolean = true,
)

internal class CardVideoLandscapeProvider :
    PreviewParameterProvider<CardVideoLandscapePreviewParam> {
    override val values: Sequence<CardVideoLandscapePreviewParam>
        get() = listOf(
            CardVideoLandscapePreviewParam(
                data = GlobalParams.DataAnimeVideosSingle,
            ),
        ).asSequence()
}
