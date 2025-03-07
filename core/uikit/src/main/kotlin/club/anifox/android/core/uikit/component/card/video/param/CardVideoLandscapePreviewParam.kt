package club.anifox.android.core.uikit.component.card.video.param

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import club.anifox.android.core.uikit.component.card.video.CardVideoLandscapeDefaults
import club.anifox.android.core.uikit.param.GlobalParams
import club.anifox.android.domain.model.anime.videos.AnimeVideosLight

internal data class CardVideoLandscapePreviewParam(
    val modifier: Modifier = Modifier,
    val data: AnimeVideosLight,
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
                data = GlobalParams.DataAnimeVideosLightSingle,
            ),
        ).asSequence()
}
