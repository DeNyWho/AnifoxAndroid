package club.anifox.android.feature.screenshots.composable.top

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons.AutoMirrored.Filled
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.icon.AnifoxIconPrimary
import club.anifox.android.feature.screenshots.R
import me.onebone.toolbar.CollapsingToolbarScaffoldState
import me.onebone.toolbar.CollapsingToolbarScope
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import kotlin.math.roundToInt

@Composable
internal fun CollapsingToolbarScope.ContentScreenshotsToolbar(
    onBackPressed: () -> Boolean,
    toolbarScaffoldState: CollapsingToolbarScaffoldState = rememberCollapsingToolbarScaffoldState(),
    animeTitle: String?,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
            .statusBarsPadding(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AnifoxIconPrimary(
            imageVector = Filled.ArrowBack,
            contentDescription = "back",
            modifier = Modifier
                .clickable {
                    onBackPressed.invoke()
                }
                .size(24.dp),
        )

        val density = LocalDensity.current
        val targetOffset = with(density) {
            -40.dp.toPx().roundToInt()
        }

        AnimatedVisibility(
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
                text = if(animeTitle == null) "" else "${stringResource(R.string.feature_screenshots_top_bar_title)} $animeTitle",
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f)
                    .padding(start = 16.dp, end = 12.dp),
            )
        }
    }

}