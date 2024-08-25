package club.anifox.android.feature.home.composable.content.genre

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.slider.SliderContentDefaults
import club.anifox.android.core.uikit.component.slider.header.SliderHeader
import club.anifox.android.core.uikit.component.slider.header.SliderHeaderShimmer
import club.anifox.android.core.uikit.theme.AnifoxTheme
import club.anifox.android.domain.model.anime.genre.AnimeGenre
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.feature.home.composable.content.genre.item.CardGenreContentItem
import club.anifox.android.feature.home.composable.content.genre.item.showCardGenreContentItemShimmer
import club.anifox.android.feature.home.composable.content.genre.param.GenreContentPreviewParam
import club.anifox.android.feature.home.composable.content.genre.param.GenreContentProvider
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@Composable
internal fun GenreContent(
    modifier: Modifier = Modifier,
    headerModifier: Modifier = SliderContentDefaults.BottomOnly,
    headerTitle: String,
    shimmer: Shimmer = rememberShimmer(ShimmerBounds.Custom),
    genresAnime: StateListWrapper<AnimeGenre>,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(12.dp),
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
            showCardGenreContentItemShimmer(shimmer)
        } else if(genresAnime.data.isNotEmpty()) {
            items(
                genresAnime.data,
                key = { it.id },
            ) { data ->
                CardGenreContentItem(
                    genreAnime = data,
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewGenreContent(
    @PreviewParameter(GenreContentProvider::class) param: GenreContentPreviewParam
) {
    AnifoxTheme {
        GenreContent(
            genresAnime = param.genresAnime,
            headerTitle = param.headerTitle,
        )
    }
}