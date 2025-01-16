package club.anifox.android.feature.favourite.composable.item.param

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import club.anifox.android.core.uikit.param.GlobalParams
import club.anifox.android.domain.model.anime.AnimeLightFavourite
import club.anifox.android.feature.favourite.composable.item.AnimeFavouriteItemDefaults

internal data class AnimeFavouriteItemPreviewParam(
    val thumbnailHeight: Dp = AnimeFavouriteItemDefaults.Height.Small,
    val thumbnailWidth: Dp = AnimeFavouriteItemDefaults.Width.Small,
    val data: AnimeLightFavourite,
    val onClick: (String) -> Unit = { },
)

internal class AnimeFavouriteProvider:
    PreviewParameterProvider<AnimeFavouriteItemPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<AnimeFavouriteItemPreviewParam>
        get() = listOf(
            AnimeFavouriteItemPreviewParam(
                thumbnailHeight = AnimeFavouriteItemDefaults.Height.Small,
                thumbnailWidth = AnimeFavouriteItemDefaults.Width.Small,
                data = GlobalParams.DataAnimeLightFavouriteSingle,
            ),
        ).asSequence()
}