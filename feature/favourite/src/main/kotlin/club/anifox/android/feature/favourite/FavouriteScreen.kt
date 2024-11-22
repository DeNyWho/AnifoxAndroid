package club.anifox.android.feature.favourite

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun FavouriteScreen(
    viewModel: FavouriteViewModel = hiltViewModel(),
    onAnimeClick: (String) -> Unit,
) {
    val state by viewModel.state.collectAsState()

}

@Composable
private fun FavouriteUI(

) {

}