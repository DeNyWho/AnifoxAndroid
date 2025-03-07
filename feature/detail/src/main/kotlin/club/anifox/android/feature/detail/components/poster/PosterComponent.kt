package club.anifox.android.feature.detail.components.poster

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.dialog.gallery.SwipeableImageDialog
import club.anifox.android.core.uikit.util.clickableWithoutRipple
import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.state.StateWrapper
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Scale

@Composable
internal fun PosterComponent(
    detailAnimeState: StateWrapper<AnimeDetail>,
) {
    val data = detailAnimeState.data

    val blockerColorGradients = listOf(
        MaterialTheme.colorScheme.background.copy(alpha = 0.9F),
        MaterialTheme.colorScheme.background.copy(alpha = 0.8F),
        MaterialTheme.colorScheme.background.copy(alpha = 0.7F),
        MaterialTheme.colorScheme.background.copy(alpha = 0.8F),
        MaterialTheme.colorScheme.background.copy(alpha = 0.9F),
        MaterialTheme.colorScheme.background,
    )

    if (detailAnimeState.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
        )
    } else if (data != null) {
        var showImageDialog by remember { mutableStateOf(false) }

        if (showImageDialog) {
            SwipeableImageDialog(
                images = listOf(data.image.large),
                initialIndex = 0,
                onDismiss = {
                    showImageDialog = false
                },
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
        ) {
            Box {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.onSurfaceVariant)
                        .fillMaxSize(),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(data.image.large)
                        .build(),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Item poster image",
                    imageLoader = ImageLoader.Builder(LocalContext.current).build(),
                    onError = {
                        println(it.result.throwable.message)
                    },
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(colors = blockerColorGradients),
                        ),
                )
            }

            Card(
                modifier = Modifier
                    .aspectRatio(9 / 16f)
                    .padding(vertical = 20.dp)
                    .align(Alignment.Center),
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = 2.dp,
                ),
                shape = MaterialTheme.shapes.small,
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .clickableWithoutRipple {
                            showImageDialog = true
                        }
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.onSurfaceVariant)
                        .clip(MaterialTheme.shapes.small),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(data.image.large)
                        .scale(Scale.FILL)
                        .build(),
                    contentDescription = "Item vertical image",
                    contentScale = ContentScale.Crop,
                    imageLoader = ImageLoader.Builder(LocalContext.current).build(),
                    onError = {
                        println(it.result.throwable.message)
                    },
                )
            }
        }
    }
}