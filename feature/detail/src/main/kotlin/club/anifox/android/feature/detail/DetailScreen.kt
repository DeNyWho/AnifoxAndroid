package club.anifox.android.feature.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import club.anifox.android.commonui.component.button.AnifoxButtonPrimary
import club.anifox.android.commonui.component.icon.AnifoxIcon
import club.anifox.android.commonui.component.icon.AnifoxIconPrimary
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
            DetailContentUI(detailAnime)
        }
    )
}

@Composable
internal fun DetailContentUI(
    detailAnime: StateWrapper<AnimeDetail>
) {
    Column (
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
    ) {
        AnifoxButtonPrimary (
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 2.dp
            ),
            paddingValues = PaddingValues(0.dp)
        ) {
            AnifoxIconPrimary(Filled.PlayArrow, contentDescription = "Watch button", modifier = Modifier.size(40.dp))
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = "Watch",
                style = MaterialTheme.typography.labelLarge,
            )
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
                detailAnime = param.detailAnime,
                onBackPressed = param.onBackPressed,
            )
        }
    }
}
