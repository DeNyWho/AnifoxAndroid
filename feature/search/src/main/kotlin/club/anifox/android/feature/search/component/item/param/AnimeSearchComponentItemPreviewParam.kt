package club.anifox.android.feature.search.component.item.param

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import club.anifox.android.core.uikit.param.GlobalParams
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.feature.search.component.item.AnimeSearchComponentItemDefaults

internal data class AnimeSearchComponentItemPreviewParam(
    val thumbnailHeight: Dp,
    val thumbnailWidth: Dp,
    val data: AnimeLight,
    val onClick: (String) -> Unit = { },
)

internal class AnimeSearchComponentItemProvider:
    PreviewParameterProvider<AnimeSearchComponentItemPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<AnimeSearchComponentItemPreviewParam>
        get() = listOf(
            AnimeSearchComponentItemPreviewParam(
                thumbnailHeight = AnimeSearchComponentItemDefaults.Height.Small,
                thumbnailWidth = AnimeSearchComponentItemDefaults.Width.Small,
                data = GlobalParams.DataAnimeLightSingle,
            ),
            AnimeSearchComponentItemPreviewParam(
                thumbnailHeight = AnimeSearchComponentItemDefaults.Height.Medium,
                thumbnailWidth = AnimeSearchComponentItemDefaults.Width.Medium,
                data = GlobalParams.DataAnimeLightSingle,
            ),
            AnimeSearchComponentItemPreviewParam(
                thumbnailHeight = AnimeSearchComponentItemDefaults.Height.Large,
                thumbnailWidth = AnimeSearchComponentItemDefaults.Width.Large,
                data = GlobalParams.DataAnimeLightSingle,
            ),
        ).asSequence()
}