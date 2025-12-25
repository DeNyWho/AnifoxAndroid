package su.anfiox.core.uikit.components.card.character.param

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import su.anfiox.core.uikit.components.card.character.CardCharactersItemDefaults
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider
import su.anfiox.core.uikit.param.GlobalParams
import su.anifox.domain.model.characters.AnimeCharactersLight

internal data class CardCharactersItemPreviewParam(
    val modifier: Modifier,
    val data: AnimeCharactersLight = GlobalParams.DataSetCharactersLight.first(),
    val thumbnailHeight: Dp = CardCharactersItemDefaults.Height.Default,
    val thumbnailWidth: Dp = CardCharactersItemDefaults.Width.Default,
    val onClick: () -> Unit = { },
)

internal class CardCharactersItemProvider :
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