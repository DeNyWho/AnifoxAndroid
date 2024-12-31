package club.anifox.android.feature.character

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.hilt.navigation.compose.hiltViewModel
import club.anifox.android.core.uikit.component.progress.CircularProgress
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.domain.model.character.full.CharacterFull
import club.anifox.android.domain.state.StateWrapper
import club.anifox.android.feature.character.param.CharacterContentPreviewParam
import club.anifox.android.feature.character.param.CharacterContentProvider

@Composable
internal fun CharacterScreen(
    viewModel: CharacterViewModel = hiltViewModel(),
    onBackPressed: () -> Boolean,
    id: String,
) {
    LaunchedEffect(viewModel, id) {
        viewModel.loadData(id)
    }

    CharacterUI(
        onBackPressed = onBackPressed,
        characterState = viewModel.character.value,
    )
}

@Composable
internal fun CharacterUI(
    onBackPressed: () -> Boolean,
    characterState: StateWrapper<CharacterFull>,
) {
    if(characterState.isLoading) {
        CircularProgress()
    } else {

    }
}

@PreviewScreenSizes
@Composable
private fun PreviewCharacterUI(
    @PreviewParameter(CharacterContentProvider::class) param: CharacterContentPreviewParam,
) {
    DefaultPreview {
        CharacterUI(
            onBackPressed = param.onBackPressed,
            characterState = param.characterState,
        )
    }
}
