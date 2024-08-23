package club.anifox.android.feature.home.composable.content.genre.item

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
import club.anifox.android.core.uikit.theme.AnifoxTheme
import club.anifox.android.domain.model.anime.genre.AnimeGenre
import club.anifox.android.feature.home.composable.content.genre.item.param.GenreContentItemPreviewParam
import club.anifox.android.feature.home.composable.content.genre.item.param.GenreContentItemProvider

@Composable
internal fun GenreContentItem(genreAnime: AnimeGenre) {
    Card(
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
private fun PreviewGenreContentItem(
    @PreviewParameter(GenreContentItemProvider::class) param: GenreContentItemPreviewParam
) {
    AnifoxTheme {
        GenreContentItem(genreAnime = param.genreAnime)
    }
}