package club.anifox.android.commonui.component.chip

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import club.anifox.android.commonui.theme.AnifoxTheme

@Composable
fun AnifoxChip(
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
private fun PreviewAnifoxChip() {
    AnifoxTheme {
        AnifoxChip(
            title = "Жанр",
        )
    }
}