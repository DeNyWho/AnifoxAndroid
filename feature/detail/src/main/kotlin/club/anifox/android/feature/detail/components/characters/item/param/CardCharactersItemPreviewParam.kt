package club.anifox.android.feature.detail.components.characters.item.param

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import club.anifox.android.core.uikit.param.GlobalParams
import club.anifox.android.domain.model.anime.characters.AnimeCharactersLight
import club.anifox.android.feature.detail.components.characters.item.CardCharactersItemDefaults

internal data class CardCharactersItemPreviewParam(
    val modifier: Modifier,
    val data: AnimeCharactersLight = GlobalParams.DataSetCharactersLight.first(),
    val thumbnailHeight: Dp = CardCharactersItemDefaults.Height.Default,
    val thumbnailWidth: Dp = CardCharactersItemDefaults.Width.Default,
    val onClick: () -> Unit = { },
)

internal class CardCharactersItemProvider:
    PreviewParameterProvider<CardCharactersItemPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<CardCharactersItemPreviewParam>
        get() = listOf(
            CardCharactersItemPreviewParam(
                Modifier,
            )
        ).asSequence()
}