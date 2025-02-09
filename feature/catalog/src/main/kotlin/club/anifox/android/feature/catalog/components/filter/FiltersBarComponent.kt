package club.anifox.android.feature.catalog.components.filter

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
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import club.anifox.android.core.uikit.component.icon.AnifoxIconCustomTintVector
import club.anifox.android.core.uikit.component.textfield.SearchField
import club.anifox.android.core.uikit.icon.AnifoxIcons
import club.anifox.android.core.uikit.util.LocalScreenInfo
import club.anifox.android.domain.model.anime.enum.AnimeOrder
import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeSort
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType
import club.anifox.android.domain.model.anime.genre.AnimeGenre
import club.anifox.android.domain.model.anime.studio.AnimeStudio
import club.anifox.android.domain.model.anime.translations.AnimeTranslation
import club.anifox.android.domain.model.common.device.ScreenType
import club.anifox.android.domain.model.navigation.catalog.CatalogFilterParams
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.feature.catalog.R
import club.anifox.android.feature.catalog.model.FilterType
import club.anifox.android.feature.catalog.model.state.CatalogUiState
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.SimpleColorFilter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty

@Composable
internal fun FiltersBarComponent(
    modifier: Modifier = Modifier,
    mainFilters: List<FilterType> = FilterType.entries,
    animeYears: StateListWrapper<Int>,
    animeGenres: StateListWrapper<AnimeGenre>,
    uiState: CatalogUiState,
    updateFilter: (CatalogFilterParams, FilterType) -> Unit,
    animeStudios: StateListWrapper<AnimeStudio>,
    animeTranslations: StateListWrapper<AnimeTranslation>,
) {
    val interactionSource = remember { MutableInteractionSource() }
    var drawer: FilterType? by remember { mutableStateOf(null) }
    var drawerFilters: Boolean by remember { mutableStateOf(false) }
    val screenInfo = LocalScreenInfo.current
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val iconWidth = 24.dp
    val horizontalPadding = 32.dp
    val minColumnWidth = when (screenInfo.screenType) {
        ScreenType.SMALL -> 80.dp
        ScreenType.DEFAULT -> 100.dp
        ScreenType.LARGE -> 120.dp
        ScreenType.EXTRA_LARGE -> 140.dp
    }
    val availableWidth = screenWidth - iconWidth - horizontalPadding
    val filterColumnCount = (availableWidth / minColumnWidth).toInt().coerceAtLeast(3)

    val visibleFilters = mainFilters.filter { it.isShow }.take(filterColumnCount)
    val hiddenFilters = mainFilters.filter { it.isShow }.drop(filterColumnCount)
    val hiddenActive: Boolean = hiddenFilters.any { filter ->
        when(filter) {
            FilterType.YEARS -> uiState.selectedYears != null
            FilterType.GENRE -> uiState.selectedGenres != null
            FilterType.TYPE -> uiState.selectedType != null
            FilterType.STATUS -> uiState.selectedStatus != null
            FilterType.TRANSLATION -> uiState.selectedTranslation != null
            FilterType.STUDIO -> uiState.selectedStudios != null
            FilterType.SEASON -> uiState.selectedSeason != null
            FilterType.SORT -> uiState.selectedSort != null
            FilterType.ORDER -> uiState.selectedOrder != null
        }
    }

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
                                onClick = {
                                    drawer = if (drawer != filter) {
                                        if(drawerFilters) {
                                            drawerFilters = false
                                            filter
                                        } else {
                                            filter
                                        }
                                    } else null
                                },
                            )
                            .padding(horizontal = 4.dp),
                        expand = drawer == filter,
                        filterType = filter,
                        uiState = uiState,
                    )
                }
            }

            if (hiddenFilters.isNotEmpty()) {
                AnifoxIconCustomTintVector(
                    modifier = Modifier
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            role = Role.DropdownList,
                            onClick = { drawerFilters = !drawerFilters },
                        )
                        .size(24.dp),
                    imageVector = Outlined.Tune,
                    contentDescription = null,
                    tint = if (hiddenActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                )
            }
        }

        Box {
            androidx.compose.animation.AnimatedVisibility(
                visible = drawer != null || drawerFilters,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.1f))
                        .pointerInput(Unit) {
                            detectTapGestures {
                                drawerFilters = false
                                drawer = null
                            }
                        }
                )
            }

            AnimatedDrawer(drawerFilters && drawer == null) {
                AllFiltersDraw(
                    hiddenFilters = hiddenFilters,
                    animeYears = animeYears,
                    animeGenres = animeGenres,
                    animeStudios = animeStudios,
                    animeTranslations = animeTranslations,
                    uiState = uiState,
                    updateFilter = updateFilter,
                )
            }

            for (filter in mainFilters) {
                AnimatedDrawer(drawer == filter) {
                    when(drawer) {
                        FilterType.YEARS -> YearsFilterDraw(animeYears, uiState, updateFilter)
                        FilterType.GENRE -> GenresFilterDraw(animeGenres, uiState, updateFilter)
                        FilterType.TYPE -> AnimeTypeFilterDraw(uiState, updateFilter)
                        FilterType.STATUS -> StatusFilterDraw(uiState, updateFilter)
                        FilterType.TRANSLATION -> TranslationFilterDraw(animeTranslations, uiState, updateFilter)
                        FilterType.STUDIO -> StudiosFilterDraw(animeStudios, uiState, updateFilter)
                        FilterType.SEASON -> SeasonFilterDraw(uiState, updateFilter)
                        FilterType.ORDER -> AnimeOrderFilterDraw(uiState, updateFilter)
                        FilterType.SORT -> AnimeSortFilterDraw(uiState, updateFilter)
                        null -> { }
                    }
                }
            }
        }
    }
}

@Composable
private fun AnimeTypeFilterDraw(
    uiState: CatalogUiState,
    updateFilter: (CatalogFilterParams, FilterType) -> Unit,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.CenterHorizontally),
) {
    FilterDraw(
        items = AnimeType.entries,
        selectedItem = uiState.selectedType,
        updateFilter = updateFilter,
        filterType = FilterType.TYPE,
        itemToString = { it.toString() },
        horizontalArrangement = horizontalArrangement,
    )
}

@Composable
private fun AnimeOrderFilterDraw(
    uiState: CatalogUiState,
    updateFilter: (CatalogFilterParams, FilterType) -> Unit,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.CenterHorizontally),
) {
    FilterDraw(
        items = AnimeOrder.entries,
        selectedItem = uiState.selectedOrder,
        updateFilter = updateFilter,
        filterType = FilterType.ORDER,
        itemToString = { it.toString() },
        horizontalArrangement = horizontalArrangement,
    )
}

@Composable
private fun AnimeSortFilterDraw(
    uiState: CatalogUiState,
    updateFilter: (CatalogFilterParams, FilterType) -> Unit,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.CenterHorizontally),
) {
    FilterDraw(
        items = AnimeSort.entries,
        selectedItem = uiState.selectedSort,
        updateFilter = updateFilter,
        filterType = FilterType.SORT,
        itemToString = { it.toString() },
        horizontalArrangement = horizontalArrangement,
    )
}

@Composable
private fun YearsFilterDraw(
    animeYears: StateListWrapper<Int>,
    uiState: CatalogUiState,
    updateFilter: (CatalogFilterParams, FilterType) -> Unit,
) {
    FilterDraw(
        items = animeYears.data,
        selectedItem = null,
        selectedItems = uiState.selectedYears,
        updateFilter = updateFilter,
        filterType = FilterType.YEARS,
        itemToString = { it.toString() },
        isMultiSelect = true,
    )
}

@Composable
private fun SeasonFilterDraw(
    uiState: CatalogUiState,
    updateFilter: (CatalogFilterParams, FilterType) -> Unit,
) {
    FilterDraw(
        items = AnimeSeason.entries,
        selectedItem = uiState.selectedSeason,
        updateFilter = updateFilter,
        filterType = FilterType.SEASON,
        itemToString = { it.toString() },
    )
}

@Composable
private fun StatusFilterDraw(
    uiState: CatalogUiState,
    updateFilter: (CatalogFilterParams, FilterType) -> Unit,
) {
    FilterDraw(
        items = AnimeStatus.entries,
        selectedItem = uiState.selectedStatus,
        updateFilter = updateFilter,
        filterType = FilterType.STATUS,
        itemToString = { it.toString() },
    )
}

@Composable
private fun GenresFilterDraw(
    animeGenres: StateListWrapper<AnimeGenre>,
    uiState: CatalogUiState,
    updateFilter: (CatalogFilterParams, FilterType) -> Unit,
) {
    FilterDraw(
        items = animeGenres.data,
        selectedItem = null,
        selectedItems = uiState.selectedGenres,
        updateFilter = updateFilter,
        filterType = FilterType.GENRE,
        itemToString = { it.name },
        isMultiSelect = true,
    )
}

@Composable
private fun StudiosFilterDraw(
    animeStudios: StateListWrapper<AnimeStudio>,
    uiState: CatalogUiState,
    updateFilter: (CatalogFilterParams, FilterType) -> Unit,
) {
    var searchQuery by remember { mutableStateOf("") }

    Column {
        SearchField(
            modifier = Modifier
                .padding(16.dp),
            placeHolder = stringResource(R.string.feature_catalog_filter_studio_search_placeholder),
            isEnabled = true,
            searchQuery = searchQuery,
            onSearchQueryChanged = { searchQuery = it },
        )

        val filteredStudios = animeStudios.data
            .filter { it.name.contains(searchQuery, ignoreCase = true) }
            .sortedByDescending { uiState.selectedStudios?.contains(it) ?: false }

        FilterDraw(
            items = filteredStudios,
            selectedItem = null,
            selectedItems = uiState.selectedStudios,
            updateFilter = updateFilter,
            filterType = FilterType.STUDIO,
            itemToString = { it.name },
            isMultiSelect = true,
        )
    }
}

@Composable
private fun TranslationFilterDraw(
    animeTranslations: StateListWrapper<AnimeTranslation>,
    uiState: CatalogUiState,
    updateFilter: (CatalogFilterParams, FilterType) -> Unit,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.CenterHorizontally),
) {
    FilterDraw(
        items = animeTranslations.data,
        selectedItem = uiState.selectedTranslation,
        updateFilter = updateFilter,
        filterType = FilterType.TRANSLATION,
        itemToString = { it.title },
        horizontalArrangement = horizontalArrangement,
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun <T> FilterDraw(
    items: List<T>,
    selectedItem: T?,
    updateFilter: (CatalogFilterParams, FilterType) -> Unit,
    filterType: FilterType,
    itemToString: (T) -> String,
    isMultiSelect: Boolean = false,
    selectedItems: List<T>? = null,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.CenterHorizontally),
) {
    FlowRow(
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items.forEach { item ->
            val isSelected = if (isMultiSelect) {
                selectedItems?.contains(item) ?: false
            } else {
                selectedItem == item
            }

            OptionChip(
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    role = Role.Checkbox,
                    onClick = {
                        if (isMultiSelect) {
                            val newSelection = selectedItems?.let { current ->
                                if (isSelected) current - item
                                    else if(filterType != FilterType.YEARS) current + item
                                        else if (current.size < 2) current + item
                                            else current
                            } ?: listOf(item)

                            when (filterType) {
                                FilterType.GENRE -> {
                                    val genreList: List<AnimeGenre>? = newSelection.filterIsInstance<AnimeGenre>().takeUnless { it.isEmpty() }
                                    updateFilter(
                                        CatalogFilterParams(genres = genreList),
                                        filterType
                                    )
                                }
                                FilterType.STUDIO -> {
                                    val studioList: List<AnimeStudio>? = newSelection.filterIsInstance<AnimeStudio>().takeUnless { it.isEmpty() }
                                    updateFilter(
                                        CatalogFilterParams(studios = studioList),
                                        filterType
                                    )
                                }
                                FilterType.YEARS -> {
                                    val years = newSelection.filterIsInstance<Int>().takeUnless { it.isEmpty() }
                                    if (years != null) {
                                        if (years.size == 1) {
                                            updateFilter(CatalogFilterParams(years = years), filterType)
                                        } else {
                                            val sortedYears = years.sorted()
                                            val yearRange = sortedYears.first()..sortedYears.last()
                                            updateFilter(CatalogFilterParams(years = listOf(yearRange.first, yearRange.last)), filterType)
                                        }
                                    } else {
                                        updateFilter(CatalogFilterParams(years = null), filterType)
                                    }
                                }
                                else -> {
                                    throw IllegalArgumentException("Unsupported filter type for multi-select")
                                }
                            }
                        } else {
                            val newSelection = if (isSelected) null else item
                            updateFilter(
                                when (filterType) {
                                    FilterType.TYPE -> CatalogFilterParams(type = newSelection as? AnimeType)
                                    FilterType.SEASON -> CatalogFilterParams(season = newSelection as? AnimeSeason)
                                    FilterType.STATUS -> CatalogFilterParams(status = newSelection as? AnimeStatus)
                                    FilterType.TRANSLATION -> CatalogFilterParams(translation = newSelection as? AnimeTranslation)
                                    FilterType.ORDER -> CatalogFilterParams(order = newSelection as? AnimeOrder, sort = AnimeSort.Desc)
                                    FilterType.SORT -> CatalogFilterParams(sort = newSelection as? AnimeSort)
                                    else -> throw IllegalArgumentException("Unsupported filter type")
                                },
                                filterType,
                            )
                        }
                    }
                ),
                text = itemToString(item),
                isSelected = isSelected,
            )
        }
    }
}

@Composable
private fun AllFiltersDraw(
    hiddenFilters: List<FilterType>,
    animeYears: StateListWrapper<Int>,
    animeGenres: StateListWrapper<AnimeGenre>,
    animeStudios: StateListWrapper<AnimeStudio>,
    animeTranslations: StateListWrapper<AnimeTranslation>,
    uiState: CatalogUiState,
    updateFilter: (CatalogFilterParams, FilterType) -> Unit,
) {
    LazyColumn (
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
    ) {
        items(
            hiddenFilters,
            key = { it.name },
        ) { filter ->
            Column(
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = stringResource(filter.displayTitleId),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleMedium,
                )

                when (filter) {
                    FilterType.YEARS -> YearsFilterDraw(animeYears, uiState, updateFilter)
                    FilterType.GENRE -> GenresFilterDraw(animeGenres, uiState, updateFilter)
                    FilterType.TYPE -> AnimeTypeFilterDraw(
                        uiState = uiState,
                        updateFilter = updateFilter,
                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                    )
                    FilterType.STATUS -> StatusFilterDraw(uiState, updateFilter)
                    FilterType.TRANSLATION -> TranslationFilterDraw(
                        animeTranslations = animeTranslations,
                        uiState = uiState,
                        updateFilter = updateFilter,
                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                    )
                    FilterType.STUDIO -> StudiosFilterDraw(animeStudios, uiState, updateFilter)
                    FilterType.SEASON -> SeasonFilterDraw(uiState, updateFilter)
                    FilterType.ORDER -> AnimeOrderFilterDraw(
                        uiState = uiState,
                        updateFilter = updateFilter,
                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                    )
                    FilterType.SORT -> AnimeSortFilterDraw(
                        uiState = uiState,
                        updateFilter = updateFilter,
                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                    )
                }
            }
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
    uiState: CatalogUiState,
) {
    val label: String = when(filterType) {
        FilterType.YEARS -> uiState.selectedYears?.joinToString(", ") { it.toString() } ?: stringResource(filterType.displayTitleId)
        FilterType.GENRE -> uiState.selectedGenres?.firstOrNull()?.name?.let { if (uiState.selectedGenres.size > 1) "$it..." else it }
            ?: stringResource(filterType.displayTitleId)
        FilterType.TYPE -> uiState.selectedType?.toString() ?: stringResource(filterType.displayTitleId)
        FilterType.STATUS -> uiState.selectedStatus.toString()
        FilterType.TRANSLATION -> uiState.selectedTranslation?.title ?: stringResource(filterType.displayTitleId)
        FilterType.STUDIO -> uiState.selectedStudios?.firstOrNull()?.name?.let { if (uiState.selectedStudios.size > 1) "$it..." else it }
            ?: stringResource(filterType.displayTitleId)
        FilterType.SEASON -> if(uiState.selectedSeason != null) uiState.selectedSeason.toString() else stringResource(filterType.displayTitleId)
        FilterType.ORDER -> if(uiState.selectedOrder != null) uiState.selectedOrder.toString() else stringResource(filterType.displayTitleId)
        FilterType.SORT -> if(uiState.selectedSort != null) uiState.selectedSort.toString() else stringResource(filterType.displayTitleId)
    }

    val isActive: Boolean = when(filterType) {
        FilterType.YEARS -> uiState.selectedYears != null
        FilterType.GENRE -> uiState.selectedGenres != null
        FilterType.TYPE -> uiState.selectedType != null
        FilterType.STATUS -> uiState.selectedStatus != null
        FilterType.TRANSLATION -> uiState.selectedTranslation != null
        FilterType.STUDIO -> uiState.selectedStudios != null
        FilterType.SEASON -> uiState.selectedSeason != null
        FilterType.ORDER -> uiState.selectedOrder != null
        FilterType.SORT -> uiState.selectedSort != null
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = if (isActive) label else stringResource(filterType.displayTitleId),
            color = if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
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
                value = if (isActive) SimpleColorFilter(MaterialTheme.colorScheme.primary.toArgb())
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
