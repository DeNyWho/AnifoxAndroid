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
    val uiState by viewModel.uiState.collectAsState()

}

@Composable
private fun FavouriteUI(

) {

}