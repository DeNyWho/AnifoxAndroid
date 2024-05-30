package club.anifox.android.commonui.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons.AutoMirrored
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

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
internal fun PreviewAnifoxButton() {
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

@PreviewLightDark
@Composable
internal fun PreviewAnifoxButtonWithIcon() {
    AnifoxButton(
        modifier = Modifier.size(40.dp),
        shape = MaterialTheme.shapes.small,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 2.dp
        ),
        paddingValues = PaddingValues(8.dp)
    ) {
        Icon(AutoMirrored.Filled.ArrowBack, contentDescription = "content description", tint = MaterialTheme.colorScheme.onSurface)
    }
}
