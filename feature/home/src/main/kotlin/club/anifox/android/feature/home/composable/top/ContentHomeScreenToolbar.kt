package club.anifox.android.feature.home.composable.top

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.icon.AnifoxIconOnSurface
import club.anifox.android.core.uikit.component.textfield.SearchField
import club.anifox.android.core.uikit.util.clickableWithoutRipple
import club.anifox.android.feature.home.R
import me.onebone.toolbar.CollapsingToolbarScaffoldState
import me.onebone.toolbar.CollapsingToolbarScope
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import kotlin.math.roundToInt

@Composable
internal fun CollapsingToolbarScope.ContentHomeScreenToolbar(
    onSearchClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onCatalogClick: () -> Unit,
    toolbarScaffoldState: CollapsingToolbarScaffoldState = rememberCollapsingToolbarScaffoldState(),
) {
    var isSearchExpanded by remember { mutableStateOf(false) }

    val searchWidthFraction by animateFloatAsState(
        targetValue = if (isSearchExpanded) 1f else 0.85f,
        animationSpec = tween(
            durationMillis = 600,
            easing = FastOutSlowInEasing
        )
    )

    val rightIconAlpha by animateFloatAsState(
        targetValue = if (isSearchExpanded) 0f else 1f,
        animationSpec = tween(
            durationMillis = 600,
            easing = FastOutSlowInEasing
        )
    )

    Row(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AnifoxIconOnSurface(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(24.dp)
                .clickableWithoutRipple { onHistoryClick() },
            imageVector = ImageVector.vectorResource(R.drawable.history),
            contentDescription = null,
        )

        SearchField(
            modifier = Modifier
                .weight(searchWidthFraction)
                .clickableWithoutRipple {
                    if (!isSearchExpanded) {
                        isSearchExpanded = true
                        onSearchClick()
                    }
                },
            placeHolder = stringResource(R.string.feature_home_search_placeholder),
            isEnabled = isSearchExpanded,
        )

        if (!isSearchExpanded) {
            AnifoxIconOnSurface(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(24.dp)
                    .alpha(rightIconAlpha)
                    .clickableWithoutRipple { onCatalogClick() },
                imageVector = ImageVector.vectorResource(R.drawable.browse),
                contentDescription = null,
            )
        }
    }
}