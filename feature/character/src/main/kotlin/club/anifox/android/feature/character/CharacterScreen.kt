package club.anifox.android.feature.character

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import club.anifox.android.core.uikit.component.progress.CircularProgress
import club.anifox.android.core.uikit.component.slider.SliderComponentDefaults
import club.anifox.android.core.uikit.component.topbar.SimpleTopBarCollapse
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.core.uikit.util.toolbarShadow
import club.anifox.android.domain.model.character.full.CharacterFull
import club.anifox.android.domain.state.StateWrapper
import club.anifox.android.feature.character.components.about.AboutComponent
import club.anifox.android.feature.character.components.anime.AnimeComponent
import club.anifox.android.feature.character.components.overview.OverviewComponent
import club.anifox.android.feature.character.components.pictures.PicturesComponent
import club.anifox.android.feature.character.param.CharacterUIPreviewParam
import club.anifox.android.feature.character.param.CharacterUIProvider
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
internal fun CharacterScreen(
    viewModel: CharacterViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
    onAnimeClick: (String) -> Unit,
    id: String,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        if(!uiState.isInitialized) {
            viewModel.initialize(id)
        }
    }

    CharacterUI(
        onBackPressed = onBackPressed,
        onAnimeClick = onAnimeClick,
        characterState = viewModel.character.value,
    )
}

@Composable
private fun CharacterUI(
    onBackPressed: () -> Unit,
    onAnimeClick: (String) -> Unit,
    characterState: StateWrapper<CharacterFull>,
) {
    val toolbarScaffoldState = rememberCollapsingToolbarScaffoldState()

    when {
        characterState.isLoading -> CircularProgress()
        characterState.error.message.isNotEmpty() -> { }
        else -> {
            characterState.data?.let { character ->
                CollapsingToolbarScaffold(
                    modifier = Modifier.fillMaxSize(),
                    state = toolbarScaffoldState,
                    scrollStrategy = ScrollStrategy.EnterAlwaysCollapsed,
                    toolbar = {
                        SimpleTopBarCollapse(
                            title = "${stringResource(R.string.feature_character_top_bar_title)} ${character.name}",
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
                        CharacterContentUI(
                            onAnimeClick = onAnimeClick,
                            character = character,
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun CharacterContentUI(
    onAnimeClick: (String) -> Unit,
    character: CharacterFull,
) {
    var isDescriptionExpanded by rememberSaveable { mutableStateOf(false) }
    val lazyColumnState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        state = lazyColumnState,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            OverviewComponent(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                character = character,
            )
        }

        if(!character.about.isNullOrEmpty()) {
            item {
                AboutComponent(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    headerModifier = SliderComponentDefaults.Default,
                    character = character,
                    isExpanded = isDescriptionExpanded,
                    onExpandedChanged = { isDescriptionExpanded = it },
                )
            }
        }

        if(character.roles.isNotEmpty()) {
            item {
                AnimeComponent(
                    headerModifier = SliderComponentDefaults.Default,
                    roles = character.roles,
                    onItemClick = onAnimeClick,
                )
            }
        }

        if(character.pictures.isNotEmpty()) {
            item {
                PicturesComponent(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    headerModifier = SliderComponentDefaults.BottomOnly,
                    pictures = character.pictures,
                )
            }
        }
    }
}

@PreviewScreenSizes
@Composable
private fun PreviewCharacterUI(
    @PreviewParameter(CharacterUIProvider::class) param: CharacterUIPreviewParam,
) {
    DefaultPreview {
        CharacterUI(
            onBackPressed = param.onBackPressed,
            onAnimeClick = param.onAnimeClick,
            characterState = param.characterState,
        )
    }
}
