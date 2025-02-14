package club.anifox.android.feature.character.param

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import club.anifox.android.core.uikit.param.GlobalParams
import club.anifox.android.domain.model.character.full.CharacterFull
import club.anifox.android.domain.state.StateWrapper

internal data class CharacterUIPreviewParam(
    val onBackPressed: () -> Unit = { },
    val onAnimeClick: (String) -> Unit = { },
    val characterState: StateWrapper<CharacterFull>,
)

internal class CharacterUIProvider:
    PreviewParameterProvider<CharacterUIPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<CharacterUIPreviewParam>
        get() = listOf(
            CharacterUIPreviewParam(
                characterState = StateWrapper.loading(),
            ),
            CharacterUIPreviewParam(
                characterState = StateWrapper(data = GlobalParams.DataCharacterFull, isLoading = false),
            ),
        ).asSequence()
}