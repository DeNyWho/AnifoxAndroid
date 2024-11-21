package club.anifox.android.feature.detail.components.genres

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
import club.anifox.android.feature.detail.components.genres.param.GenresContentPreviewParam
import club.anifox.android.feature.detail.components.genres.param.GenresContentProvider

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun GenresContent(
    modifier: Modifier = Modifier,
    headerModifier: Modifier = SliderContentDefaults.BottomOnly,
    detailAnimeState: StateWrapper<AnimeDetail>,
    onCatalogClick: (CatalogFilterParams) -> Unit,
) {
    if(!detailAnimeState.isLoading) {
        Column(
            modifier = modifier,
        ) {
            SliderHeader(
                modifier = headerModifier,
                title = stringResource(R.string.core_uikit_header_title_genres),
            )

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                detailAnimeState.data?.genres?.forEach { genre ->
                    AnifoxChipPrimary(
                        modifier = Modifier.clickableWithoutRipple {
                            onCatalogClick.invoke(
                                CatalogFilterParams(
                                    genres = listOf(genre),
                                )
                            )
                        },
                        title = genre.name,
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewGenresContent(
    @PreviewParameter(GenresContentProvider::class) param: GenresContentPreviewParam,
) {
    DefaultPreview {
        GenresContent(
            detailAnimeState = param.detailAnime,
            onCatalogClick = { },
        )
    }
}