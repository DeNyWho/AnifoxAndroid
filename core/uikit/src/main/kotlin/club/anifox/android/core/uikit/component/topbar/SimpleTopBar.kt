package club.anifox.android.core.uikit.component.topbar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons.AutoMirrored
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.R
import club.anifox.android.core.uikit.component.icon.AnifoxIconPrimary
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.core.uikit.util.clickableWithoutRipple

@Composable
fun SimpleTopBar(
    onBackPressed: () -> Unit,
    title: String,
    tonalElevation: Dp = 0.dp,
    shadowElevation: Dp = 0.dp,
    surfaceColor: Color = MaterialTheme.colorScheme.background,
) {
    Surface (
        modifier = Modifier.fillMaxWidth(),
        tonalElevation = tonalElevation,
        shadowElevation = shadowElevation,
        color = surfaceColor,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
                .statusBarsPadding(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AnifoxIconPrimary(
                modifier = Modifier
                    .clickableWithoutRipple {
                        onBackPressed.invoke()
                    }
                    .size(24.dp),
                imageVector = AutoMirrored.Filled.ArrowBack,
                contentDescription = "back",
            )

            Text(
                text = title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f)
                    .padding(start = 16.dp, end = 12.dp),
            )

            Spacer(Modifier)
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewSimpleTopBar() {
    DefaultPreview {
        SimpleTopBar(
            onBackPressed = { false },
            title = stringResource(R.string.core_uikit_component_preview_top_bar),
            tonalElevation = 4.dp,
            shadowElevation = 4.dp,
        )
    }
}