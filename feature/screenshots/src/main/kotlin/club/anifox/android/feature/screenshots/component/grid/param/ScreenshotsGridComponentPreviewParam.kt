package club.anifox.android.feature.screenshots.component.grid.param

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import club.anifox.android.domain.state.StateListWrapper
import kotlinx.collections.immutable.toImmutableList
import java.util.UUID

internal data class ScreenshotsGridComponentPreviewParam(
    val contentState: StateListWrapper<String>,
)

private val DataSet = List(10) { UUID.randomUUID().toString() }.toImmutableList()

internal class ScreenshotsGridComponentProvider:
    PreviewParameterProvider<ScreenshotsGridComponentPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<ScreenshotsGridComponentPreviewParam>
        get() = listOf(
            ScreenshotsGridComponentPreviewParam(
                contentState = StateListWrapper.loading(),
            ),
            ScreenshotsGridComponentPreviewParam(
                contentState = StateListWrapper(data = DataSet, isLoading = false)
            ),
        ).asSequence()
}