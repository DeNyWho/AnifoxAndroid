package su.anifox.domain.model.anime.episodes

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.datetime.LocalDateTime
import su.anifox.domain.model.anime.episodes.translations.AnimeEpisodeTranslation

@Immutable
data class AnimeEpisode(
    val id: String,
    val animeId: String,
    val number: Int,
    val title: String? = null,
    val titleEn: String? = null,
    val description: String? = null,
    val descriptionEn: String? = null,
    val image: String,
    val aired: LocalDateTime? = null,
    val duration: Int? = null,
    val filler: Boolean = false,
    val recap: Boolean = false,
    val translations: ImmutableList<AnimeEpisodeTranslation>,
)