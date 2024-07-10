package club.anifox.android.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import club.anifox.android.core.uikit.component.slider.simple.content.SliderContent
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.state.StateListWrapper

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    onAnimeClick: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    LaunchedEffect(viewModel) {
        viewModel.getPopularOngoingAnime(0,12)
        viewModel.getPopularAnime(0,12)
        viewModel.getFilmsAnime(0, 12)
    }

    HomeUI(
        modifier = modifier,
        onAnimeClick = onAnimeClick,
        onPopularOngoingAnime = viewModel.onPopularOngoingAnime.value,
        onPopularAnime = viewModel.onPopularAnime.value,
        filmsAnime = viewModel.filmsAnime.value,
    )
}

@Composable
internal fun HomeUI(
    modifier: Modifier = Modifier,
    lazyColumnState: LazyListState = rememberLazyListState(),
    onAnimeClick: (String) -> Unit,
    onPopularOngoingAnime: StateListWrapper<AnimeLight>,
    onPopularAnime: StateListWrapper<AnimeLight>,
    filmsAnime: StateListWrapper<AnimeLight>,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        state = lazyColumnState
    ) {
        item {
            SliderContent(headerTitle = stringResource(R.string.feature_home_section_header_title_ongoing_popular), contentState = onPopularOngoingAnime, onItemClick = onAnimeClick)
        }
        item {
            SliderContent(headerTitle = stringResource(R.string.feature_home_section_header_title_popular), contentState = onPopularAnime, onItemClick = onAnimeClick)
        }
        item {
            SliderContent(headerTitle = stringResource(R.string.feature_home_section_header_title_films), contentState = filmsAnime, onItemClick = onAnimeClick)
        }
    }
}
