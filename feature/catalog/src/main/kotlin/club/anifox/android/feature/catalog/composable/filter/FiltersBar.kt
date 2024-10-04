package club.anifox.android.feature.catalog.composable.filter

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import club.anifox.android.core.uikit.component.icon.AnifoxIconPrimary
import club.anifox.android.core.uikit.icon.AnifoxIcons
import club.anifox.android.domain.model.anime.enum.AnimeType
import club.anifox.android.domain.model.anime.genre.AnimeGenre
import club.anifox.android.domain.model.navigation.catalog.CatalogFilterParams
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.feature.catalog.data.CatalogState
import club.anifox.android.feature.catalog.model.FilterType
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.SimpleColorFilter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty

@Composable
internal fun FiltersBar(
    modifier: Modifier = Modifier,
    mainFilters: List<FilterType> = listOf(
        FilterType.YEAR,
        FilterType.GENRE,
        FilterType.TYPE,
        FilterType.STATUS,
        FilterType.TRANSLATION,
    ),
    animeYears: StateListWrapper<Int>,
    animeGenres: StateListWrapper<AnimeGenre>,
    catalogState: CatalogState,
    updateFilter: (CatalogFilterParams) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    var drawer: FilterType? by remember { mutableStateOf(null) }
    val hideDrawer = { drawer = null }
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val filterColumnWidth = 100.dp
    val iconWidth = 24.dp
    val availableWidth = screenWidth - iconWidth
    val filterColumnCount = (availableWidth / filterColumnWidth).toInt().coerceAtLeast(1)

    val visibleFilters = mainFilters.filter { it.isShow }.take(filterColumnCount)
    val hiddenFilters = mainFilters.filter { it.isShow }.drop(filterColumnCount)

    Column(modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .zIndex(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            LazyRow(
                userScrollEnabled = false,
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                items(visibleFilters) { filter ->
                    CategoryHead(
                        modifier = Modifier
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null,
                                role = Role.DropdownList,
                                onClick = { drawer = if (drawer != filter) filter else null }
                            )
                            .padding(horizontal = 20.dp),
                        expand = false,
                        active = false,
                        filterType = filter,
                        label = "",
                    )
                }
            }
            AnifoxIconPrimary(
                modifier = Modifier
                    .size(24.dp),
                imageVector = Outlined.Tune,
                contentDescription = null,
            )
        }
        Box {
            androidx.compose.animation.AnimatedVisibility(
                visible = drawer != null,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.1f))
                    .pointerInput(Unit) { detectTapGestures { drawer = null } })
            }
            for (filter in mainFilters) {
                AnimatedDrawer(drawer == filter) {
                    when(drawer) {
                        FilterType.YEAR -> {
                            YearFilterDraw(
                                animeYears = animeYears,
                                catalogState = catalogState,
                                updateFilter = updateFilter,
                            )
                        }
                        FilterType.GENRE -> {
                            GenresFilterDraw(
                                animeGenres = animeGenres,
                                catalogState = catalogState,
                                updateFilter = updateFilter,
                            )
                        }
                        FilterType.TYPE -> {
                            AnimeTypeFilterDraw(
                                catalogState = catalogState,
                                updateFilter = updateFilter,
                            )
                        }
                        FilterType.STATUS -> {}
                        FilterType.TRANSLATION -> {}
                        null -> { }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AnimeTypeFilterDraw(
    catalogState: CatalogState,
    updateFilter: (CatalogFilterParams) -> Unit,
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        AnimeType.entries.forEach { type ->
            val isSelected = catalogState.type == type
            OptionChip(
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    role = Role.Checkbox,
                    onClick = {
                        updateFilter.invoke(CatalogFilterParams(type = type))
                    }
                ),
                text = type.toString(),
                isSelected = isSelected,
            )
        }
    }

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun YearFilterDraw(
    animeYears: StateListWrapper<Int>,
    catalogState: CatalogState,
    updateFilter: (CatalogFilterParams) -> Unit,
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        animeYears.data.forEach { year ->
            val isSelected = catalogState.year == year
            OptionChip(
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    role = Role.Checkbox,
                    onClick = {
                        updateFilter.invoke(CatalogFilterParams(year = year))
                    }
                ),
                text = year.toString(),
                isSelected = isSelected,
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun GenresFilterDraw(
    animeGenres: StateListWrapper<AnimeGenre>,
    catalogState: CatalogState,
    updateFilter: (CatalogFilterParams) -> Unit,
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        animeGenres.data.forEach { genre ->
            val isSelected = if (catalogState.genres != null) {
                catalogState.genres.contains(genre.id)
            } else {
                false
            }
            OptionChip(
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    role = Role.Checkbox,
                    onClick = {
                        val updatedGenres = if (isSelected) {
                            catalogState.genres?.filter { it != genre.id }
                        } else {
                            (catalogState.genres ?: emptyList()) + genre.id
                        }
                        updateFilter(CatalogFilterParams(genres = updatedGenres))
                    }
                ),
                text = genre.name,
                isSelected = isSelected,
            )
        }
    }
}

@Composable
private fun OptionChip(
    modifier: Modifier,
    text: String,
    isSelected: Boolean = false,
) {
    Box(
        modifier = modifier
            .background(
                color = if(isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(8.dp),
            )
            .padding(vertical = 4.dp, horizontal = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            color = if(isSelected) Color.White.copy(0.9f) else MaterialTheme.colorScheme.onBackground.copy(0.7f),
            style = MaterialTheme.typography.titleSmall,
        )
    }
}

@Composable
private fun AnimatedDrawer(
    visible: Boolean,
    content: @Composable () -> Unit,
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically { -it },
        exit = slideOutVertically(
            animationSpec = spring(
                stiffness = Spring.StiffnessHigh,
                visibilityThreshold = IntOffset.VisibilityThreshold
            ),
            targetOffsetY = { -it }
        )
    ) {
        Surface(
            Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.background,
        ) {
            content()
        }
    }
}

@Composable
private fun CategoryHead(
    modifier: Modifier = Modifier,
    expand: Boolean,
    active: Boolean,
    filterType: FilterType,
    label: String,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = if (active) label else stringResource(filterType.displayTitleId),
            color = if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium
        )

        val arrowUpDown by rememberLottieComposition(
            LottieCompositionSpec.RawRes(AnifoxIcons.Animated.arrowUpDown)
        )
        val animationProgress by animateFloatAsState(
            targetValue = if (expand) 1f else 0f,
            animationSpec = tween(300, easing = LinearEasing), label = ""
        )


        val arrowColor = rememberLottieDynamicProperties(
            rememberLottieDynamicProperty(
                property = LottieProperty.COLOR_FILTER,
                value = if (active) SimpleColorFilter(MaterialTheme.colorScheme.primary.toArgb())
                else SimpleColorFilter(MaterialTheme.colorScheme.onBackground.toArgb()),
                "**"
            )
        )

        LottieAnimation(
            composition = arrowUpDown,
            progress = { animationProgress },
            dynamicProperties = arrowColor
        )
    }
}
