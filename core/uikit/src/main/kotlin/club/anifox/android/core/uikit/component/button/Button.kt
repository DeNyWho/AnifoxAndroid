package club.anifox.android.core.uikit.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons.AutoMirrored
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.icon.AnifoxIconOnPrimary
import club.anifox.android.core.uikit.component.icon.AnifoxIconOnSurface
import club.anifox.android.core.uikit.util.DefaultPreview

@Composable
fun AnifoxButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    paddingValues: PaddingValues,
    content: @Composable() (RowScope.() -> Unit)
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.onBackground,
            containerColor = MaterialTheme.colorScheme.background,
        ),
        elevation = elevation,
        content = content,
        border = border,
        contentPadding = paddingValues,
    )
}

@PreviewLightDark
@Composable
private fun PreviewAnifoxButton() {
    DefaultPreview {
        AnifoxButton(
            shape = MaterialTheme.shapes.small,
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 2.dp
            ),
            paddingValues = PaddingValues(4.dp)
        ) {
            Text("Hello world")
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewAnifoxButtonWithIcon() {
    DefaultPreview {
        AnifoxButton(
            modifier = Modifier.size(40.dp),
            shape = MaterialTheme.shapes.small,
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 2.dp
            ),
            paddingValues = PaddingValues(8.dp)
        ) {
            AnifoxIconOnSurface(imageVector = AutoMirrored.Filled.ArrowBack, contentDescription = "content description")
        }
    }
}

@Composable
fun AnifoxButtonPrimary(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    paddingValues: PaddingValues,
    content: @Composable() (RowScope.() -> Unit)
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        elevation = elevation,
        content = content,
        border = border,
        contentPadding = paddingValues,
    )
}

@PreviewLightDark
@Composable
private fun PreviewAnifoxButtonPrimaryWithIcon() {
    DefaultPreview {
        AnifoxButtonPrimary(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 2.dp
            ),
            paddingValues = PaddingValues(0.dp)
        ) {
            AnifoxIconOnPrimary(imageVector = Filled.PlayArrow, contentDescription = "content description")
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "Watch",
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}


@Composable
fun AnifoxButtonIconTransparent(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    elevation: ButtonElevation? = null,
    contentColor: Color,
    border: BorderStroke? = null,
    paddingValues: PaddingValues,
    content: @Composable() (RowScope.() -> Unit)
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            contentColor = contentColor,
            containerColor = Color.Transparent,
        ),
        elevation = elevation,
        content = content,
        border = border,
        contentPadding = paddingValues,
    )
}

@PreviewLightDark
@Composable
private fun PreviewAnifoxButtonIconTransparent() {
    DefaultPreview {
        AnifoxButtonIconTransparent(
            modifier = Modifier.size(20.dp),
            shape = MaterialTheme.shapes.small,
            paddingValues = PaddingValues(4.dp),
            contentColor = MaterialTheme.colorScheme.surfaceVariant,
        ) {
            AnifoxIconOnSurface(imageVector = Outlined.ContentCopy, contentDescription = "content description")
        }
    }
}

@Composable
fun AnifoxButtonSurface(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    paddingValues: PaddingValues,
    content: @Composable() (RowScope.() -> Unit)
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.onSurface,
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        elevation = elevation,
        content = content,
        border = border,
        contentPadding = paddingValues,
    )
}

@PreviewLightDark
@Composable
private fun PreviewAnifoxButtonSurface() {
    DefaultPreview {
        AnifoxButtonSurface(
            modifier = Modifier.fillMaxWidth(),
            paddingValues = PaddingValues(0.dp),
            shape = MaterialTheme.shapes.small,
            onClick = {

            },
        ) {
            Text(
                text = "Hello world",
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}