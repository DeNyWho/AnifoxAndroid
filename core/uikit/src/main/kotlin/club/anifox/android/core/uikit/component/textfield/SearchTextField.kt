package club.anifox.android.core.uikit.component.textfield

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.icon.AnifoxIconPrimary
import club.anifox.android.core.uikit.icon.AnifoxIcons
import club.anifox.android.core.uikit.theme.AnifoxTheme

@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    placeHolder: String? = null,
    searchQuery: String = "",
    focusRequest: FocusRequester = FocusRequester(),
    onSearchQueryChanged: (String) -> Unit = { },
) {
    CustomTextField(
        modifier = modifier
            .fillMaxWidth(),
        padding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
        leadingIcon = {
            AnifoxIconPrimary(imageVector = AnifoxIcons.search, contentDescription = null)
        },
        content = {
            if (isEnabled) {
                BasicTextField(
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .fillMaxWidth()
                        .focusRequester(focusRequest),
                    value = searchQuery,
                    onValueChange = { onSearchQueryChanged(it) },
                    textStyle = MaterialTheme.typography.titleMedium,
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                    maxLines = 1,
                )
            }

            if (searchQuery.isEmpty() && placeHolder != null) {
                Text(
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .fillMaxWidth(),
                    text = placeHolder,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(0.4f),
                )
            }
        },
    )
}

@PreviewLightDark
@Composable
private fun PreviewSearchField() {
    AnifoxTheme {
        SearchField(
            placeHolder = "Поиск"
        )
    }
}
