package club.anifox.android.feature.video

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import club.anifox.android.core.uikit.component.slider.SliderComponentDefaults
import club.anifox.android.core.uikit.component.slider.video.SliderVideoComponent
import club.anifox.android.core.uikit.component.topbar.SimpleTopBar
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.domain.model.anime.videos.AnimeVideosLight
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.feature.video.param.VideosContentPreviewParam
import club.anifox.android.feature.video.param.VideosContentProvider
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
internal fun VideoScreen(
    viewModel: VideoViewModel = hiltViewModel(),
    url: String = "",
    animeTitle: String? = null,
    onBackPressed: () -> Boolean,
) {
    LaunchedEffect(viewModel) {
        viewModel.getTrailerVideos(url)
        viewModel.getOpeningVideos(url)
        viewModel.getEndingVideos(url)
        viewModel.getOtherVideos(url)
    }

    VideoUI(
        trailerVideoState = viewModel.trailerVideos.value,
        openingVideoState = viewModel.openingVideos.value,
        endingVideoState = viewModel.endingVideos.value,
        otherVideoState = viewModel.otherVideos.value,
        onBackPressed = onBackPressed,
        animeTitle = animeTitle,
        onVideoClick = { youtubeUrl ->
            viewModel.openYoutube(youtubeUrl)
        },
    )
}

@Composable
private fun VideoUI(
    modifier: Modifier = Modifier,
    trailerVideoState: StateListWrapper<AnimeVideosLight>,
    openingVideoState: StateListWrapper<AnimeVideosLight>,
    endingVideoState: StateListWrapper<AnimeVideosLight>,
    otherVideoState: StateListWrapper<AnimeVideosLight>,
    onVideoClick: (String) -> Unit,
    onBackPressed: () -> Boolean,
    animeTitle: String?,
) {
    val toolbarScaffoldState = rememberCollapsingToolbarScaffoldState()
    CollapsingToolbarScaffold(
        modifier = modifier
            .fillMaxSize(),
        state = toolbarScaffoldState,
        scrollStrategy = ScrollStrategy.EnterAlwaysCollapsed,
        toolbar = {
            SimpleTopBar(
                onBackPressed = onBackPressed,
                title = if(animeTitle == null) "" else "${stringResource(R.string.feature_video_top_bar_title)} $animeTitle",
            )
        },
    ) {
        VideoContent(
            modifier = Modifier,
            trailerVideoState = trailerVideoState,
            openingVideoState = openingVideoState,
            endingVideoState = endingVideoState,
            otherVideoState = otherVideoState,
            onVideoClick = onVideoClick,
        )
    }
}

@Composable
internal fun VideoContent(
    modifier: Modifier = Modifier,
    trailerVideoState: StateListWrapper<AnimeVideosLight>,
    openingVideoState: StateListWrapper<AnimeVideosLight>,
    endingVideoState: StateListWrapper<AnimeVideosLight>,
    otherVideoState: StateListWrapper<AnimeVideosLight>,
    onVideoClick: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .padding(start = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        SliderVideoComponent(
            headerModifier = SliderComponentDefaults.VerticalOnly,
            contentState = trailerVideoState,
            headerTitle = stringResource(R.string.feature_video_section_trailer),
            onItemClick = onVideoClick,
            contentPadding = PaddingValues(),
            showCardMoreWhenPastLimit = false,
            isTypeVisible = false,
        )
        SliderVideoComponent(
            headerModifier = SliderComponentDefaults.VerticalOnly,
            contentState = openingVideoState,
            headerTitle = stringResource(R.string.feature_video_section_opening),
            onItemClick = onVideoClick,
            contentPadding = PaddingValues(),
            showCardMoreWhenPastLimit = false,
            isTypeVisible = false,
        )
        SliderVideoComponent(
            headerModifier = SliderComponentDefaults.VerticalOnly,
            contentState = endingVideoState,
            headerTitle = stringResource(R.string.feature_video_section_ending),
            onItemClick = onVideoClick,
            contentPadding = PaddingValues(),
            showCardMoreWhenPastLimit = false,
            isTypeVisible = false,
        )
        SliderVideoComponent(
            headerModifier = SliderComponentDefaults.VerticalOnly,
            contentState = otherVideoState,
            headerTitle = stringResource(R.string.feature_video_section_other),
            onItemClick = onVideoClick,
            contentPadding = PaddingValues(),
            showCardMoreWhenPastLimit = false,
            isTypeVisible = false,
        )
    }
}

@PreviewScreenSizes
@Composable
private fun PreviewVideoScreenUI(
    @PreviewParameter(VideosContentProvider::class) param: VideosContentPreviewParam,
) {
    DefaultPreview {
        VideoUI(
            modifier = param.modifier,
            trailerVideoState = param.trailerVideoState,
            openingVideoState = param.openingVideoState,
            endingVideoState = param.endingVideoState,
            otherVideoState = param.otherVideoState,
            onVideoClick = param.onVideoClick,
            onBackPressed = param.onBackPressed,
            animeTitle = param.animeTitle,
        )
    }
}