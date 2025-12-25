package su.anfiox.core.uikit.components.textfield

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import su.anfiox.core.uikit.components.icon.AnifoxIconCustomTintVector
import org.jetbrains.compose.ui.tooling.preview.Preview
import su.anfiox.core.uikit.util.DefaultPreview
import su.anfiox.core.uikit.util.clickableWithoutRipple

@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    placeHolder: String? = null,
    searchQuery: String = "",
    focusRequester: FocusRequester = remember { FocusRequester() },
    onSearchQueryChanged: (String) -> Unit = { },
    onTrailingIconClick: () -> Unit = { },
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue(searchQuery, TextRange(searchQuery.length))) }

    if (textFieldValue.text != searchQuery) {
        textFieldValue = TextFieldValue(searchQuery, TextRange(searchQuery.length))
    }

    CustomTextField(
        modifier = modifier
            .fillMaxWidth(),
        padding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
        leadingIcon = {
            // TODO: ADD ICON
//            AnifoxIconPrimary(imageVector = AnifoxIcons.search, contentDescription = null)
        },
        content = {
            if (isEnabled) {
                BasicTextField(
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    value = textFieldValue,
                    onValueChange = { newValue ->
                        textFieldValue = newValue
                        onSearchQueryChanged(newValue.text)
                    },
                    textStyle = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onBackground),
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                    singleLine = true,
                )
            }

            if (textFieldValue.text.isEmpty() && placeHolder != null) {
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
        trailingIcon = {
            if (isEnabled && textFieldValue.text.isNotEmpty()) {
                AnifoxIconCustomTintVector(
                    modifier = Modifier
                        .clickableWithoutRipple {
                            onTrailingIconClick.invoke()
                        },
                    imageVector = Icons.Filled.Clear,
                    contentDescription = null,
                    tint = Color.Red,
                )
            }
        },
    )
}

@Preview
@Composable
private fun PreviewSearchField() {
    DefaultPreview {
        SearchField(
            placeHolder = "Поиск",
        )
    }
}
