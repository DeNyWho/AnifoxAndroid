package club.anifox.android.feature.video

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons.AutoMirrored.Filled
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import club.anifox.android.core.uikit.component.icon.AnifoxIconPrimary
import club.anifox.android.core.uikit.component.slider.SliderContentDefaults
import club.anifox.android.core.uikit.component.slider.video.content.SliderVideoContent
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
        onVideoClick = { },
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
                    .statusBarsPadding(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AnifoxIconPrimary(
                    imageVector = Filled.ArrowBack,
                    contentDescription = "back",
                    modifier = Modifier
                        .clickable {
                            onBackPressed.invoke()
                        }
                        .size(24.dp),
                )

                Text(
                    text = if(animeTitle == null) "" else "${stringResource(R.string.feature_video_top_bar_title)} $animeTitle",
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .weight(1f)
                        .padding(start = 16.dp, end = 12.dp),
                )
            }
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
        SliderVideoContent(
            headerModifier = SliderContentDefaults.VerticalOnly,
            contentState = trailerVideoState,
            headerTitle = stringResource(R.string.feature_video_section_trailer),
            onItemClick = onVideoClick,
            contentPadding = PaddingValues(),
            showCardMoreWhenPastLimit = false,
            isTypeVisible = false,
        )
        SliderVideoContent(
            headerModifier = SliderContentDefaults.VerticalOnly,
            contentState = openingVideoState,
            headerTitle = stringResource(R.string.feature_video_section_opening),
            onItemClick = onVideoClick,
            contentPadding = PaddingValues(),
            showCardMoreWhenPastLimit = false,
            isTypeVisible = false,
        )
        SliderVideoContent(
            headerModifier = SliderContentDefaults.VerticalOnly,
            contentState = endingVideoState,
            headerTitle = stringResource(R.string.feature_video_section_ending),
            onItemClick = onVideoClick,
            contentPadding = PaddingValues(),
            showCardMoreWhenPastLimit = false,
            isTypeVisible = false,
        )
        SliderVideoContent(
            headerModifier = SliderContentDefaults.VerticalOnly,
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