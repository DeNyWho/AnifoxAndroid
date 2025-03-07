package club.anifox.android.core.uikit.component.card.video

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import club.anifox.android.core.uikit.component.card.video.param.CardVideoLandscapePreviewParam
import club.anifox.android.core.uikit.component.card.video.param.CardVideoLandscapeProvider
import club.anifox.android.core.uikit.icon.AnifoxIcons
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.domain.model.anime.videos.AnimeVideosLight
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size

/**
 * Anifox CardVideoLandscape (anime video card) [CardVideoLandscape].
 *
 * @param modifier Modifier.
 * @param data AnimeVideosLight.
 * @param thumbnailHeight The height of the anime video card.
 * @param thumbnailWidth The width of the anime video card.
 * @param onClick Will be called when the user clicks on the anime video card.
 */
@Composable
fun CardVideoLandscape(
    modifier: Modifier = Modifier,
    data: AnimeVideosLight,
    thumbnailHeight: Dp = CardVideoLandscapeDefaults.Height.Default,
    thumbnailWidth: Dp = CardVideoLandscapeDefaults.Width.Default,
    onClick: () -> Unit,
    isTypeVisible: Boolean = true,
) {
    Column(
        modifier = Modifier
            .width(thumbnailWidth),
    ) {
        Box(
            modifier = modifier
                .width(thumbnailWidth)
                .height(thumbnailHeight)
                .clip(MaterialTheme.shapes.medium)
                .clickable { onClick.invoke() }
        ) {
            Image(
                modifier = Modifier
                    .padding(start = 4.dp, top = 4.dp)
                    .zIndex(2f)
                    .align(Alignment.TopStart)
                    .size(24.dp),
                painter = painterResource(AnifoxIcons.youtube),
                contentDescription = null,
            )
            AsyncImage(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.onSurfaceVariant)
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.medium),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(data.imageUrl)
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
        Text(
            text = data.name,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
        if (isTypeVisible) {
            Text(
                text = data.type.toString(),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
        }
    }
}

@Preview
@Composable
private fun PreviewCardVideoLandscapeDefault(
    @PreviewParameter(CardVideoLandscapeProvider::class) param: CardVideoLandscapePreviewParam,
) {
    DefaultPreview {
        CardVideoLandscape(
            modifier = param.modifier,
            data = param.data,
            thumbnailHeight = param.thumbnailHeight,
            thumbnailWidth = param.thumbnailWidth,
            onClick = param.onClick,
            isTypeVisible = param.isTypeVisible,
        )
    }
}
