package club.anifox.android.core.uikit.component.chip

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
 * Anifox ChipSurface (chip with surface colors) [AnifoxChipSurface].
 */
@Composable
fun AnifoxChipPrimary(
    modifier: Modifier = Modifier,
    title: String = "",
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        contentColor = MaterialTheme.colorScheme.surfaceVariant,
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