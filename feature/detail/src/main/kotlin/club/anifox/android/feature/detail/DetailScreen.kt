package club.anifox.android.feature.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons.AutoMirrored
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import club.anifox.android.core.uikit.component.button.AnifoxButtonSurface
import club.anifox.android.core.uikit.component.icon.AnifoxIconPrimary
import club.anifox.android.core.uikit.component.progress.CircularProgress
import club.anifox.android.core.uikit.component.slider.SliderComponentDefaults
import club.anifox.android.core.uikit.component.slider.screenshots.SliderScreenshotsComponent
import club.anifox.android.core.uikit.component.slider.simple.SliderComponent
import club.anifox.android.core.uikit.component.slider.video.SliderVideoComponent
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.core.uikit.util.clickableWithoutRipple
import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.characters.AnimeCharactersLight
import club.anifox.android.domain.model.anime.enum.AnimeFavouriteStatus
import club.anifox.android.domain.model.anime.related.AnimeRelatedLight
import club.anifox.android.domain.model.anime.videos.AnimeVideosLight
import club.anifox.android.domain.model.navigation.catalog.CatalogFilterParams
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.domain.state.StateWrapper
import club.anifox.android.feature.detail.components.characters.CharactersComponent
import club.anifox.android.feature.detail.components.description.DescriptionComponent
import club.anifox.android.feature.detail.components.favourite.FavouriteComponent
import club.anifox.android.feature.detail.components.genres.GenresComponent
import club.anifox.android.feature.detail.components.information.InformationComponent
import club.anifox.android.feature.detail.components.poster.PosterComponent
import club.anifox.android.feature.detail.components.related.RelationComponent
import club.anifox.android.feature.detail.components.studios.StudiosComponent
import club.anifox.android.feature.detail.components.title.TitleInformationComponent
import club.anifox.android.feature.detail.components.watch.WatchComponent
import club.anifox.android.feature.detail.param.DetailContentPreviewParam
import club.anifox.android.feature.detail.param.DetailContentProvider

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
    onCharacterClick: (String) -> Unit,
    onMoreCharactersClick: (String, String) -> Unit,
) {
    LaunchedEffect(viewModel, url) {
        viewModel.loadData(url)
    }

    DetailUI(
        url = url,
        detailAnimeState = viewModel.detailAnime.value,
        screenshotAnimeState = viewModel.screenshotsAnime.value,
        videosAnimeState = viewModel.videosAnime.value,
        relationAnimeState = viewModel.relatedAnime.value,
        similarAnimeState = viewModel.similarAnime.value,
        charactersAnimeState = viewModel.charactersAnime.value,
        selectedFavouriteState = viewModel.selectedFavouriteStatus.value,
        isInFavouriteState = viewModel.isInFavourite.value,
        onBackPressed = onBackPressed,
        onWatchClick = onWatchClick,
        onAnimeClick = onAnimeClick,
        onMoreScreenshotClick = { title ->
            onMoreScreenshotClick.invoke(url, title)
        },
        onMoreVideoClick = { title ->
            onMoreVideoClick.invoke(url, title)
        },
        onCatalogClick = onCatalogClick,
        onVideoClick = { youtubeUrl ->
            viewModel.openYoutube(youtubeUrl)
        },
        onCharacterClick = onCharacterClick,
        onMoreCharactersClick = { title ->
            onMoreCharactersClick.invoke(url, title)
        },
        onUpdateFavouriteStatus = { status ->
            viewModel.updateFavouriteStatus(status)
        },
        onUpdateIsInFavourite = {
            viewModel.updateIsInFavourite()
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
    charactersAnimeState: StateListWrapper<AnimeCharactersLight>,
    selectedFavouriteState: AnimeFavouriteStatus?,
    isInFavouriteState: Boolean,
    onBackPressed: () -> Boolean,
    onWatchClick: (String) -> Unit,
    onAnimeClick: (String) -> Unit,
    onMoreScreenshotClick: (String) -> Unit,
    onMoreVideoClick: (String) -> Unit,
    onCatalogClick: (CatalogFilterParams) -> Unit,
    onVideoClick: (String) -> Unit,
    onCharacterClick: (String) -> Unit,
    onMoreCharactersClick: (String) -> Unit,
    onUpdateFavouriteStatus: (AnimeFavouriteStatus?) -> Unit,
    onUpdateIsInFavourite: () -> Unit,
) {
    if(detailAnimeState.isLoading) {
        CircularProgress()
    } else {
        Box {
            Row (
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                    .align(Alignment.TopCenter)
                    .zIndex(1f)
                    .fillMaxWidth(),
            ) {
                AnifoxButtonSurface(
                    modifier = Modifier.size(32.dp),
                    paddingValues = PaddingValues(4.dp),
                    shape = MaterialTheme.shapes.small,
                    onClick = {
                        onBackPressed.invoke()
                    },
                    elevation = ButtonDefaults.elevatedButtonElevation(),
                ) {
                    AnifoxIconPrimary(
                        modifier = Modifier
                            .clickableWithoutRipple {
                                onBackPressed.invoke()
                            }
                            .size(28.dp),
                        imageVector = AutoMirrored.Filled.ArrowBack,
                        contentDescription = "back",
                    )
                }
                Spacer(modifier.weight(1f))
            }

            DetailContentUI(
                url = url,
                detailAnimeState = detailAnimeState,
                screenshotAnimeState = screenshotAnimeState,
                videosAnimeState = videosAnimeState,
                relationAnimeState = relationAnimeState,
                similarAnimeState = similarAnimeState,
                charactersAnimeState = charactersAnimeState,
                selectedFavouriteState = selectedFavouriteState,
                isInFavouriteState = isInFavouriteState,
                onWatchClick = onWatchClick,
                onAnimeClick = onAnimeClick,
                onMoreScreenshotClick = onMoreScreenshotClick,
                onMoreVideoClick = onMoreVideoClick,
                onCatalogClick = onCatalogClick,
                onVideoClick = onVideoClick,
                onCharacterClick = onCharacterClick,
                onMoreCharactersClick = onMoreCharactersClick,
                onUpdateFavouriteStatus = onUpdateFavouriteStatus,
                onUpdateIsInFavourite = onUpdateIsInFavourite,
            )
        }
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
    charactersAnimeState: StateListWrapper<AnimeCharactersLight>,
    selectedFavouriteState: AnimeFavouriteStatus?,
    isInFavouriteState: Boolean,
    onWatchClick: (String) -> Unit,
    onAnimeClick: (String) -> Unit,
    onMoreScreenshotClick: (String) -> Unit,
    onMoreVideoClick: (String) -> Unit,
    onCatalogClick: (CatalogFilterParams) -> Unit,
    onVideoClick: (String) -> Unit,
    onCharacterClick: (String) -> Unit,
    onMoreCharactersClick: (String) -> Unit,
    onUpdateFavouriteStatus: (AnimeFavouriteStatus?) -> Unit,
    onUpdateIsInFavourite: () -> Unit,
) {
    var isDescriptionExpanded by rememberSaveable { mutableStateOf(false) }
    val lazyColumnState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        state = lazyColumnState,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item(key = "poster") {
            PosterComponent(
                detailAnimeState = detailAnimeState,
            )
        }

        item(key = "title") {
            TitleInformationComponent(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 4.dp),
                detailAnimeState = detailAnimeState,
            )
        }

        item(key = "favourite") {
            FavouriteComponent(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                selectedFavouriteState = selectedFavouriteState,
                isInFavouriteState = isInFavouriteState,
                onUpdateFavouriteStatus = onUpdateFavouriteStatus,
                onUpdateIsInFavourite = onUpdateIsInFavourite,
            )
        }

        item(key = "watch") {
            WatchComponent(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                detailAnimeState = detailAnimeState,
                onWatchClick = {
                    onWatchClick.invoke(url)
                },
            )
        }

        item(key = "information") {
            InformationComponent(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                detailAnimeState = detailAnimeState,
                onCatalogClick = onCatalogClick,
            )
        }

        detailAnimeState.data?.let { detail ->
            if (detail.genres.isNotEmpty()) {
                item(key = "genres") {
                    GenresComponent(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                        detailAnimeState = detailAnimeState,
                        onCatalogClick = onCatalogClick,
                    )
                }
            }

            if(detail.studios.isNotEmpty()) {
                item(key = "studios") {
                    StudiosComponent(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                        detailAnimeState = detailAnimeState,
                        onCatalogClick = onCatalogClick,
                    )
                }
            }

            if(!detail.description.isNullOrEmpty()) {
                item(key = "description") {
                    DescriptionComponent(
                        headerModifier = SliderComponentDefaults.Default,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                        detailAnimeState = detailAnimeState,
                        isExpanded = isDescriptionExpanded,
                        onExpandedChanged = { isDescriptionExpanded = it },
                    )
                }
            }
        }

        item(key = "screenshots") {
            SliderScreenshotsComponent(
                headerModifier = SliderComponentDefaults.Default,
                screenshotsState = screenshotAnimeState,
                headerTitle = stringResource(R.string.feature_detail_section_screenshots_header_title),
                onMoreClick = {
                    detailAnimeState.data?.title?.let { title ->
                        onMoreScreenshotClick.invoke(title)
                    }
                },
            )
        }

        item(key = "video") {
            SliderVideoComponent(
                headerModifier = SliderComponentDefaults.Default,
                contentState = videosAnimeState,
                headerTitle = stringResource(R.string.feature_detail_section_video_header_title),
                onItemClick = onVideoClick,
                onMoreClick = {
                    detailAnimeState.data?.title?.let { title ->
                        onMoreVideoClick.invoke(title)
                    }
                },
            )
        }

        item(key = "characters") {
            CharactersComponent(
                headerModifier = SliderComponentDefaults.Default,
                headerTitle = stringResource(R.string.feature_detail_section_header_title_characters),
                contentState = charactersAnimeState,
                onItemClick = onCharacterClick,
                onMoreClick = {
                    detailAnimeState.data?.title?.let { title ->
                        onMoreCharactersClick.invoke(title)
                    }
                },
            )
        }

        item(key = "relation") {
            RelationComponent(
                modifier = Modifier.padding(start = 16.dp),
                headerModifier = SliderComponentDefaults.Default,
                headerTitle = stringResource(R.string.feature_detail_section_header_title_relation),
                contentState = relationAnimeState,
                onItemClick = onAnimeClick,
                countContent = 3,
            )
        }

        item(key = "similar") {
            SliderComponent(
                headerModifier = SliderComponentDefaults.Default,
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
            videosAnimeState = param.videosAnime,
            relationAnimeState = param.relationAnime,
            similarAnimeState = param.similarAnime,
            charactersAnimeState = param.charactersAnime,
            selectedFavouriteState = param.selectedFavouriteState,
            isInFavouriteState = false,
            onBackPressed = { false },
            onWatchClick = { },
            onAnimeClick = { },
            onMoreScreenshotClick = { },
            onMoreVideoClick = { },
            onCatalogClick = { },
            onVideoClick = { },
            onCharacterClick = { },
            onMoreCharactersClick = { },
            onUpdateFavouriteStatus = { },
            onUpdateIsInFavourite = { },
        )
    }
}
