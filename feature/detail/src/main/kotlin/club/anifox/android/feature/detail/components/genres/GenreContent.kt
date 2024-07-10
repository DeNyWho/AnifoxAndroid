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
import club.anifox.android.core.uikit.component.chip.AnifoxChipPrimary
import club.anifox.android.core.uikit.component.slider.SliderContentDefaults
import club.anifox.android.core.uikit.component.slider.header.SliderHeader
import club.anifox.android.core.uikit.theme.AnifoxTheme
import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.state.StateWrapper
import club.anifox.android.feature.detail.R
import club.anifox.android.feature.detail.components.genres.param.GenreContentPreviewParam
import club.anifox.android.feature.detail.components.genres.param.GenreContentProvider

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun GenreContent(
    modifier: Modifier = Modifier,
    headerModifier: Modifier = SliderContentDefaults.BottomOnly,
    detailAnimeState: StateWrapper<AnimeDetail>,
) {
    if(!detailAnimeState.isLoading) {
        Column (
            modifier = modifier,
        ) {
            SliderHeader(
                modifier = headerModifier,
                title = stringResource(R.string.feature_detail_section_header_title_genres),
            )

            FlowRow (
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                detailAnimeState.data?.genre?.forEach { genre ->
                    AnifoxChipPrimary(
                        title = genre.name,
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewGenreContent(
    @PreviewParameter(GenreContentProvider::class) param: GenreContentPreviewParam,
) {
    AnifoxTheme {
        GenreContent(
            detailAnimeState = param.detailAnime,
        )
    }
}