package club.anifox.android.core.uikit.component.topbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.R
import club.anifox.android.core.uikit.component.icon.AnifoxIconPrimary
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.core.uikit.util.clickableWithoutRipple
import me.onebone.toolbar.CollapsingToolbarScaffoldState
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import kotlin.math.roundToInt

@Composable
fun SimpleTopBarCollapse(
    onBackPressed: () -> Boolean,
    title: String,
    titleAlign: TextAlign = TextAlign.Start,
    titleStyle: TextStyle = MaterialTheme.typography.titleMedium,
    surfaceColor: Color = MaterialTheme.colorScheme.background,
    toolbarScaffoldState: CollapsingToolbarScaffoldState = rememberCollapsingToolbarScaffoldState(),
) {
    Surface (
        modifier = Modifier.fillMaxWidth(),
        color = surfaceColor,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
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

            val density = LocalDensity.current
            val targetOffset = with(density) {
                -40.dp.toPx().roundToInt()
            }

            AnimatedVisibility(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f)
                    .padding(start = 16.dp, end = 12.dp),
                visible = toolbarScaffoldState.toolbarState.progress < 0.25,
                enter = slideInVertically(
                    initialOffsetY = { targetOffset },
                    animationSpec = tween(
                        durationMillis = 800,
                        delayMillis = 50,
                        easing = LinearOutSlowInEasing,
                    ),
                ),
                exit = slideOutVertically(
                    targetOffsetY = { targetOffset },
                    animationSpec = tween(
                        durationMillis = 800,
                        delayMillis = 50,
                        easing = LinearOutSlowInEasing,
                    ),
                ) + fadeOut(),
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = title,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = titleStyle,
                    textAlign = titleAlign,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }

            Spacer(Modifier.size(24.dp))
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewSimpleTopBarCollapse() {
    DefaultPreview {
        SimpleTopBarCollapse(
            onBackPressed = { false },
            title = stringResource(R.string.core_uikit_component_preview_top_bar),
        )
    }
}