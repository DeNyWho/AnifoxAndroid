package club.anifox.android.feature.characters

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun CharactersScreen(
    viewModel: CharactersViewModel = hiltViewModel(),
    url: String = "",
    animeTitle: String? = null,
    onBackPressed: () -> Boolean,
) {

}