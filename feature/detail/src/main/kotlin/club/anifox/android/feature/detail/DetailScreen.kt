package club.anifox.android.feature.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import club.anifox.android.commonui.component.button.AnifoxButtonPrimary
import club.anifox.android.commonui.component.icon.AnifoxIconPrimary
import club.anifox.android.commonui.component.slider.content.SliderContent
import club.anifox.android.commonui.theme.AnifoxTheme
import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.domain.state.StateWrapper
import club.anifox.android.feature.detail.components.description.DescriptionContent
import club.anifox.android.feature.detail.components.title.TitleInformationContent
import club.anifox.android.feature.detail.components.top.ContentDetailsScreenToolbar
import club.anifox.android.feature.detail.param.DetailContentPreviewParam
import club.anifox.android.feature.detail.param.DetailContentProvider
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel(),
    url: String = "",
    onBackPressed: () -> Boolean,
    onAnimeClick: (String) -> Unit,
) {
    LaunchedEffect(viewModel) {
        viewModel.getDetailAnime(url)
        viewModel.getSimilarAnime(url)
        viewModel.getRelatedAnime(url)
        viewModel.getScreenshotAnime(url)
    }

    DetailUI(
        detailAnimeState = viewModel.detailAnime.value,
        similarAnimeState = viewModel.similarAnime.value,
        onBackPressed = onBackPressed,
        onAnimeClick = onAnimeClick,
    )
}

@Composable
internal fun DetailUI(
    modifier: Modifier = Modifier,
    detailAnimeState: StateWrapper<AnimeDetail>,
    similarAnimeState: StateListWrapper<AnimeLight>,
    onBackPressed: () -> Boolean,
    onAnimeClick: (String) -> Unit,
) {
    val toolbarScaffoldState = rememberCollapsingToolbarScaffoldState()

    CollapsingToolbarScaffold(
        modifier = Modifier
            .fillMaxSize(),
        state = toolbarScaffoldState,
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        toolbar = {
            ContentDetailsScreenToolbar(
                contentDetailState = detailAnimeState,
                toolbarScaffoldState = toolbarScaffoldState,
                navigateBack = onBackPressed,
            )
        },
        body = {
            DetailContentUI(
                detailAnimeState = detailAnimeState,
                similarAnimeState = similarAnimeState,
                onAnimeClick = onAnimeClick,
            )
        }
    )
}

@Composable
internal fun DetailContentUI(
    detailAnimeState: StateWrapper<AnimeDetail>,
    similarAnimeState: StateListWrapper<AnimeLight>,
    onAnimeClick: (String) -> Unit,
    lazyColumnState: LazyListState = rememberLazyListState(),
) {
    var isDescriptionExpanded by remember { mutableStateOf(false) }

    Column (
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
    ) {
        LazyColumn(
            state = lazyColumnState,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                TitleInformationContent(detailAnimeState)
            }
            item {
                AnifoxButtonPrimary(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.small,
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 2.dp
                    ),
                    paddingValues = PaddingValues(0.dp),
                ) {
                    AnifoxIconPrimary(
                        imageVector = Filled.PlayArrow,
                        contentDescription = stringResource(R.string.feature_detail_content_description_button_watch),
                        modifier = Modifier.size(40.dp),
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = stringResource(R.string.feature_detail_button_watch_title),
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
            }
            item {
                DescriptionContent(
                    detailAnimeState = detailAnimeState,
                    isExpanded = isDescriptionExpanded,
                    onExpandedChanged = { isDescriptionExpanded = it }
                )
            }
            item {
                SliderContent(
                    headerTitle = stringResource(R.string.feature_detail_section_header_title_similar),
                    contentState = similarAnimeState,
                    onItemClick = onAnimeClick,
                    contentPadding = PaddingValues()
                )
            }
        }
    }
}

@PreviewScreenSizes
@Composable
private fun PreviewScrollableHorizontalContentDefault(
    @PreviewParameter(DetailContentProvider::class) param: DetailContentPreviewParam,
) {
    AnifoxTheme {
        Column (
            Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            DetailUI (
                modifier = param.modifier,
                detailAnimeState = param.detailAnime,
                similarAnimeState = param.similarAnime,
                onBackPressed = param.onBackPressed,
                onAnimeClick = param.onAnimeClick,
            )
        }
    }
}
