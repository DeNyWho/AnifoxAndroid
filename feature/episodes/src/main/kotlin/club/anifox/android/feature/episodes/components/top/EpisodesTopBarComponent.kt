package club.anifox.android.feature.episodes.components.top

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons.AutoMirrored
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.icon.AnifoxIconCustomTintVector
import club.anifox.android.core.uikit.component.icon.AnifoxIconPrimary
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.core.uikit.util.clickableWithoutRipple
import club.anifox.android.feature.episodes.R

@Composable
internal fun EpisodesTopBarComponent(
    title: String,
    onBackPressed: (() -> Unit)? = null,
    endIcons: List<@Composable () -> Unit> = emptyList(),
    onSearchQueryChange: ((String) -> Unit)? = null,
    onSearchClose: (() -> Unit)? = null,
    tonalElevation: Dp = 0.dp,
    shadowElevation: Dp = 0.dp,
    surfaceColor: Color = MaterialTheme.colorScheme.background,
    isSearchActive: Boolean,
    searchQuery: String,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        tonalElevation = tonalElevation,
        shadowElevation = shadowElevation,
        color = surfaceColor,
    ) {
        AnimatedContent(
            targetState = isSearchActive,
            transitionSpec = {
                fadeIn(animationSpec = tween(300)) togetherWith
                    fadeOut(animationSpec = tween(300))
            },
            label = "SearchBarTransition"
        ) { isSearching ->
            if (isSearching) {
                // Поисковый режим
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 16.dp, bottom = 16.dp, end = 16.dp)
                        .statusBarsPadding(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AnifoxIconPrimary(
                        modifier = Modifier
                            .clickableWithoutRipple {
                                onSearchClose?.invoke()
                            }
                            .size(24.dp),
                        imageVector = AutoMirrored.Filled.ArrowBack,
                        contentDescription = "close search"
                    )

                    BasicTextField(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 16.dp),
                        value = searchQuery,
                        onValueChange = { newQuery ->
                            onSearchQueryChange?.invoke(newQuery)
                        },
                        textStyle = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.onBackground),
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                        singleLine = true,
                        decorationBox = { innerTextField ->
                            Box {
                                if (searchQuery.isEmpty()) {
                                    Text(
                                        text = stringResource(R.string.feature_episodes_top_bar_placeholder),
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                                    )
                                }
                                innerTextField()
                            }
                        },
                    )

                    if (searchQuery.isNotEmpty()) {
                        AnifoxIconCustomTintVector(
                            modifier = Modifier
                                .clickableWithoutRipple {
                                    onSearchQueryChange?.invoke("")
                                }
                                .size(24.dp),
                            imageVector = Filled.Clear,
                            contentDescription = "clear search",
                            tint = Color.Red,
                        )
                    }
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                        .statusBarsPadding(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (onBackPressed != null) {
                        AnifoxIconPrimary(
                            modifier = Modifier
                                .clickableWithoutRipple {
                                    onBackPressed.invoke()
                                }
                                .size(24.dp),
                            imageVector = AutoMirrored.Filled.ArrowBack,
                            contentDescription = "back",
                        )
                    }

                    Text(
                        text = title,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .weight(1f)
                            .padding(
                                start = if (onBackPressed != null) 16.dp else 0.dp,
                                end = if (endIcons.isNotEmpty()) 12.dp else 0.dp
                            ),
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        endIcons.forEach { icon ->
                            icon()
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun ExampleUsage() {
    DefaultPreview {
        EpisodesTopBarComponent(
            title = "",
            onBackPressed = { },
            endIcons = listOf(),
            onSearchQueryChange = { },
            onSearchClose = { },
            isSearchActive = true,
            searchQuery = "",
        )
    }
}