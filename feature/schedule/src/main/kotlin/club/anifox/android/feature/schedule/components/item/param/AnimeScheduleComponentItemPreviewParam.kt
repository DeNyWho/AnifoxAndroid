package club.anifox.android.feature.schedule.components.item.param

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import club.anifox.android.core.uikit.param.GlobalParams
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.feature.schedule.components.item.AnimeScheduleComponentItemDefaults

internal data class AnimeScheduleComponentItemPreviewParam(
    val thumbnailHeight: Dp = AnimeScheduleComponentItemDefaults.Height.Small,
    val thumbnailWidth: Dp = AnimeScheduleComponentItemDefaults.Width.Small,
    val data: AnimeLight,
    val onClick: (String) -> Unit = { },
)

internal class AnimeScheduleComponentProvider:
    PreviewParameterProvider<AnimeScheduleComponentItemPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<AnimeScheduleComponentItemPreviewParam>
        get() = listOf(
            AnimeScheduleComponentItemPreviewParam(
                thumbnailHeight = AnimeScheduleComponentItemDefaults.Height.Small,
                thumbnailWidth = AnimeScheduleComponentItemDefaults.Width.Small,
                data = GlobalParams.DataAnimeLightSingle,
            ),
        ).asSequence()
}