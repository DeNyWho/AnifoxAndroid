package club.anifox.android.feature.characters

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
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
import club.anifox.android.core.uikit.component.card.character.showCardCharactersItemShimmer
import club.anifox.android.core.uikit.component.error.NoSearchResultsError
import club.anifox.android.core.uikit.component.icon.AnifoxIconPrimary
import club.anifox.android.core.uikit.component.topbar.TopBarWithSearch
import club.anifox.android.core.uikit.util.KeyboardManager
import club.anifox.android.core.uikit.util.LocalScreenInfo
import club.anifox.android.core.uikit.util.clickableWithoutRipple
import club.anifox.android.core.uikit.util.rememberLazyGridState
import club.anifox.android.domain.model.anime.characters.AnimeCharactersLight
import club.anifox.android.feature.characters.model.state.CharactersUiState
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import kotlinx.coroutines.flow.Flow

@Composable
internal fun CharactersScreen(
    viewModel: CharactersViewModel = hiltViewModel(),
    url: String = "",
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
        charactersResults = charactersResults,
        onCharacterClick = onCharacterClick,
        onQueryChange = { search ->
            viewModel.search(search)
        },
        onTrailingIconClick = {
            viewModel.clearSearch()
        },
        onBackPressed = onBackPressed,
    )
}

@Composable
private fun CharactersUI(
    uiState: CharactersUiState,
    charactersResults: Flow<PagingData<AnimeCharactersLight>>,
    onCharacterClick: (String) -> Unit,
    onQueryChange: (String) -> Unit,
    onTrailingIconClick: () -> Unit,
    onBackPressed: () -> Unit,
) {
    var isSearchActive by rememberSaveable { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    val endIcons = listOf<@Composable () -> Unit> {
        AnifoxIconPrimary(
            modifier = Modifier
                .clickableWithoutRipple {
                    isSearchActive = !isSearchActive
                }
                .size(24.dp),
            imageVector = Outlined.Search,
            contentDescription = "search"
        )
    }

    Scaffold (
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopBarWithSearch(
                searchQuery = uiState.searchQuery,
                title = stringResource(R.string.feature_characters_top_bar_title),
                placeholder = stringResource(R.string.feature_characters_top_bar_placeholder),
                isSearchActive = isSearchActive,
                focusRequester = focusRequester,
                endIcons = endIcons,
                onSearchQueryChange = onQueryChange,
                onTrailingIconClick = onTrailingIconClick,
                onSearchClose = {
                    isSearchActive = false
                },
                onBackPressed = onBackPressed,
            )
        },
    ) { padding ->
        CharactersContentUI(
            modifier = Modifier.padding(padding),
            charactersResults = charactersResults,
            onCharacterClick = onCharacterClick,
        )
    }
}

@Composable
private fun CharactersContentUI(
    modifier: Modifier = Modifier,
    shimmer: Shimmer = rememberShimmer(ShimmerBounds.View),
    charactersResults: Flow<PagingData<AnimeCharactersLight>>,
    onCharacterClick: (String) -> Unit,
) {
    val items = charactersResults.collectAsLazyPagingItems()
    val lazyGridState = items.rememberLazyGridState()

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

    Box(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
    ) {
        when {
            items.loadState.append.endOfPaginationReached && items.itemCount == 0 -> {
                NoSearchResultsError()
            }
            else -> {
                LazyVerticalGrid(
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

                    when {
                        items.loadState.append is LoadState.Loading || items.loadState.refresh is LoadState.Loading -> {
                            showCardCharactersItemShimmer(
                                shimmerInstance = shimmer,
                                thumbnailHeight = CardCharactersItemDefaults.Height.Default,
                                thumbnailWidth = CardCharactersItemDefaults.Width.Default,
                            )
                        }

                        items.loadState.append is LoadState.Error -> {

                        }
                    }
                }

                KeyboardManager(lazyGridState)
            }
        }
    }
}