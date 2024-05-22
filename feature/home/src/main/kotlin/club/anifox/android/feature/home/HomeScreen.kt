package club.anifox.android.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import club.anifox.android.commonui.component.slider.content.SliderContent
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.state.StateListWrapper
import java.time.LocalDateTime

@Composable
internal fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    LaunchedEffect(viewModel) {
        viewModel.getPopularOngoingAnime(0,12)
    }

    HomeScreen(
        modifier = modifier,
        onPopularOngoingAnime = viewModel.onPopularOngoingAnime.value,

    )
}

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    lazyColumnState: LazyListState = rememberLazyListState(),
    onPopularOngoingAnime: StateListWrapper<AnimeLight>,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        LazyColumn(
            state = lazyColumnState
        ) {
            item {
                SliderContent(headerTitle = stringResource(R.string.feature_home_section_header_title_ongoing_popular), contentState = onPopularOngoingAnime, onItemClick = {})
            }
        }
    }
}