package club.anifox.android.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.state.StateListWrapper
import java.time.LocalDateTime

@Composable
internal fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    LaunchedEffect(viewModel) {
        viewModel.getPopularOngoingAnime(0,12)
    }

    HomeScreen(modifier, viewModel.onPopularOngoingAnime.value)
}


@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    onPopularOngoingAnime: StateListWrapper<AnimeLight>,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(text = "Home", fontSize = 36.sp)
    }

}