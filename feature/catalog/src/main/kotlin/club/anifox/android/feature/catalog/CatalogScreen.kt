package club.anifox.android.feature.catalog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import club.anifox.android.core.uikit.component.card.anime.CardAnimePortraitDefaults
import club.anifox.android.core.uikit.component.grid.GridComponentDefaults
import club.anifox.android.core.uikit.component.grid.simple.GridComponent
import club.anifox.android.core.uikit.util.LocalScreenInfo
import club.anifox.android.core.uikit.util.rememberLazyGridState
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.genre.AnimeGenre
import club.anifox.android.domain.model.anime.studio.AnimeStudio
import club.anifox.android.domain.model.anime.translations.AnimeTranslation
import club.anifox.android.domain.model.common.device.ScreenType
import club.anifox.android.domain.model.navigation.catalog.CatalogFilterParams
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.feature.catalog.components.filter.FiltersBarComponent
import club.anifox.android.feature.catalog.components.top.CatalogTopBarComponent
import club.anifox.android.feature.catalog.model.FilterType
import club.anifox.android.feature.catalog.model.state.CatalogUiState
import kotlinx.coroutines.flow.Flow

@Composable
internal fun CatalogScreen(
    viewModel: CatalogViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
    onSearchClick: () -> Unit,
    onAnimeClick: (String) -> Unit,
    initialParams: CatalogFilterParams,
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchResults = viewModel.searchResults
    val animeYears by viewModel.animeYears.collectAsState()
    val animeGenres by viewModel.animeGenres.collectAsState()
    val animeStudios by viewModel.animeStudios.collectAsState()
    val animeTranslations by viewModel.animeTranslations.collectAsState()

    LaunchedEffect(Unit) {
        if (!uiState.isInitialized) {
            viewModel.initializeFilters(initialParams)
        }
    }

    CatalogUI(
        onBackPressed = onBackPressed,
        onSearchClick = onSearchClick,
        uiState = uiState,
        searchResults = searchResults,
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
    uiState: CatalogUiState,
    onAnimeClick: (String) -> Unit,
    onBackPressed: () -> Unit,
    onSearchClick: () -> Unit,
    searchResults: Flow<PagingData<AnimeLight>>,
    animeYears: StateListWrapper<Int>,
    animeGenres: StateListWrapper<AnimeGenre>,
    animeStudios: StateListWrapper<AnimeStudio>,
    animeTranslations: StateListWrapper<AnimeTranslation>,
    updateFilter: (CatalogFilterParams, FilterType) -> Unit,
) {
    val items = searchResults.collectAsLazyPagingItems()
    val lazyGridState = items.rememberLazyGridState()

    val previousFilters = remember {
        mutableStateOf(
            CatalogFilterParams(
                genres = uiState.selectedGenres,
                status = uiState.selectedStatus,
                type = uiState.selectedType,
                years = uiState.selectedYears,
                season = uiState.selectedSeason,
                studios = uiState.selectedStudios,
                translation = uiState.selectedTranslation,
                order = uiState.selectedOrder,
                sort = uiState.selectedSort,
            )
        )
    }

    LaunchedEffect(
        uiState.selectedGenres,
        uiState.selectedStatus,
        uiState.selectedType,
        uiState.selectedYears,
        uiState.selectedSeason,
        uiState.selectedStudios,
        uiState.selectedTranslation,
        uiState.selectedOrder,
        uiState.selectedSort,
    ) {
        val currentFilters = CatalogFilterParams(
            genres = uiState.selectedGenres,
            status = uiState.selectedStatus,
            type = uiState.selectedType,
            years = uiState.selectedYears,
            season = uiState.selectedSeason,
            studios = uiState.selectedStudios,
            translation = uiState.selectedTranslation,
            order = uiState.selectedOrder,
            sort = uiState.selectedSort,
        )

        if (previousFilters.value != currentFilters) {
            lazyGridState.scrollToItem(0)
            previousFilters.value = currentFilters
        }
    }

    val screenInfo = LocalScreenInfo.current

    val (thumbnailWidth, thumbnailHeight) = when (screenInfo.screenType) {
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

    val minColumnSize =
        (screenInfo.portraitWidthDp.dp / (if (screenInfo.portraitWidthDp.dp < 600.dp) 4 else 6)).coerceAtLeast(
            if (screenInfo.portraitWidthDp.dp < 600.dp) CardAnimePortraitDefaults.Width.Min else thumbnailWidth
        )

    Scaffold(
        topBar = {
            CatalogTopBarComponent(
                onBackPressed = onBackPressed,
                onSearchClick = onSearchClick,
            )
        }
    ) { padding ->
        FiltersBarComponent(
            modifier = Modifier
                .padding(padding),
            animeYears = animeYears,
            animeGenres = animeGenres,
            animeStudios = animeStudios,
            animeTranslations = animeTranslations,
            uiState = uiState,
            updateFilter = updateFilter,
        )

        Box(
            modifier = Modifier
                .padding(top = 40.dp)
                .padding(padding)
                .zIndex(-1f)
                .fillMaxSize(),
        ) {
            GridComponent(
                modifier = GridComponentDefaults.Default.fillMaxSize(),
                thumbnailHeight = thumbnailHeight,
                thumbnailWidth = thumbnailWidth,
                contentState = items,
                horizontalContentArrangement = CardAnimePortraitDefaults.HorizontalArrangement.Grid,
                verticalContentArrangement = CardAnimePortraitDefaults.VerticalArrangement.Grid,
                onItemClick = onAnimeClick,
                minColumnSize = minColumnSize,
                state = lazyGridState,
            )
        }
    }
}
