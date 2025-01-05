package club.anifox.android.feature.characters

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import club.anifox.android.core.uikit.component.topbar.SimpleTopBarCollapse
import club.anifox.android.core.uikit.util.toolbarShadow
import club.anifox.android.domain.model.anime.characters.AnimeCharactersLight
import club.anifox.android.feature.characters.model.state.CharactersUiState
import kotlinx.coroutines.flow.Flow
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
internal fun CharactersScreen(
    viewModel: CharactersViewModel = hiltViewModel(),
    url: String = "",
    animeTitle: String? = null,
    onCharacterClick: (String) -> Unit,
    onBackPressed: () -> Boolean,
) {
    val uiState by viewModel.uiState.collectAsState()
    val charactersResults = viewModel.charactersResults

    LaunchedEffect(url, uiState) {
        if (!uiState.isInitialized) {
            viewModel.initializeParams(url)
        }
    }

    CharactersUI(
        uiState = uiState,
        animeTitle = animeTitle,
        charactersResults = charactersResults,
        updateFilter = { role ->
            viewModel.updateFilter(role)
        },
        onCharacterClick = onCharacterClick,
        onBackPressed = onBackPressed,
    )
}

@Composable
private fun CharactersUI(
    uiState: CharactersUiState,
    animeTitle: String?,
    charactersResults: Flow<PagingData<AnimeCharactersLight>>,
    updateFilter: (String?) -> Unit,
    onCharacterClick: (String) -> Unit,
    onBackPressed: () -> Boolean,
) {
    val toolbarScaffoldState = rememberCollapsingToolbarScaffoldState()
    CollapsingToolbarScaffold(
        modifier = Modifier
            .fillMaxSize(),
        state = toolbarScaffoldState,
        scrollStrategy = ScrollStrategy.EnterAlwaysCollapsed,
        toolbar = {
            SimpleTopBarCollapse(
                onBackPressed = onBackPressed,
                title = if(animeTitle == null) "" else "${stringResource(R.string.feature_characters_top_bar_title)} $animeTitle",
                toolbarScaffoldState = toolbarScaffoldState,
            )
        },
        toolbarModifier = Modifier.toolbarShadow(
            shadowElevation = 4.dp,
            tonalElevation = 4.dp,
            shape = RectangleShape,
        ),
        body = {
            CharactersContentUI(
                uiState = uiState,
                onCharacterClick = onCharacterClick,
                onBackPressed = onBackPressed,
                charactersResults = charactersResults,
                updateFilter = updateFilter,
            )
        }
    )
}

@Composable
private fun CharactersContentUI(
    modifier: Modifier = Modifier,
    uiState: CharactersUiState,
    onCharacterClick: (String) -> Unit,
    onBackPressed: () -> Boolean,
    charactersResults: Flow<PagingData<AnimeCharactersLight>>,
    updateFilter: (String?) -> Unit,
) {
    Column(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp),
    ) {

    }
}