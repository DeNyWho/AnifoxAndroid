package su.anifox.domain.model.anime.episodes.translations

import androidx.compose.runtime.Immutable
import su.anifox.domain.model.anime.enum.TranslationType

@Immutable
data class AnimeEpisodeTranslation(
    val id: Int,
    val link: String,
    val title: String,
    val type: TranslationType
)