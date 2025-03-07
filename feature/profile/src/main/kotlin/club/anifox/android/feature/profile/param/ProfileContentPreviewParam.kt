package club.anifox.android.feature.profile.param

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import club.anifox.android.feature.profile.state.ScreenState

internal data class ProfileContentPreviewParam(
    val screenState: ScreenState,
)

internal class ProfileContentProvider :
    PreviewParameterProvider<ProfileContentPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<ProfileContentPreviewParam>
        get() = listOf(
            ProfileContentPreviewParam(
                screenState = ScreenState.Authenticated,
            ),
            ProfileContentPreviewParam(
                screenState = ScreenState.NotAuthenticated,
            ),
        ).asSequence()
}