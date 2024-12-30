package club.anifox.android.feature.character

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun CharacterScreen(
    viewModel: CharacterViewModel = hiltViewModel(),
    onBackPressed: () -> Boolean,
    id: String,
) {

}