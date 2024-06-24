package club.anifox.android.feature.detail.components.video.item.param

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.related.AnimeRelatedLight
import club.anifox.android.feature.detail.components.related.item.CardRelationItemDefaults

internal data class CardVideoItemPreviewParam(
    val modifier: Modifier,
    val data: AnimeRelatedLight = AnimeRelatedLight(
        anime = AnimeLight(
            title = "Провожающая в последний путь Фрирен",
            image = "https://cdn.anifox.club/images/anime/large/provozhaiushchaia-v-poslednii-put-friren/08f43e5054966f85ed4bcdbe7dc77b7b.png",
            url = "provozhaiushchaia-v-poslednii-put-friren"
        ),
        type = "Сериал",
    ),
    val thumbnailHeight: Dp = CardRelationItemDefaults.Height.Default,
    val thumbnailWidth: Dp = CardRelationItemDefaults.Width.Default,
    val onClick: () -> Unit = { },
)

internal class CardVideoItemProvider:
    PreviewParameterProvider<CardVideoItemPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<CardVideoItemPreviewParam>
        get() = listOf(
            CardVideoItemPreviewParam(
                Modifier,
            )
        ).asSequence()
}