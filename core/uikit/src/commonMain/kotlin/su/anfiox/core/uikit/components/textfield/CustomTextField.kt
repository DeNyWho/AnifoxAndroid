package su.anfiox.core.uikit.components.textfield

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import su.anfiox.core.uikit.util.DefaultPreview

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(0.dp),
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    content: (@Composable () -> Unit)? = null
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceContainer,
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.onSurfaceVariant),
    ) {
        Row(
            modifier = Modifier.padding(padding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (leadingIcon != null) {
                leadingIcon()
            }

            Box(
                modifier = Modifier.weight(1f)
            ) {
                if (content != null) {
                    content()
                }
            }

            if (trailingIcon != null) {
                trailingIcon()
            }
        }
    }
}

@Preview
@Composable
private fun CustomTextFieldPreview() {
    DefaultPreview {
        CustomTextField(
            content = { },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            },
            trailingIcon = {
                Icon(imageVector = Icons.Default.Image, contentDescription = null)
            },
            padding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}