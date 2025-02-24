package club.anifox.android.feature.detail.components.description

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import club.anifox.android.core.uikit.component.slider.SliderComponentDefaults
import club.anifox.android.core.uikit.component.slider.header.SliderHeader
import club.anifox.android.core.uikit.component.text.ExpandedText
import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.state.StateWrapper
import club.anifox.android.feature.detail.R

@Composable
internal fun DescriptionComponent(
    modifier: Modifier,
    detailAnimeState: StateWrapper<AnimeDetail>,
    headerModifier: Modifier = SliderComponentDefaults.BottomOnly,
    isExpanded: Boolean,
    onExpandedChanged: (Boolean) -> Unit,
) {
    val description = detailAnimeState.data?.description.orEmpty()

    if (!detailAnimeState.isLoading && description.isNotEmpty()) {
        SliderHeader(
            modifier = headerModifier,
            title = stringResource(R.string.feature_detail_section_header_title_description),
        )

        ExpandedText(
            modifier = modifier,
            text = description,
            isExpanded = isExpanded,
            onExpandedChanged = onExpandedChanged,
        )
    }
}