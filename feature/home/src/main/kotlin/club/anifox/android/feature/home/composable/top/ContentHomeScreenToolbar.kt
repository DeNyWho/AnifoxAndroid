package club.anifox.android.feature.home.composable.top

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.icon.AnifoxIconOnSurface
import club.anifox.android.core.uikit.component.textfield.SearchField
import club.anifox.android.core.uikit.util.clickableWithoutRipple
import club.anifox.android.feature.home.R
import me.onebone.toolbar.CollapsingToolbarScaffoldState
import me.onebone.toolbar.CollapsingToolbarScope
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
internal fun CollapsingToolbarScope.ContentHomeScreenToolbar(
    onSearchClick: () -> Unit,
    onCatalogClick: () -> Unit,
    toolbarScaffoldState: CollapsingToolbarScaffoldState = rememberCollapsingToolbarScaffoldState(),
) {
    var hasNavigatedToSearch by remember { mutableStateOf(false) }

    LaunchedEffect(hasNavigatedToSearch) {
        if (hasNavigatedToSearch) {
            onSearchClick.invoke()
            hasNavigatedToSearch = false
        }
    }

    val searchWidthFraction by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        )
    )

    val rightIconAlpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(
            durationMillis = 200,
            easing = LinearEasing
        )
    )

    Row(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Spacer(modifier = Modifier.size(24.dp))

        SearchField(
            modifier = Modifier
                .weight(searchWidthFraction)
                .clickableWithoutRipple {
                    hasNavigatedToSearch = true
                },
            placeHolder = stringResource(R.string.feature_home_search_placeholder),
            isEnabled = false,
        )

        AnifoxIconOnSurface(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(24.dp)
                .alpha(rightIconAlpha)
                .clickableWithoutRipple { onCatalogClick() },
            imageVector = ImageVector.vectorResource(R.drawable.feature_home_browse),
            contentDescription = null,
        )
    }
}