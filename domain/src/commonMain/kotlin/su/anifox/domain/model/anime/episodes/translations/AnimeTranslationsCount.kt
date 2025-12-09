package su.anifox.domain.model.anime.episodes.translations

import androidx.compose.runtime.Immutable

@Immutable
data class AnimeTranslationsCount(
    val translation: AnimeTranslation,
    val countEpisodes: Int,
    val link: String?,
)