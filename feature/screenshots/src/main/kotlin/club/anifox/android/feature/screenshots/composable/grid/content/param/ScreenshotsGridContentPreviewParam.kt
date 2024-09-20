package club.anifox.android.feature.screenshots.composable.grid.content.param

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import club.anifox.android.domain.state.StateListWrapper
import java.util.UUID

internal data class ScreenshotsGridContentPreviewParam(
    val contentState: StateListWrapper<String>,
    val onItemClick: (String) -> Unit = { },
)

private val DataSet = List(10) { UUID.randomUUID().toString() }

internal class ScreenshotsGridContentProvider:
    PreviewParameterProvider<ScreenshotsGridContentPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<ScreenshotsGridContentPreviewParam>
        get() = listOf(
            ScreenshotsGridContentPreviewParam(
                contentState = StateListWrapper.loading(),
            ),
            ScreenshotsGridContentPreviewParam(
                contentState = StateListWrapper(data = DataSet, isLoading = false)
            ),
        ).asSequence()
}