package club.anifox.android.feature.episodes.components.item.param

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import club.anifox.android.core.uikit.param.GlobalParams
import club.anifox.android.domain.model.anime.episodes.AnimeEpisodesLight

internal data class CardEpisodeComponentItemPreviewParam(
    val modifier: Modifier = Modifier,
    val data: AnimeEpisodesLight,
    val onClick: (String) -> Unit = { },
    val currentTranslationId: Int = 1269,
)

internal class CardEpisodeComponentItemProvider :
    PreviewParameterProvider<CardEpisodeComponentItemPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<CardEpisodeComponentItemPreviewParam>
        get() = listOf(
            CardEpisodeComponentItemPreviewParam(
                data = GlobalParams.DataAnimeEpisodeSingle,
            ),
        ).asSequence()
}