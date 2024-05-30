package club.anifox.android.commonui.component.icon

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons.AutoMirrored
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@Composable
fun AnifoxIcon(
    imageVector: ImageVector,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
) {
    Icon(
        imageVector,
        modifier = modifier,
        contentDescription = contentDescription,
        tint = MaterialTheme.colorScheme.onSurface,
    )
}

@PreviewLightDark
@Composable
internal fun PreviewAnifoxIcon() {
    AnifoxIcon(AutoMirrored.Filled.ArrowBack, contentDescription = "content description", modifier = Modifier.size(40.dp))

}


@Composable
fun AnifoxIconPrimary(
    imageVector: ImageVector,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
) {
    Icon(
        imageVector,
        modifier = modifier,
        contentDescription = contentDescription,
        tint = MaterialTheme.colorScheme.onPrimaryContainer,
    )
}

@PreviewLightDark
@Composable
internal fun PreviewAnifoxIconPrimary() {
    AnifoxIconPrimary(AutoMirrored.Filled.ArrowBack, contentDescription = "content description", modifier = Modifier.size(40.dp))
}
