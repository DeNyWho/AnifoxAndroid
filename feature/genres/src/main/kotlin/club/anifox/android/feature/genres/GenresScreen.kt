package club.anifox.android.feature.genres

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import club.anifox.android.core.uikit.component.card.anime.CardAnimePortraitDefaults
import club.anifox.android.core.uikit.component.grid.GridComponentDefaults
import club.anifox.android.core.uikit.component.grid.simple.GridComponent
import club.anifox.android.core.uikit.component.topbar.SimpleTopBarCollapse
import club.anifox.android.core.uikit.util.LocalScreenInfo
import club.anifox.android.core.uikit.util.toolbarShadow
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.genre.AnimeGenre
import club.anifox.android.domain.model.common.device.ScreenType
import club.anifox.android.feature.genres.model.state.GenreUiState
import kotlinx.coroutines.flow.Flow
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
internal fun GenresScreen(
    viewModel: GenresViewModel = hiltViewModel(),
    genre: AnimeGenre,
    onAnimeClick: (String) -> Unit,
    onBackPressed: () -> Unit,
) {
    val searchResults = viewModel.searchResults
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        if (!uiState.isInitialized) {
            viewModel.initializeFilter(genre)
        }
    }

    GenresUI(
        uiState = uiState,
        searchResults = searchResults,
        onAnimeClick = onAnimeClick,
        onBackPressed = onBackPressed,
    )
}

@Composable
private fun GenresUI(
    uiState: GenreUiState,
    onAnimeClick: (String) -> Unit,
    onBackPressed: () -> Unit,
    searchResults: Flow<PagingData<AnimeLight>>,
) {
    val toolbarScaffoldState = rememberCollapsingToolbarScaffoldState()

    CollapsingToolbarScaffold(
        modifier = Modifier.fillMaxSize(),
        state = toolbarScaffoldState,
        scrollStrategy = ScrollStrategy.EnterAlwaysCollapsed,
        toolbar = {
            SimpleTopBarCollapse(
                title = uiState.selectedGenre.name,
                titleStyle = MaterialTheme.typography.titleLarge,
                titleAlign = TextAlign.Center,
                toolbarScaffoldState = toolbarScaffoldState,
                onBackPressed = onBackPressed,
            )
        },
        toolbarModifier = Modifier.toolbarShadow(
            shadowElevation = 4.dp,
            tonalElevation = 4.dp,
            shape = RectangleShape,
        ),
        body = {
            GenresContent(
                searchResults = searchResults,
                onAnimeClick = onAnimeClick,
            )
        }
    )
}

@Composable
private fun GenresContent(
    searchResults: Flow<PagingData<AnimeLight>>,
    onAnimeClick: (String) -> Unit,
) {
    val lazyGridState = rememberLazyGridState()
    val items = searchResults.collectAsLazyPagingItems()

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

    val minColumnSize = (screenInfo.portraitWidthDp.dp / (if (screenInfo.portraitWidthDp.dp < 600.dp) 4 else 6)).coerceAtLeast(if(screenInfo.portraitWidthDp.dp < 600.dp) CardAnimePortraitDefaults.Width.Min else thumbnailWidth )

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