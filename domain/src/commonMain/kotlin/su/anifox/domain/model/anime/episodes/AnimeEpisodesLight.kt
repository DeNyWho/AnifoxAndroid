package su.anifox.domain.model.anime.episodes

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.datetime.LocalDate
import su.anifox.domain.model.anime.episodes.translations.AnimeEpisodeTranslation

@Immutable
data class AnimeEpisodesLight(
    val title: String,
    val number: Int,
    val image: String,
    val aired: LocalDate?,
    val description: String,
    val filler: Boolean,
    val recap: Boolean,
    val translation: ImmutableList<AnimeEpisodeTranslation>,
)