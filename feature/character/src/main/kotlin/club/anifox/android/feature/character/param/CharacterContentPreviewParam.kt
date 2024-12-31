package club.anifox.android.feature.character.param

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import club.anifox.android.core.uikit.param.GlobalParams
import club.anifox.android.domain.model.character.full.CharacterFull
import club.anifox.android.domain.state.StateWrapper

internal data class CharacterContentPreviewParam(
    val onBackPressed: () -> Boolean = { false },
    val characterState: StateWrapper<CharacterFull>,
)

internal class CharacterContentProvider:
    PreviewParameterProvider<CharacterContentPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<CharacterContentPreviewParam>
        get() = listOf(
            CharacterContentPreviewParam(
                characterState = StateWrapper.loading(),
            ),
            CharacterContentPreviewParam(
                characterState = StateWrapper(data = GlobalParams.DataCharacterFull, isLoading = false),
            ),
        ).asSequence()
}