package club.anifox.android.domain.model.anime.episodes

import club.anifox.android.domain.model.anime.translations.AnimeEpisodeTranslation
import java.time.LocalDate

data class AnimeEpisodesLight(
    val title: String,
    val number: Int,
    val image: String,
    val aired: LocalDate,
    val filler: Boolean,
    val recap: Boolean,
    val translation: List<AnimeEpisodeTranslation>,
)