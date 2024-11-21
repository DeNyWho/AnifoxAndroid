package club.anifox.android.feature.home.composable.content.genre.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.domain.model.anime.genre.AnimeGenre
import club.anifox.android.feature.home.composable.content.genre.item.param.CardGenreContentItemPreviewParam
import club.anifox.android.feature.home.composable.content.genre.item.param.CardGenreContentItemProvider

@Composable
internal fun CardGenreContentItem(
    genreAnime: AnimeGenre,
    onItemClick: (String) -> Unit,
) {
    Card(
        modifier = Modifier.clickable {
            onItemClick.invoke(genreAnime.id)
        },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onBackground
        )
    ) {
        Text(
            modifier = Modifier.padding(28.dp),
            text = genreAnime.name,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.background,
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewCardGenreContentItem(
    @PreviewParameter(CardGenreContentItemProvider::class) param: CardGenreContentItemPreviewParam
) {
    DefaultPreview {
        CardGenreContentItem(
            genreAnime = param.genreAnime,
            onItemClick = param.onItemClick,
        )
    }
}