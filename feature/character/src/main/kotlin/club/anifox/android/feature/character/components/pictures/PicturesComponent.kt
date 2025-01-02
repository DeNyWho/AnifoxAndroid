package club.anifox.android.feature.character.components.pictures

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.card.image.CardSimpleImageLandscape
import club.anifox.android.core.uikit.component.card.image.CardSimpleImageLandscapeDefaults
import club.anifox.android.core.uikit.component.dialog.gallery.SwipeableImageDialog
import club.anifox.android.core.uikit.component.slider.header.SliderHeader
import club.anifox.android.core.uikit.util.LocalScreenInfo
import club.anifox.android.domain.model.common.device.ScreenType
import club.anifox.android.feature.character.R

@Composable
internal fun PicturesComponent(
    pictures: List<String>,
    headerModifier: Modifier = Modifier,
    modifier: Modifier = Modifier
) {
    var selectedImageIndex by rememberSaveable { mutableStateOf<Int?>(null) }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        SliderHeader(
            modifier = headerModifier,
            title = stringResource(R.string.feature_character_pictures_header_title),
        )

        val screenInfo = LocalScreenInfo.current
        val configuration = LocalConfiguration.current
        val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

        val currentWidth = if (isPortrait) {
            screenInfo.portraitWidthDp
        } else {
            screenInfo.landscapeWidthDp
        }

        val columnWidth = when (screenInfo.screenType) {
            ScreenType.SMALL -> PicturesComponentDefaults.Width.GridSmall
            ScreenType.DEFAULT -> PicturesComponentDefaults.Width.GridMedium
            ScreenType.LARGE -> PicturesComponentDefaults.Width.GridLarge
            ScreenType.EXTRA_LARGE -> PicturesComponentDefaults.Width.GridExtraLarge
        }

        val minColumnSize = (if (isPortrait) {
            currentWidth.dp / 6
        } else {
            currentWidth.dp / 12
        }).coerceAtLeast(columnWidth)

        val numberOfColumns = (currentWidth / minColumnSize.value).toInt()
        val gridItems = pictures.chunked(numberOfColumns)

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = CardSimpleImageLandscapeDefaults.VerticalArrangement.GridPictures,
        ) {
            gridItems.forEach { rowPictures ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = CardSimpleImageLandscapeDefaults.HorizontalArrangement.GridPictures,
                ) {
                    rowPictures.forEach { imageUrl ->
                        Box(
                            modifier = Modifier
                                .weight(1f, fill = true)
                                .aspectRatio(1f)
                        ) {
                            CardSimpleImageLandscape(
                                image = imageUrl,
                                onClick = {
                                    selectedImageIndex = pictures.indexOf(imageUrl)
                                },
                                ratio = 1f,
                            )
                        }
                    }

                    if (rowPictures.size < numberOfColumns) {
                        repeat(numberOfColumns - rowPictures.size) {
                            Spacer(modifier = Modifier.weight(1f, fill = true))
                        }
                    }
                }
            }
        }

        if (selectedImageIndex != null) {
            SwipeableImageDialog(
                images = pictures,
                initialIndex = selectedImageIndex!!,
                onDismiss = { selectedImageIndex = null },
            )
        }
    }
}