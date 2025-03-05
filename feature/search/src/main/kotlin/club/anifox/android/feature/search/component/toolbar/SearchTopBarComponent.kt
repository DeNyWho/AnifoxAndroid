package club.anifox.android.feature.search.component.toolbar

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons.AutoMirrored.Filled
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.icon.AnifoxIconOnSurface
import club.anifox.android.core.uikit.component.textfield.SearchField
import club.anifox.android.core.uikit.util.clickableWithoutRipple
import club.anifox.android.feature.search.R

@Composable
internal fun SearchTopBarComponent(
    onBackPressed: () -> Unit,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onTrailingIconClick: () -> Unit,
    focusRequester: FocusRequester,
) {
    var isAnimatingBack by remember { mutableStateOf(false) }

    val searchEndPadding by animateIntAsState(
        targetValue = if (isAnimatingBack) 32 else 0,
        animationSpec = tween(
            durationMillis = 300,
            easing = LinearOutSlowInEasing
        )
    )

    Surface (
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .fillMaxWidth()
                .statusBarsPadding(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AnifoxIconOnSurface(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(24.dp)
                    .clickableWithoutRipple {
                        isAnimatingBack = true
                        onBackPressed.invoke()
                    },
                imageVector = Filled.ArrowBack,
                contentDescription = null,
            )

            SearchField(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = searchEndPadding.dp),
                isEnabled = true,
                searchQuery = searchQuery,
                onSearchQueryChanged = onSearchQueryChanged,
                onTrailingIconClick = onTrailingIconClick,
                placeHolder = stringResource(R.string.feature_search_search_placeholder),
                focusRequester = focusRequester,
            )
        }
    }
}