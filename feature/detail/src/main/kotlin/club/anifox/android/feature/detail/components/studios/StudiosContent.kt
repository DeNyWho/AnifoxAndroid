package club.anifox.android.feature.detail.components.studios

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.R
import club.anifox.android.core.uikit.component.chip.AnifoxChipPrimary
import club.anifox.android.core.uikit.component.slider.SliderContentDefaults
import club.anifox.android.core.uikit.component.slider.header.SliderHeader
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.core.uikit.util.clickableWithoutRipple
import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.model.navigation.catalog.CatalogFilterParams
import club.anifox.android.domain.state.StateWrapper
import club.anifox.android.feature.detail.components.studios.param.StudiosContentPreviewParam
import club.anifox.android.feature.detail.components.studios.param.StudiosContentProvider

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun StudiosContent(
    modifier: Modifier = Modifier,
    headerModifier: Modifier = SliderContentDefaults.BottomOnly,
    detailAnimeState: StateWrapper<AnimeDetail>,
    onCatalogClick: (CatalogFilterParams) -> Unit,
) {
    val studios = detailAnimeState.data?.studios.orEmpty()

    if (!detailAnimeState.isLoading && studios.isNotEmpty()) {
        Column(modifier = modifier) {
            val headerTitle = if (studios.size > 1) {
                stringResource(R.string.core_uikit_header_title_studios)
            } else {
                stringResource(R.string.core_uikit_header_title_studio)
            }

            SliderHeader(
                modifier = headerModifier,
                title = headerTitle
            )

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                studios.forEach { studio ->
                    AnifoxChipPrimary(
                        modifier = Modifier.clickableWithoutRipple {
                            onCatalogClick(
                                CatalogFilterParams(
                                    studios = listOf(studio),
                                )
                            )
                        },
                        title = studio.name,
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewStudiosContent(
    @PreviewParameter(StudiosContentProvider::class) param: StudiosContentPreviewParam,
) {
    DefaultPreview {
        StudiosContent (
            detailAnimeState = param.detailAnime,
            onCatalogClick = { },
        )
    }
}