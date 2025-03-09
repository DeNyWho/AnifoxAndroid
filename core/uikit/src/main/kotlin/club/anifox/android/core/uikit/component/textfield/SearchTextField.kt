package club.anifox.android.core.uikit.component.textfield

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
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.icon.AnifoxIconCustomTintVector
import club.anifox.android.core.uikit.component.icon.AnifoxIconPrimary
import club.anifox.android.core.uikit.icon.AnifoxIcons
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.core.uikit.util.clickableWithoutRipple

@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    placeHolder: String? = null,
    searchQuery: String = "",
    focusRequester: FocusRequester = FocusRequester(),
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
            AnifoxIconPrimary(imageVector = AnifoxIcons.search, contentDescription = null)
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
                        textFieldValue = newValue // Обновляем состояние
                        onSearchQueryChanged(newValue.text) // Передаем текст наружу
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

@PreviewLightDark
@Composable
private fun PreviewSearchField() {
    DefaultPreview {
        SearchField(
            placeHolder = "Поиск",
        )
    }
}
