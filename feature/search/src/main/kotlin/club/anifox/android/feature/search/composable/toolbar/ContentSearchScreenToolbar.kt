package club.anifox.android.feature.search.composable.toolbar

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.textfield.SearchField
import me.onebone.toolbar.CollapsingToolbarScaffoldState
import me.onebone.toolbar.CollapsingToolbarScope
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
internal fun CollapsingToolbarScope.ContentSearchScreenToolbar(
    toolbarScaffoldState: CollapsingToolbarScaffoldState = rememberCollapsingToolbarScaffoldState(),
    navigateBack: () -> Boolean,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    focusRequest: FocusRequester = FocusRequester(),
) {
    SearchField(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp),
        isEnabled = true,
        searchQuery = searchQuery,
        onSearchQueryChanged = onSearchQueryChanged,
        focusRequest = focusRequest,
    )
}