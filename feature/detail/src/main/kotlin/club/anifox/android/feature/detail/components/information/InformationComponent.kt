package club.anifox.android.feature.detail.components.information

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
import club.anifox.android.core.uikit.component.slider.SliderComponentDefaults
import club.anifox.android.core.uikit.component.slider.header.SliderHeader
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.core.uikit.util.clickableWithoutRipple
import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.model.navigation.catalog.CatalogFilterParams
import club.anifox.android.domain.state.StateWrapper
import club.anifox.android.feature.detail.components.information.param.InformationComponentPreviewParam
import club.anifox.android.feature.detail.components.information.param.InformationComponentProvider

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun InformationComponent(
    modifier: Modifier = Modifier,
    headerModifier: Modifier = SliderComponentDefaults.BottomOnly,
    detailAnimeState: StateWrapper<AnimeDetail>,
    onCatalogClick: (CatalogFilterParams) -> Unit,
) {
    if(!detailAnimeState.isLoading && detailAnimeState.data != null) {
        val data = detailAnimeState.data!!

        Column(
            modifier = modifier,
        ) {
            SliderHeader(
                modifier = headerModifier,
                title = stringResource(R.string.core_uikit_header_title_information),
            )

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                AnifoxChipPrimary(
                    modifier = Modifier.clickableWithoutRipple {
                        onCatalogClick.invoke(
                            CatalogFilterParams(
                                years = listOf(data.year),
                            )
                        )
                    },
                    title = data.year.toString(),
                )
                AnifoxChipPrimary(
                    modifier = Modifier.clickableWithoutRipple {
                        onCatalogClick.invoke(
                            CatalogFilterParams(
                                season = data.season,
                            )
                        )
                    },
                    title = data.season.toString(),
                )
                AnifoxChipPrimary(
                    modifier = Modifier.clickableWithoutRipple {
                        onCatalogClick.invoke(
                            CatalogFilterParams(
                                type = data.type,
                            )
                        )
                    },
                    title = data.type.toString(),
                )
                AnifoxChipPrimary(
                    modifier = Modifier.clickableWithoutRipple {
                        onCatalogClick.invoke(
                            CatalogFilterParams(
                                status = data.status,
                            )
                        )
                    },
                    title = data.status.toString(),
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewInformationComponent(
    @PreviewParameter(InformationComponentProvider::class) param: InformationComponentPreviewParam,
) {
    DefaultPreview(true) {
        InformationComponent(
            detailAnimeState = param.detailAnime,
            onCatalogClick = { },
        )
    }
}