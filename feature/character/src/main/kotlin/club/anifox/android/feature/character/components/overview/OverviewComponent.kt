package club.anifox.android.feature.character.components.overview

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.dialog.gallery.SwipeableImageDialog
import club.anifox.android.core.uikit.util.LocalScreenInfo
import club.anifox.android.core.uikit.util.clickableWithoutRipple
import club.anifox.android.domain.model.character.full.CharacterFull
import club.anifox.android.domain.model.common.device.ScreenType
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size

@Composable
internal fun OverviewComponent(
    modifier: Modifier,
    character: CharacterFull,
) {
    val screenInfo = LocalScreenInfo.current
    var showImageDialog by remember { mutableStateOf(false) }

    val (widthImage, height) = when (screenInfo.screenType) {
        ScreenType.SMALL -> {
            Pair(
                OverviewComponentDefaults.WidthImage.Small,
                OverviewComponentDefaults.Height.Small,
            )
        }

        ScreenType.DEFAULT -> {
            Pair(
                OverviewComponentDefaults.WidthImage.Medium,
                OverviewComponentDefaults.Height.Medium,
            )
        }

        ScreenType.LARGE -> {
            Pair(
                OverviewComponentDefaults.WidthImage.Large,
                OverviewComponentDefaults.Height.Large,
            )
        }

        ScreenType.EXTRA_LARGE -> {
            Pair(
                OverviewComponentDefaults.WidthImage.ExtraLarge,
                OverviewComponentDefaults.Height.ExtraLarge,
            )
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(height),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Card(
            modifier = Modifier
                .width(widthImage)
                .fillMaxHeight()
                .clip(MaterialTheme.shapes.medium),
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 2.dp,
            ),
            border = BorderStroke(
                width = 2.dp,
                brush = Brush.linearGradient(
                    listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.onBackground,
                    )
                ),
            )
        ) {
            AsyncImage(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.onSurfaceVariant)
                    .fillMaxSize()
                    .clickableWithoutRipple {
                        showImageDialog = true
                    }
                    .clip(MaterialTheme.shapes.medium),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(character.image)
                    .crossfade(true)
                    .size(Size.ORIGINAL)
                    .build(),
                contentDescription = "Content thumbnail",
                contentScale = ContentScale.Crop,
                onError = {
                    println(it.result.throwable.message)
                },
            )
        }

        Column(
            modifier = Modifier
                .padding(top = 24.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                modifier = Modifier
                    .padding(bottom = 4.dp),
                text = character.name,
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = character.nameEn,
                style = MaterialTheme.typography.titleSmall,
            )
            if(character.nameKanji != null) {
                Text(
                    text = character.nameKanji ?: "",
                    style = MaterialTheme.typography.titleSmall,
                )
            }
        }
    }

    if (showImageDialog) {
        SwipeableImageDialog(
            images = listOf(character.image),
            initialIndex = 0,
            onDismiss = {
                showImageDialog = false
            },
        )
    }
}