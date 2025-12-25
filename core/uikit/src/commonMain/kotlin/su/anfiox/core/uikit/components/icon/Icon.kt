package su.anfiox.core.uikit.components.icon

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons.AutoMirrored
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import su.anfiox.core.uikit.util.DefaultPreview

@Composable
fun AnifoxIcon(
    modifier: Modifier = Modifier,
    painter: Painter,
    contentDescription: String? = null,
) {
    Icon(
        modifier = modifier,
        painter = painter,
        contentDescription = contentDescription,
    )
}

@Composable
fun AnifoxIconCustomTintVector(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription: String? = null,
    tint: Color,
) {
    Icon(
        modifier = modifier,
        imageVector = imageVector,
        contentDescription = contentDescription,
        tint = tint,
    )
}

@Composable
fun AnifoxIconOnSurface(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription: String? = null,
) {
    Icon(
        imageVector,
        modifier = modifier,
        contentDescription = contentDescription,
        tint = MaterialTheme.colorScheme.onSurface,
    )
}

@Preview
@Composable
private fun PreviewAnifoxIconOnSurface() {
    DefaultPreview {
        AnifoxIconOnSurface(
            modifier = Modifier.size(40.dp),
            AutoMirrored.Filled.ArrowBack,
            contentDescription = "content description"
        )
    }
}

@Composable
fun AnifoxIconPrimary(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription: String? = null,
) {
    Icon(
        modifier = modifier,
        imageVector = imageVector,
        contentDescription = contentDescription,
        tint = MaterialTheme.colorScheme.primary,
    )
}

@Preview
@Composable
private fun PreviewAnifoxIconPrimary() {
    DefaultPreview {
        AnifoxIconPrimary(
            modifier = Modifier.size(40.dp),
            AutoMirrored.Filled.ArrowBack,
            contentDescription = "content description"
        )
    }
}

@Composable
fun AnifoxIconOnPrimary(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription: String? = null,
) {
    Icon(
        modifier = modifier,
        imageVector = imageVector,
        contentDescription = contentDescription,
        tint = MaterialTheme.colorScheme.onPrimaryContainer,
    )
}

@Preview
@Composable
private fun PreviewAnifoxIconOnPrimary() {
    DefaultPreview {
        AnifoxIconOnPrimary(
            modifier = Modifier.size(40.dp),
            AutoMirrored.Filled.ArrowBack,
            contentDescription = "content description"
        )
    }
}

@Composable
fun AnifoxIconOnBackground(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription: String? = null,
) {
    Icon(
        modifier = modifier,
        imageVector = imageVector,
        contentDescription = contentDescription,
        tint = MaterialTheme.colorScheme.onBackground,
    )
}

@Preview
@Composable
private fun PreviewAnifoxIconOnBackground() {
    DefaultPreview {
        AnifoxIconOnBackground(
            modifier = Modifier.size(40.dp),
            AutoMirrored.Filled.ArrowBack,
            contentDescription = "content description"
        )
    }
}
