package club.anifox.android.feature.characters

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import club.anifox.android.core.uikit.component.card.character.CardCharactersItem
import club.anifox.android.core.uikit.component.card.character.CardCharactersItemDefaults
import club.anifox.android.core.uikit.component.grid.GridComponentDefaults
import club.anifox.android.core.uikit.component.progress.CircularProgress
import club.anifox.android.core.uikit.component.topbar.SimpleTopBarCollapse
import club.anifox.android.core.uikit.util.LocalScreenInfo
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
    onBackPressed: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val charactersResults = viewModel.charactersResults

    LaunchedEffect(Unit) {
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
    onBackPressed: () -> Unit,
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
                charactersResults = charactersResults,
                updateFilter = updateFilter,
                onCharacterClick = onCharacterClick,
            )
        }
    )
}

@Composable
private fun CharactersContentUI(
    modifier: Modifier = Modifier,
    uiState: CharactersUiState,
    charactersResults: Flow<PagingData<AnimeCharactersLight>>,
    updateFilter: (String?) -> Unit,
    onCharacterClick: (String) -> Unit,
) {
    val lazyGridState = rememberLazyGridState()
    val items = charactersResults.collectAsLazyPagingItems()

    val screenInfo = LocalScreenInfo.current
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    val currentWidth = if (isPortrait) {
        screenInfo.portraitWidthDp
    } else {
        screenInfo.landscapeWidthDp
    }

    val minColumnSize = (if (isPortrait) {
        currentWidth.dp / 4
    } else {
        currentWidth.dp / 8
    }).coerceAtLeast(CardCharactersItemDefaults.Width.Default.times(1.1f))

    if (items.loadState.refresh is LoadState.Loading) {
        CircularProgress()
    } else {
        LazyVerticalGrid(
            modifier = GridComponentDefaults.Default.fillMaxSize(),
            columns = GridCells.Adaptive(minSize = minColumnSize),
            state = lazyGridState,
            horizontalArrangement = CardCharactersItemDefaults.HorizontalArrangement.Grid,
            verticalArrangement = CardCharactersItemDefaults.VerticalArrangement.Grid,
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Spacer(modifier = Modifier.height(CardCharactersItemDefaults.GridItemSpan.Default))
            }

            items(
                count = items.itemCount,
                key = items.itemKey { it.id }
            ) { index ->
                val character = items[index]
                if (character != null) {
                    Box(
                        modifier = Modifier.width(minColumnSize),
                        contentAlignment = Alignment.Center,
                    ) {
                        CardCharactersItem(
                            data = character,
                            thumbnailHeight = CardCharactersItemDefaults.Height.Default,
                            thumbnailWidth = CardCharactersItemDefaults.Width.Default,
                            onClick = { onCharacterClick.invoke(character.id) }
                        )
                    }
                }
            }
        }
    }
}