package club.anifox.android.feature.search.composable.item.param

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import club.anifox.android.core.uikit.param.GlobalParams
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.feature.search.composable.item.AnimeSearchItemDefaults

internal data class AnimeSearchItemPreviewParam(
    val thumbnailHeight: Dp,
    val thumbnailWidth: Dp,
    val data: AnimeLight,
    val onClick: (String) -> Unit = { },
)

internal class AnimeSearchItemProvider:
    PreviewParameterProvider<AnimeSearchItemPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<AnimeSearchItemPreviewParam>
        get() = listOf(
            AnimeSearchItemPreviewParam(
                thumbnailHeight = AnimeSearchItemDefaults.Height.Small,
                thumbnailWidth = AnimeSearchItemDefaults.Width.Small,
                data = GlobalParams.DataAnimeLightSingle,
            ),
            AnimeSearchItemPreviewParam(
                thumbnailHeight = AnimeSearchItemDefaults.Height.Medium,
                thumbnailWidth = AnimeSearchItemDefaults.Width.Medium,
                data = GlobalParams.DataAnimeLightSingle,
            ),
            AnimeSearchItemPreviewParam(
                thumbnailHeight = AnimeSearchItemDefaults.Height.Large,
                thumbnailWidth = AnimeSearchItemDefaults.Width.Large,
                data = GlobalParams.DataAnimeLightSingle,
            ),
        ).asSequence()
}