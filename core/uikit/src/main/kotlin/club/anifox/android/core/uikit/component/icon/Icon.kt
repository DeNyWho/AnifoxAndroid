package club.anifox.android.core.uikit.component.icon

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
import club.anifox.android.core.uikit.theme.AnifoxTheme

@Composable
fun AnifoxIcon(
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

@PreviewLightDark
@Composable
internal fun PreviewAnifoxIcon() {
    AnifoxIcon(modifier = Modifier.size(40.dp), AutoMirrored.Filled.ArrowBack, contentDescription = "content description")

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

@PreviewLightDark
@Composable
internal fun PreviewAnifoxIconPrimary() {
    AnifoxTheme {
        AnifoxIconPrimary(modifier = Modifier.size(40.dp), AutoMirrored.Filled.ArrowBack, contentDescription = "content description")
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

@PreviewLightDark
@Composable
internal fun PreviewAnifoxIconOnPrimary() {
    AnifoxTheme {
        AnifoxIconOnPrimary(modifier = Modifier.size(40.dp), AutoMirrored.Filled.ArrowBack, contentDescription = "content description")
    }
}

@Composable
fun AnifoxIconOnBackground(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription: String? = null,
) {
    AnifoxTheme {
        Icon(
            modifier = modifier,
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@PreviewLightDark
@Composable
internal fun PreviewAnifoxIconOnBackground() {
    AnifoxTheme {
        AnifoxIconOnBackground(
            modifier = Modifier.size(40.dp),
            AutoMirrored.Filled.ArrowBack,
            contentDescription = "content description"
        )
    }
}
