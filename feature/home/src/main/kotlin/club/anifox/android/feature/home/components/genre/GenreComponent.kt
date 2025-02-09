package club.anifox.android.feature.home.components.genre

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.slider.SliderComponentDefaults
import club.anifox.android.core.uikit.component.slider.header.SliderHeader
import club.anifox.android.core.uikit.component.slider.header.SliderHeaderShimmer
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.domain.model.anime.genre.AnimeGenre
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.feature.home.components.genre.item.CardGenreComponentItem
import club.anifox.android.feature.home.components.genre.item.showCardGenreComponentItemShimmer
import club.anifox.android.feature.home.components.genre.param.GenreComponentPreviewParam
import club.anifox.android.feature.home.components.genre.param.GenreComponentProvider
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@Composable
internal fun GenreComponent(
    modifier: Modifier = Modifier,
    headerModifier: Modifier = SliderComponentDefaults.BottomOnly,
    headerTitle: String,
    shimmer: Shimmer = rememberShimmer(ShimmerBounds.Custom),
    genresAnime: StateListWrapper<AnimeGenre>,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(12.dp),
    onItemClick: (String) -> Unit,
) {
    // header
    if(genresAnime.isLoading) {
        SliderHeaderShimmer(
            modifier = headerModifier,
            shimmerInstance = shimmer,
        )
    } else if (genresAnime.data.isNotEmpty()) {
        SliderHeader(
            modifier = headerModifier,
            title = headerTitle,
        )
    }

    LazyRow(
        modifier = modifier,
        contentPadding = contentPadding,
        horizontalArrangement = horizontalArrangement,
    ) {
        if(genresAnime.isLoading) {
            showCardGenreComponentItemShimmer(shimmer)
        } else if(genresAnime.data.isNotEmpty()) {
            items(
                genresAnime.data,
                key = { it.id },
            ) { data ->
                CardGenreComponentItem(
                    genreAnime = data,
                    onItemClick = onItemClick,
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewGenreComponent(
    @PreviewParameter(GenreComponentProvider::class) param: GenreComponentPreviewParam
) {
    DefaultPreview {
        GenreComponent(
            genresAnime = param.genresAnime,
            headerTitle = param.headerTitle,
            onItemClick = param.onItemClick,
        )
    }
}