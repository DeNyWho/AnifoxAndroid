package club.anifox.android.feature.detail

import androidx.compose.foundation.layout.Arrangement
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
import club.anifox.android.core.uikit.component.button.AnifoxButtonPrimary
import club.anifox.android.core.uikit.component.icon.AnifoxIconOnPrimary
import club.anifox.android.core.uikit.component.progress.CircularProgress
import club.anifox.android.core.uikit.component.slider.SliderContentDefaults
import club.anifox.android.core.uikit.component.slider.screenshots.content.SliderScreenshotsContent
import club.anifox.android.core.uikit.component.slider.simple.content.SliderContent
import club.anifox.android.core.uikit.component.slider.video.content.SliderVideoContent
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.core.uikit.util.clickableWithoutRipple
import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.related.AnimeRelatedLight
import club.anifox.android.domain.model.anime.videos.AnimeVideosLight
import club.anifox.android.domain.model.navigation.catalog.CatalogFilterParams
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.domain.state.StateWrapper
import club.anifox.android.feature.detail.components.description.DescriptionContent
import club.anifox.android.feature.detail.components.genres.GenresContent
import club.anifox.android.feature.detail.components.information.InformationComponent
import club.anifox.android.feature.detail.components.related.RelationContent
import club.anifox.android.feature.detail.components.studios.StudiosContent
import club.anifox.android.feature.detail.components.title.TitleInformationContent
import club.anifox.android.feature.detail.components.top.ContentDetailsScreenToolbar
import club.anifox.android.feature.detail.param.DetailContentPreviewParam
import club.anifox.android.feature.detail.param.DetailContentProvider
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

/*
    TODO: redo the information section, implement an adaptive option
 */

@Composable
internal fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    url: String = "",
    onBackPressed: () -> Boolean,
    onWatchClick: (String) -> Unit,
    onAnimeClick: (String) -> Unit,
    onMoreScreenshotClick: (String, String) -> Unit,
    onMoreVideoClick: (String, String) -> Unit,
    onCatalogClick: (CatalogFilterParams) -> Unit,
) {
    LaunchedEffect(viewModel) {
        viewModel.getDetailAnime(url)
        viewModel.getScreenshotAnime(url)
        viewModel.getVideosAnime(url)
        viewModel.getRelatedAnime(url)
        viewModel.getSimilarAnime(url)
    }

    DetailUI(
        url = url,
        detailAnimeState = viewModel.detailAnime.value,
        screenshotAnimeState = viewModel.screenshotsAnime.value,
        videosAnimeState = viewModel.videosAnime.value,
        relationAnimeState = viewModel.relatedAnime.value,
        similarAnimeState = viewModel.similarAnime.value,
        onBackPressed = onBackPressed,
        onWatchClick = onWatchClick,
        onAnimeClick = onAnimeClick,
        onMoreScreenshotClick = { title ->
            onMoreScreenshotClick(url, title)
        },
        onMoreVideoClick = { title ->
            onMoreVideoClick(url, title)
        },
        onCatalogClick = onCatalogClick,
        onVideoClick = { youtubeUrl ->
            viewModel.openYoutube(youtubeUrl)
        },
    )
}

@Composable
internal fun DetailUI(
    modifier: Modifier = Modifier,
    url: String,
    detailAnimeState: StateWrapper<AnimeDetail>,
    screenshotAnimeState: StateListWrapper<String>,
    videosAnimeState: StateListWrapper<AnimeVideosLight>,
    relationAnimeState: StateListWrapper<AnimeRelatedLight>,
    similarAnimeState: StateListWrapper<AnimeLight>,
    onBackPressed: () -> Boolean,
    onWatchClick: (String) -> Unit,
    onAnimeClick: (String) -> Unit,
    onMoreScreenshotClick: (String) -> Unit,
    onMoreVideoClick: (String) -> Unit,
    onCatalogClick: (CatalogFilterParams) -> Unit,
    onVideoClick: (String) -> Unit,
) {
    if(detailAnimeState.isLoading) {
        CircularProgress()
    } else {
        val toolbarScaffoldState = rememberCollapsingToolbarScaffoldState()

        CollapsingToolbarScaffold(
            modifier = modifier
                .fillMaxSize(),
            state = toolbarScaffoldState,
            scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
            toolbar = {
                ContentDetailsScreenToolbar(
                    contentDetailState = detailAnimeState,
                    toolbarScaffoldState = toolbarScaffoldState,
                    onBackPressed = onBackPressed,
                )
            },
            body = {
                DetailContentUI(
                    url = url,
                    detailAnimeState = detailAnimeState,
                    screenshotAnimeState = screenshotAnimeState,
                    videosAnimeState = videosAnimeState,
                    relationAnimeState = relationAnimeState,
                    similarAnimeState = similarAnimeState,
                    onWatchClick = onWatchClick,
                    onAnimeClick = onAnimeClick,
                    onMoreScreenshotClick = onMoreScreenshotClick,
                    onMoreVideoClick = onMoreVideoClick,
                    onCatalogClick = onCatalogClick,
                    onVideoClick = onVideoClick,
                )
            }
        )
    }
}

@Composable
internal fun DetailContentUI(
    url: String,
    detailAnimeState: StateWrapper<AnimeDetail>,
    screenshotAnimeState: StateListWrapper<String>,
    videosAnimeState: StateListWrapper<AnimeVideosLight>,
    relationAnimeState: StateListWrapper<AnimeRelatedLight>,
    similarAnimeState: StateListWrapper<AnimeLight>,
    onWatchClick: (String) -> Unit,
    onAnimeClick: (String) -> Unit,
    onMoreScreenshotClick: (String) -> Unit,
    onMoreVideoClick: (String) -> Unit,
    onCatalogClick: (CatalogFilterParams) -> Unit,
    onVideoClick: (String) -> Unit,
    lazyColumnState: LazyListState = rememberLazyListState(),
) {
    var isDescriptionExpanded by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        state = lazyColumnState,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item {
            TitleInformationContent(
                modifier = Modifier.padding(start = 16.dp, end =  16.dp, top = 4.dp),
                detailAnimeState = detailAnimeState
            )
        }
        item {
            AnifoxButtonPrimary(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 2.dp
                ),
                paddingValues = PaddingValues(0.dp),
                onClick = {
                    onWatchClick.invoke(url)
                },
            ) {
                AnifoxIconOnPrimary(
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
            InformationComponent(
                modifier = Modifier.padding(start = 16.dp, end =  16.dp),
                detailAnimeState = detailAnimeState,
                onCatalogClick = onCatalogClick,
            )
        }
        item {
            GenresContent(
                modifier = Modifier.padding(start = 16.dp, end =  16.dp),
                detailAnimeState = detailAnimeState,
                onCatalogClick = onCatalogClick,
            )
        }
        item {
            StudiosContent(
                modifier = Modifier.padding(start = 16.dp, end =  16.dp),
                detailAnimeState = detailAnimeState,
                onCatalogClick = onCatalogClick,
            )
        }
        item {
            DescriptionContent(
                headerModifier = SliderContentDefaults.Default,
                modifier = Modifier.padding(start = 16.dp, end =  16.dp),
                detailAnimeState = detailAnimeState,
                isExpanded = isDescriptionExpanded,
                onExpandedChanged = { isDescriptionExpanded = it },
            )
        }
        item {
            SliderScreenshotsContent(
                headerModifier = SliderContentDefaults.Default,
                contentState = screenshotAnimeState,
                headerTitle = stringResource(R.string.feature_detail_section_screenshots_header_title),
                onMoreClick = {
                    detailAnimeState.data?.title?.let { title ->
                        onMoreScreenshotClick(title)
                    }
                },
            )
        }
        item {
            SliderVideoContent(
                headerModifier = SliderContentDefaults.Default,
                contentState = videosAnimeState,
                headerTitle = stringResource(R.string.feature_detail_section_video_header_title),
                onItemClick = onVideoClick,
                onMoreClick = {
                    detailAnimeState.data?.title?.let { title ->
                        onMoreVideoClick(title)
                    }
                },
            )
        }
        item {
            RelationContent(
                modifier = Modifier.padding(start = 8.dp),
                headerModifier = SliderContentDefaults.Default,
                headerTitle = stringResource(R.string.feature_detail_section_header_title_relation),
                contentState = relationAnimeState,
                onItemClick = onAnimeClick,
                countContent = 3,
            )
        }
        item {
            SliderContent(
                headerModifier = SliderContentDefaults.Default,
                headerTitle = stringResource(R.string.feature_detail_section_header_title_similar),
                contentState = similarAnimeState,
                onItemClick = onAnimeClick,
            )
        }
    }
}

@PreviewScreenSizes
@Composable
private fun PreviewDetailScreenUI(
    @PreviewParameter(DetailContentProvider::class) param: DetailContentPreviewParam,
) {
    DefaultPreview(true) {
        DetailUI (
            modifier = param.modifier,
            url = "",
            detailAnimeState = param.detailAnime,
            screenshotAnimeState = param.screenshotsAnime,
            relationAnimeState = param.relationAnime,
            similarAnimeState = param.similarAnime,
            videosAnimeState = param.videosAnime,
            onBackPressed = { false },
            onWatchClick = { },
            onAnimeClick = { },
            onMoreScreenshotClick = { },
            onMoreVideoClick = { },
            onCatalogClick = { },
            onVideoClick = { },
        )
    }
}
