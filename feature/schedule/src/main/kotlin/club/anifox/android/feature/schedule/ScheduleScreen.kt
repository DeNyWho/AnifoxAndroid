package club.anifox.android.feature.schedule

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import club.anifox.android.core.uikit.component.grid.GridContentDefaults
import club.anifox.android.core.uikit.component.tab.simple.AnifoxScrollableTabRow
import club.anifox.android.core.uikit.util.LocalScreenInfo
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.common.device.ScreenType
import club.anifox.android.domain.model.common.enum.WeekDay
import club.anifox.android.feature.schedule.composable.item.AnimeScheduleItem
import club.anifox.android.feature.schedule.composable.item.AnimeScheduleItemDefaults
import club.anifox.android.feature.schedule.model.state.ScheduleUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
internal fun ScheduleScreen(
    viewModel: ScheduleViewModel = hiltViewModel(),
    onAnimeClick: (String) -> Unit,
) {
    val uiState by viewModel.scheduleUiState.collectAsState()
    val daysOfWeek = WeekDay.entries.toTypedArray()
    val localWeek = LocalDate.now().dayOfWeek.value - 1
    val pagerState = rememberPagerState(initialPage = localWeek, pageCount = { daysOfWeek.size })

    // Create a map to store all LazyPagingItems
    val scheduleResults = remember {
        daysOfWeek.associateWith { dayOfWeek ->
            viewModel.getScheduleForDay(dayOfWeek)
        }
    }

    // Preload current and adjacent pages
    LaunchedEffect(pagerState.currentPage) {
        // Preload next page if exists
        val nextPageIndex = (pagerState.currentPage + 1) % daysOfWeek.size
        val nextDay = daysOfWeek[nextPageIndex]
        viewModel.prefetchScheduleForDay(nextDay)

        // Preload previous page if exists
        val prevPageIndex = (pagerState.currentPage - 1).coerceAtLeast(0)
        val prevDay = daysOfWeek[prevPageIndex]
        viewModel.prefetchScheduleForDay(prevDay)
    }

    ScheduleUI(
        uiState = uiState,
        pagerState = pagerState,
        daysOfWeek = daysOfWeek,
        scheduleResults = scheduleResults,
        onAnimeClick = onAnimeClick,
    )
}

@Composable
private fun ScheduleUI(
    uiState: ScheduleUiState,
    pagerState: PagerState,
    daysOfWeek: Array<WeekDay>,
    scheduleResults: Map<WeekDay, Flow<PagingData<AnimeLight>>>,
    onAnimeClick: (String) -> Unit,
) {
    val scope = rememberCoroutineScope()

    Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            Column {
                AnifoxScrollableTabRow(
                    modifier = Modifier
                        .padding(bottom = 20.dp),
                    itemModifier = Modifier.height(48.dp),
                    items = daysOfWeek.toList(),
                    selectedIndex = pagerState.currentPage,
                    onTabSelected = { index ->
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    itemToText = { it.russianName },
                    selectedColor = MaterialTheme.colorScheme.primary,
                    unSelectedColor = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                Box(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    HorizontalPager(
                        state = pagerState,
                        pageSize = PageSize.Fill,
                        modifier = Modifier.fillMaxSize(),
                        beyondViewportPageCount = 1 // Keep one page on each side loaded
                    ) { page ->
                        val currentDayOfWeek = daysOfWeek[page]
                        val currentDayFlow = scheduleResults[currentDayOfWeek]

                        if (currentDayFlow != null) {
                            key(currentDayOfWeek) {
                                val currentDayItems = currentDayFlow.collectAsLazyPagingItems()

                                // Проверяем состояния загрузки
                                val isLoading = uiState.isLoading ||
                                        currentDayItems.loadState.refresh is LoadState.Loading ||
                                        currentDayItems.loadState.append is LoadState.Loading

                                Box(modifier = Modifier.fillMaxSize()) {
                                    if (isLoading) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.align(Alignment.Center)
                                        )
                                    } else {
                                        ScheduleContent(
                                            scheduleResults = currentDayItems,
                                            onAnimeClick = onAnimeClick,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ScheduleContent(
    modifier: Modifier = Modifier,
    scheduleResults: LazyPagingItems<AnimeLight>,
    onAnimeClick: (String) -> Unit,
) {
    val lazyGridState = rememberLazyGridState()

    val screenInfo = LocalScreenInfo.current
    val (width, height) = when (screenInfo.screenType) {
        ScreenType.SMALL -> {
            Pair(
                AnimeScheduleItemDefaults.Width.Small,
                AnimeScheduleItemDefaults.Height.Small,
            )
        }
        ScreenType.DEFAULT -> {
            Pair(
                AnimeScheduleItemDefaults.Width.Medium,
                AnimeScheduleItemDefaults.Height.Medium,
            )
        }
        else -> {
            Pair(
                AnimeScheduleItemDefaults.Width.Large,
                AnimeScheduleItemDefaults.Height.Large,
            )
        }
    }
    val minColumnSize = (screenInfo.portraitWidthDp.dp / 4).coerceAtLeast(300.dp)

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyVerticalGrid(
            modifier = GridContentDefaults.Default
                .fillMaxSize()
                .animateContentSize(),
            columns = GridCells.Adaptive(minSize = minColumnSize),
            state = lazyGridState,
            horizontalArrangement = AnimeScheduleItemDefaults.HorizontalArrangement.Grid,
            verticalArrangement = AnimeScheduleItemDefaults.VerticalArrangement.Grid,
        ) {
            when {
                scheduleResults.loadState.refresh is LoadState.Loading -> {
                    item {
                        // Loading state
                    }
                }
                scheduleResults.itemCount == 0 -> {
                    item {
                        // Empty state
                    }
                }
                else -> {
                    items(
                        count = scheduleResults.itemCount,
                        key = scheduleResults.itemKey { it.url }
                    ) { index ->
                        val item = scheduleResults[index]
                        if (item != null) {
                            AnimeScheduleItem(
                                thumbnailWidth = width,
                                thumbnailHeight = height,
                                data = item,
                                onClick = onAnimeClick,
                            )
                        }
                    }

                    if (scheduleResults.loadState.append is LoadState.Loading) {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
