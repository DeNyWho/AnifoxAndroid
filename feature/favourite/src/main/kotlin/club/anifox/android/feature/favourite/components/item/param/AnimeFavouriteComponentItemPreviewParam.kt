package club.anifox.android.feature.favourite.components.item.param

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import club.anifox.android.core.uikit.param.GlobalParams
import club.anifox.android.domain.model.anime.AnimeLightFavourite
import club.anifox.android.feature.favourite.components.item.AnimeFavouriteComponentItemDefaults

internal data class AnimeFavouriteComponentItemPreviewParam(
    val thumbnailHeight: Dp = AnimeFavouriteComponentItemDefaults.Height.Small,
    val thumbnailWidth: Dp = AnimeFavouriteComponentItemDefaults.Width.Small,
    val data: AnimeLightFavourite,
    val onClick: (String) -> Unit = { },
)

internal class AnimeFavouriteComponentItemProvider :
    PreviewParameterProvider<AnimeFavouriteComponentItemPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<AnimeFavouriteComponentItemPreviewParam>
        get() = listOf(
            AnimeFavouriteComponentItemPreviewParam(
                thumbnailHeight = AnimeFavouriteComponentItemDefaults.Height.Small,
                thumbnailWidth = AnimeFavouriteComponentItemDefaults.Width.Small,
                data = GlobalParams.DataAnimeLightFavouriteSingle,
            ),
        ).asSequence()
}