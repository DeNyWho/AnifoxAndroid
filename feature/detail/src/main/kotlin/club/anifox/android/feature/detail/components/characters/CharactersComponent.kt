package club.anifox.android.feature.detail.components.characters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.card.character.CardCharactersItem
import club.anifox.android.core.uikit.component.card.character.CardCharactersItemDefaults
import club.anifox.android.core.uikit.component.card.character.showCardCharactersItemShimmer
import club.anifox.android.core.uikit.component.slider.SliderComponentDefaults
import club.anifox.android.core.uikit.component.slider.header.SliderHeader
import club.anifox.android.core.uikit.component.slider.header.SliderHeaderShimmer
import club.anifox.android.core.uikit.util.onUpdateShimmerBounds
import club.anifox.android.domain.model.anime.characters.AnimeCharactersLight
import club.anifox.android.domain.state.StateListWrapper
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds.Custom
import com.valentinilk.shimmer.rememberShimmer

@Composable
internal fun CharactersComponent(
    modifier: Modifier = Modifier,
    headerModifier: Modifier = SliderComponentDefaults.BottomOnly,
    itemModifier: Modifier = Modifier,
    shimmer: Shimmer = rememberShimmer(Custom),
    thumbnailHeight: Dp = CardCharactersItemDefaults.Height.Default,
    thumbnailWidth: Dp = CardCharactersItemDefaults.Width.Default,
    headerTitle: String,
    contentState: StateListWrapper<AnimeCharactersLight>,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
    contentArrangement: Arrangement.Horizontal = CardCharactersItemDefaults.HorizontalArrangement.Default,
    onItemClick: (String) -> Unit,
    onMoreClick: () -> Unit,
) {
    // header
    if(contentState.isLoading) {
        SliderHeaderShimmer(
            modifier = headerModifier,
            shimmerInstance = shimmer,
        )
    } else if (contentState.data.isNotEmpty()) {
        SliderHeader(
            modifier = headerModifier,
            title = headerTitle,
            isMoreVisible = true,
            onMoreClick = onMoreClick,
        )
    }

    // content
    LazyRow (
        modifier = modifier.onUpdateShimmerBounds(shimmer),
        contentPadding = contentPadding,
        horizontalArrangement = contentArrangement,
    ) {
        if(contentState.isLoading) {
            showCardCharactersItemShimmer(
                modifier = itemModifier,
                shimmerInstance = shimmer,
                thumbnailHeight = thumbnailHeight,
                thumbnailWidth = thumbnailWidth,
            )
        } else if(contentState.data.isNotEmpty()) {
            items(
                contentState.data,
                key = { it.id },
            ) { character ->
                CardCharactersItem(
                    modifier = itemModifier,
                    data = character,
                    thumbnailHeight = thumbnailHeight,
                    thumbnailWidth = thumbnailWidth,
                    onClick = { onItemClick.invoke(character.id) },
                )
            }
        }
    }
}