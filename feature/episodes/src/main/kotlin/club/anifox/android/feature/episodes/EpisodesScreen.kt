package club.anifox.android.feature.episodes

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.SwapVert
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import club.anifox.android.core.uikit.component.error.NoSearchResultsError
import club.anifox.android.core.uikit.component.icon.AnifoxIconPrimary
import club.anifox.android.core.uikit.util.LocalScreenInfo
import club.anifox.android.core.uikit.util.clickableWithoutRipple
import club.anifox.android.core.uikit.util.rememberLazyGridState
import club.anifox.android.domain.model.anime.episodes.AnimeEpisodesLight
import club.anifox.android.domain.model.common.device.ScreenType
import club.anifox.android.feature.episodes.components.item.CardEpisodeGridComponentItem
import club.anifox.android.feature.episodes.components.item.CardEpisodeGridComponentItemDefaults
import club.anifox.android.feature.episodes.components.item.showCardEpisodeGridComponentItemShimmer
import club.anifox.android.feature.episodes.components.top.EpisodesTopBarComponent
import club.anifox.android.feature.episodes.model.state.EpisodesUiState
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import kotlinx.coroutines.flow.Flow

@Composable
internal fun EpisodesScreen(
    viewModel: EpisodesViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
    url: String,
    translationId: Int,
    onEpisodeClick: (String, Boolean?) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        if (!uiState.isInitialized) {
            viewModel.initializeFilter(url, translationId)
        }
    }

    if (uiState.searchQuery.isNotEmpty()) {
        BackHandler {
            viewModel.clearSearch()
        }
    }

    EpisodesUI(
        onBackPressed = onBackPressed,
        uiState = uiState,
        episodesResults = viewModel.episodesResults,
        onEpisodeClick = onEpisodeClick,
        onSortUpdate = {
            viewModel.updateSort()
        },
        onQueryChange = { search ->
            viewModel.search(search)
        },
        onTrailingIconClick = {
            viewModel.clearSearch()
        },
    )
}

@Composable
private fun EpisodesUI(
    onBackPressed: () -> Unit,
    uiState: EpisodesUiState,
    episodesResults: Flow<PagingData<AnimeEpisodesLight>>,
    onEpisodeClick: (String, Boolean?) -> Unit,
    onSortUpdate: () -> Unit,
    onQueryChange: (String) -> Unit,
    onTrailingIconClick: () -> Unit,
) {
    var isSearchActive by rememberSaveable { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    val endIcons = listOf<@Composable () -> Unit>(
        {
            AnifoxIconPrimary(
                modifier = Modifier
                    .clickableWithoutRipple {
                        onSortUpdate.invoke()
                    }
                    .size(24.dp),
                imageVector = Outlined.SwapVert,
                contentDescription = "Swap",
            )
        },
        {
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
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            EpisodesTopBarComponent(
                searchQuery = uiState.searchQuery,
                title = stringResource(R.string.feature_episodes_top_bar_title),
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
        EpisodesContentUI(
            modifier = Modifier
                .padding(padding),
            uiState = uiState,
            episodesResults = episodesResults,
            onEpisodeClick = onEpisodeClick,
        )
    }
}

@Composable
private fun EpisodesContentUI(
    modifier: Modifier,
    uiState: EpisodesUiState,
    episodesResults: Flow<PagingData<AnimeEpisodesLight>>,
    onEpisodeClick: (String, Boolean?) -> Unit,
    shimmerInstance: Shimmer = rememberShimmer(ShimmerBounds.View),
) {
    val items = episodesResults.collectAsLazyPagingItems()
    val lazyGridState = items.rememberLazyGridState()

    val screenInfo = LocalScreenInfo.current
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    val columns = when {
        screenInfo.screenType == ScreenType.EXTRA_LARGE && isPortrait -> 2
        screenInfo.screenType == ScreenType.EXTRA_LARGE && !isPortrait -> 3
        !isPortrait -> 2
        else -> 1
    }

    Box(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
    ) {
        if (items.loadState.append.endOfPaginationReached && items.itemCount == 0) {
            NoSearchResultsError()
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                state = lazyGridState,
                horizontalArrangement = CardEpisodeGridComponentItemDefaults.HorizontalArrangement.Grid,
                verticalArrangement = CardEpisodeGridComponentItemDefaults.VerticalArrangement.Grid,
            ) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Spacer(modifier = Modifier)
                }

                items(
                    count = items.itemCount,
                    key = items.itemKey { it.number }
                ) { index ->
                    val item = items[index]
                    if (item != null) {
                        CardEpisodeGridComponentItem(
                            currentTranslationId = uiState.translationId,
                            data = item,
                            onClick = { url ->
                                // TODO Add a player selection dialog
                                onEpisodeClick.invoke(url, true)
                            },
                        )
                    }
                }

                when {
                    items.loadState.append is LoadState.Loading -> {
                        showCardEpisodeGridComponentItemShimmer(shimmerInstance)
                    }

                    items.loadState.refresh is LoadState.Loading -> {
                        showCardEpisodeGridComponentItemShimmer(shimmerInstance)
                    }

                    items.loadState.append is LoadState.Error -> {
                    }
                }
            }
        }
    }
}
