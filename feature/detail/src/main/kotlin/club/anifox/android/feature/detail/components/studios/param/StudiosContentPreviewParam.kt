package club.anifox.android.feature.detail.components.studios.param

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import club.anifox.android.core.uikit.param.GlobalParams
import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.state.StateWrapper

internal data class StudiosContentPreviewParam(
    val detailAnime: StateWrapper<AnimeDetail>,
)

internal class StudiosContentProvider:
    PreviewParameterProvider<StudiosContentPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<StudiosContentPreviewParam>
        get() = listOf(
            StudiosContentPreviewParam(
                detailAnime = StateWrapper(data = GlobalParams.DataAnimeDetail, isLoading = false),
            )
        ).asSequence()
}