package club.anifox.android.core.uikit.component.chip

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.util.DefaultPreview

/**
 * Anifox ChipSurface (chip with surface colors) [AnifoxChipSurface].
 */
@Composable
fun AnifoxChipSurface(
    modifier: Modifier = Modifier,
    title: String = "",
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        contentColor = MaterialTheme.colorScheme.onSurface,
        color = MaterialTheme.colorScheme.surfaceVariant,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewAnifoxChipSurface() {
    DefaultPreview {
        AnifoxChipSurface(
            title = "2024",
        )
    }
}

/**
 * Anifox ChipPrimary (chip with primary colors) [AnifoxChipPrimary].
 */
@Composable
fun AnifoxChipPrimary(
    modifier: Modifier = Modifier,
    title: String = "",
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        color = MaterialTheme.colorScheme.primaryContainer,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewAnifoxChipPrimary() {
    DefaultPreview {
        AnifoxChipPrimary(
            title = "Жанр",
        )
    }
}

/**
 * Anifox ChipCustom (chip with custom colors) [AnifoxChipCustom].
 */
@Composable
fun AnifoxChipCustom(
    modifier: Modifier = Modifier,
    modifierText: Modifier = Modifier,
    title: String = "",
    shape: Shape = MaterialTheme.shapes.large,
    containerColor: Color,
    contentColor: Color,
) {
    Surface(
        modifier = modifier,
        shape = shape,
        contentColor = contentColor,
        color = containerColor,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            modifier = modifierText,
        )
    }
}


@Composable
fun AnifoxChipSurfaceSelectable(
    modifier: Modifier = Modifier,
    title: String? = null,
    textStyle: TextStyle = MaterialTheme.typography.labelMedium,
    selected: Boolean = false,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    surfaceShape: Shape = MaterialTheme.shapes.large,
    icon: @Composable (() -> Unit)? = null,
) {
    Surface(
        modifier = modifier,
        shape = surfaceShape,
        contentColor = if (selected) {
            selectedColor
        } else {
            MaterialTheme.colorScheme.onSurfaceVariant
        },
        color = Color.Transparent,
        border = BorderStroke(
            width = 1.dp,
            color = if (selected) {
                selectedColor
            } else {
                MaterialTheme.colorScheme.outline
            }
        )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            title?.let {
                Text(
                    modifier = Modifier.padding(vertical = 4.dp),
                    text = it,
                    style = textStyle,
                )
            }
            icon?.let {
                if (title != null) {
                    Spacer(modifier = Modifier.width(4.dp))
                }
                it()
            }
        }
    }
}

@Preview
@Composable
private fun PreviewAnifoxChipSurfaceSelectable() {
    DefaultPreview {
        AnifoxChipSurfaceSelectable(
            title = "2024",
            selected = true
        )
    }
}

@Preview
@Composable
private fun PreviewAnifoxChipSurfaceSelectableNot() {
    DefaultPreview {
        AnifoxChipSurfaceSelectable(
            title = "2024",
            selected = false
        )
    }
}

@Preview
@Composable
private fun PreviewAnifoxChipSurfaceSelectableWithIcon() {
    DefaultPreview {
        AnifoxChipSurfaceSelectable(
            title = "Избранное",
            selected = true,
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Favorite,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }
        )
    }
}

@Preview
@Composable
private fun PreviewAnifoxChipSurfaceSelectableNotWithIcon() {
    DefaultPreview {
        AnifoxChipSurfaceSelectable(
            title = "Избранное",
            selected = false,
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Favorite,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }
        )
    }
}
