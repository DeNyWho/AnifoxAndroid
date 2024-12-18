package club.anifox.android.feature.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import club.anifox.android.core.uikit.component.slider.SliderContentDefaults
import club.anifox.android.core.uikit.component.slider.simple.content.SliderContent
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.enum.AnimeOrder
import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeSort
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType
import club.anifox.android.domain.model.anime.genre.AnimeGenre
import club.anifox.android.domain.model.navigation.catalog.CatalogFilterParams
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.feature.home.composable.content.genre.GenreContent
import club.anifox.android.feature.home.composable.top.ContentHomeScreenToolbar
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import java.time.LocalDate

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    onAnimeClick: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    onSearchClick: () -> Unit,
    onGenresClick: (String) -> Unit,
    onCatalogClick: (CatalogFilterParams) -> Unit,
) {
    LaunchedEffect(viewModel) {
        viewModel.getAnimeOfSeason(0,12)
        viewModel.getPopularAnime(0,12)
        viewModel.getUpdatedAnime(0,12)
        viewModel.getAnimeGenres()
        viewModel.getFilmsAnime(0, 12)
    }

    HomeUI(
        modifier = modifier,
        onAnimeClick = onAnimeClick,
        animeOfSeason = viewModel.animeOfSeason.value,
        onPopularAnime = viewModel.onPopularAnime.value,
        onUpdatedAnime = viewModel.onUpdatedAnime.value,
        filmsAnime = viewModel.filmsAnime.value,
        genresAnime = viewModel.genresAnime.value,
        onSearchClick = onSearchClick,
        onGenresClick = onGenresClick,
        onCatalogClick = onCatalogClick,
    )
}

@Composable
private fun HomeUI(
    modifier: Modifier = Modifier,
    onAnimeClick: (String) -> Unit,
    onSearchClick: () -> Unit,
    onGenresClick: (String) -> Unit,
    onCatalogClick: (CatalogFilterParams) -> Unit,
    animeOfSeason: StateListWrapper<AnimeLight>,
    onPopularAnime: StateListWrapper<AnimeLight>,
    onUpdatedAnime: StateListWrapper<AnimeLight>,
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
            ContentHomeScreenToolbar(
                toolbarScaffoldState = toolbarScaffoldState,
                onSearchClick = onSearchClick,
                onHistoryClick = { },
                onCatalogClick = {
                    onCatalogClick(
                        CatalogFilterParams()
                    )
                },
            )
        },
    ) {
        HomeContent(
            modifier = modifier,
            onAnimeClick = onAnimeClick,
            onGenresClick = onGenresClick,
            onCatalogClick = onCatalogClick,
            animeOfSeason = animeOfSeason,
            onPopularAnime = onPopularAnime,
            onUpdatedAnime = onUpdatedAnime,
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
    onCatalogClick: (CatalogFilterParams) -> Unit,
    animeOfSeason: StateListWrapper<AnimeLight>,
    onPopularAnime: StateListWrapper<AnimeLight>,
    onUpdatedAnime: StateListWrapper<AnimeLight>,
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
                headerTitle = stringResource(R.string.feature_home_section_header_title_anime_of_season),
                headerModifier = SliderContentDefaults.Default,
                contentState = animeOfSeason,
                onItemClick = onAnimeClick,
                isMoreVisible = true,
                onMoreClick = {
                    onCatalogClick(
                        CatalogFilterParams(
                            genres = null,
                            status = AnimeStatus.Ongoing,
                            season = AnimeSeason.fromMonth(LocalDate.now().month.value),
                            years = listOf(LocalDate.now().year),
                            order = AnimeOrder.Rating,
                            sort = AnimeSort.Desc,
                        )
                    )
                },
            )
        }
        item {
            SliderContent(
                headerTitle = stringResource(R.string.feature_home_section_header_title_popular),
                headerModifier = SliderContentDefaults.Default,
                contentState = onPopularAnime,
                onItemClick = onAnimeClick,
                isMoreVisible = true,
                onMoreClick = {
                    onCatalogClick(
                        CatalogFilterParams(
                            order = AnimeOrder.Rating,
                            sort = AnimeSort.Desc,
                        )
                    )
                },
            )
        }
        item {
            SliderContent(
                headerTitle = stringResource(R.string.feature_home_section_header_title_updated),
                headerModifier = SliderContentDefaults.Default,
                contentState = onUpdatedAnime,
                onItemClick = onAnimeClick,
                isMoreVisible = true,
                onMoreClick = {
                    onCatalogClick(
                        CatalogFilterParams(
                            order = AnimeOrder.Update,
                            sort = AnimeSort.Desc,
                        )
                    )
                },
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
                isMoreVisible = true,
                onMoreClick = {
                    onCatalogClick(CatalogFilterParams(genres = null, type = AnimeType.Movie))
                },
            )
        }
    }
}
