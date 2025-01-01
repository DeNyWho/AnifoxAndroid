package club.anifox.android.feature.character

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import club.anifox.android.core.uikit.component.progress.CircularProgress
import club.anifox.android.core.uikit.component.topbar.SimpleTopBarCollapse
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.core.uikit.util.toolbarShadow
import club.anifox.android.domain.model.character.full.CharacterFull
import club.anifox.android.domain.state.StateWrapper
import club.anifox.android.feature.character.param.CharacterContentPreviewParam
import club.anifox.android.feature.character.param.CharacterContentProvider
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

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
private fun CharacterUI(
    onBackPressed: () -> Boolean,
    characterState: StateWrapper<CharacterFull>,
) {
    val toolbarScaffoldState = rememberCollapsingToolbarScaffoldState()

    if(characterState.isLoading) {
        CircularProgress()
    } else {
        CollapsingToolbarScaffold(
            modifier = Modifier.fillMaxSize(),
            state = toolbarScaffoldState,
            scrollStrategy = ScrollStrategy.EnterAlwaysCollapsed,
            toolbar = {
                SimpleTopBarCollapse(
                    title = stringResource(R.string.feature_character_top_bar_title),
                    titleStyle = MaterialTheme.typography.titleLarge,
                    toolbarScaffoldState = toolbarScaffoldState,
                    onBackPressed = onBackPressed,
                )
            },
            toolbarModifier = Modifier.toolbarShadow(
                shadowElevation = 4.dp,
                tonalElevation = 4.dp,
                shape = RectangleShape,
            ),
            body = {
                CharacterContent()
            }
        )
    }
}

@Composable
private fun CharacterContent() {
    Column {

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
