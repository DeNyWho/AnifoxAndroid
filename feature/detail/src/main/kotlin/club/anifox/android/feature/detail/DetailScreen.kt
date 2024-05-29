package club.anifox.android.feature.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.hilt.navigation.compose.hiltViewModel
import club.anifox.android.commonui.theme.AnifoxTheme
import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.state.StateWrapper
import club.anifox.android.feature.detail.components.ContentDetailsScreenToolbar
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
) {
    LaunchedEffect(viewModel) {
        viewModel.getDetailAnime(url)
    }

    DetailUI(
        detailAnime = viewModel.detailAnime.value,
        onBackPressed = onBackPressed
    )
}

@Composable
internal fun DetailUI(
    modifier: Modifier = Modifier,
    detailAnime: StateWrapper<AnimeDetail>,
    onBackPressed: () -> Boolean,
) {
    val toolbarScaffoldState = rememberCollapsingToolbarScaffoldState()

    CollapsingToolbarScaffold(
        modifier = Modifier
            .fillMaxSize(),
        state = toolbarScaffoldState,
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        toolbar = {
            ContentDetailsScreenToolbar(
                contentDetailState = detailAnime,
                toolbarScaffoldState = toolbarScaffoldState,
                navigateBack = onBackPressed,
            )
        },
        body = {
            Box(modifier = Modifier.fillMaxSize())
        }
    )
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
                detailAnime = param.detailAnime,
                onBackPressed = param.onBackPressed,
            )
        }
    }
}
