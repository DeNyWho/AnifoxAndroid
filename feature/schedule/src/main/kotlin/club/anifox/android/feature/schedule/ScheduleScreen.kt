package club.anifox.android.feature.schedule

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import club.anifox.android.core.uikit.component.card.anime.CardAnimePortrait
import club.anifox.android.core.uikit.component.grid.GridContentDefaults
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.common.enum.WeekDay
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Composable
internal fun ScheduleScreen(
    viewModel: ScheduleViewModel = hiltViewModel(),
) {
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
        pagerState = pagerState,
        daysOfWeek = daysOfWeek,
        scheduleResults = scheduleResults,
    )
}

@Composable
private fun ScheduleUI(
    pagerState: PagerState,
    daysOfWeek: Array<WeekDay>,
    scheduleResults: Map<WeekDay, Flow<PagingData<AnimeLight>>>
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
        Box(modifier = Modifier.padding(padding)) {
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

                        Box(modifier = Modifier.fillMaxSize()) {
                            ScheduleContent(
                                scheduleResults = currentDayItems,
                            )
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
) {
    val lazyGridState = rememberLazyGridState()

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyVerticalGrid(
            modifier = GridContentDefaults.Default
                .fillMaxSize()
                .animateContentSize(),
            columns = GridCells.Adaptive(minSize = 160.dp),
            state = lazyGridState,
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
                            Modifier
                                .padding(8.dp)
                            CardAnimePortrait(
                                modifier = Modifier.animateItem(
                                    fadeInSpec = null,
                                    fadeOutSpec = null
                                ),
                                data = item,
                                onClick = { /* Navigation */ }
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
