package club.anifox.android.commonui.component.button

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun AnifoxIconButton(
    modifier: Modifier,
    onClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String? = null,
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Composable
fun AnifoxIconButtonSurface(
    modifier: Modifier,
    onClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String? = null,
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.onSurface,
        )
    }
}