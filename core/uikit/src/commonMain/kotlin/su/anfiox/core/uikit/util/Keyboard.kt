package su.anfiox.core.uikit.util

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

// TODO: REWORK TO ALL PLATFORMS
@Composable
fun KeyboardManager(lazyGridState: LazyGridState) {
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(lazyGridState.isScrollInProgress) {
        if (lazyGridState.isScrollInProgress) {
            keyboardController?.hide()
        }
    }
}