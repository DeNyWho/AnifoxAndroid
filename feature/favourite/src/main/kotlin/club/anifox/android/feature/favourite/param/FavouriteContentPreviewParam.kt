package club.anifox.android.feature.favourite.param

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import club.anifox.android.feature.favourite.state.ScreenState

internal data class FavouriteContentPreviewParam(
    val screenState: ScreenState,
)

internal class FavouriteContentProvider:
    PreviewParameterProvider<FavouriteContentPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<FavouriteContentPreviewParam>
        get() = listOf(
            FavouriteContentPreviewParam(
                screenState = ScreenState.Authenticated,
            ),
            FavouriteContentPreviewParam(
                screenState = ScreenState.NotAuthenticated,
            ),
        ).asSequence()
}