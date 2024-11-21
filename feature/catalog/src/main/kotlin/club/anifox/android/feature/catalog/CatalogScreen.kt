package club.anifox.android.feature.catalog

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import club.anifox.android.core.uikit.component.card.anime.CardAnimePortrait
import club.anifox.android.core.uikit.component.card.anime.CardAnimePortraitDefaults
import club.anifox.android.core.uikit.component.error.NoSearchResultsError
import club.anifox.android.core.uikit.component.grid.GridContentDefaults
import club.anifox.android.core.uikit.util.LocalScreenInfo
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.genre.AnimeGenre
import club.anifox.android.domain.model.anime.studio.AnimeStudio
import club.anifox.android.domain.model.anime.translations.AnimeTranslation
import club.anifox.android.domain.model.common.device.ScreenType
import club.anifox.android.domain.model.navigation.catalog.CatalogFilterParams
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.feature.catalog.composable.filter.FiltersBar
import club.anifox.android.feature.catalog.composable.top.CatalogTopBar
import club.anifox.android.feature.catalog.model.FilterType
import club.anifox.android.feature.catalog.model.state.CatalogState
import kotlinx.coroutines.flow.Flow

@Composable
internal fun CatalogScreen(
    viewModel: CatalogViewModel = hiltViewModel(),
    onBackPressed: () -> Boolean,
    onSearchClick: () -> Unit,
    onAnimeClick: (String) -> Unit,
    initialParams: CatalogFilterParams,
) {
    val catalogState by viewModel.catalogState.collectAsState()
    val items = viewModel.searchResults.collectAsLazyPagingItems()
    val loadState by viewModel.loadState.collectAsState()
    val animeYears by viewModel.animeYears.collectAsState()
    val animeGenres by viewModel.animeGenres.collectAsState()
    val animeStudios by viewModel.animeStudios.collectAsState()
    val animeTranslations by viewModel.animeTranslations.collectAsState()

    BackHandler {
        onBackPressed.invoke()
    }

    LaunchedEffect(initialParams) {
        viewModel.initializeParams(initialParams)
    }

    LaunchedEffect(items.loadState) {
        viewModel.updateLoadState(items.loadState)
    }

    LaunchedEffect(loadState) {
        val currentLoadState = loadState
        if (currentLoadState?.refresh is LoadState.NotLoading) {
            viewModel.updateLoadingState(false)
        }
    }

    CatalogUI(
        onBackPressed = onBackPressed,
        onSearchClick = onSearchClick,
        catalogState = catalogState,
        searchResults = viewModel.searchResults,
        onAnimeClick = onAnimeClick,
        animeGenres = animeGenres,
        animeYears = animeYears,
        animeStudios = animeStudios,
        animeTranslations = animeTranslations,
        updateFilter = { filterParams, filterType ->
            viewModel.updateFilter(filterParams, filterType)
        },
    )
}

@Composable
private fun CatalogUI(
    modifier: Modifier = Modifier,
    catalogState: CatalogState,
    onAnimeClick: (String) -> Unit,
    onBackPressed: () -> Boolean,
    onSearchClick: () -> Unit,
    searchResults: Flow<PagingData<AnimeLight>>,
    animeYears: StateListWrapper<Int>,
    animeGenres: StateListWrapper<AnimeGenre>,
    animeStudios: StateListWrapper<AnimeStudio>,
    animeTranslations: StateListWrapper<AnimeTranslation>,
    updateFilter: (CatalogFilterParams, FilterType) -> Unit,
) {
    val lazyGridState = rememberLazyGridState()
    val items = searchResults.collectAsLazyPagingItems()

    val screenInfo = LocalScreenInfo.current

    val (width, height) = when (screenInfo.screenType) {
        ScreenType.SMALL -> {
            Pair(
                CardAnimePortraitDefaults.Width.GridSmall,
                CardAnimePortraitDefaults.Height.GridSmall,
            )
        }
        ScreenType.DEFAULT -> {
            Pair(
                CardAnimePortraitDefaults.Width.GridMedium,
                CardAnimePortraitDefaults.Height.GridMedium,
            )
        }
        else -> {
            Pair(
                CardAnimePortraitDefaults.Width.GridLarge,
                CardAnimePortraitDefaults.Height.GridLarge,
            )
        }
    }

    val minColumnSize = (screenInfo.portraitWidthDp.dp / (if (screenInfo.portraitWidthDp.dp < 600.dp) 4 else 6)).coerceAtLeast(if(screenInfo.portraitWidthDp.dp < 600.dp) CardAnimePortraitDefaults.Width.Min else width )

    LaunchedEffect(catalogState) {
        if (!catalogState.isLoading) {
            items.refresh()
        }
    }

    Scaffold (
        topBar = {
            CatalogTopBar(
                onBackPressed = onBackPressed,
                onSearchClick = onSearchClick,
            )
        }
    ) { padding ->
        FiltersBar(
            modifier = Modifier
                .padding(padding),
            animeYears = animeYears,
            animeGenres = animeGenres,
            animeStudios = animeStudios,
            animeTranslations = animeTranslations,
            catalogState = catalogState,
            updateFilter = updateFilter,
        )
        Box(
            modifier = Modifier
                .padding(top = 40.dp)
                .padding(padding)
                .zIndex(-1f)
                .fillMaxSize(),
        ) {
            when {
                catalogState.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                items.itemCount == 0 && !catalogState.isLoading -> {
                    NoSearchResultsError()
                }
                else -> {
                    LazyVerticalGrid(
                        modifier = GridContentDefaults.Default.fillMaxSize(),
                        columns = GridCells.Adaptive(minSize = minColumnSize),
                        state = lazyGridState,
                        horizontalArrangement = CardAnimePortraitDefaults.HorizontalArrangement.Grid,
                        verticalArrangement = CardAnimePortraitDefaults.VerticalArrangement.Grid,
                    ) {
                        items(
                            count = items.itemCount,
                            key = items.itemKey { it.url }
                        ) { index ->
                            val item = items[index]
                            if (item != null) {
                                CardAnimePortrait(
                                    modifier = Modifier.width(width),
                                    data = item,
                                    onClick = { onAnimeClick.invoke(item.url) },
                                    thumbnailHeight = height,
                                    thumbnailWidth = width,
                                )
                            }
                        }

                        if(items.loadState.append is LoadState.Loading) {
                            item { CircularProgressIndicator() }
                        }
                    }
                }
            }
        }
    }
}
