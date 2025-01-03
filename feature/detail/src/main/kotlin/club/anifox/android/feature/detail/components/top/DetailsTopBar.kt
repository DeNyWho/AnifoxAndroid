package club.anifox.android.feature.detail.components.top

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons.AutoMirrored.Filled
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.dialog.gallery.SwipeableImageDialog
import club.anifox.android.core.uikit.component.icon.AnifoxIconPrimary
import club.anifox.android.core.uikit.util.clickableWithoutRipple
import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.state.StateWrapper
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import me.onebone.toolbar.CollapsingToolbarScaffoldState
import me.onebone.toolbar.CollapsingToolbarScope
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import kotlin.math.roundToInt

@Composable
internal fun CollapsingToolbarScope.ContentDetailsScreenToolbar(
    contentDetailState: StateWrapper<AnimeDetail>,
    toolbarScaffoldState: CollapsingToolbarScaffoldState = rememberCollapsingToolbarScaffoldState(),
    onBackPressed: () -> Boolean,
) {
    val blockerColorGradients = listOf(
        MaterialTheme.colorScheme.background.copy(alpha = 0.9F),
        MaterialTheme.colorScheme.background.copy(alpha = 0.8F),
        MaterialTheme.colorScheme.background.copy(alpha = 0.7F),
        MaterialTheme.colorScheme.background.copy(alpha = 0.8F),
        MaterialTheme.colorScheme.background.copy(alpha = 0.9F),
        MaterialTheme.colorScheme.background,
    )

    if(contentDetailState.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .parallax(0.5f)
                .verticalScroll(rememberScrollState())
                .graphicsLayer {
                    alpha = toolbarScaffoldState.toolbarState.progress
                },
        )
    } else if(contentDetailState.data != null) {
        val data = contentDetailState.data!!
        var showImageDialog by remember { mutableStateOf(false) }

        if(showImageDialog) {
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
                .height(380.dp)
                .parallax(0.5f)
                .graphicsLayer {
                    alpha = toolbarScaffoldState.toolbarState.progress
                },
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
                        .background(MaterialTheme.colorScheme.onSurfaceVariant)
                        .width(200.dp)
                        .height(300.dp)
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
                .statusBarsPadding(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AnifoxIconPrimary(
                modifier = Modifier
                    .clickableWithoutRipple {
                        onBackPressed.invoke()
                    }
                    .size(24.dp),
                imageVector = Filled.ArrowBack,
                contentDescription = "back",
            )

            val density = LocalDensity.current
            val targetOffset = with(density) {
                -40.dp.toPx().roundToInt()
            }

            AnimatedVisibility(
                visible = toolbarScaffoldState.toolbarState.progress < 0.25,
                enter = slideInVertically(
                    initialOffsetY = { targetOffset },
                    animationSpec = tween(
                        durationMillis = 800,
                        delayMillis = 50,
                        easing = LinearOutSlowInEasing,
                    ),
                ),
                exit = slideOutVertically(
                    targetOffsetY = { targetOffset },
                    animationSpec = tween(
                        durationMillis = 800,
                        delayMillis = 50,
                        easing = LinearOutSlowInEasing,
                    ),
                ) + fadeOut(),
            ) {
                Text(
                    text = data.title,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp, end = 12.dp),
                )
            }
        }
    }
}
