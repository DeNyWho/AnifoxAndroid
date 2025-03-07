package club.anifox.android.feature.detail.components.related.item.param

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.related.AnimeRelatedLight
import club.anifox.android.feature.detail.components.related.item.CardRelationComponentItemDefaults

internal data class CardRelationComponentItemPreviewParam(
    val modifier: Modifier,
    val data: AnimeRelatedLight = AnimeRelatedLight(
        anime = AnimeLight(
            title = "Провожающая в последний путь Фрирен",
            image = "https://cdn.anifox.club/images/anime/large/provozhaiushchaia-v-poslednii-put-friren/08f43e5054966f85ed4bcdbe7dc77b7b.png",
            url = "provozhaiushchaia-v-poslednii-put-friren"
        ),
        type = "Сериал",
    ),
    val thumbnailHeight: Dp = CardRelationComponentItemDefaults.Height.Default,
    val thumbnailWidth: Dp = CardRelationComponentItemDefaults.Width.Default,
    val onClick: () -> Unit = { },
)

internal class CardRelationComponentItemProvider :
    PreviewParameterProvider<CardRelationComponentItemPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<CardRelationComponentItemPreviewParam>
        get() = listOf(
            CardRelationComponentItemPreviewParam(
                Modifier,
            )
        ).asSequence()
}