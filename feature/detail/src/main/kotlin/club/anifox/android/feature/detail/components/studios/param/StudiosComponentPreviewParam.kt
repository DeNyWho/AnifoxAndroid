package club.anifox.android.feature.detail.components.studios.param

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import club.anifox.android.core.uikit.param.GlobalParams
import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.state.StateWrapper

internal data class StudiosComponentPreviewParam(
    val detailAnime: StateWrapper<AnimeDetail>,
)

internal class StudiosComponentProvider:
    PreviewParameterProvider<StudiosComponentPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<StudiosComponentPreviewParam>
        get() = listOf(
            StudiosComponentPreviewParam(
                detailAnime = StateWrapper(data = GlobalParams.DataAnimeDetail, isLoading = false),
            )
        ).asSequence()
}