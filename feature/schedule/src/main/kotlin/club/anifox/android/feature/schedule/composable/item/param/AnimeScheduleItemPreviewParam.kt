package club.anifox.android.feature.schedule.composable.item.param

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import club.anifox.android.core.uikit.param.GlobalParams
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.feature.schedule.composable.item.AnimeScheduleItemDefaults

internal data class AnimeScheduleItemPreviewParam(
    val thumbnailHeight: Dp = AnimeScheduleItemDefaults.Height.Small,
    val thumbnailWidth: Dp = AnimeScheduleItemDefaults.Width.Small,
    val data: AnimeLight,
    val onClick: (String) -> Unit = { },
)

internal class AnimeScheduleProvider:
    PreviewParameterProvider<AnimeScheduleItemPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<AnimeScheduleItemPreviewParam>
        get() = listOf(
            AnimeScheduleItemPreviewParam(
                thumbnailHeight = AnimeScheduleItemDefaults.Height.Small,
                thumbnailWidth = AnimeScheduleItemDefaults.Width.Small,
                data = GlobalParams.DataAnimeLightSingle,
            ),
        ).asSequence()
}