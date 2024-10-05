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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import club.anifox.android.core.uikit.component.icon.AnifoxIconPrimary
import club.anifox.android.core.uikit.component.textfield.SearchField
import club.anifox.android.core.uikit.icon.AnifoxIcons
import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType
import club.anifox.android.domain.model.anime.genre.AnimeGenre
import club.anifox.android.domain.model.anime.studio.AnimeStudio
import club.anifox.android.domain.model.anime.translations.AnimeTranslation
import club.anifox.android.domain.model.navigation.catalog.CatalogFilterParams
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.feature.catalog.R
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
    mainFilters: List<FilterType> = FilterType.entries,
    animeYears: StateListWrapper<Int>,
    animeGenres: StateListWrapper<AnimeGenre>,
    catalogState: CatalogState,
    updateFilter: (CatalogFilterParams, FilterType) -> Unit,
    animeStudios: StateListWrapper<AnimeStudio>,
    animeTranslations: StateListWrapper<AnimeTranslation>,
) {
    val interactionSource = remember { MutableInteractionSource() }
    var drawer: FilterType? by remember { mutableStateOf(null) }
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val iconWidth = 24.dp
    val horizontalPadding = 32.dp
    val minColumnWidth = 100.dp
    val availableWidth = screenWidth - iconWidth - horizontalPadding
    val filterColumnCount = (availableWidth / minColumnWidth).toInt().coerceAtLeast(3)

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
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Start,
            ) {
                visibleFilters.forEach { filter ->
                    CategoryHead(
                        modifier = Modifier
                            .weight(1f)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null,
                                role = Role.DropdownList,
                                onClick = { drawer = if (drawer != filter) filter else null }
                            )
                            .padding(horizontal = 4.dp),
                        expand = drawer == filter,
                        filterType = filter,
                        catalogState = catalogState,
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
                        FilterType.STATUS -> {
                            StatusFilterDraw(
                                catalogState = catalogState,
                                updateFilter = updateFilter,
                            )
                        }
                        FilterType.TRANSLATION -> {
                            TranslationFilterDraw(
                                animeTranslations = animeTranslations,
                                catalogState = catalogState,
                                updateFilter = updateFilter,
                            )
                        }
                        null -> { }
                        FilterType.STUDIO -> {
                            StudiosFilterDraw(
                                animeStudios = animeStudios,
                                catalogState = catalogState,
                                updateFilter = updateFilter,
                            )
                        }
                        FilterType.SEASON -> {
                            SeasonFilterDraw(
                                catalogState = catalogState,
                                updateFilter = updateFilter,
                            )
                        }
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
    updateFilter: (CatalogFilterParams, FilterType) -> Unit,
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
                        val newType = if (isSelected) null else type
                        updateFilter.invoke(CatalogFilterParams(type = newType), FilterType.TYPE)
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
    updateFilter: (CatalogFilterParams, FilterType) -> Unit,
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
                        val newYear = if (isSelected) null else year
                        updateFilter.invoke(CatalogFilterParams(year = newYear), FilterType.YEAR)
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
private fun SeasonFilterDraw(
    catalogState: CatalogState,
    updateFilter: (CatalogFilterParams, FilterType) -> Unit,
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        AnimeSeason.entries.forEach { season ->
            val isSelected = catalogState.season == season
            OptionChip(
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    role = Role.Checkbox,
                    onClick = {
                        val newSeason = if (isSelected) null else season
                        updateFilter.invoke(CatalogFilterParams(season = newSeason), FilterType.SEASON)
                    }
                ),
                text = season.toString(),
                isSelected = isSelected,
            )
        }
    }
}

@Composable
private fun StatusFilterDraw(
    catalogState: CatalogState,
    updateFilter: (CatalogFilterParams, FilterType) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.CenterHorizontally),
    ) {
        AnimeStatus.entries.forEach { status ->
            val isSelected = catalogState.status == status
            OptionChip(
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    role = Role.Checkbox,
                    onClick = {
                        val newStatus = if (isSelected) null else status
                        updateFilter.invoke(CatalogFilterParams(status = newStatus), FilterType.STATUS)
                    }
                ),
                text = status.toString(),
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
    updateFilter: (CatalogFilterParams, FilterType) -> Unit,
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        animeGenres.data.forEach { genre ->
            val isSelected = if (catalogState.genres != null) {
                catalogState.genres.contains(genre)
            } else {
                false
            }
            OptionChip(
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    role = Role.Checkbox,
                    onClick = {
                        val updatedGenres = catalogState.genres?.let { currentGenres ->
                            if (isSelected) {
                                currentGenres - genre
                            } else {
                                currentGenres + genre
                            }
                        } ?: listOf(genre)

                        updateFilter(
                            CatalogFilterParams(
                                genres = updatedGenres.takeUnless { it.isEmpty() }
                            ),
                            FilterType.GENRE
                        )
                    }
                ),
                text = genre.name,
                isSelected = isSelected,
            )
        }
    }
}

@Composable
private fun StudiosFilterDraw(
    animeStudios: StateListWrapper<AnimeStudio>,
    catalogState: CatalogState,
    updateFilter: (CatalogFilterParams, FilterType) -> Unit,
) {
    var searchQuery by remember { mutableStateOf("") }

    Column {
        SearchField(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 24.dp),
            placeHolder = stringResource(R.string.feature_catalog_filter_studio_search_placeholder),
            isEnabled = true,
            searchQuery = searchQuery,
            onSearchQueryChanged = { searchQuery = it },
        )

        LazyVerticalGrid(
            columns = GridCells.Adaptive(100.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            val filteredStudios = animeStudios.data
                .filter { it.name.contains(searchQuery, ignoreCase = true) }
                .sortedByDescending { studio ->
                    catalogState.studios?.contains(studio) ?: false
                }

            items(filteredStudios.size) { index ->
                val studio = filteredStudios[index]
                val isSelected = catalogState.studios?.contains(studio) ?: false

                OptionChip(
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        role = Role.Checkbox,
                        onClick = {
                            val updatedStudios = catalogState.studios?.let { currentStudios ->
                                if (isSelected) {
                                    currentStudios - studio
                                } else {
                                    currentStudios + studio
                                }
                            } ?: listOf(studio)

                            updateFilter(
                                CatalogFilterParams(
                                    studio = updatedStudios.takeUnless { it.isEmpty() }
                                ),
                                FilterType.STUDIO,
                            )
                        }
                    ),
                    text = studio.name,
                    isSelected = isSelected,
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TranslationFilterDraw(
    animeTranslations: StateListWrapper<AnimeTranslation>,
    catalogState: CatalogState,
    updateFilter: (CatalogFilterParams, FilterType) -> Unit,
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        animeTranslations.data.forEach { translation ->
            val isSelected = catalogState.translation == translation
            OptionChip(
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    role = Role.Checkbox,
                    onClick = {
                        val newTranslation = if (isSelected) null else translation
                        updateFilter.invoke(
                            CatalogFilterParams(
                                translation = newTranslation
                            ),
                            FilterType.TRANSLATION,
                        )
                    }
                ),
                text = translation.title,
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
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background,
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
            Box(
                Modifier.padding(bottom = 8.dp),
                contentAlignment = Alignment.Center,
            ) {
                content()
            }
        }
    }
}

@Composable
private fun CategoryHead(
    modifier: Modifier = Modifier,
    expand: Boolean,
    filterType: FilterType,
    catalogState: CatalogState,
) {
    val label: String = when(filterType) {
        FilterType.YEAR -> if(catalogState.year != null) catalogState.year.toString() else stringResource(filterType.displayTitleId)
        FilterType.GENRE -> if(catalogState.genres != null) {
            if(catalogState.genres.size > 1) {
                "${catalogState.genres[0].name}..."
            } else {
                catalogState.genres[0].name
            }
        } else {
            stringResource(filterType.displayTitleId)
        }
        FilterType.TYPE -> if(catalogState.type != null) {
            catalogState.type.toString()
        } else {
            stringResource(filterType.displayTitleId)
        }
        FilterType.STATUS -> catalogState.status.toString()
        FilterType.TRANSLATION -> catalogState.translation?.title ?: stringResource(filterType.displayTitleId)
        FilterType.STUDIO -> if(catalogState.studios != null) {
            if(catalogState.studios.size > 1) {
                "${catalogState.studios[0].name}..."
            } else {
                catalogState.studios[0].name
            }
        } else {
            stringResource(filterType.displayTitleId)
        }
        FilterType.SEASON -> if(catalogState.season != null) catalogState.season.toString() else stringResource(filterType.displayTitleId)
    }
    val active: Boolean = when(filterType) {
        FilterType.YEAR -> catalogState.year != null
        FilterType.GENRE -> catalogState.genres != null
        FilterType.TYPE -> catalogState.type != null
        FilterType.STATUS -> catalogState.status != null
        FilterType.TRANSLATION -> catalogState.translation != null
        FilterType.STUDIO -> catalogState.studios != null
        FilterType.SEASON -> catalogState.season != null
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = if (active) label else stringResource(filterType.displayTitleId),
            color = if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
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
            modifier = Modifier.size(24.dp),
            composition = arrowUpDown,
            progress = { animationProgress },
            dynamicProperties = arrowColor
        )
    }
}
