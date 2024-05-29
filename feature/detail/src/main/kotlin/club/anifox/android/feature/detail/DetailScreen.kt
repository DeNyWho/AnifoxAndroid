package club.anifox.android.feature.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.state.StateWrapper

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel(),
    url: String = "",
) {
    LaunchedEffect(viewModel) {
        viewModel.getDetailAnime(url)
    }

    DetailUI(
        detailAnime = viewModel.detailAnime.value,
    )
}

@Composable
internal fun DetailUI(
    modifier: Modifier = Modifier,
    detailAnime: StateWrapper<AnimeDetail>,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        Text(text = detailAnime.data?.title ?: "")
    }
}