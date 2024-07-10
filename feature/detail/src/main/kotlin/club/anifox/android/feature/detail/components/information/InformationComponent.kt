package club.anifox.android.feature.detail.components.information

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import club.anifox.android.core.uikit.component.slider.SliderContentDefaults
import club.anifox.android.core.uikit.theme.AnifoxTheme
import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.state.StateWrapper
import club.anifox.android.feature.detail.components.information.param.InformationContentPreviewParam
import club.anifox.android.feature.detail.components.information.param.InformationContentProvider

@Composable
internal fun InformationComponent(
    modifier: Modifier = Modifier,
    headerModifier: Modifier = SliderContentDefaults.BottomOnly,
    detailAnimeState: StateWrapper<AnimeDetail>,
) {
    if(!detailAnimeState.isLoading) {

    }
}

@PreviewLightDark
@Composable
private fun PreviewInformationContent(
    @PreviewParameter(InformationContentProvider::class) param: InformationContentPreviewParam,
) {
    AnifoxTheme {
        InformationComponent(
            detailAnimeState = param.detailAnime,
        )
    }
}