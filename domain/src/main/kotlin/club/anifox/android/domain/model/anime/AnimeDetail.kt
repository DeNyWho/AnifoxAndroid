package club.anifox.android.domain.model.anime

import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType
import club.anifox.android.domain.model.anime.image.AnimeImage
import java.time.LocalDate
import java.time.LocalDateTime

data class AnimeDetail(
    val url: String = "",
    val title: String = "",
    val image: AnimeImage = AnimeImage(),
    val type: AnimeType = AnimeType.Tv,
    val rating: Double? = null,
    val ratingMpa: String? = null,
    val minimalAge: Int = 0,
    val year: Int = 0,
    val status: AnimeStatus = AnimeStatus.Ongoing,
    val season: AnimeSeason = AnimeSeason.Fall,
    val episodes: Int? = null,
    val episodesAired: Int = 0,
    val nextEpisode: LocalDateTime? = null,
//    val genres: List<AnimeGenre>,
//    val studios: List<AnimeStudio>,
    val releasedOn: LocalDate? = null,
    val airedOn: LocalDate = LocalDate.now(),
    val description: String? = null,
//    val translations: List<AnimeTranslations>,
)
