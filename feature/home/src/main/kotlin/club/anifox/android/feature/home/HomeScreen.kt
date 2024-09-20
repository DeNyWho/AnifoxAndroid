package club.anifox.android.feature.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import club.anifox.android.core.uikit.component.slider.SliderContentDefaults
import club.anifox.android.core.uikit.component.slider.simple.content.SliderContent
import club.anifox.android.core.uikit.component.textfield.SearchField
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.genre.AnimeGenre
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.feature.home.composable.content.genre.GenreContent
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    onAnimeClick: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    onSearchClick: () -> Unit,
    onGenresClick: (String) -> Unit,
) {
    LaunchedEffect(viewModel) {
        viewModel.getPopularOngoingAnime(0,12)
        viewModel.getPopularAnime(0,12)
        viewModel.getAnimeGenres()
        viewModel.getFilmsAnime(0, 12)
    }

    HomeUI(
        modifier = modifier,
        onAnimeClick = onAnimeClick,
        onPopularOngoingAnime = viewModel.onPopularOngoingAnime.value,
        onPopularAnime = viewModel.onPopularAnime.value,
        filmsAnime = viewModel.filmsAnime.value,
        genresAnime = viewModel.genresAnime.value,
        onSearchClick = onSearchClick,
        onGenresClick = onGenresClick,
    )
}

@Composable
private fun HomeUI(
    modifier: Modifier = Modifier,
    onAnimeClick: (String) -> Unit,
    onSearchClick: () -> Unit,
    onGenresClick: (String) -> Unit,
    onPopularOngoingAnime: StateListWrapper<AnimeLight>,
    onPopularAnime: StateListWrapper<AnimeLight>,
    filmsAnime: StateListWrapper<AnimeLight>,
    genresAnime: StateListWrapper<AnimeGenre>,
) {
    val toolbarScaffoldState = rememberCollapsingToolbarScaffoldState()
    CollapsingToolbarScaffold(
        modifier = modifier
            .fillMaxSize(),
        state = toolbarScaffoldState,
        scrollStrategy = ScrollStrategy.EnterAlwaysCollapsed,
        toolbar = {
            SearchField(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .clickable {
                    onSearchClick.invoke()
                },
                placeHolder = stringResource(R.string.feature_home_search_placeholder),
                isEnabled = false,
            )
        },
    ) {
        HomeContent(
            modifier = modifier,
            onAnimeClick = onAnimeClick,
            onGenresClick = onGenresClick,
            onPopularOngoingAnime = onPopularOngoingAnime,
            onPopularAnime = onPopularAnime,
            filmsAnime = filmsAnime,
            genresAnime = genresAnime,
        )
    }
}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    lazyColumnState: LazyListState = rememberLazyListState(),
    onAnimeClick: (String) -> Unit,
    onGenresClick: (String) -> Unit,
    onPopularOngoingAnime: StateListWrapper<AnimeLight>,
    onPopularAnime: StateListWrapper<AnimeLight>,
    filmsAnime: StateListWrapper<AnimeLight>,
    genresAnime: StateListWrapper<AnimeGenre>,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        state = lazyColumnState,
    ) {
        item {
            SliderContent(
                headerTitle = stringResource(R.string.feature_home_section_header_title_ongoing_popular),
                headerModifier = SliderContentDefaults.Default,
                contentState = onPopularOngoingAnime,
                onItemClick = onAnimeClick,
            )
        }
        item {
            SliderContent(
                headerTitle = stringResource(R.string.feature_home_section_header_title_popular),
                headerModifier = SliderContentDefaults.Default,
                contentState = onPopularAnime,
                onItemClick = onAnimeClick,
            )
        }
        item {
            GenreContent(
                headerTitle = stringResource(R.string.feature_home_section_header_title_genres),
                headerModifier = SliderContentDefaults.Default,
                genresAnime = genresAnime,
                onItemClick = onGenresClick,
            )
        }
        item {
            SliderContent(
                headerTitle = stringResource(R.string.feature_home_section_header_title_films),
                headerModifier = SliderContentDefaults.Default,
                contentState = filmsAnime,
                onItemClick = onAnimeClick,
            )
        }
    }
}
